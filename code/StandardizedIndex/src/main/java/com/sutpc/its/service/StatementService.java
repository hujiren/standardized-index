package com.sutpc.its.service;

import com.alibaba.fastjson.JSONObject;
import com.sutpc.its.dao.IModuleTemplateDao;
import com.sutpc.its.dao.IReportDao;
import com.sutpc.its.statement.ModuleFactory;
import com.sutpc.its.statement.ModuleFileConfig;
import com.sutpc.its.statement.ModuleHandler;
import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.statement.bean.StatementValue;
import com.sutpc.its.tools.DocxUtil;
import com.sutpc.its.tools.TpiUtils;
import com.sutpc.its.tools.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 报告处理服务.
 *
 * @author admin
 * @date 2020/5/19 14:06
 */
@Service
@Slf4j
public class StatementService {

  @Autowired
  private ModuleFactory factory;

  @Autowired
  private IModuleTemplateDao moduleTemplateDao;

  @Autowired
  private IReportDao dao;

  @Value("${tpi.combined.report}")
  private String root;

  private ModuleFileConfig config = ModuleFileConfig.getInstance();

  /**
   * 组合报告.
   *
   * @param param 参数
   * @return 结果
   */
  public StatementValue build(StatementParam param) {
    StatementValue value = new StatementValue();
    value.setStartTime(System.currentTimeMillis());
    for (String type : param.getTypes()) {
      ModuleHandler handler = factory.getHandler(type);
      // 计算模块值
      ModuleValue moduleValue = handler.handle(type, param);
      moduleValue.setType(type);
      value.putModule(moduleValue);
    }
    value.setEndTime(System.currentTimeMillis());
    return value;
  }

  /**
   * 生成组合报告.
   *
   * @param values 参数
   * @return 结果
   */
  public String export(List<ModuleValue> values) {
    List<String> types = values.stream().map(ModuleValue::getType).collect(Collectors.toList());
    List<ModuleTemplate> templates = moduleTemplateDao.getAllByTypes(types);
    Map<String, ModuleTemplate> mapTemplate = templates.stream()
        .collect(Collectors.toMap(ModuleTemplate::getType, template -> template));
    // 遍历所有模块
    List<String> paths = new ArrayList<>();
    int index = 0;
    for (ModuleValue value : values) {
      String type = value.getType();
      if ("MODULE_DISTRICT_HEAD".equals(type) || "MODULE_DISTRICT_FOOT".equals(type)) {
        Logger.getAnonymousLogger();
      } else {
        //设置标题
        String title = TpiUtils.getTitleIndex(index++) + mapTemplate.get(type).getName();
        value.setTitle(title);
      }
      ModuleHandler handler = factory.getHandler(type);
      // 生成word
      if (mapTemplate.containsKey(type)) {
        String filepath = handler.buildWord(value, mapTemplate.get(type));
        if (filepath != null) {
          paths.add(filepath);
        }
      }
    }
    // 合成word
    String mergePath = config.getFilepath();
    try {
      DocxUtil.mergeDocxList(paths, mergePath);
      DocxUtil.delTemporaryDocx(paths);
    } catch (Exception e) {
      e.printStackTrace();
    }
    int startIndex = mergePath.lastIndexOf("/");
    mergePath = mergePath.substring(startIndex + 1, mergePath.length());
    return mergePath;
  }

  /**
   * 查询模板.
   *
   * @param categoryId 参数
   * @return 结果
   */
  public List<ModuleTemplate> template(Integer categoryId) {
    return moduleTemplateDao.getAll(categoryId);
  }

  /**
   * 下载文件.
   */
  public void download1File(HttpServletResponse response,
      String filePath) {
    OutputStream out = null;
    File file = null;
    FileInputStream input = null;
    try {
      out = response.getOutputStream();
      //设置ConetentType CharacterEncoding Header,需要在out.write()之前设置
      response.setContentType("mutipart/form-data");
      response.setCharacterEncoding("utf-8");
      response.setHeader("Content-disposition",
          "attachment;filename=" + java.net.URLEncoder.encode(filePath, "UTF-8"));
      //本地已存在的文件路径
      file = new File(root + filePath);
      input = new FileInputStream(file);
      int len;
      byte[] bytes = new byte[1024];
      while ((len = input.read(bytes)) > 0) {
        out.write(bytes, 0, len);
      }
      out.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        input.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        out.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * 月报数据.
   */
  public JSONObject getMonthReportData(String date, int districtFid) {
    String key = date + "_" + districtFid;
    Map<String, Object> map = dao.getMonthReport(key);
    if (map != null && map.containsKey("JSON")) {
      Clob clob = (Clob) map.get("JSON");
      try {
        String json = Utils.clobToString(clob);
        return JSONObject.parseObject(json);
      } catch (Exception e) {
        log.info("[读取月报数据]->:{}", e.getMessage());
      }
      return null;
    }
    return null;
  }

}
