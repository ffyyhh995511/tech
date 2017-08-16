package org.tech.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
/**
 * 基础公共controller
 * 基础拦截器
 * @author Administrator
 *
 */
public class BaseController extends HandlerInterceptorAdapter{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public static final int RESPONSE_FAIL = 0;//失败
	public static final int RESPONSE_SUCC = 1;//成功
	public static final int RESPONSE_AUTH = 2;//权限
	
	private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> RESPONSE = new ThreadLocal<HttpServletResponse>();

	/**
	 * 拦截器之前
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		RESPONSE.set(response);
		REQUEST.set(request);
		return true;
	}

	
	/**
	 * 拦截器之后
	 */
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		REQUEST.remove();
		RESPONSE.remove();
	}
	
	/**
	 * 获取Request方法
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return REQUEST.get();
	}

	
	/**
	 * 获取Response方法
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return RESPONSE.get();
	}
	
	
	/**
	 * 打印参数
	 * @return
	 */
	public String getParameterMap(){
		Enumeration enu= getRequest().getParameterNames();
		Map<String,Object> map = new HashMap<String, Object>();
		while(enu.hasMoreElements()){
			String key = (String) enu.nextElement();
			Object value = getRequest().getParameter(key);
			map.put(key, value);
		}
		if(map.size() > 0 ){
			return "params = " + JSON.toJSONString(map);
		}
		return null;
	}
	
	/**
	 * 打印head参数
	 * @return
	 */
	public String getHeadsMap(){
		Enumeration enu= getRequest().getHeaderNames();
		Map<String,Object> map = new HashMap<String, Object>();
		while(enu.hasMoreElements()){
			String headerName=(String)enu.nextElement();
			String headerValue=getRequest().getHeader(headerName);//取出头信息内容
			map.put(headerName, headerValue);
		}
		if(map.size() > 0 ){
			return "heads = " + JSON.toJSONString(map);
		}
		return null;
	}

	
	/**
	 * 获取session方法
	 * @return
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}


	
	/**
	 * 参数错误
	 * @param msg
	 * @return
	 */
	public Map<String, Object> responseParamFail(String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", RESPONSE_FAIL);
		if(StringUtils.isBlank(msg)){
			map.put("msg", "Parameter error");
		}else{
			map.put("msg", msg);
		}
		return map;
	}
	
	
	/**
	 * 失败返回
	 * @param msg
	 * @return
	 */
	public Map<String, Object> responseFail(String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", RESPONSE_FAIL);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 成功返回
	 * @param msg
	 * @param object
	 * @return
	 */
	public Map<String, Object> responseSuccess(String msg, Object object) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", RESPONSE_SUCC);
		map.put("msg", msg);
		map.put("data", object);
		return map;
	}
	
	/**
	 * 权限相关
	 * @param msg
	 * @param object
	 * @return
	 */
	public Map<String, Object> responseAuth(String msg, Object object) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", RESPONSE_AUTH);
		map.put("msg", msg);
		map.put("data", object);
		return map;
	}
	
	public Map<String, Object> responseSuccess(Object object) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", RESPONSE_AUTH);
		map.put("data", object);
		return map;
	}
	
	public Map<String, Object> responseSuccess(String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", RESPONSE_SUCC);
		map.put("msg", msg);
		return map;
	}
	
	public Map<String, Object> responseSuccess() {
		return responseSuccess(null, null);
	}
	
	/**
	 * 校验参数是否null
	 * @param object
	 * @param parms
	 * @return
	 */
	public boolean isParamsNull(Object ...object){
		for (Object obj : object) {
			if(obj == null){
				return false;
			}
		}
		return true;
	}
}
