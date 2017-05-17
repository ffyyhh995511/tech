package test.org.server.impl;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.tech.model.Hello;


public class Test3Client {
	public static void main(String[] args) throws Exception {
		String address = "localhost";
		int port = 7911;
		int timeout = 100 * 1000;
		// 使用非阻塞方式，按块的大小进行传输，类似于Java中的NIO。记得调用close释放资源
		TTransport transport = new TFramedTransport(new TSocket(address, port, timeout));
		// 高效率的、密集的二进制编码格式进行数据传输协议
		TProtocol protocol = new TCompactProtocol(transport);
		Hello.Client client = new Hello.Client(protocol);  
		try {
			transport.open();
			System.out.println(client.helloInt(111));
			transport.close();
		} catch (TException e) {
			e.printStackTrace();
		}

	}
}
