package com.sutpc.its.tools.system;


public class PropertyUtils {
	

    public static String getProperty(String key){
      
        return (String)CustomizedPropertyPlaceholderConfigurer.getContextProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
    	
    	String v = getProperty(key);
        if(null == v) {
            return defaultValue;
        }
        return v;
    }

	

}
