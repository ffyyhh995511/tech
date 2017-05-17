package org.tech.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.tech.commons.OpenPage;
import org.tech.commons.util.UUIDUtil;
import org.tech.dao.PeopleMapper;
import org.tech.domain.People;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;



@Service
public class PeopleService extends BaseService{
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
	
	
}
