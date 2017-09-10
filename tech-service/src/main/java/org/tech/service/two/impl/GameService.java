/**
 * 
 */
package org.tech.service.two.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.tech.commons.util.UUIDUtil;
import org.tech.two.dao.GameMapper;
import org.tech.two.domain.Game;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author fangyunhe
 * 2017年9月10日下午3:37:20
 */
@Service
public class GameService {
	@Resource
	GameMapper gameMapper;
	
	public int insert(){
		Game record = new Game();
		record.setId(UUIDUtil.getUUID());
		record.setCompany("网易");
		record.setCreateDate(new Date());
		record.setName("阴阳师");
		record.setType("IOS");
		return gameMapper.insert(record);
	}
	
	public Game selectByPrimaryKey(String id){
		return gameMapper.selectByPrimaryKey(id);
	}
}
