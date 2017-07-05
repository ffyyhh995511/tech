package org.tech.service.rabbitmq;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

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
				log.info(" [Consumer] Received : "+ message);
				Map<String,String> map = null;
				//只有服务端返回非失败才发ack
				if("1".equals(map.get("status"))){
					// envelope.getDeliveryTag() 获取消息ID
					// 接口返回消息除非网络异常和接口异常，其他（参数错误、已处理等）都做确认消息动作
					// 返回接收到消息的确认信息
					channel.basicAck(envelope.getDeliveryTag(), false);
				}else if("2".equals(map.get("status"))){
					// 如果nack的requeue = false,mq把消息直接丢弃
					// message requeue = true
					channel.basicNack(envelope.getDeliveryTag(), false, true);
				}else if("3".equals(map.get("status"))){
					// 参数不合法直接丢弃
					channel.basicNack(envelope.getDeliveryTag(), false, false);
				}
				log.info(" [Consumer] Done :" + message);
				
			}
		};
		//打开消息应答机制
		channel.basicConsume(MQServer.GSM_QUEUE, false, consumer);
	}

}
