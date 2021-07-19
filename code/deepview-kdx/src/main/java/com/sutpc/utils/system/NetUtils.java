package com.sutpc.utils.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtils {

	public static String getUrlResponseString(String _url)  {
		try {  
			 URL url = new URL(_url);  
	         HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();  
	         //GET Request Define:   
	         urlConnection.setRequestMethod("GET");  
	         urlConnection.connect();  
	           
	         //Connection Response From Test Servlet  
	         //System.out.println("Connection Response From Test Servlet");  
	         InputStream inputStream = urlConnection.getInputStream();  
	           
	         //Convert Stream to String  
	      
	         InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");  
	        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
	        StringBuilder result = new StringBuilder();  
	        String line = null;  
	        try {  
	            while((line = bufferedReader.readLine()) != null){ 
	                result.append(line +"\n");  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	        	
	            try{  
	                inputStreamReader.close();  
	                inputStream.close();  
	                bufferedReader.close();  
	            }catch(IOException e){  
	                e.printStackTrace();  
	            }  
	        } 
	        //System.out.println(result); 
         
	        return result.toString();
         
		} catch (IOException e) {  
            e.printStackTrace();  
        }
		
         return "";
	}
}
