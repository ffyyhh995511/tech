package org.tech.domain.vo;

import java.util.List;

import org.tech.domain.Student;
import org.tech.domain.Teacher;
import org.tech.domain.TeacherInfo;

public class TeacherVo extends Teacher {
	
	/**
	 * 一对多关联
	 */
	private List<Student> students;
	
	/**
	 * 一对一关联
	 */
	private TeacherInfo teacherInfo;

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public TeacherInfo getTeacherInfo() {
		return teacherInfo;
	}

	public void setTeacherInfo(TeacherInfo teacherInfo) {
		this.teacherInfo = teacherInfo;
	}
	
	
}
