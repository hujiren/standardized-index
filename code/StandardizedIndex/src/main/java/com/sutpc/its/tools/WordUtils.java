package com.sutpc.its.tools;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

/**
 * 通过模板生成word文档的代码。 通用.
 *
 * @author ztw
 */
public class WordUtils {

  /**
   * mapToWordByFtl.
   */
  public static void mapToWordByFtl(String outFilePath, String fileName,
      Map<String, Object> dataMap) {
    // 初始化配置文件
    Configuration configuration = new Configuration();
    //设置编码.
    configuration.setDefaultEncoding("utf-8");
    // 我的ftl文件是放在D盘的,如果不是请自行修改,也可以放入配置文件读取.
    String fileDirectory = "D:\\report\\";
    // 加载文件.
    try {
      configuration.setDirectoryForTemplateLoading(new File(fileDirectory));
      // 加载模板.
      Template template = configuration.getTemplate("monthreport.ftl");
      configuration.setDefaultEncoding("utf-8");
      File docFile = new File(outFilePath + fileName + ".doc");
      FileOutputStream fos = new FileOutputStream(docFile);
      Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
      template.process(dataMap, out);
      if (out != null) {
        out.close();
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
