package com.sutpc.security;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

public class DpServlet extends DispatcherServlet {
	 

	private static final long serialVersionUID = 7702451025936297422L;

	private static final ResourceBundle lStrings = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");  
     
	    private static final String METHOD_OPTIONS = "OPTIONS";  
	    
	      
	    @Override  
	    protected void doOptions(HttpServletRequest request, HttpServletResponse response)  
	            throws ServletException, IOException {  
	        methodNotAllowed(METHOD_OPTIONS, response);  
	        
	    }  
	      
	    /** 
	     * 禁止HTTP OPTIONS
	     *  
	     * @param methodName 
	     * @param response 
	     * @throws IOException 
	     */  
	    private void methodNotAllowed(String methodName, HttpServletResponse response) throws IOException {   
	         String errMsg = lStrings.getString("http.method_post_not_supported");  
	         Object[] errArgs = new Object[1];  
	         errArgs[0] = methodName;  
	         errMsg = MessageFormat.format(errMsg, errArgs);  
	           
	         response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, errMsg);  
	    }  	
}
