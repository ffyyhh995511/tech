package org.tech.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tech.domain.Teacher;
import org.tech.service.impl.TeacherService;

@RestController
@RequestMapping(value="/teacher")
public class TeacherController extends BaseController{
	
	
	@Resource
	TeacherService teacherService;
	
	@RequestMapping(value="/searchByName",method=RequestMethod.GET)
	public Map<String, Object> searchByName(String name){
		List<Teacher> searchByName = teacherService.searchByName(name);
		return responseSuccess(searchByName);
	}
}
