package test.org.server.impl;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.tech.model.Hello;

/**
 * 编写客户端，调用（阻塞式IO + 多线程处理）服务
 * @author Administrator
 *
 */
public class Test1Client {
	public static void main(String[] args) throws Exception {  
        // 设置传输通道 - 普通IO流通道  
        TTransport transport = new TSocket("localhost", 7911);  
        transport.open();  
          
        //使用高密度二进制协议  
        TProtocol protocol = new TCompactProtocol(transport);  
          
        //创建Client  
        Hello.Client client = new Hello.Client(protocol);  
          
        long start = System.currentTimeMillis();  
        for(int i=0; i<10000; i++){  
            client.helloBoolean(false);  
            client.helloInt(111);  
//            client.helloNull();  
            client.helloString("dongjian");  
            client.helloVoid();  
        }  
        System.out.println("耗时：" + (System.currentTimeMillis() - start));  
          
        //关闭资源  
        transport.close();  
    }  
}
