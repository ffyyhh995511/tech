package org.tech.service.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.stereotype.Service;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
/**
 * 集群的service
 * @author Administrator
 *
 */
@Service
public class JedisClusterService {

	private static Set<HostAndPort> jedisClusterNodes = null;
	private static GenericObjectPoolConfig poolConfig = null;
	static{
		//设置多个主结点，设置多个是为了高可用，正常一个是够了的
		jedisClusterNodes = new HashSet<HostAndPort>();
		jedisClusterNodes.add(new HostAndPort("192.168.11.162",7000));
		jedisClusterNodes.add(new HostAndPort("192.168.11.162",7001));
		jedisClusterNodes.add(new HostAndPort("192.168.11.162",7002));
		jedisClusterNodes.add(new HostAndPort("192.168.11.162",7003));
		
		poolConfig = new GenericObjectPoolConfig();		
		poolConfig.setMaxTotal(1000);
		poolConfig.setMaxIdle(100);
		poolConfig.setMinIdle(50);
	}
	
	//########################## String  ########################## 
	public String set(String key,String value) throws IOException{
		String res = null;
		JedisCluster jedisCluster = null;
		try {
			jedisCluster = new JedisCluster(jedisClusterNodes, poolConfig);
			res = jedisCluster.set(key,value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(jedisCluster != null){
				jedisCluster.close();
			}
		}
		return res;
	}
	
	public String get(String key) throws IOException{
		String res = null;
		JedisCluster jedisCluster = null;
		try {
			jedisCluster = new JedisCluster(jedisClusterNodes, poolConfig);
			res = jedisCluster.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(jedisCluster != null){
				jedisCluster.close();
			}
		}
		return res;
	}
	
	
	//########################## hash  ########################## 
	
	
	public Long hset(String key,String field,String value) throws IOException{
		Long res = null;
		JedisCluster jedisCluster = null;
		try {
			jedisCluster = new JedisCluster(jedisClusterNodes, poolConfig);
			res = jedisCluster.hset(key,field,value);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(jedisCluster != null){
				jedisCluster.close();
			}
		}
		return res;
	}
	
	public String hget(String key,String field) throws IOException{
		String res = null;
		JedisCluster jedisCluster = null;
		try {
			jedisCluster = new JedisCluster(jedisClusterNodes, poolConfig);
			res = jedisCluster.hget(key,field);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(jedisCluster != null){
				jedisCluster.close();
			}
		}
		return res;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		JedisClusterService f = new JedisClusterService();
		System.out.println(f.set("la", "la2"));
		System.out.println(f.get("la"));
		
		System.out.println(f.hset("lb","ee", "la2"));
		System.out.println(f.hget("lb","ee"));
		
	}

}
