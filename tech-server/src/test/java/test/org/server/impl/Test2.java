package test.org.server.impl;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportFactory;
import org.tech.model.Hello;
import org.tech.model.impl.HelloImpl;

/**
 * 基于非阻塞IO（NIO）的服务端
 * @author Administrator
 *
 */
public class Test2 {
	public static void main(String[] args) {  
        try {  
            //传输通道 - 非阻塞方式  
            TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(7911);  
              
            //异步IO，需要使用TFramedTransport，它将分块缓存读取。  
            TTransportFactory transportFactory = new TFramedTransport.Factory();  
              
            //使用高密度二进制协议  
            TProtocolFactory proFactory = new TCompactProtocol.Factory();  
              
            //设置处理器 HelloImpl  
            TProcessor processor = new Hello.Processor(new HelloImpl());  
              
            //创建服务器 
            
            Args tArgs = new Args(serverTransport);  
            tArgs.transportFactory(transportFactory);
            tArgs.protocolFactory(proFactory);  
            tArgs.processor(processor);  
            TServer server = new TThreadedSelectorServer(tArgs);  
             
              
            System.out.println("Start server on port 7911...");  
            server.serve();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
