package org.tech.controller;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tech.commons.util.UUIDUtil;
import org.tech.domain.People;
import org.tech.service.PeopleService;
import org.tech.service.PrintLogService;

@Controller
@RequestMapping(value="/oneThread")
public class AllInOneThreadController extends BaseController{
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource
	PeopleService peopleService;
	
	/**
	 * 这个方法是为了测试controller-->service-->dao是否都是同一个线程
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/test1",method=RequestMethod.GET)
	public Object test1(){
		logger.info("service thread name:"+Thread.currentThread().getName());
		logger.info("service thread id:"+Thread.currentThread().getId());
		try {
			People people = new People();
			people.setId(UUIDUtil.getUUID());
			people.setName("test one thread");
			peopleService.addByTestOneThread(people);
		} catch (Exception e) {
			logger.error("这里报错了",e);
		}
		return responseSuccess("test one thread",new Date());
	}
}
