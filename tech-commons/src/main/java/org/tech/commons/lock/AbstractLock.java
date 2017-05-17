package org.tech.commons.lock;

import java.util.concurrent.TimeUnit;

/** 
 * 锁的骨架实现, 真正的获取锁的步骤由子类去实现. 
 *  
 * @author  
 * 
 */  
public abstract class AbstractLock implements Lock {  
  
    /** 
     * <pre> 
     * 这里需不需要保证可见性值得讨论, 因为是分布式的锁,  
     * 1.同一个jvm的多个线程使用不同的锁对象其实也是可以的, 这种情况下不需要保证可见性  
     * 2.同一个jvm的多个线程使用同一个锁对象, 那可见性就必须要保证了. 
     * </pre> 
     */  
    protected volatile boolean locked;  
  
  
    public void lock() {  
        try {  
            lock(false, 0, null, false);  
        } catch (InterruptedException e) {  
            // TODO ignore  
        }  
    }  
  
    public void lockInterruptibly() throws InterruptedException {  
        lock(false, 0, null, true);  
    }  
  
    public boolean tryLock(long time, TimeUnit unit) {  
        try {  
            return lock(true, time, unit, false);  
        } catch (InterruptedException e) {  
            // TODO ignore  
        }  
        return false;  
    }  
  
    public boolean tryLockInterruptibly(long time, TimeUnit unit) throws InterruptedException {  
        return lock(true, time, unit, true);  
    }  
  
    public void unlock() {  
        unlock0();  
    }  
   
  
    protected abstract void unlock0();  
      
    /** 
     * 阻塞式获取锁的实现 
     *  
     * @param useTimeout 是否超时  
     * @param time 过去锁的(这个动作)过程中计划超时时长
     * @param unit 时间单位
     * @param interrupt 是否响应中断 
     * @return true:获取锁成功、false:获取锁失败
     * @throws InterruptedException 
     */  
    protected abstract boolean lock(boolean useTimeout, long time, TimeUnit unit, boolean interrupt) throws InterruptedException;  
  
}  