package com.sutpc.its.tools;


import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class IOUtils {
	//获取项目部署根目录
	public static String getRootPath() {
		String path = null;
		try {
			path = ResourceUtils.getURL("classpath:").getPath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//创建File时会自动处理前缀和jar包路径问题  => /root/tmp
		File rootFile = new File(path);
		if(!rootFile.exists()) {
			rootFile = new File("");
		}
		return rootFile.getAbsolutePath();
	}
	public static String getJarRootPath() throws FileNotFoundException {
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
