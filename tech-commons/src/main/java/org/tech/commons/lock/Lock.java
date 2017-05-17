package org.tech.commons.lock;

import java.util.concurrent.TimeUnit;
/**
 * 元接口
 * <pre>
 * 响应中断,是直当前线程被其他线程中断(线程的中断是一个标示,当线程被设置位中断,这里就做响应处理)
 * t1 = new Thread()
 * t2 = new Thread()
 * t1.start();
 * t2.start();
 * t1.interrupt()
 * </pre>
 * 
 * @author Administrator
 *
 */
public interface Lock extends Releasable {

	/**
	 * 阻塞性的获取锁, 不响应中断
	 */
	void lock();

	/**
	 * 阻塞性的获取锁, 响应中断
	 * 
	 * @throws InterruptedException
	 */
	void lockInterruptibly() throws InterruptedException;

	/**
	 * 尝试获取锁, 获取不到立即返回, 不阻塞
	 */
	boolean tryLock();

	/**
	 * 超时自动返回的阻塞性的获取锁, 不响应中断
	 * 
	 * @param time
	 * @param unit
	 * @return {@code true} 若成功获取到锁, {@code false} 若在指定时间内未获取到锁
	 * 
	 */
	boolean tryLock(long time, TimeUnit unit);

	/**
	 * 超时自动返回的阻塞性的获取锁, 响应中断
	 * 
	 * @param time
	 * @param unit
	 * @return {@code true} 若成功获取到锁, {@code false} 若在指定时间内未获取到锁
	 * @throws InterruptedException
	 *             在尝试获取锁的当前线程被中断
	 */
	boolean tryLockInterruptibly(long time, TimeUnit unit) throws InterruptedException;

	/**
	 * 释放锁
	 */
	void unlock();

}