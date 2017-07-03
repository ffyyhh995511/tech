package org.tech.service.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 消费者--异步处理消息
 * 
 * @author fangyunhe
 *
 */
public class MQConsumer {
	private static final Logger log = Logger.getLogger(MQServer.class);

	public void doGSMWork() throws Exception {
		final Connection connection = MQServer.getConnection();
		final Channel channel = connection.createChannel();
		//消息持久化
		boolean durable = true;
		channel.queueDeclare(MQServer.GSM_QUEUE, durable, false, false, null);

		// 指定该线程同时只接收一条消息
		channel.basicQos(1);

		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				Object gsmMessage = JSON.parseObject(message, Object.class);
				log.info(" [Consumer] Received ");

				//Map<String,String> map = LockHandler.processMsgID(gsmMessage);
				Map<String,String> map = null;
				//只有服务端返回非失败才发ack
				if(!"2".equals(map.get("status"))){
					// 返回接收到消息的确认信息
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
				log.info(" [Consumer] Done");
				
			}
		};
		//打开消息应答机制
		channel.basicConsume(MQServer.GSM_QUEUE, false, consumer);
	}

}
