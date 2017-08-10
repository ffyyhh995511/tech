package org.tech.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.tech.domain.Teacher;
import org.tech.domain.vo.TeacherVo;

public interface TeacherMapper {
    int deleteByPrimaryKey(String id);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

	List<Teacher> searchByName(@Param("name")String name);

	int insertBatch(List<Teacher> list);

	int deleteBatch(String array[]);

	List<TeacherVo> queryDetails();
}