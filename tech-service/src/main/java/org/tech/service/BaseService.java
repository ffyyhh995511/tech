package org.tech.service;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource
	protected JedisService jedisService;
	
}
