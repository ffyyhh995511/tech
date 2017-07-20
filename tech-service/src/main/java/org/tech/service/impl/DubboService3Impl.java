package org.tech.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.tech.dubbo.service.DubboService3;
import org.springframework.stereotype.Service;
@Service
@com.alibaba.dubbo.config.annotation.Service(version="1.0.0",group="fyh",timeout=1000)
public class DubboService3Impl implements DubboService3{
	
	public Map<String,Object> test7(){
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("1", "test7");
		return hashMap;
	}
	
	public Map<String,Object> test8(){
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("1", "test8");
		return hashMap;
	}
	
	public Map<String,Object> test9(){
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("1", "test9");
		return hashMap;
	}
}
