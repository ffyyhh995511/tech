package org.tech.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.tech.dao.BinFileMapper;
import org.tech.domain.BinFile;
/**
 * 大文件--图片、音视频、固件升级版已二进制存储到数据库
 * @author fangyunhe
 *
 */
@Service
public class BigFileService extends BaseService{
	@Resource
	private BinFileMapper binFileMapper; 
	
	
	public int insert(BinFile record){
		return binFileMapper.insert(record);
	}
	
	public BinFile selectByPrimaryKey(Integer id){
		return binFileMapper.selectByPrimaryKey(id);
	}
}
