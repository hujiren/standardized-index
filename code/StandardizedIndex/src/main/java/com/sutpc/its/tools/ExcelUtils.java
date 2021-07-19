package com.sutpc.its.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

  /**
   * 导出数据到excel文件.
   *
   * @param listResult 数据
   * @param fileName 待生成文件名
   * @param index 数据类型
   * @return excel文件路径
   */
  public static String exportToExcel(List<String> keys, List<Map<String, Object>> listResult,
      String fileName, String index, String timeprecision) {
    if (listResult != null && listResult.size() != 0) {
      // 生成文件名
      Date now = new Date();
      SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
      String filename = fileName;
      String filedir = "";
      String filepath = "";
      if ("".equals(filename) || null == filename) {
        filename = Math.round(Math.random() * 999999) + sf.format(now) + ".xlsx";
      } else {
        filename = fileName + ".xlsx";
      }
      /*String classPath = ExcelUtils.class.getClassLoader().getResource("").getPath();
      try {
        classPath = URLDecoder.decode(classPath, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }

      filedir = classPath.substring(0, classPath.indexOf("WEB-INF"));
      filedir = filedir.substring(1, filedir.length()) + "files";*/

      filedir = IoUtils.getRootPath() + "files";

      filepath = "files/" + filename;
      XSSFWorkbook workbook = new XSSFWorkbook(); //产生工作簿对象
      XSSFSheet sheet = workbook.createSheet(); //产生工作表对象

      //List<String> keys =getMapKeys(listResult.get(0));

      XSSFRow row = sheet.createRow(0);
      XSSFCell cell = null;

      String excelpara = "Month: 月,Day: 日,Lunar: 日（农历）,Lunar-Month: 月（农历）,"
          + "Weekday: 星期,Workday: 工作日,WeatherFlag: 天气,SPEED: 速度(km/h),"
          + "EXPONENT: 指数,FID: 区域id,x: 横坐标,y: 纵坐标,"
          + "FNAME: 区域,ROADSECT_TO: 路段终点,ROADSECT_FROM: 路段起点,"
          + "TPI: 指数,name: 名称,dir: 方向,from: 起点,to: 终点,"
          + "type: 类型,conindex: 指数,DIR_FNAME: 方向,"
          + "JAM_TIME_RATE: 拥堵时间比,ROAD_TYPE_FID: 类型,"
          + "ROADSECT_FID: 路段id,DISTRICT_FNAME: 行政区,"
          + "JAM_SPEED: 拥堵阈值(km/h),ALLPERIOD: 样本总量,"
          + "ROADSECT_FNAME: 路段名,period: 时间片,time: 日期,tpi: 指数,"
          + "speed: 速度(km/h),JAM_HOUR: 拥堵时长,ROADSECT_NAME: 路段名,J"
          + "AM_PERIOD: 拥堵时段,JAM_WEIGHT: 拥堵权值,DIRNAME: 方向,"
          + "SPOTSNAME: 目的地,SAMPLE_VEHICLE: 样本车辆数,PARK_ZONE_NAME: 片区";
      //newTest
      /*String excelpara = "Month: 月,Day: 日,Lunar-Month: 月（农历）,Lunar: 日（农历）,"
          + "Weekday: 星期,Workday: 工作日,SPEED: 速度(km/h),EXPONENT: 指数,WeatherFlag: 天气,"
          + "FNAME: 区域,FID: 区域id,x: 横坐标,y: 纵坐标,ROADSECT_TO: 路段终点,"
          + "ROADSECT_FROM: 路段起点,TPI: 指数,name: 名称,dir: 方向,from: 起点,to: 终点,"
          + "type: 类型,conindex: 指数,DIR_FNAME: 方向,JAM_TIME_RATE: 拥堵时间比,"
          + "ROAD_TYPE_FID: 类型,ROADSECT_FID: 路段id,DISTRICT_FNAME: 行政区,"
          + "JAM_SPEED: 拥堵阈值(km/h),ALLPERIOD: 样本总量,ROADSECT_FNAME: 路段名,"
          + "period: 时间片,time: 日期,tpi: 指数,speed: 速度(km/h),JAM_HOUR: 拥堵时长,"
          + "ROADSECT_NAME: 路段名,JAM_PERIOD: 拥堵时段,JAM_WEIGHT: 拥堵权值,DIRNAME: 方向,"
          + "SPOTSNAME: 目的地,SAMPLE_VEHICLE: 样本车辆数,PARK_ZONE_NAME: 片区";*/

      String[] str = excelpara.split(",");
      Map<String, Object> excelparamap = new HashMap<>();
      for (int i = 0; i < str.length; i++) {
        String[] strr = str[i].split(":");
        excelparamap.put(strr[0], strr[1]);
      }
      excelparamap.put("FID", "Id");
      excelparamap.put("FNAME", "名称");
      switch (index) {
        case "tpi":
          excelparamap.put("y", "指数");
          break;
        case "avg_speed":
          excelparamap.put("y", "速度(km/h)");
          break;
        case "jam_length_ratio":
          excelparamap.put("y", "拥堵里程比例");
          break;
        case "jam_space_time":
          excelparamap.put("y", "拥堵时空值(公里*小时)");
          break;
        case "avg_jam_length":
          excelparamap.put("y", "平均拥堵里程(km)");
          break;
        case "flow":
          excelparamap.put("y", "流量/pcu");
          break;
        case "flow_hour_coefficient":
          excelparamap.put("y", "流量小时系数");
          break;
        case "delay":
          excelparamap.put("y", "延误(s)");
          break;
        case "bus_speed":
          excelparamap.put("y", "速度(km/h)");
          break;
        case "speed_rate":
          excelparamap.put("y", "速度比");
          break;
        default:
          break;
      }
      switch (timeprecision) {
        case "five_min":
          excelparamap.put("x", "5分钟");
          break;
        case "fifteen_min":
          excelparamap.put("x", "15分钟");
          break;
        case "hour":
          excelparamap.put("x", "小时");
          break;
        case "day":
          excelparamap.put("x", "日");
          break;
        case "week":
          excelparamap.put("x", "周");
          break;
        case "month":
          excelparamap.put("x", "月");
          break;
        case "year":
          excelparamap.put("x", "年");
          break;
        default:
          break;
      }

      //替换道路类型的数字为实际含义
      int roadtypeposition = 0;
      for (int i = 0; i < keys.size(); i++) {
        cell = row.createCell(i);
        cell.setCellValue(excelparamap.get(keys.get(i)).toString());
        //道路类型字段未统一，可能是下列两个之一
        if (keys.get(i).equals("ROAD_TYPE_FID") || (keys.get(i).equals("type")
            && roadtypeposition == 0)) {
          roadtypeposition = i;
        }
      }
      for (int i = 0; i < listResult.size(); i++) {
        row = sheet.createRow(i + 1);
        for (int j = 0; j < keys.size(); j++) {
          cell = row.createCell(j);
          if (listResult.get(i).get(keys.get(j)) != null) {
            String v = listResult.get(i).get(keys.get(j)).toString();
            if (roadtypeposition != 0 && j == roadtypeposition) {
              switch (Integer.parseInt(v)) {
                case 1:
                  v = "高速公路";
                  break;
                case 2:
                  v = "快速路";
                  break;
                case 3:
                  v = "主干路";
                  break;
                case 4:
                  v = "次干路";
                  break;
                case 5:
                  v = "支路";
                  break;
                case 22:
                  v = "一级公路";
                  break;
                case 31:
                  v = "主干路";
                  break;
                case 32:
                  v = "二级公路";
                  break;
                case 41:
                  v = "次干路";
                  break;
                case 42:
                  v = "三级公路";
                  break;
                default:
                  v = "支路";
                  break;
              }
            }
            try {
              double dv = Double.parseDouble(v);
              cell.setCellValue(dv);
            } catch (Exception ex) {
              cell.setCellValue(v);
            }
          } else {
            cell.setCellValue("");
          }
        }
      }
      FileOutputStream fos;
      try {
        File file = new File(filedir);

        // 创建目录
        if (!file.exists()) {
          file.mkdirs();// 目录不存在的情况下，创建目录。
        }
        fos = new FileOutputStream(filedir + "/" + filename);
        workbook.write(fos);
        fos.flush();
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      File file = new File(filedir + "/" + filename);
      if (file.exists()) {
        return filepath;
      } else {
        return "";
      }
    } else {
      return "";
    }
  }

  /**
   * exportToExcel.
   */
  public static String exportToExcel(List<Map<String, Object>> listResult, String fileName) {
    if (listResult != null && listResult.size() != 0) {
      Date now = new Date();
      SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
      String filename = fileName;
      String filedir = "";
      String filepath = "";
      if ("".equals(filename) || null == filename) {
        filename = Math.round(Math.random() * 999999) + sf.format(now) + ".xlsx";
      } else {
        filename = fileName + ".xlsx";
      }
      /*String classPath = ExcelUtils.class.getClassLoader().getResource("").getPath();
      try {
        classPath = URLDecoder.decode(classPath, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }

      filedir = classPath.substring(0, classPath.indexOf("WEB-INF"));
      filedir = filedir.substring(1, filedir.length()) + "files";*/

      filedir = IoUtils.getRootPath() + "files";

      filepath = "files/" + filename;
      XSSFWorkbook workbook = new XSSFWorkbook(); //产生工作簿对象
      XSSFSheet sheet = workbook.createSheet(); //产生工作表对象

      List<String> keys = getMapKeys(listResult.get(0));

      XSSFRow row = sheet.createRow(0);
      XSSFCell cell = null;
      for (int i = 0; i < keys.size(); i++) {
        cell = row.createCell(i);
        cell.setCellValue(keys.get(i));
      }
      for (int i = 0; i < listResult.size(); i++) {
        row = sheet.createRow(i + 1);
        for (int j = 0; j < keys.size(); j++) {
          cell = row.createCell(j);
          if (listResult.get(i).get(keys.get(j)) != null) {
            String v = listResult.get(i).get(keys.get(j)).toString();
            try {
              double dv = Double.parseDouble(v);
              cell.setCellValue(dv);
            } catch (Exception ex) {
              cell.setCellValue(v);
            }
          } else {
            cell.setCellValue("");
          }
        }
      }
      FileOutputStream fos;
      try {
        File file = new File(filedir);

        // 创建目录
        if (!file.exists()) {
          file.mkdirs();// 目录不存在的情况下，创建目录。
        }
        fos = new FileOutputStream(filedir + "/" + filename);
        workbook.write(fos);
        fos.flush();
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      File file = new File(filedir + "/" + filename);
      if (file.exists()) {
        return filepath;
      } else {
        return "";
      }
    } else {
      return "";
    }
  }

  /**
   * exportToExcel.
   */
  public static String exportToExcel(List<Map<String, Object>> listResult) {

    if (listResult != null && listResult.size() != 0) {
      Date now = new Date();
      SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
      String filename = "";
      String filedir = "";
      String filepath = "";
      filename = Math.round(Math.random() * 999999) + sf.format(now) + ".xlsx";
      /*String classPath = ExcelUtils.class.getClassLoader().getResource("").getPath();
      try {
        classPath = URLDecoder.decode(classPath, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }

      filedir = classPath.substring(0, classPath.indexOf("WEB-INF"));
      filedir = filedir.substring(1, filedir.length()) + "files";*/

      filedir = IoUtils.getRootPath() + "files";

      filepath = "files/" + filename;
      XSSFWorkbook workbook = new XSSFWorkbook(); //产生工作簿对象
      XSSFSheet sheet = workbook.createSheet(); //产生工作表对象

      List<String> keys = getMapKeys(listResult.get(0));

      XSSFRow row = sheet.createRow(0);
      XSSFCell cell = null;
      for (int i = 0; i < keys.size(); i++) {
        cell = row.createCell(i);
        cell.setCellValue(keys.get(i));
      }
      for (int i = 0; i < listResult.size(); i++) {
        row = sheet.createRow(i + 1);
        for (int j = 0; j < keys.size(); j++) {
          cell = row.createCell(j);
          if (listResult.get(i).get(keys.get(j)) != null) {
            String v = listResult.get(i).get(keys.get(j)).toString();
            try {
              double dv = Double.parseDouble(v);
              cell.setCellValue(dv);
            } catch (Exception ex) {
              cell.setCellValue(v);
            }
          } else {
            cell.setCellValue("");
          }
        }
      }
      FileOutputStream fos;
      try {
        File file = new File(filedir);

        // 创建目录
        if (!file.exists()) {
          file.mkdirs();// 目录不存在的情况下，创建目录。
        }
        fos = new FileOutputStream(filedir + "/" + filename);
        workbook.write(fos);
        fos.flush();
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      File file = new File(filedir + "/" + filename);
      if (file.exists()) {
        return filepath;
      } else {
        return "";
      }
    } else {
      return "";
    }
  }

  /**
   * exportToExcel.
   */
  public static String exportToExcel(List<Map<String, Object>> listResult, String xstring,
      String ystring) {

    if (listResult != null && listResult.size() != 0) {
      Date now = new Date();
      SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
      String filename = "";
      String filedir = "";
      String filepath = "";
      filename = Math.round(Math.random() * 999999) + sf.format(now) + ".xlsx";
      /*String classPath = ExcelUtils.class.getClassLoader().getResource("").getPath();
      try {
        classPath = URLDecoder.decode(classPath, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }

      filedir = classPath.substring(0, classPath.indexOf("WEB-INF"));
      filedir = filedir.substring(1, filedir.length()) + "files";*/

      filedir = IoUtils.getRootPath() + "files";

      filepath =
          "files/" + getTimeByStr("yyyy") + "/" + getTimeByStr("MM") + "/" + getTimeByStr("dd")
              + "/" + filename;
      XSSFWorkbook workbook = new XSSFWorkbook(); //产生工作簿对象
      XSSFSheet sheet = workbook.createSheet(); //产生工作表对象

      List<String> keys = getMapKeys(listResult.get(0));
      XSSFRow row = sheet.createRow(0);
      XSSFCell cell = null;
      for (int i = 0; i < keys.size(); i++) {
        cell = row.createCell(i);
        if (keys.get(i).toString().equals("x")) {
          cell.setCellValue(xstring);
        } else {
          cell.setCellValue(ystring);
        }
      }
      for (int i = 0; i < listResult.size(); i++) {
        row = sheet.createRow(i + 1);
        for (int j = 0; j < keys.size(); j++) {
          cell = row.createCell(j);
          if (listResult.get(i).get(keys.get(j)) != null) {
            String v = listResult.get(i).get(keys.get(keys.size() - 1 - j)).toString();
            try {
              double dv = Double.parseDouble(v);
              cell.setCellValue(dv);
            } catch (Exception ex) {
              cell.setCellValue(v);
            }
          } else {
            cell.setCellValue("");
          }
        }
      }
      FileOutputStream fos;
      try {
        File file = new File(filedir);

        // 创建目录
        if (!file.exists()) {
          file.mkdirs();// 目录不存在的情况下，创建目录。
        }
        fos = new FileOutputStream(filedir + "/" + filename);
        workbook.write(fos);
        fos.flush();
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      File file = new File(filedir + "/" + filename);
      if (file.exists()) {
        return filepath;
      } else {
        return "";
      }
    } else {
      return "";
    }
  }

  /**
   * getTimeByStr.
   */
  public static String getTimeByStr(String string) {
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat(string);
    String time = sdf.format(date);
    return time;
  }

  /**
   * getMapKeys.
   */
  public static List<String> getMapKeys(Map<String, Object> map) {
    List<String> keys = new ArrayList<String>();
    Set<String> set = map.keySet();
    Iterator<String> iter = set.iterator();
    while (iter.hasNext()) {
      keys.add(iter.next().toString());
    }

    return keys;
  }

}
