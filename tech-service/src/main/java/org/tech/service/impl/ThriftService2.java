package org.tech.service.impl;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.stereotype.Service;
import org.tech.commons.model.ServerTime;

@Service
public class ThriftService2 extends BaseService{
	
	/**
	 * 分布式递增
	 * @return
	 */
	public long getDistributedIncrease(){
		int timeout = 100 * 1000;
		TTransport transport = null;
		Long increase = 0L;
		try {
			//设置传输通道，对于非阻塞服务，需要使用TFramedTransport，它将数据分块发送  
	        transport = new TFramedTransport(new TSocket("192.168.11.173", 7911,timeout));  
	        transport.open();  
	          
	        //使用高密度二进制协议  
	        TProtocol protocol = new TCompactProtocol(transport);  
	          
	        //创建Client  
	        ServerTime.Client client = new ServerTime.Client(protocol);
	        increase = client.getIncrease();  
 
		} catch (Exception e) {
//			logger.error(e.getStackTrace());
			e.printStackTrace();
		}finally {
	        //关闭资源
			if(transport != null){
				transport.close();
			}
		}
		return increase;
	}
}
