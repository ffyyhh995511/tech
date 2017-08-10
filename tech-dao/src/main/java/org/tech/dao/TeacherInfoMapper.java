package org.tech.dao;

import org.tech.domain.TeacherInfo;

public interface TeacherInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(TeacherInfo record);

    int insertSelective(TeacherInfo record);

    TeacherInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TeacherInfo record);

    int updateByPrimaryKey(TeacherInfo record);
}