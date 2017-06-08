package org.test.tutorials.rabbitmq;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessageSend {

  private final static String QUEUE_NAME = "hello";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    Message msg = new Message();
    msg.setName("first name");
    msg.setAddress("first Address");
    //对象转JSON串  
    String json = JSON.toJSONString(msg);  
    
    channel.basicPublish("", QUEUE_NAME, null, json.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + json + "'");

    channel.close();
    connection.close();
  }
}