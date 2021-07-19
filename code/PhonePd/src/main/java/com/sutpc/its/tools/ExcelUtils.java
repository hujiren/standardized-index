package com.sutpc.its.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils {
	
	
	public static String exportToExcel(List<Map<String,Object>> listResult,String fileName){
		if(listResult != null && listResult.size() !=0){
			Date now = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			String filename = fileName;
			String filedir = "";
			String filepath = "";
			if("".equals(filename)||null==filename){
				filename = Math.round(Math.random()*999999)+ sf.format(now)+".xlsx";
			}else{
				filename = fileName+".xlsx";
			}
			/*String classPath = ExcelUtils.class.getClassLoader().getResource("").getPath();
			try {
				classPath=URLDecoder.decode(classPath,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		
			filedir = classPath.substring(0,classPath.indexOf("WEB-INF"));	
			filedir = filedir.substring(1,filedir.length()) + "files";*/
			
			filedir =IOUtils.getRootPath()+ "files";
			
			filepath = "files/" + filename;
			XSSFWorkbook workbook = new XSSFWorkbook(); //产生工作簿对象  
			XSSFSheet sheet = workbook.createSheet(); //产生工作表对象  
			
			List<String> keys =getMapKeys(listResult.get(0));
			
			XSSFRow row = sheet.createRow(0); 
			XSSFCell cell = null;
			for(int i=0;i<keys.size();i++){
				cell = row.createCell(i);
				cell.setCellValue(keys.get(i));
			}
			for(int i=0;i<listResult.size();i++){
				row = sheet.createRow(i+1);
				for(int j=0;j<keys.size();j++){
					cell = row.createCell(j);
					if(listResult.get(i).get(keys.get(j)) != null){
						String v=listResult.get(i).get(keys.get(j)).toString();
						try{
							double dv =	Double.parseDouble(v);
							cell.setCellValue(dv);
						}
						catch(Exception ex){
							cell.setCellValue(v);
						}
					}else{
						cell.setCellValue("");
					}
				}
			}
			FileOutputStream fOut;
			try {
				File file = new File(filedir);  
				
		        // 创建目录  
		        if (!file.exists()) {  
		        	file.mkdirs();// 目录不存在的情况下，创建目录。  
		        }
				fOut = new FileOutputStream(filedir + "/" + filename);
				workbook.write(fOut);
				fOut.flush();  
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			File file = new File(filedir + "/" + filename);
			if(file.exists()) {
				return filepath;
			}else {
				return "";
			}
		}else{
			return "";
		}
	}
	public static String exportToExcel(List<Map<String, Object>> listResult) {
		
		if(listResult != null && listResult.size() !=0){
			Date now = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			String filename = "";
			String filedir = "";
			String filepath = "";
			filename = Math.round(Math.random()*999999)+ sf.format(now)+".xlsx";
			/*String classPath = ExcelUtils.class.getClassLoader().getResource("").getPath();
			try {
				classPath=URLDecoder.decode(classPath,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		
			filedir = classPath.substring(0,classPath.indexOf("WEB-INF"));	
			filedir = filedir.substring(1,filedir.length()) + "files";*/
			
			filedir =IOUtils.getRootPath()+ "files";
			
			filepath = "files/" + filename;
			XSSFWorkbook workbook = new XSSFWorkbook(); //产生工作簿对象  
			XSSFSheet sheet = workbook.createSheet(); //产生工作表对象  
			
			List<String> keys =getMapKeys(listResult.get(0));
			
			XSSFRow row = sheet.createRow(0); 
			XSSFCell cell = null;
			for(int i=0;i<keys.size();i++){
				cell = row.createCell(i);
				cell.setCellValue(keys.get(i));
			}
			for(int i=0;i<listResult.size();i++){
				row = sheet.createRow(i+1);
				for(int j=0;j<keys.size();j++){
					cell = row.createCell(j);
					if(listResult.get(i).get(keys.get(j)) != null){
						String v=listResult.get(i).get(keys.get(j)).toString();
						try{
							double dv =	Double.parseDouble(v);
							cell.setCellValue(dv);
						}
						catch(Exception ex){
							cell.setCellValue(v);
						}
					}else{
						cell.setCellValue("");
					}
				}
			}
			FileOutputStream fOut;
			try {
				File file = new File(filedir);  
				
		        // 创建目录  
		        if (!file.exists()) {  
		        	file.mkdirs();// 目录不存在的情况下，创建目录。  
		        }
				fOut = new FileOutputStream(filedir + "/" + filename);
				workbook.write(fOut);
				fOut.flush();  
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			File file = new File(filedir + "/" + filename);
			if(file.exists()) {
				return filepath;
			}else {
				return "";
			}
		}else{
			return "";
		}
	}
public static String exportToExcel(List<Map<String, Object>> listResult,String xString,String yString) {
		
		if(listResult != null && listResult.size() !=0){
			Date now = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			String filename = "";
			String filedir = "";
			String filepath = "";
			filename = Math.round(Math.random()*999999)+ sf.format(now)+".xlsx";
			/*String classPath = ExcelUtils.class.getClassLoader().getResource("").getPath();
			try {
				classPath=URLDecoder.decode(classPath,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		
			filedir = classPath.substring(0,classPath.indexOf("WEB-INF"));	
			filedir = filedir.substring(1,filedir.length()) + "files";*/
			
			filedir =IOUtils.getRootPath()+ "files";
			
			filepath = "files/" +getTimeByStr("yyyy")+"/"+getTimeByStr("MM")+"/"+getTimeByStr("dd")+"/"+ filename;
			XSSFWorkbook workbook = new XSSFWorkbook(); //产生工作簿对象  
			XSSFSheet sheet = workbook.createSheet(); //产生工作表对象  
			
			List<String> keys =getMapKeys(listResult.get(0));
			XSSFRow row = sheet.createRow(0); 
			XSSFCell cell = null;
			for(int i=0;i<keys.size();i++){
				cell = row.createCell(i);
				if(keys.get(i).toString().equals("x")){
					cell.setCellValue(xString);
				}else{
					cell.setCellValue(yString);
				}
			}
			for(int i=0;i<listResult.size();i++){
				row = sheet.createRow(i+1);
				for(int j=0;j<keys.size();j++){
					cell = row.createCell(j);
					if(listResult.get(i).get(keys.get(j)) != null){
						String v=listResult.get(i).get(keys.get(keys.size()-1-j)).toString();
						try{
							double dv =	Double.parseDouble(v);
							cell.setCellValue(dv);
						}
						catch(Exception ex){
							cell.setCellValue(v);
						}
					}else{
						cell.setCellValue("");
					}
				}
			}
			FileOutputStream fOut;
			try {
				File file = new File(filedir);  
				
		        // 创建目录  
		        if (!file.exists()) {  
		        	file.mkdirs();// 目录不存在的情况下，创建目录。  
		        }
				fOut = new FileOutputStream(filedir + "/" + filename);
				workbook.write(fOut);
				fOut.flush();  
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			File file = new File(filedir + "/" + filename);
			if(file.exists()) {
				return filepath;
			}else {
				return "";
			}
		}else{
			return "";
		}
	}

public static String getTimeByStr(String string){
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat(string);
	String time = sdf.format(date);
	return time;
}
	public static List<String> getMapKeys(Map<String, Object> map){
		List<String> keys = new ArrayList<String>();
		Set<String> set = map.keySet();
		Iterator<String> iter = set.iterator();
		while(iter.hasNext()){
			keys.add(iter.next().toString());
		}
		
		return keys;
	}

}
