package com.sutpc.its.tools;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.Configure.ELMode;
import com.deepoove.poi.policy.DocxRenderPolicy;
import com.deepoove.poi.policy.HackLoopTableRenderPolicy;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.sutpc.its.statement.ModuleFileConfig;
import com.sutpc.its.statement.bean.ModuleTemplate;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * docx格式处理工具类.
 */
@Component
public class DocxUtil {

  private static final Logger logger = LoggerFactory.getLogger(DocxUtil.class);


  private static ModuleFileConfig config = ModuleFileConfig.getInstance();


  /**
   * 对docx模板进行参数填充，获取docx文件.
   *
   * @param tempPath 模板绝对路径 (not null)
   * @param resultPath 生成文件绝对路径 (not null)
   * @param map 参数map,获取方式查看README.md (not null)
   */
  public static void getDocxFromTemplate(String tempPath, String resultPath, Object map)
      throws Exception {
    FileOutputStream out = new FileOutputStream(resultPath);
    //循环表格插件
    HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
    //文档嵌套插件
    DocxRenderPolicy docxRenderPolicy = new DocxRenderPolicy();
    Configure config = Configure.newBuilder()
        .buildGramer("{{", "}}")
        .setElMode(ELMode.SPEL_MODE)
        .bind("list", policy)
        .bind("fastList", policy)
        .bind("subList", policy)
        .bind("mainList", policy)
        .bind("result", docxRenderPolicy)
        .bind("listSon", docxRenderPolicy)
        .build();
    XWPFTemplate template = XWPFTemplate.compile(tempPath, config).render(map);

    try {
      template.write(out);
      out.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      if (null != out) {
        out.close();
      }
      if (null != template) {
        template.close();
      }
    }
  }

  /**
   * 合并多个docx文件.
   *
   * @param pathList 多个docx文件的绝对路径列表. (not null)
   * @param resultPath 生成文件绝对路径. (not null)
   * @throws Exception (can be null)
   */
  public static void mergeDocxList(List<String> pathList, String resultPath) throws Exception {
    if (null == pathList && pathList.size() == 0) {
      return;
    }
    NiceXWPFDocument main = new NiceXWPFDocument(new FileInputStream(pathList.get(0)));
    FileOutputStream out = new FileOutputStream(resultPath);
    try {
      for (int i = 1; i < pathList.size(); i++) {
        NiceXWPFDocument sub = new NiceXWPFDocument(new FileInputStream(pathList.get(i)));
        main = main.merge(sub);
      }
      main.write(out);
      out.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      if (null != out) {
        out.close();
      }
      if (null != main) {
        main.close();
      }
    }
  }

  /**
   * 通过模板和实体数据生成docx文件.
   *
   * @param template 模板
   * @param data 实体信息
   */
  public static String getDocxPathByConditions(ModuleTemplate template, Object data) {
    String tempPath = template.getFilepath();
    String resultPath = config.getFilepath();
    try {
      DocxUtil.getDocxFromTemplate(tempPath, resultPath, data);
    } catch (Exception e) {
      e.printStackTrace();
      logger.info("result = {}", e.getMessage());
      return null;
    }
    return resultPath;
  }

  /**
   * 删除文件.
   *
   * @param path 文件路径（包含文件名及扩展名）
   */
  public static boolean deleteFile(String path) {
    File file = new File(path);
    if (!file.exists()) {
      return false;
    }
    if (file.isFile()) {
      return file.delete();
    }
    return file.delete();
  }

  /**
   * 删除临时docx模块文件.
   */
  public static void delTemporaryDocx(List<String> listPaths) {
    for (String path : listPaths) {
      boolean flag = deleteFile(path);
      if (flag) {
        logger.info("result = 删除临时docx：{}成功", path);
      }
    }
  }
}
