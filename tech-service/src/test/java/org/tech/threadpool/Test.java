/**
 * 
 */
package org.tech.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**腾讯课堂在线教育问题：
 * 测试线程池线程回收
 * @author fangyunhe
 * 2017年8月29日下午11:19:51
 */
public class Test {
	public static void main(String[] args) {
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(5);
		cachedThreadPool.execute(new Thread(new Runnable() {
			public void run() {
				System.out.println("##############");
			}
		}));
	}
}
