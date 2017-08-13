package org.tech.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.tech.commons.enmu.ResponseMsg;
import org.tech.domain.People;
import org.tech.service.impl.MultipleService;

/**
 * 多个功能的测试
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/multiple")
public class MultipleController extends BaseController{
	@Resource
	private MultipleService multipleService;
	
	/**
	 * JedisService test
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/redis",method=RequestMethod.GET)
	public Object test1(){
		return responseSuccess(null, multipleService.redisSet());
	}
	
	/**
	 * propertiesUtil 工具类测试
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/properties",method=RequestMethod.GET)
	public Object properties(){
		return responseSuccess(null, multipleService.propertiesTest());
	}
	
	/**
	 * 测试报错时打印对应的参数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/testParamsError",method=RequestMethod.GET)
	public Object testParamsError(){
		try {
			throw new NullPointerException();
		} catch (Exception e) {
			logger.error(getParameterMap(),e);
		}
		return responseFail("test");
	}
	
	
	/**
	 * 测试返回的数据有空字段时，过滤掉这些没用的空字段
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/testNullValue",method=RequestMethod.GET)
	public Object testNullValue(){
		People p = new People();
		p.setAddress("address");
		p.setAge(20);
		return responseSuccess(null, p);
	}
	
	/**
	 * Distributed测试
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/testDistributed",method=RequestMethod.GET)
	public Object testDistributed(){
		multipleService.distributedIncrease();
		return responseSuccess("OK", null);
	}
	
	/**
	 * 测试并发支持多少客户端
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/testGetManyJedis",method=RequestMethod.GET)
	public Object testGetManyJedis(){
		multipleService.testGetManyJedis(5000);
		return responseSuccess("OK", null);
	}
	
	
	
	/**
	 * testRPC测试1
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/testRPC",method=RequestMethod.GET)
	public Object testRPC(){
		Integer result = multipleService.thriftIncrease();
		if(result == 1){
			logger.info("thriftIncrease result is:"+result);
			return responseSuccess(null, null);
		}
		return responseFail(null);
	}
	
	/**
	 * testRPC测试2
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/testRPC2",method=RequestMethod.GET)
	public Object testRPC2(){
		Integer result = multipleService.thriftIncrease2();
		if(result == 1){
			logger.info("thriftIncrease result is:"+result);
			return responseSuccess(null, null);
		}
		return responseFail(null);
	}
	
	@ResponseBody
	@RequestMapping(value="/testOutOfMemory",method=RequestMethod.GET)
	public Object testOutOfMemory(){
		try {
			multipleService.outOfMemory();
		} catch (Exception e) {
			return responseFail(e.getMessage());
		}
		return responseSuccess("success", null);
	}
	
	/**
	 * 1:支持多文件上传
	 * 2:使用springMVC进行多文件的效率显然要比字符流写入方式效率上要高得多
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/upload"  )  
    public Object upload2(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {  
        //创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){  
                        System.out.println(myFileName);  
                        //重命名上传后的文件名  
                        String fileName = file.getOriginalFilename();  
                        //定义上传路径  
                        String path = request.getSession().getServletContext().getRealPath("/upload") + fileName;  
                        File localFile = new File(path);  
                        file.transferTo(localFile);  
                    }  
                }  
            }  
              
        }  
        return responseSuccess("test",new Date());
    }
	
	/**
	 * 枚举测试
	 * 枚举是用了替换public static final 这样的变量
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/nemu",method=RequestMethod.GET)
	public Object nemu(){
		try {
			logger.info(ResponseMsg.STATUS200.getCode());
			logger.info(ResponseMsg.STATUS200.getMsg());
		} catch (Exception e) {
			logger.error(getParameterMap());
			return responseFail(e.getMessage());
		}
		return responseSuccess("success", ResponseMsg.STATUS200);
	}
}
