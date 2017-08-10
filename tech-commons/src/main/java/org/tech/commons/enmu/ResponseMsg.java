package org.tech.commons.enmu;

public enum ResponseMsg {
	STATUS200(200,"正常状态"),
	STATUS304(304,"重定向"),
	STATUS404(404,"客户端错误"),
	STATUS500(500,"服务端报错"),
	STATUS502(502,"网关超时");
	
	ResponseMsg(){
		
	}
	
	ResponseMsg(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	private int code;
	
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
