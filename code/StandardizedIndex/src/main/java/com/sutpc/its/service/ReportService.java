package com.sutpc.its.service;

import com.sutpc.its.aop.ControllerException;
import com.sutpc.its.config.HttpResultInfo;
import com.sutpc.its.dao.IReportDao;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.model.StandardizedPageModel;
import com.sutpc.its.tools.GeoserverUtils;
import com.sutpc.its.tools.IoUtils;
import com.sutpc.its.tools.PeriodUtils;
import com.sutpc.its.tools.Utils;
import com.sutpc.its.tools.WordUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReportService {

  private Logger logger = LogManager.getLogger(ReportService.class);
  /**
   * 專題報告.
   */
  @Autowired
  private IReportDao reportDao;

  @Value("${tpi.monthReportTxtPath}")
  private String monthReportTxtPath;

  @Value("${tpi.weekReportTxtPath}")
  private String weekReportTxtPath;

  @Value("${tpi.waterImagePath}")
  private String waterImagePath;

  @Value("${tpi.geoserverUrl}")
  private String geoserverUrl;

  /**
   * 月报.
   */
  public HttpResult<Object> getMonthReportData(Map<String, Object> map) {
    Object date = map.get("date");
    if (date == null) {
      return HttpResult.error(HttpResultInfo.CODE500, null, HttpResultInfo.NODATEMSG);
    }
    int year = Integer.parseInt(date.toString().substring(0, 4));
    int month = Integer.parseInt(date.toString().substring(4, 6));
    String outFilePath = monthReportTxtPath;
    String fileName = date + "" + Utils.getMaxDayByYearMonth(year, month);
    String districtFid = map.get("district_fid") + "";
    if (!districtFid.equals("null")) {
      fileName = fileName + "_" + districtFid;
    }
    String result = Utils.readTxtFile(outFilePath + fileName + ".txt");
    if (result.equals("nofile")) {
      return HttpResult.error(HttpResultInfo.CODE201, null, HttpResultInfo.NOFILEMSG);
    }
    Map<String, Object> dataMap = new HashMap<>();
    dataMap = Utils.stringToMap(result);
    return HttpResult.ok(dataMap);
  }

  /**
   * 周报.
   */
  public HttpResult<Object> getWeekReportData(Map<String, Object> map) {
    Object date = map.get("date");
    if (date == null) {
      return HttpResult.error(HttpResultInfo.CODE500, null, HttpResultInfo.NODATEMSG);
    }
    String outFilePath = weekReportTxtPath;
    String fileName = String.valueOf(date);
    String result = Utils.readTxtFile(outFilePath + fileName + ".txt");
    if (result.equals("nofile")) {
      return HttpResult.error(HttpResultInfo.CODE201, null, HttpResultInfo.NOFILEMSG);
    }
    Map<String, Object> dataMap = new HashMap<>();
    dataMap = Utils.stringToMap(result);
    return HttpResult.ok(dataMap);
  }

  /**
   * 专题报告.
   */
  public int setSpecialReportInfo(Map<String, Object> map) {
    map.put("create_time", System.currentTimeMillis() / 1000);
    return reportDao.setSpecialReportInfo(map);
  }

  /*public List<Map<String, Object>> getSpecialReportInfo(Map<String, Object> map) {
    List<Map<String, Object>> list = reportDao.getSpecialReportInfo(map);
    for (Map<String, Object> m : list) {
      long timelong = Long.parseLong(m.get("create_time").toString());
      m.put("create_time", PeriodUtils.getTimeStrByTimeStamp(timelong * 1000));
    }
    return list;
  }*/

  /**
   * 专题报告.
   */
  public StandardizedPageModel getSpecialReportInfo(Map<String, Object> map) {

    map.put("pageSize", Integer.valueOf((String) map.get("pageSize")));
    map.put("pageNum", Integer.valueOf((String) map.get("pageNum")));

    List<Map<String, Object>> resultList = reportDao.getSpecialReportInfoByPage(map);
    for (Map<String, Object> m : resultList) {
      long timelong = Long.parseLong(m.get("create_time").toString());
      m.put("create_time", PeriodUtils.getTimeStrByTimeStamp(timelong * 1000));
    }

    StandardizedPageModel pm = new StandardizedPageModel();
    pm.setList(resultList);
    List<Map<String, Object>> list = reportDao.getSpecialReportInfo(map);
    pm.setTotalRecords(list.size());
    pm.setPageSize((int) map.get("pageSize"));
    pm.setPageNum((int) map.get("pageNum"));

    return pm;

    //return list;
  }

  /**
   * 专题报告.
   */
  public int updateSpecialReportInfo(Map<String, Object> map) {
    map.put("update_time", System.currentTimeMillis() / 1000);
    return reportDao.updateSpecialReportInfo(map);
  }

  /**
   * .
   */
  public int deleteSpecialReportInfo(Map<String, Object> map) {
    return reportDao.deleteSpecialReportInfo(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getSubmitReportInfo(Map<String, Object> map) {
    String startmonth = map.get("startmonth").toString();
    String endmonth = map.get("endmonth").toString();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
    try {
      Date date = simpleDateFormat.parse(startmonth);
      long starttimestamp = date.getTime();
      map.put("starttimestamp", starttimestamp / 1000);

      date = simpleDateFormat.parse(endmonth);
      Calendar cdate = Calendar.getInstance();
      cdate.setTime(date);
      cdate.add(Calendar.MONTH, 1);
      date = cdate.getTime();
      long endtimestamp = date.getTime();
      map.put("endtimestamp", endtimestamp / 1000);
    } catch (Exception e) {
      e.printStackTrace();
    }
    List<Map<String, Object>> list = reportDao.getSubmitReportInfo(map);
    for (Map<String, Object> m : list) {
      long timelong = Long.parseLong(m.get("create_time").toString());
      m.put("create_time", PeriodUtils.getTimeStrByTimeStamp(timelong * 1000));
      timelong = Long.parseLong(m.get("update_time").toString());
      m.put("update_time", PeriodUtils.getTimeStrByTimeStamp(timelong * 1000));
    }
    return list;
  }

  /**
   * .
   */
  public Map<String, Object> getMonthReportDoc(Map<String, Object> map) {
    //date:201807
    Object date = map.get("date");
    if (date == null) {
      return null;
    }
    logger.info("---获取年月");
    int year = Integer.parseInt(date.toString().substring(0, 4));
    int month = Integer.parseInt(date.toString().substring(4, 6));
    //读取txt数据的路径
    // String StringoutFile = "D://TPMODEL//Reports//District_month//";
    //本地测试
    String txtPath = monthReportTxtPath;
    //word文档生成的路径
    String outFilePath = IoUtils.getRootPath() + "files//month//";
    //txt文件名    同月报 word文件名
    String fileName = date + "" + Utils.getMaxDayByYearMonth(year, month);
    //获取得到txt的json数据
    String result = Utils.readTxtFile(txtPath + fileName + ".txt");
    //把json数据转换成map，用来作为生成doc文档的数据源
    Map<String, Object> dataMap = Utils.stringToMap(result);

    dataMap.putAll(map);
    logger.info("开始制定第4张图");
    logger.error("开始制定第四张图");
    dataMap.put("IMAGE4", GeoserverUtils.getBase64ByBufImage(GeoserverUtils
        .getMonthImages("subsect", year + "", month + "", geoserverUrl, waterImagePath)));
    logger.info("开始制定第13张图");
    logger.error("开始制定第13张图");
    dataMap.put("IMAGE13", GeoserverUtils.getBase64ByBufImage(GeoserverUtils
        .getMonthImages("majorRoad", year + "", month + "", geoserverUrl, waterImagePath)));
    logger.info("开始制定第14张图");
    logger.error("开始制定第14张图");
    dataMap.put("IMAGE14", GeoserverUtils.getBase64ByBufImage(GeoserverUtils
        .getMonthImages("top10jam", year + "", month + "", geoserverUrl, waterImagePath)));
    logger.info(
        "----------------------------------------------开始输出月报-------------------------------------------------------");
    //如果输出路径文件夹不存在则创建
    File filedoc = new File(outFilePath);
    if (!filedoc.exists()) {
      filedoc.mkdirs();
    }
    logger.info("文件夹位置：" + outFilePath);
    logger.info("开始准备生成word文档");
    logger.error("开始准备生成word文档");
    WordUtils.mapToWordByFtl(outFilePath, fileName, dataMap);
    logger.info("**************************************************************************生成完毕");
    logger.error("#########################################################################生成完毕");
    Map<String, Object> fileMap = new HashMap<String, Object>();

    fileMap.put("filepath", "files/month/" + fileName + ".doc");
    return fileMap;
  }

  /**
   * .
   */
  public Map<String, Object> getWeekReportDoc(Map<String, Object> map) {
    //date:201807
    Object date = map.get("date");
    if (date == null) {
      return null;
    }
    int week = Integer.parseInt(String.valueOf(map.get("weekno")));
    int year = Integer.parseInt(date.toString().substring(0, 4));
    String txtPath = weekReportTxtPath;
    //word文档生成的路径
    String outFilePath = IoUtils.getRootPath() + "files//week//";
    //txt文件名    同月报 word文件名
    String fileName = date + "";
    //获取得到txt的json数据
    String result = Utils.readTxtFile(txtPath + fileName + ".txt");
    //把json数据转换成map，用来作为生成doc文档的数据源
    Map<String, Object> dataMap = Utils.stringToMap(result);

    dataMap.putAll(map);

    dataMap.put("IMAGE4", GeoserverUtils.getBase64ByBufImage(GeoserverUtils
        .getWeekImages("subsect", year + "", week + "", geoserverUrl, waterImagePath)));

    dataMap.put("IMAGE13", GeoserverUtils.getBase64ByBufImage(GeoserverUtils
        .getWeekImages("majorRoad", year + "", week + "", geoserverUrl, waterImagePath)));

    dataMap.put("IMAGE14", GeoserverUtils.getBase64ByBufImage(GeoserverUtils
        .getWeekImages("top10jam", year + "", week + "", geoserverUrl, waterImagePath)));

    //如果输出路径文件夹不存在则创建
    File filedoc = new File(outFilePath);
    if (!filedoc.exists()) {
      filedoc.mkdirs();
    }
    System.out.println(outFilePath);
    GeoserverUtils.mapToWordByFtlWeekly(outFilePath, fileName, dataMap);

    Map<String, Object> fileMap = new HashMap<String, Object>();

    fileMap.put("filepath", "files/week/" + fileName + ".doc");
    return fileMap;
  }
}
