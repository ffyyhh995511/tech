package org.tech.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tech.commons.OpenPage;
import org.tech.domain.People;
import org.tech.service.impl.PeopleService;
/**
 * people demo
 * 增删改查 people 表
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/people")
public class PeopleController extends BaseController{
	
	@Resource
	private PeopleService peopleService;
	
	/**
	 * 插入
	 * @param people
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Object add(People people){
		try {
			int res = peopleService.add(people);
			if(res > 0 ){
				return responseSuccess("插入成功", null);
			}
		} catch (Exception e) {
			logger.error("插入不成功",e);
			logger.error(getParameterMap());
		}
		return responseFail("插入不成功");
	}
	
	
	@ResponseBody
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Object update(People people){
		try {
			int res = peopleService.update(people);
			if(res > 0 ){
				return responseSuccess("编辑成功", null);
			}
		} catch (Exception e) {
			logger.error("编辑不成功",e);
			logger.error(getParameterMap());
		}
		return responseFail("编辑不成功");
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public Object delete(String id){
		if(StringUtils.isBlank(id)){
			return responseParamFail("参数有误");
		}
		try {
			int res = peopleService.deleteByPrimaryKey(id);
			if(res > 0 ){
				return responseSuccess("删除成功", null);
			}
		} catch (Exception e) {
			logger.error("删除不成功",e);
			logger.error(getParameterMap());
		}
		return responseFail("删除不成功");
	}
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryPage",method=RequestMethod.GET)
	public Object query(Integer pageNum,Integer pageSize){
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 :pageSize; 
		pageSize = pageSize > 500 ? 500 : pageSize;
		try {
			OpenPage page = peopleService.queryPage(pageNum, pageSize);
			return responseSuccess("成功", page);
		} catch (Exception e) {
			logger.error("查询不成功",e);
			logger.error(getParameterMap());
		}
		return responseFail("查询不成功");
	}
	
	
}
