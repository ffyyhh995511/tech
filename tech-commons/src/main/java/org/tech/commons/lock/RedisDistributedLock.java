package org.tech.commons.lock;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.tech.commons.model.ServerTime;
import redis.clients.jedis.Jedis;
/**
 * 分布式锁的实现
 * <pre>
 * 这里继承了AbstractLock，用redis方式实现
 * 如果用其他方式，例如mysql，也是继承AbstractLock，然后实现lock、unlock0、等方法即可
 * </pre>
 * @author Administrator
 *
 */
public class RedisDistributedLock extends AbstractLock {

	private Jedis jedis;

	// 锁的名字
	protected String lockKey;

	// 锁的有效时长(毫秒)
	protected long lockExpires;

	/**
	 * 
	 * @param jedis
	 * @param lockKey 锁的名称
	 * @param lockExpires  锁的过期时长
	 * @throws IOException
	 */
	public RedisDistributedLock(Jedis jedis, String lockKey, long lockExpires)
			throws IOException {
		this.jedis = jedis;
		this.lockKey = lockKey;
		this.lockExpires = lockExpires;
	}

	/**
	 * 阻塞式获取锁的实现
	 */
	protected boolean lock(boolean useTimeout, long time, TimeUnit unit, boolean interrupt)
			throws InterruptedException {
		//响应中断
		if (interrupt) {
			//如果当前线程被中断，就处理，抛出异常
			checkInterruption();
		}

		// 超时控制 
		//获取锁的动作(过程)，时间是本地（调用者）的时间
		long start = localTimeMillis();
		long timeout = unit.toMillis(time); // if !useTimeout, then it's useless

		while (useTimeout ? !isTimeout(start, timeout) : false) {
			if (interrupt) {
				checkInterruption();
			}

			long lockExpireTime = serverTimeMillis() + lockExpires + 1;// 锁超时时间
			String stringOfLockExpireTime = String.valueOf(lockExpireTime);
		
			// 获取到锁
			if (jedis.setnx(lockKey, stringOfLockExpireTime) == 1) { 
				// TODO 成功获取到锁, 设置相关标识
				locked = true;
				return true;
			}
			
			
			String value = jedis.get(lockKey);
			// lock is expired
			if (StringUtils.isNotBlank(value) && isTimeExpired(value)) { 
				// 假设多个线程(非单jvm)同时走到这里
				// getset is atomic
				String oldValue = jedis.getSet(lockKey, stringOfLockExpireTime); 
				// 但是走到这里时每个线程拿到的oldValue肯定不可能一样(因为getset是原子性的)
				// 假如拿到的oldValue依然是expired的，那么就说明拿到锁了
				if (oldValue != null && isTimeExpired(oldValue)) {
					// TODO 成功获取到锁, 设置相关标识
					locked = true;
					return true;
				}
			} else {
				// TODO lock is not expired, enter next loop retrying
			}
		}
		return false;
	}

	public boolean tryLock() {
		long lockExpireTime = serverTimeMillis() + lockExpires + 1;// 锁超时时间
		String stringOfLockExpireTime = String.valueOf(lockExpireTime);

		if (jedis.setnx(lockKey, stringOfLockExpireTime) == 1) { // 获取到锁
			// TODO 成功获取到锁, 设置相关标识
			locked = true;
			return true;
		}

		String value = jedis.get(lockKey);
		if (StringUtils.isNotBlank(value) && isTimeExpired(value)) { // lock is expired
			// 假设多个线程(非单jvm)同时走到这里
			String oldValue = jedis.getSet(lockKey, stringOfLockExpireTime); // getset is atomic
			// 但是走到这里时每个线程拿到的oldValue肯定不可能一样(因为getset是原子性的)
			// 假如拿到的oldValue依然是expired的，那么就说明拿到锁了
			if (StringUtils.isNotBlank(oldValue) && isTimeExpired(oldValue)) {
				// TODO 成功获取到锁, 设置相关标识
				locked = true;
				return true;
			}
		} else {
			// TODO lock is not expired, enter next loop retrying
		}

		return false;
	}

	/**
	 * Queries if this lock is held by any thread.
	 * 
	 * @return {@code true} if any thread holds this lock and {@code false}
	 *         otherwise
	 */
	public boolean isLocked() {
		if (locked) {
			return true;
		} else {
			String value = jedis.get(lockKey);
			// TODO 这里其实是有问题的, 想:当get方法返回value后, 假设这个value已经是过期的了,
			// 而就在这瞬间, 另一个节点set了value, 这时锁是被别的线程(节点持有), 而接下来的判断
			// 是检测不出这种情况的.不过这个问题应该不会导致其它的问题出现, 因为这个方法的目的本来就
			// 不是同步控制, 它只是一种锁状态的报告.
			return !isTimeExpired(value);
		}
	}

	/**
	 * 这个方法又各个具体（分布式锁）的实现去解除锁，这里是redisshix
	 * 其他的也可以用数据库等方法实现
	 */
	@Override
	protected void unlock0() {
		// TODO 判断锁是否过期
		if(jedis == null){
			System.out.println(" jedis is null");
		}
		if(lockKey == null){
			System.out.println(" lockKey is null");
		}
		String value = jedis.get(lockKey);
		//锁没过期就删除、过期了就不理了
		if (StringUtils.isNotBlank(value) && !isTimeExpired(value)) {
			doUnlock();
		}
	}
	
	/**
	 * 释放
	 */
	public void release() {
		jedis.close();
	}
	
	/**
	 * 删除锁
	 */
	private void doUnlock() {
		jedis.del(lockKey);
	}

	/**
	 * 判断线程是否中断
	 * @throws InterruptedException
	 */
	private void checkInterruption() throws InterruptedException {
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException();
		}
	}
	
	/**
	 * 锁是否过期
	 * @param value
	 * @return
	 */
	private boolean isTimeExpired(String value) {
		// 这里拿服务器的时间来比较
		if(Long.parseLong(value) > serverTimeMillis()){
			return false;
		}
		return true;
	}

	/**
	 * 判断获取锁这个动作调用者的超时时间
	 * @param start
	 * @param timeout
	 * @return
	 */
	private boolean isTimeout(long start, long timeout) {
		// 这里拿本地的时间来比较
		if(start + timeout > System.currentTimeMillis()){
			return false;
		}
		return true;
	}

	/**
	 * 获取服务器时间
	 * @return
	 */
	private long serverTimeMillis() {
		long time = 0;
		TTransport transport = null;
		try {
	        //设置传输通道，对于非阻塞服务，需要使用TFramedTransport，它将数据分块发送  
	        transport = new TFramedTransport(new TSocket("192.168.11.173", 7911));  
	        transport.open();  
	          
	        //使用高密度二进制协议  
	        TProtocol protocol = new TCompactProtocol(transport);  
	          
	        //创建Client  
	        ServerTime.Client client = new ServerTime.Client(protocol);  
	        time = client.getTime();
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(transport != null){
				//关闭资源  
		        transport.close(); 
			}
		}
		return time;
	}
	
	/**
	 * 本地时间
	 * @return
	 */
	private long localTimeMillis() {
		return System.currentTimeMillis();
	}

}
