package org.tech.server.impl;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportFactory;
import org.tech.model.Hello;
import org.tech.model.impl.HelloImpl;


public class ThriftDemo {
	/**
	 * 基于非阻塞IO（NIO）的服务端
	 */
	public void nioServer(){
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
	
	/**
	 * 编写客户端，调用（阻塞式IO + 多线程处理）服务
	 */
	public void nioClient(){
		try {
			  
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
//	            client.helloNull();  
	            client.helloString("360buy");  
	            client.helloVoid();  
	        }  
	        System.out.println("耗时：" + (System.currentTimeMillis() - start));  
	          
	        //关闭资源  
	        transport.close();  
	    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
