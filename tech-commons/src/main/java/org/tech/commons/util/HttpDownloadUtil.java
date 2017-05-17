package org.tech.commons.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

/**
 * 说明
 * 利用httpclient下载文件
 * maven依赖
 * 		<dependency>
*			<groupId>org.apache.httpcomponents</groupId>
*			<artifactId>httpclient</artifactId>
*			<version>4.0.1</version>
*		</dependency>
*  可下载http文件、图片、压缩文件
*  bug：获取response header中Content-Disposition中filename中文乱码问题
 * @author tanjundong
 *
 */
//public class HttpDownloadUtil {
//	
//	public static final int cache = 1024 * 1024;
//	public static final boolean isWindows;
//	public static final String root;
//	static {
//		if (System.getProperty("os.name") != null && System.getProperty("os.name").toLowerCase().contains("windows")) {
//			isWindows = true;
//			root="D:";
//		} else {
//			isWindows = false;
//			root="/search";
//		}
//	}
//	
//	/**
//	 * 根据url下载文件，文件名从response header头中获取
//	 * @param url
//	 * @return
//	 */
//	public static String download(String url) {
//		return download(url, null);
//	}
//
//	/**
//	 * 根据url下载文件，保存到filepath中
//	 * @param url
//	 * @param filepath
//	 * @return
//	 */
//	public static String download(String url, String filepath) {
//		try {
//			HttpClient client = new DefaultHttpClient();
//			HttpGet httpget = new HttpGet(url);
//			HttpResponse response = client.execute(httpget);
//
//			HttpEntity entity = response.getEntity();
//			InputStream is = entity.getContent();
//			if (filepath == null)
//				filepath = getFilePath(url);
//			File file = new File(filepath);
//			file.getParentFile().mkdirs();
//			FileOutputStream fileout = new FileOutputStream(file);
//			/**
//			 * 根据实际运行效果 设置缓冲区大小
//			 */
//			byte[] buffer=new byte[cache];
//			int ch = 0;
//			System.out.println("下载ing...");
//			while ((ch = is.read(buffer)) != -1) {
//				fileout.write(buffer,0,ch);
//			}
//			System.out.println("下载成功");
//			is.close();
//			fileout.flush();
//			fileout.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	/**
//	 * 通过url获取下载文件的后缀名
//	 * @param url
//	 * @return
//	 */
//	public static String getFileType(String url){
//		String fileType = null;
//		if(url != null && !"".equals(url)){
//			fileType = url.substring(url.lastIndexOf(".")+1);
//		}
//		return fileType;
//	}
//	
//	/**
//	 * 获取response要下载的文件的默认路径
//	 * @param response
//	 * @return
//	 */
//	public static String getFilePath(String url) {
//		String filepath = root + File.separator + getRandomFileName() + "." + getFileType(url);
//		return filepath;
//	}
//	
//	/**
//	 * 获取随机文件名
//	 * @return
//	 */
//	public static String getRandomFileName() {
//		return String.valueOf(System.currentTimeMillis());
//	}
//	public static void main(String[] args) {
//		//下载图片
//		String url="http://www.iqiyi.com/common/flashplayer/20161025/1121f98c2359.swf";
////		HttpDownloadUtil.download(url);
//		
//		//下载视频
//		String url2="http://newoss.maiziedu.com/sjms/sjms1.mp4";
//		HttpDownloadUtil.download(url2);
//		
//	}
//}
