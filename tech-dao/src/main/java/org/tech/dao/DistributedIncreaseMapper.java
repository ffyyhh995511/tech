package org.tech.dao;

import org.tech.domain.DistributedIncrease;

public interface DistributedIncreaseMapper {
    int deleteByPrimaryKey(String id);

    int insert(DistributedIncrease record);

    int insertSelective(DistributedIncrease record);

    DistributedIncrease selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DistributedIncrease record);

    int updateByPrimaryKey(DistributedIncrease record);
}