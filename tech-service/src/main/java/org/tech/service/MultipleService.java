package org.tech.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.tech.commons.lock.RedisDistributedLock;
import org.tech.commons.util.PropertiesUtil;
import org.tech.commons.util.UUIDUtil;
import org.tech.dao.DistributedIncreaseMapper;
import org.tech.dao.PeopleMapper;
import org.tech.domain.DistributedIncrease;
import org.tech.domain.People;

import redis.clients.jedis.Jedis;
/**
 * 其他的测试sevice demo
 * @author Administrator
 *
 */
@Service
public class MultipleService extends BaseService{
	
	
	@Resource
	private ThriftService thriftService;
	
	@Resource
	private ThriftService2 thriftService2;
	
	@Resource
	private JedisService jedisService;
	
	@Resource
	private DistributedIncreaseMapper distributedIncreaseMapper;
	
	@Resource
	private PeopleMapper peopleMapper; 
	
	/**
	 * 缓存测试demo
	 * @return
	 */
	public String redisSet(){
		return jedisService.set("A", "A101");
	}

	
	/**
	 * properties 测试demo
	 * @return
	 */
	public Object propertiesTest() {
		return PropertiesUtil.getProperties("redis.properties", "master_ip");
	}
	
	/**
	 * 返回1标示成功、0标示失败
	 * @return
	 */
	public Integer distributedIncrease(){
		int result = 0;
		RedisDistributedLock rd = null;
		Jedis jedis = null;
		try {
			jedis = jedisService.getJedis();
			if(jedis != null){
				rd = new RedisDistributedLock(jedis, "lockKey", 1000*3);
				if(rd.tryLock(1000*3, TimeUnit.SECONDS)){
					//这个的递增不是原子性的，需要加锁
					Long value = thriftService2.getDistributedIncrease();
					if(value != 0 && value != null){
						DistributedIncrease di = new DistributedIncrease();
						di.setId(UUIDUtil.getUUID());
						di.setValue(new Long(value).intValue());
						distributedIncreaseMapper.insertSelective(di);
						result = 1;
					}
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
		}finally {
			//释放锁
			if(rd !=null){
				rd.unlock();
				rd.release();
			}
			if(jedis != null){
				jedis.close();
			}
		}
		return result;
	}
	
	/**
	 * 测试最大支持多少客户端
	 */
	public void testGetManyJedis(int millis){
		try{
			Jedis jedis = jedisService.getJedis();
			if(jedis != null){
//				logger.info("获取成功:"+jedis);
			}
			Thread.sleep(millis);
			jedis.close();
		}catch (Exception e) {
			logger.error("获取失败");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 返回1标示成功、0标示失败
	 * @return
	 */
	public Integer thriftIncrease(){
		Integer result = null;
		//这个的递增不是原子性的，需要加锁
		Long value = thriftService.getDistributedIncrease();
		if(value != null){
			DistributedIncrease di = new DistributedIncrease();
			di.setId(UUIDUtil.getUUID());
			di.setValue(new Long(value).intValue());
			distributedIncreaseMapper.insertSelective(di);
			result = 1;
		}
		return result;
	}
	

	/**
	 * 返回1标示成功、0标示失败
	 * @return
	 */
	public Integer thriftIncrease2(){
		Integer result = null;
		//这个的递增不是原子性的，需要加锁
		Long value = thriftService2.getDistributedIncrease();
		if(value != null){
			DistributedIncrease di = new DistributedIncrease();
			di.setId(UUIDUtil.getUUID());
			di.setValue(new Long(value).intValue());
			distributedIncreaseMapper.insertSelective(di);
			result = 1;
		}
		return result;
	}
	
	/**
	 * 测试内存异常
	 */
	public void outOfMemory(){
		List list = new ArrayList<People>();
		while(true){
			list.add(peopleMapper.queryAll());
		}
	} 
	
	
}
