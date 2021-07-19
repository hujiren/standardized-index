package com.sutpc.security;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sutpc.utils.system.SecurityUtils;


public class Interceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		
		
		//防止"跨站点请求伪造"的安全漏洞检测		
		if(SecurityUtils.checkCSRF(request)){
			return false;
		}
        
		
		
		
		return true;
	}
	
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
	
}
