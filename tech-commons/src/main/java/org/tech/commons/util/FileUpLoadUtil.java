package org.tech.commons.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

/**
 * 原生的httpServletRequest 类实现上传，spring mvc 的话request被重写了，
 * 因此建议用spring mvc 自带的上传工具类
 * 
 *  
 * 文件上传 具体步骤： 
 * 1）获得磁盘文件条目工厂 DiskFileItemFactory 要导包 
 * 2） 利用request 获取 真实路径 ，供临时文件存储，和 最终文件存储 ，这两个存储位置可不同，也可相同 
 * 3）对DiskFileItemFactory 对象设置一些 属性 
 * 4）高水平的API文件上传处理 ServletFileUpload
 *      upload = new ServletFileUpload(factory); 目的是调用
 *      parseRequest（request）方法 获得 FileItem 集合list ，
 *5）在 FileItem 对象中 获取信息， 遍历， 判断 表单提交过来的信息 是否是 普通文本信息 另做处理 
 *6） 第一种. 用第三方提供的 item.write( new File(path,filename) ); 直接写到磁盘上 
 *		第二种. 手动处理
 * 
 */
public class FileUpLoadUtil {
	
	private static final Logger logger = Logger.getLogger(FileUpLoadUtil.class);  
	
	
	public static List<UpLoadFileInfo> fileUpload(HttpServletRequest request) throws Exception {
		//图片上传记录信息
		List<UpLoadFileInfo> info = new ArrayList<UpLoadFileInfo>();
		request.setCharacterEncoding("utf-8"); // 设置编码

		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 获取文件需要上传到的路径
		// String path = request.getRealPath("/upload");
		String path = request.getSession().getServletContext()
				.getRealPath("/upload");

		// 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
		// 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
		/**
		 * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem
		 * 格式的 然后再将其真正写到 对应目录的硬盘上
		 */
		factory.setRepository(new File(path));
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);

		// 高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);

			for (FileItem item : list) {
				//获取不是表单字符串的字段
				if( ! item.isFormField() ){
					
					UpLoadFileInfo file = new UpLoadFileInfo();
					
					/**
					 * 以下三步，主要获取 上传文件的名字
					 */
					// 获取路径名
					String value = item.getName();
					// 索引到最后一个反斜杠
					int start = value.lastIndexOf("\\");
					// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
					String fileName = value.substring(start + 1);

					// 真正写到磁盘上
					// 它抛出的异常 用exception 捕捉

					// item.write( new File(path,filename) );//第三方提供的
					
					//重新定义文件名
					String uuid = UUID.randomUUID().toString().replace("-", "");
					String suffix= fileName.substring(fileName.lastIndexOf(".")+1);
					StringBuffer saveFilePath = new StringBuffer(uuid+"."+suffix);
					
					// 手动写的
					OutputStream out = new FileOutputStream(new File(path,saveFilePath.toString()));

					InputStream in = item.getInputStream();

					int length = 0;
					byte[] buf = new byte[1024];

					logger.info("获取上传文件的总共的容量：" + item.getSize());

					// in.read(buf) 每次读到的数据存放在 buf 数组中
					while ((length = in.read(buf)) != -1) {
						// 在 buf 数组中 取出数据 写到 （输出流）磁盘上
						out.write(buf, 0, length);
					}

					in.close();
					out.close();
					
					//记录文件信息
					file.setSaveFileName(saveFilePath.toString());
					file.setReadFileName(fileName);
					file.setReadFileSize(item.getSize());
					file.setStatus(true);
					file.setSaveFilePath(path+File.separator+saveFilePath.toString());
					
					info.add(file);
				}
				
			}
			
			
			
		} catch (Exception e) {
			logger.error(e);
		}

		return info;

	}

}
