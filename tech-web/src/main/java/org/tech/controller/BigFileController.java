package org.tech.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tech.domain.BinFile;
import org.tech.service.BigFileService;
/**
 * 文件已二进制方式存储
 * @author fangyunhe
 *
 */
@Controller
@RequestMapping(value="/binary")
public class BigFileController extends BaseController{
	@Resource
	private BigFileService bigFileService;
	
	@ResponseBody
	@RequestMapping(value="/insert",method=RequestMethod.GET)
	public Object insert(String title){
		
		try {
			BinFile binFile = new BinFile();
			binFile.setTitle(title);
			binFile.setCreateTime(new Date());
			binFile.setNewHardwareNum("1");
			binFile.setNewSoftwareNum("1");
			binFile.setOldHardwareNum("1");
			binFile.setOldSoftwareNum("1");
			binFile.setMark("test");
			byte [] bin = getOTAFileBytes("E:/nrf51422_xxac_s130/nrf51422_xxac_s130.bin");
			binFile.setBinData(bin);
			int res = bigFileService.insert(binFile);
			if(res > 0){
				return responseSuccess("二进制文件保存成功+", binFile);
			}
		} catch (Exception e) {
			
		}
		
		return responseFail("二进制文件保存失败");
	}
	
	@ResponseBody
	@RequestMapping(value="/selectByPrimaryKey",method=RequestMethod.GET)
	public Object selectByPrimaryKey(Integer id){
		BinFile binFile = null;
		try {
			binFile = bigFileService.selectByPrimaryKey(id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return responseFail("二进制文件保存失败");
		}
		return responseSuccess("二进制文件查询成功+", binFile);
	}
	
	/**
	 * 读取升级文件二进制
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public static byte[] getOTAFileBytes(String fileName) throws Exception{
		List<Byte> list = new ArrayList<Byte>();
		File file = new File(fileName);  
        InputStream in = null;  
        //以字节为单位读取文件内容，一次读一个字节  
        in = new FileInputStream(file);  
        int tempbyte;  
        while ( (tempbyte = in.read()) != -1) {
        	list.add((byte)tempbyte);
        }
        in.close();
        
        byte binByte[] = new byte[list.size()];
        for(int i = 0 ; i < list.size() ; i++){
        	binByte[i] = list.get(i);
        }
        return binByte;
	}
	
}
