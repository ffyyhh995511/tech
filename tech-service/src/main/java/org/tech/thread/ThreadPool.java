package org.tech.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	
	private static ExecutorService fixedThreadPool = null;
	
	
	public static  void init(){
		if(fixedThreadPool == null){
			fixedThreadPool = Executors.newFixedThreadPool(4);
		}
	}
	
	public static void execute(Runnable runnable){
		init();
		fixedThreadPool.execute(runnable);
	}
	
	
	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().availableProcessors());
		 
		/*
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 10; i++) {
			final int index = i;
			fixedThreadPool.execute(new Runnable() {
				public void run() {
					try {
						System.out.println(index);
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	*/}
}
