package org.tech.service.rabbitmq;

import org.apache.log4j.Logger;
import org.tech.commons.util.PropertiesUtil;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
/**
 * 
 * @author fangyunhe
 *
 */
public class MQServer {
	private static final Logger log = Logger.getLogger(MQServer.class); 
	public final static String GSM_QUEUE = "GSM_MESSAGE";
	
	static ConnectionFactory factory = null;
	
	public static Connection getConnection () throws Exception{
		if(factory == null){
			factory = new ConnectionFactory();
		}
		factory.setHost(org.tech.commons.util.PropertiesUtil.getProperties("rabbitmq.properties", "mqHost"));
	    factory.setPort(Integer.parseInt(PropertiesUtil.getProperties("rabbitmq.properties", "mqPort")));
	    factory.setUsername(PropertiesUtil.getProperties("rabbitmq.properties", "mqUsername"));
	    factory.setPassword(PropertiesUtil.getProperties("rabbitmq.properties", "mqPassword"));
	    //virtualHost 可以理解是命名空间
	    factory.setVirtualHost(PropertiesUtil.getProperties("rabbitmq.properties", "virtualHost"));
	    Connection connection = factory.newConnection();
	    return connection;
	}
	
	/**
	 * 发送消息
	 * @param msg
	 * @return true:成功、false:失败
	 * @throws Exception
	 */
	public static boolean push (Object msg) throws Exception{
		long start = System.currentTimeMillis();
		Connection connection = null;
		Channel channel = null;
		boolean status = false;;
	    try {
	    	connection = getConnection();
	    	channel = connection.createChannel();
	    	//队列持久化
	    	boolean durable = true;
		    channel.queueDeclare(GSM_QUEUE, durable, false, false, null);
		    String message = JSON.toJSONString(msg);
		    //消息持久化
		    channel.basicPublish("", GSM_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
		    status = true;
		} catch (Exception e) {
			log.error("发送消息到消息队列失败",e);
		}finally {
			if(channel != null){
				channel.close();
			}
			if(connection != null){
				connection.close();
			}
		}
	    long end = System.currentTimeMillis();
	    log.info(Thread.currentThread().getName() +" 存储队列消耗时间: "+(end-start));
	    return status;
	}
}

