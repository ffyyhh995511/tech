package org.tech.two.dao;

import org.tech.two.domain.Game;

public interface GameMapper {
    int deleteByPrimaryKey(String id);

    int insert(Game record);

    int insertSelective(Game record);

    Game selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Game record);

    int updateByPrimaryKey(Game record);
}