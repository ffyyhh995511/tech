package org.tech.activemq;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
 
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
 
public class TopicReceive
{
    private MessageConsumer consumer;
    private Session session;
 
    public void init() throws Exception
    {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://127.0.0.1:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("fyhTopic");
        consumer = session.createConsumer(topic);
 
        consumer.setMessageListener(new MessageListener(){
            public void onMessage(Message message)
            {
                TextMessage tm = (TextMessage) message;
                System.out.println(tm);
                try
                {
                    System.out.println(tm.getText());
                }
                catch (JMSException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
 
    public static void main(String[] args) throws Exception
    {
    	System.out.println("TopicReceive");
        TopicReceive receive = new TopicReceive();
        receive.init();
    }
}