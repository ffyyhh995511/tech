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
		logger.info("controller thread name:"+Thread.currentThread().getName());
		logger.info("controller thread id:"+Thread.currentThread().getId());
		logger.info("controller activeCount:"+Thread.activeCount());
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
	
	/**
	 * 当前容器的线程池大小测试
	 * 
	 * 测试结果：jetty默认线程池大小200，tomcat也是200
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/test2",method=RequestMethod.GET)
	public Object test2(){
		Thread ta[] = new Thread[Thread.activeCount()];   
	    Thread.enumerate(ta);   
	    System.out.println("Active thread's number:"+ta.length);   
	    for(Thread t:ta){   
	        if(t!=null)   
	        System.out.println("Thread's name:"+t.getName()+" id:"+t.getId());  
	    }
	    return responseSuccess("test one thread",new Date());
	}
	 
}
