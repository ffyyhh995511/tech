package org.tech.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.tech.dao.TeacherMapper;
import org.tech.domain.Teacher;


@Service
public class TeacherService {
	
	@Resource
	TeacherMapper teacherMapper;
	
	/**
	 * 测试使用mybitas的参数，不用之前的对象作为参数查询
	 * @param name
	 * @return
	 */
	public List<Teacher> searchByName(String name){
		return teacherMapper.searchByName(name);
	}
	
}
