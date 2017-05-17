package org.tech.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tech.domain.People;
import org.tech.service.MultipleService;

/**
 * 多个功能的测试
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/multiple")
public class MultipleController extends BaseController{
	@Resource
	private MultipleService multipleService;
	
	/**
	 * JedisService test
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/redis",method=RequestMethod.GET)
	public Object test1(){
		return responseSuccess(null, multipleService.redisSet());
	}
	
	/**
	 * propertiesUtil 工具类测试
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/properties",method=RequestMethod.GET)
	public Object properties(){
		return responseSuccess(null, multipleService.propertiesTest());
	}
	
	/**
	 * 测试报错时打印对应的参数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/testParamsError",method=RequestMethod.GET)
	public Object testParamsError(){
		try {
			throw new NullPointerException();
		} catch (Exception e) {
			logger.error(getParameterMap(),e);
		}
		return responseFail("test");
	}
	
	
	/**
	 * 测试返回的数据有空字段时，过滤掉这些没用的空字段
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/testNullValue",method=RequestMethod.GET)
	public Object testNullValue(){
		People p = new People();
		p.setAddress("address");
		p.setAge(20);
		return responseSuccess(null, p);
	}
	
	/**
	 * Distributed测试
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/testDistributed",method=RequestMethod.GET)
	public Object testDistributed(){
		multipleService.distributedIncrease();
		return responseSuccess("OK", null);
	}
	
	/**
	 * 测试并发支持多少客户端
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/testGetManyJedis",method=RequestMethod.GET)
	public Object testGetManyJedis(){
		multipleService.testGetManyJedis(5000);
		return responseSuccess("OK", null);
	}
	
	
	
	/**
	 * testRPC测试1
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/testRPC",method=RequestMethod.GET)
	public Object testRPC(){
		Integer result = multipleService.thriftIncrease();
		if(result == 1){
			logger.info("thriftIncrease result is:"+result);
			return responseSuccess(null, null);
		}
		return responseFail(null);
	}
	
	/**
	 * testRPC测试2
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/testRPC2",method=RequestMethod.GET)
	public Object testRPC2(){
		Integer result = multipleService.thriftIncrease2();
		if(result == 1){
			logger.info("thriftIncrease result is:"+result);
			return responseSuccess(null, null);
		}
		return responseFail(null);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/testOutOfMemory",method=RequestMethod.GET)
	public Object testOutOfMemory(){
		try {
			//test-for-commit-git
			multipleService.outOfMemory();
		} catch (Exception e) {
			return responseFail(e.getMessage());
		}
		return responseSuccess("success", null);
	}
}
