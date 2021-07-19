package com.sutpc.utils.system;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



public class SysUtils {

	public static String getUuid(){
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

	static HttpServletRequest getCurrentHttpRequest(){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static String getRemoteIP(){		
		return getCurrentHttpRequest().getRemoteAddr();
	}
		
	public static String getSysBasePath(){
		HttpServletRequest request = getCurrentHttpRequest();
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		
	}
	
	
}
