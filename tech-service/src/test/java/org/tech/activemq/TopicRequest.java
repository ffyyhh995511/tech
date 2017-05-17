package org.tech.activemq;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
 
public class TopicRequest
{
    //消息发送者
    private MessageProducer producer;
    //一个发送或者接受消息的线程
    private Session session;
    //Connection：JMS客户端到JMS Provider的连接
    private Connection connection;
 
    public void init() throws Exception
    {
        //ConnectionFactory连接工厂，JMS用它创建连接
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://127.0.0.1:61616");
        //从构造工厂中得到连接对象
        connection = connectionFactory.createConnection();
        //启动
        connection.start();
        //获取连接操作
        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("fyhTopic");
        producer = session.createProducer(topic);
        //设置不持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }
 
    public void submit(String mess) throws Exception
    {
        TextMessage message = session.createTextMessage();
        message.setText(mess);
        producer.send(message);
    }
 
    public void close()
    {
        try
        {
            if(session != null)
                session.close();
            if(producer != null)
                producer.close();
            if(connection !=null )
                connection.close();
        }
        catch (JMSException e)
        {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) throws Exception
    {
    	System.out.println("ing");
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.init();
        topicRequest.submit("I'm first 2");
        topicRequest.close();
    }
}
