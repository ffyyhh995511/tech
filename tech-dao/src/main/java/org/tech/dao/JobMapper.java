package org.tech.dao;

import org.tech.domain.Job;

public interface JobMapper {
    int deleteByPrimaryKey(String id);

    int insert(Job record);

    int insertSelective(Job record);

    Job selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Job record);

    int updateByPrimaryKey(Job record);
}