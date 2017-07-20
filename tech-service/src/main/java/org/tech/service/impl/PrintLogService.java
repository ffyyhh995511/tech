package org.tech.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 测试打印错误日志
 * @author Administrator
 *
 */
@Service
public class PrintLogService {
	
	public boolean echoErrorLog(boolean status){
		if(status == false){
			throw new NullPointerException();
		}
		return true;
	}
}
