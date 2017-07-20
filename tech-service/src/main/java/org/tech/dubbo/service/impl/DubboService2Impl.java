package org.tech.dubbo.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.tech.dubbo.service.DubboService2;
/**
 * 模拟一个dubbo 提供者的服务
 * @author fangyunhe
 *
 */
@com.alibaba.dubbo.config.annotation.Service(version="1.0.0",group="fyh",timeout=1000)
public class DubboService2Impl implements DubboService2{
	
	public Map<String,Object> test4(){
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("1", "test4");
		return hashMap;
	}
	
	public Map<String,Object> test5(){
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("1", "test5");
		return hashMap;
	}
	
	public Map<String,Object> test6(){
		System.out.println("@PostConstruct");
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("1", "test6");
		return hashMap;
	}
}
