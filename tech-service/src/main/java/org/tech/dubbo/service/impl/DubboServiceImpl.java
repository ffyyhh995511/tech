package org.tech.dubbo.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.tech.dubbo.service.DubboService;
/**
 * 模拟一个dubbo 提供者的服务
 * @author fangyunhe
 *
 */
@com.alibaba.dubbo.config.annotation.Service(version="1.0.0",group="fyh",timeout=1000)
public class DubboServiceImpl implements DubboService{
	
	public Map<String,Object> test1(){
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("1", "test1");
		return hashMap;
	}
	
	public Map<String,Object> test2(){
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("1", "test2");
		return hashMap;
	}
	
	public Map<String,Object> test3(){
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("1", "test3");
		return hashMap;
	}
}
