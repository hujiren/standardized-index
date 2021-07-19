package com.sutpc.its.tools.system;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



public class SystemUtils {

	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	/**
     * 是否为页面请求
     */
	public static Boolean isPageRequest(String path){
		
		if(path.endsWith(".html"))
			return true;
		
		return false;
	}

	
	
	
	public static HttpServletRequest getCurrentHttpRequest(){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static String getRemoteIP(){		
		try{
			HttpServletRequest hr = getCurrentHttpRequest();
			String ip = hr.getHeader("X-Real-IP");//Nginx转发真实IP
			if(ip==null || "".equals(ip)){
				return hr.getRemoteAddr();
			}
			
			return ip;
		}
		catch(Exception ex){
			return "";
		}
	}
	public static String getRemoteIP_Direct(){		
		HttpServletRequest hr = getCurrentHttpRequest();
		return hr.getRemoteAddr();
	}
	
	public static String getHttpHost(){		
		try{
			HttpServletRequest hr = getCurrentHttpRequest();
			String host = hr.getHeader("Host");//Nginx转发Host
			if(host==null || "".equals(host)){
				return hr.getRemoteAddr();
			}
			
			return host;
		}
		catch(Exception ex){
			return "";
		}
	}
		
	public static String getSysBasePath(){
		HttpServletRequest request = getCurrentHttpRequest();
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		
	}
	
	
	public static void cleanAutoInjectKeys(Map<String, Object> map){
		map.remove("sys_uuid");
		map.remove("sys_now");
		map.remove("sys_ip");
		map.remove("session");
	}
	
}
