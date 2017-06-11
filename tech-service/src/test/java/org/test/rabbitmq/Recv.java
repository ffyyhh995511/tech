package org.test.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Recv {
	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void recv() throws java.io.IOException, java.lang.InterruptedException, TimeoutException {
		System.out.print("----------main method is begin----------");
		ConnectionFactory factory = new ConnectionFactory();
		System.out.println("----------factory successful!----------");
		factory.setHost("127.0.0.1");
//		factory.setUsername("mq");
//		factory.setPassword("mq");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		System.out.println("----------connected successful!----------");

		// 指定队列持久化
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		// 指定该线程同时只接收一条消息
		channel.basicQos(1);

		QueueingConsumer consumer = new QueueingConsumer(channel);

		// 打开消息应答机制
		channel.basicConsume(TASK_QUEUE_NAME, false, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());

			System.out.println(" [x] Received '" + message + "'");
			doWork(message);
			System.out.println(" [x] Done");

			// 返回接收到消息的确认信息
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}

	private static void doWork(String task) throws InterruptedException {
		for (char ch : task.toCharArray()) {
			if (ch == '.')
				Thread.sleep(100);
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
		recv();
	}
}