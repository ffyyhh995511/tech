package org.tech.commons.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * 用BufferedReader的缓存功能读取大文件数据 实测读取3G基站数据文件，内存占用在100M左右
 * 
 * @author fangyunhe
 *
 *         2017年8月28日 下午2:59:07
 */
public class ReadBigFile {
	public void test() throws Exception {
		String filepath = "D:\\用户目录\\下载2\\cell_towers (1).csv\\cell_towers.csv";
		File file = new File(filepath);
		BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"), 5 * 1024 * 1024);// 用5M的缓冲读取文本文件
		String line = "";
		int index = 0;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			System.out.println(index++);
		}
		System.out.println(index);
		reader.close();
		fis.close();
	}
}
