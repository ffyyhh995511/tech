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
import org.apache.log4j.Logger;
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
	
	private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> RESPONSE = new ThreadLocal<HttpServletResponse>();

	/**
	 * 拦截器之前
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		REQUEST.set(request);
		RESPONSE.set(response);
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
		map.put("status", false);
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
		map.put("status", false);
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
		map.put("status", true);
		map.put("msg", msg);
		map.put("data", object);
		return map;
	}
}
