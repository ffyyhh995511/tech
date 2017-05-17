package test.org.server.impl;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.tech.model.Hello;
/**
 * 调用非阻塞IO（NIO）服务的客户端：
 * @author Administrator
 *
 */
public class Test2Client {
	public static void main(String[] args) throws Exception {  
        //设置传输通道，对于非阻塞服务，需要使用TFramedTransport，它将数据分块发送  
        TTransport transport = new TFramedTransport(new TSocket("localhost", 7911));  
        transport.open();  
          
        //使用高密度二进制协议  
        TProtocol protocol = new TCompactProtocol(transport);  
          
        //创建Client  
        Hello.Client client = new Hello.Client(protocol);  
        System.out.println("starting...");  
        long start = System.currentTimeMillis();  
        for(int i=0; i<10000; i++){  
            client.helloBoolean(false);  
            client.helloInt(111);  
//            client.helloNull();  
            client.helloString("360buy");  
            client.helloVoid();  
        }  
        System.out.println("耗时：" + (System.currentTimeMillis() - start));  
          
        //关闭资源  
        transport.close();  
    }  
}
