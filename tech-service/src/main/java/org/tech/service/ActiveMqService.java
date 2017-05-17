package org.tech.service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class ActiveMqService {

	protected final Log logger = LogFactory.getLog(getClass());
	
    ConnectionFactory connectionFactory=null;
 
    public void init() throws Exception
    {
        //ConnectionFactory连接工厂，JMS用它创建连接；
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://127.0.0.1:61616");
    }


    /**
     * 消息放入队列,成功返回true，失败放回false
     * @param queueName
     * @param object
     * @return
     */
	public boolean sendMessage(String queueName,String text){
		boolean status = false;
		//链接
		Connection connection = null;
		 //消息发送者
	    MessageProducer producer = null;
	    //一个发送或者接受消息的线程
	    Session session = null;
		try {
			if(connectionFactory == null){
				init();
			}
			//Connection：JMS客户端到JMS Provider的连接，从构造工厂中得到连接对象
	        connection = connectionFactory.createConnection();
	        //启动
	        connection.start();
	        //获取连接操作
	        session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
	        Destination destinatin = session.createQueue(queueName);
	        //得到消息生成（发送）者
	        producer = session.createProducer(destinatin);
	        //设置不持久化
	        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	        
	        TextMessage message = session.createTextMessage(text);
	        producer.send(message);
	        session.commit();
	        status = true;
		} catch (Exception e) {
			logger.error(e);
		} finally {
			try {
				if(session != null){
					session.close();
				}
				if(producer != null){
					producer.close();
				}
				if(connection != null){
					connection.close();
				}
			} catch (JMSException e) {
				logger.error(e);
			}

		}
		return status;
	}
	
	
	/**
	 * 队列获取消息
	 * @param queueName
	 * @return
	 */
	public String receiveMessage(String queueName){
		String receiveMsg = null;
		Connection connection = null;
		Session session = null;
		MessageConsumer consumer = null;
		try {
			if(connectionFactory == null){
				init();
			}
			//Connection：JMS客户端到JMS Provider的连接，从构造工厂中得到连接对象
			connection = connectionFactory.createConnection();
	        //启动
	        connection.start();
	        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
	        Destination destination = session.createQueue(queueName);
	        //消息消费（接收）者
	        consumer = session.createConsumer(destination);
	        
	        TextMessage message = (TextMessage) consumer.receive(1000);
	        
	        receiveMsg = message.getText();
		} catch (Exception e) {
			logger.error(e);
		}finally {
			try {

				if(session != null){
					session.close();
				}
				if(consumer != null){
					consumer.close();
				}
				if(connection != null){
					connection.close();
				}
			
			} catch (Exception e2) {
				logger.error(e2);
			}
			
		}
		return receiveMsg;
	}
	
	
	/**
	 * 发送订阅
	 * @param topicStr
	 * @param msg
	 * @return
	 */
	public Boolean sendTopic(String topicStr,String msg){
		boolean status = false;
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			if(connectionFactory == null){
				init();
			}
			//从构造工厂中得到连接对象
	        connection = connectionFactory.createConnection();
	        //启动
	        connection.start();
	        //获取连接操作
	        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
	        Topic topic = session.createTopic(topicStr);
	        producer = session.createProducer(topic);
	        //设置不持久化
	        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	        
	        TextMessage message = session.createTextMessage();
	        message.setText(msg);
	        producer.send(message);
	        
		} catch (Exception e) {
			logger.error(e);
		}
		return status;
	}
	
	/**
	 * 接受active mq的topic(订阅)
	 * 通过一个消费者的监听接受推送的消息
	 * @param topicStr
	 * @throws Exception
	 */
	public void receiveTopic(String topicStr) throws Exception{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://127.0.0.1:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic(topicStr);
		MessageConsumer consumer = session.createConsumer(topic);

		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage tm = (TextMessage) message;
				System.out.println(tm);
				try {
					System.out.println(tm.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}
    
    public static void main(String[] args) {
    	ActiveMqService as = new ActiveMqService();
    	boolean status = as.sendMessage("text","lalala");
    	System.out.println(status);
    	
    	String msg = as.receiveMessage("text");
    	System.out.println(msg);
    	
	}
}
