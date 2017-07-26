/**
 * 
 */
package org.tech.service.impl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
/**
 * 业务上rsa加解密相关操作
 * 项目的秘钥、公钥放在文件上，统一对客户端加密解密
 * @author fangyunhe
 * 2017年7月26日下午11:37:01
 */

@Service
public class RSAService {
	
	public static String PUBLIC_FILE_NAME = "rsa.pub";
	
	public static String PRIVATE_FILE_NAME = "rsa.pub";
	
	public static String PUBLIC_KEY = null;
	
	public static String PRIVATE_KEY = null;
	
	@PostConstruct
	public void init() throws Exception{
		PUBLIC_KEY = readFileByPath(this.getClass().getResource("/rsa.pub").getPath());
		PRIVATE_KEY = readFileByPath(this.getClass().getResource("/rsa.pri").getPath());
	}
	
	/**
	 * 读取公钥私钥文件内容
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String readFileByPath(String path) throws IOException{
		File file = new File(path);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
		BufferedReader publicKeybufferedReader = new BufferedReader(reader);
		String line = null;
		StringBuffer sb = new StringBuffer();
		while((line = publicKeybufferedReader.readLine()) != null){
			sb.append(line);
		}
		publicKeybufferedReader.close();
		return sb.toString();
	}
	
}
