package test.org.server.impl;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.tech.model.Hello;
import org.tech.model.impl.HelloImpl;

public class Test1 {
	/** 
     * 编写服务端，发布（阻塞式IO + 多线程处理）服务 
     *  
     * @param args 
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    public static void main(String[] args) {  
        try {  
            //设置传输通道，普通通道  
            TServerTransport serverTransport = new TServerSocket(7911);  
              
            //使用高密度二进制协议  
            TProtocolFactory proFactory = new TCompactProtocol.Factory();  
              
            //设置处理器HelloImpl  
            TProcessor processor = new Hello.Processor(new HelloImpl());  
              
            //创建服务器 
            
            Args args2 = new Args(serverTransport);
            args2.protocolFactory(proFactory);
            args2.processor(processor);
            TServer server = new TThreadPoolServer(args2);  
              
            System.out.println("Start server on port 7911...");  
            server.serve();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
