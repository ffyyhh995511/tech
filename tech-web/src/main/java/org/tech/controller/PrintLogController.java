package org.tech.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tech.service.PrintLogService;

@Controller
@RequestMapping(value="/error")
public class PrintLogController extends BaseController{
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource
	PrintLogService printLogService;
	
	@ResponseBody
	@RequestMapping(value="/test1",method=RequestMethod.GET)
	public Object test1(){
		try {
			printLogService.echoErrorLog(false);
		} catch (Exception e) {
			logger.error("这里报错了",e);
		}
		return responseSuccess("test",new Date());
	}
}
