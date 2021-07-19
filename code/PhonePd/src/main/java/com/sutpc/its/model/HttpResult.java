package com.sutpc.its.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("接口统一返回结果")
public class HttpResult<T> {
	
	@ApiModelProperty(value = "返回码")
	private String code;
	@ApiModelProperty(value = "附加消息")
	private String msg;
	@ApiModelProperty(value = "附加数据")
	private Object data;
	
	
	public HttpResult() {
		code = "200" ;
		msg = "success";
	}
	
	public static HttpResult ok() {
		HttpResult r = new HttpResult();
		return r;
	}
	public static HttpResult ok(Object data) {
		HttpResult r = new HttpResult();
		r.setData(data);
		return r;
	}
	public static HttpResult ok(Object data, String msg) {
		HttpResult r = new HttpResult();
		r.setData(data);
		r.setMsg(msg);
		return r;
	}
	public static HttpResult ok(String code, Object data, String msg) {
		HttpResult r = new HttpResult();
		r.setCode(code);
		r.setData(data);
		r.setMsg(msg);
		return r;
	}
	
	public static HttpResult error() {
		HttpResult r = new HttpResult();
		r.setCode("500");
		r.setMsg("未知异常，请联系管理员");
		return r;
	}
	public static HttpResult error(String msg) {
		HttpResult r = new HttpResult();
		r.setCode("500");
		r.setMsg(msg);
		return r;
	}
	public static HttpResult error(Object data, String msg) {
		HttpResult r = new HttpResult();
		r.setCode("500");
		r.setData(data);
		r.setMsg(msg);
		return r;
	}
	public static HttpResult error(String code, Object data, String msg) {
		HttpResult r = new HttpResult();
		r.setCode(code);
		r.setData(data);
		r.setMsg(msg);
		return r;
	}
	
	


}
