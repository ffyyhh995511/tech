package org.tech.commons.lock;

public interface Releasable {

	/**
	 * 释放持有的所有资源
	 */
	void release();

}