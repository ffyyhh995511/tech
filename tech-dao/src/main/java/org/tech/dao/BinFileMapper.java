package org.tech.dao;

import org.tech.domain.BinFile;

public interface BinFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BinFile record);

    int insertSelective(BinFile record);

    BinFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BinFile record);

    int updateByPrimaryKeyWithBLOBs(BinFile record);

    int updateByPrimaryKey(BinFile record);
}