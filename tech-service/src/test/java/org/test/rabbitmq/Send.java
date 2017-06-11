package org.test.rabbitmq;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Send {
	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] argv) throws java.io.IOException, TimeoutException, InterruptedException {
		System.out.println("----------main method is begin----------");
		// 创建工厂
		ConnectionFactory factory = new ConnectionFactory();
		System.out.println("----------factory successful!----------");
		// 设置IP,端口,账户和密码
		factory.setHost("127.0.0.1");
		factory.setPort(5672);
//		factory.setUsername("mq");
//		factory.setPassword("mq");
		// 创建连接对象
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		System.out.println("----------connected successful!----------");

		// 指定队列持久化
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		String message = getMessage(argv);
		// 共发送10万条数据，每隔1S发送1次
		int index = 0;
		while (index < 100000) {
			Thread.sleep(1 * 100);
			channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
			index++;
		}
		channel.close();
		connection.close();
	}

	private static String getMessage(String[] strings) {
		if (strings.length < 1)
			return "Hello World!";
		return joinStrings(strings, " ");
	}

	private static String joinStrings(String[] strings, String delimiter) {
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuilder words = new StringBuilder(strings[0]);
		for (int i = 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}
}