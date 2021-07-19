package com.sutpc.its.tools.system;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

public class JsonMapUtils {
	public static Map<String,Object> Json2Map(String json){
		return JSON.parseObject(json, Map.class);
	}
	public static String Map2Json(Map<String,Object> map){
		return JSON.toJSONString(map);
	}	
	public static Map getReqParamMap(HttpServletRequest request){
		
		 Map map = new HashMap();  
	     Enumeration paramNames = request.getParameterNames();  
	     while (paramNames.hasMoreElements()) {  
	    	 String paramName = (String) paramNames.nextElement();  
	  
	         String[] paramValues = request.getParameterValues(paramName);  
	         if (paramValues.length == 1) {  
	        	 String paramValue = paramValues[0];  
	             if (paramValue.length() != 0) {  
	            	 map.put(paramName, paramValue);  
	             }  
	         }  
	     } 
	        
	     return map;
	}	
	public static List<String> MapKeys2List(Map<String, Object> map){
		List<String> keys = new ArrayList<String>();
    	Set<String> set = map.keySet();
		Iterator<String> iter = set.iterator();
		while(iter.hasNext()){
			keys.add(iter.next().toString());
		}		
		return keys;
	}
}
