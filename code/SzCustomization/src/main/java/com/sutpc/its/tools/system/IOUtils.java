package com.sutpc.its.tools.system;


import java.io.File;

public class IOUtils {
	//获取项目部署根目录
	public static String getRootPath() {
		  String classPath = IOUtils.class.getClassLoader().getResource("/").getPath();
		  String rootPath  = "";
		  //windows下   部署路径千万不能有空格或中文
		  if("\\".equals(File.separator)){   
		   rootPath  = classPath.substring(1,classPath.indexOf("/WEB-INF/classes"));
		   rootPath = rootPath.replace("/", "\\");
		  }
		  //linux下
		  if("/".equals(File.separator)){   
		   rootPath  = classPath.substring(0,classPath.indexOf("/WEB-INF/classes"));
		   rootPath = rootPath.replace("\\", "/");
		  }
		  return rootPath+"/";
	}
}
