package org.tech.commons;

class TestRunnable implements Runnable {
	public void run() {
		try {
			Thread.sleep(1000000); // 这个线程将被阻塞1000秒
		} catch (InterruptedException e) {
			System.out.println("be Interrupted");
			System.out.println("be Interrupted");
			System.out.println("be Interrupted");
			System.out.println("be Interrupted");
			System.out.println("be Interrupted");
			System.out.println("be Interrupted");
			
			e.printStackTrace();
		}
	}
}