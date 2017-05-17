package org.tech.activemq;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
 
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
 
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
 
public class RequestProcessor{
	
	 Connection connection;
	
	 Session session;
	 
	static MessageConsumer consumer;
	 
	public void init() throws JMSException{
		 ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
	                ActiveMQConnection.DEFAULT_USER,
	                ActiveMQConnection.DEFAULT_PASSWORD,
	                "tcp://127.0.0.1:61616");
	        connection = connectionFactory.createConnection();
	        connection.start();
	        session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
	        Destination destination = session.createQueue("RequestQueue");
	        //消息消费（接收）者
	        consumer = session.createConsumer(destination);
	}
	
    public void requestHandler(HashMap<Serializable,Serializable> requestParam) throws Exception
    {
        System.out.println("requestHandler....."+requestParam.toString());
        for(Map.Entry<Serializable, Serializable> entry : requestParam.entrySet())
        {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
    
    public void close()
    {
        try
        {
            if(session != null)
                session.close();
            if(consumer != null)
            	consumer.close();
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
       
 
        RequestProcessor processor = new RequestProcessor();
        processor.init();
 
        while(true)
        {
            ObjectMessage message = (ObjectMessage) consumer.receive(1000);
            if(null != message)
            {	
            	System.out.println("message receive.....");
                System.out.println(message);
                HashMap<Serializable,Serializable> requestParam = (HashMap<Serializable,Serializable>) message.getObject();
                processor.requestHandler(requestParam);
            }
            else
            {
                break;
            }
        }
    }
}