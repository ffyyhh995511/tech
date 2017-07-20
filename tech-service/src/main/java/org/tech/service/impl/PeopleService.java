package org.tech.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.tech.commons.OpenPage;
import org.tech.commons.util.UUIDUtil;
import org.tech.dao.PeopleMapper;
import org.tech.domain.People;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;



@Service
public class PeopleService extends BaseService{
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource
	private PeopleMapper peopleMapper;
	
	
	public Integer add(People people){
		people.setId(UUIDUtil.getUUID());
		return peopleMapper.insert(people);
	}
	
	public Integer deleteByPrimaryKey(String id){
		return peopleMapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(People people){
		return peopleMapper.updateByPrimaryKeySelective(people);
	}
	
	public OpenPage queryPage(Integer pageNum,Integer pageSize){
		//获取第1页，10条内容，默认查询总数count
	    PageHelper.startPage(pageNum, pageSize);

	    //紧跟着的第一个select方法会被分页
	    List<People> list = peopleMapper.queryAll();
	    Page p = ((Page) list);
	    
	    return OpenPage.buildPage(p);
	}
	
	/**
	 * 结合controller测试mvc 三个层是否都是同一个thread在处理
	 * @param people
	 * @return
	 */
	public Integer addByTestOneThread(People people){
		logger.info("service thread name:"+Thread.currentThread().getName());
		logger.info("service thread id:"+Thread.currentThread().getId());
		people.setId(UUIDUtil.getUUID());
		return peopleMapper.insert(people);
	}
	
	
}
