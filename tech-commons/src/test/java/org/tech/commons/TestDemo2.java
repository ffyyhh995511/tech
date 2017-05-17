package org.tech.commons;

public class TestDemo2 {
	public static void main(String[] args) {
		Runnable tr = new TestRunnable();
		Thread th1 = new Thread(tr);
		th1.start(); // 开始执行分线程
		while (true) {
			th1.interrupt(); // 中断这个分线程
		}
	}
}