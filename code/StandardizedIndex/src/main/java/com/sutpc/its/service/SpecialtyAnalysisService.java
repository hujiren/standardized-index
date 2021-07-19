package com.sutpc.its.service;

import com.sutpc.its.constant.FileColumnIndexConstants;
import com.sutpc.its.dao.ICommonQueryDao;
import com.sutpc.its.dao.ISpecialtyAnalysisDao;
import com.sutpc.its.dto.RoadCharacteristicDto;
import com.sutpc.its.dto.RoadLevelDistributionDto;
import com.sutpc.its.dto.RoadPortrayalTagDto;
import com.sutpc.its.dto.RoadSectionJamCountDto;
import com.sutpc.its.dto.RoadSixDByIdDto;
import com.sutpc.its.dto.RoadSixDDto;
import com.sutpc.its.dto.RoadsectDistributionDto;
import com.sutpc.its.model.RoadLevelDistributionEntity;
import com.sutpc.its.model.RoadPortraitEntity;
import com.sutpc.its.model.RoadPortrayalRoadsectEntity;
import com.sutpc.its.model.RoadPortrayalTagEntity;
import com.sutpc.its.model.StandardizedPageModel;
import com.sutpc.its.tools.ExcelUtils;
import com.sutpc.its.tools.PeriodUtils;
import com.sutpc.its.tools.TpiUtils;
import com.sutpc.its.tools.Utils;
import com.sutpc.its.tools.time.TimeUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class SpecialtyAnalysisService {

  @Autowired
  private ISpecialtyAnalysisDao specialtyAnalysisDao;

  @Autowired
  private ICommonQueryDao commonQueryDao;

  /**
   * .
   */

  public Map<String, Object> getRoadWeekCompare(Map<String, Object> map) {
    // DataSourceContextHolder.setDbType("TPMODEL");
    Map<String, Object> dataMap = new HashMap<String, Object>();
    List<Map<String, Object>> data2 = new ArrayList<Map<String, Object>>();
    String starttime = map.get("starttime") == null ? "" : map.get("starttime").toString();
    String endtime = map.get("endtime") == null ? "" : map.get("endtime").toString();
    int startperiod = 0;
    int endperiod = 0;
    if (starttime != null && !"".equals(starttime)) {
      int hour = Integer.parseInt(starttime.split(":")[0]);
      int min = Integer.parseInt(starttime.split(":")[1]);
      if (min % 5 == 0) {
        startperiod = hour * 12 + min / 5 + 1;
      } else {
        startperiod = hour * 12 + min / 5 + 2;
      }
    }
    if (endtime != null && !"".equals(endtime)) {
      int hour = Integer.parseInt(endtime.split(":")[0]);
      int min = Integer.parseInt(endtime.split(":")[1]);
      if (min % 5 == 0) {
        endperiod = hour * 12 + min / 5;
      } else {
        endperiod = hour * 12 + min / 5 + 1;
      }
    }
    map.put("startperiod", startperiod);
    map.put("endperiod", endperiod);
    String date = map.get("time").toString();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date dateTemp;
    String date2 = "";
    try {
      dateTemp = sdf.parse(date);
      dateTemp = new Date(dateTemp.getTime() - 7 * 24 * 60 * 60 * 1000);
      date2 = sdf.format(dateTemp);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String filepath = "";
    List<Map<String, Object>> data1 = new ArrayList<Map<String, Object>>();
    data1 = specialtyAnalysisDao.getRoadWeekCompare(map);
    /*map.put("time", date2);
    data2 = specialtyAnalysisDao.getRoadWeekCompare(map);

    dataMap.put("data2", data2);
    List<Map<String, Object>> data = new ArrayList<>();
    if ((data1 != null && data1.size() != 0) || (data2 != null && data2.size() != 0)) {
      for (int i = 0; i < data1.size(); i++) {
        data.add(data1.get(i));
      }
      for (int i = 0; i < data2.size(); i++) {
        data.add(data2.get(i));
      }
      //String excelName = "空间剖面图_"+date+"_表_";
      //String fileName = Utils.getDownloadFileName(map, excelName,commonQueryDao);
      //filepath = ExcelUtils.exportToExcel(data,fileName);
      // filepath = exportWeekCompareData(data1,data2);
    }*/
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("data", data1);
    result.put("filepath", filepath);
    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getRoadWeekChange(Map<String, Object> map) {
    // DataSourceContextHolder.setDbType("TPMODEL");
    int id = Integer.parseInt(map.get("roadsect_fid").toString());
    Map<String, Object> param = new HashMap<String, Object>();
    String weekNo = map.get("week_no").toString();
    param.put("year", weekNo.substring(0, 4));
    int startIndex = weekNo.indexOf("年") + 1;
    int endIndex = weekNo.indexOf("周");
    param.put("week_no", weekNo.substring(startIndex, endIndex));
    Map<String, Object> resultMap = specialtyAnalysisDao.getWeekDays(param);
    map.put("startdate", resultMap.get("FROM_DAY"));
    map.put("enddate", resultMap.get("TO_DAY"));
    List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
    listMap = specialtyAnalysisDao.getRoadWeekChangeData(map);
    String filepath = "";
    List<String> data = new ArrayList<String>();
    data = changeWeekResult(listMap, Integer.parseInt(map.get("roadsect_fid").toString()));

    String startdate = map.get("startdate").toString();
    String enddate = map.get("enddate").toString();
    String excelName = "道路运行周变图_" + startdate + "-" + enddate + "_表_";
    map.put("type", "道路运行周变图");
    String fileName = Utils.getDownloadFileName(map, excelName, commonQueryDao);
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("filepath", ExcelUtils
        .exportToExcel(FileColumnIndexConstants.keysForRoadWeekChange, listMap, fileName, "", ""));
    result.put("data", data);

    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getJamSpaceTimeData(Map<String, Object> map) {
    Map<String, Object> result = new LinkedHashMap();
    String filepath = "";

    List<Map<String, Object>> data = specialtyAnalysisDao.getJamSpaceTimeData(map);
    if (data != null && data.size() != 0) {
      data = getStandardTimeSpace(data, "15分钟");
      String excelName = "拥堵时空图_" + map.get("startdate") + "-" + map.get("enddate") + "_表_";
      String fileName = Utils.getDownloadFileName(map, excelName, commonQueryDao);

      filepath = ExcelUtils
          .exportToExcel(FileColumnIndexConstants.keysForJamSpaceTimeData, data, fileName, "", "");
      double y = 0;
      int type;
      double exponent;
      for (int i = 0; i < data.size(); i++) {
        if (data.get(i).get("y") != null) {
          type = Integer.parseInt(data.get(i).get("type").toString());
          y = Double.parseDouble(data.get(i).get("y").toString());
          exponent = getExponent(y, type);
          if (exponent <= 2) {
            data.get(i).put("y", "c5");
          } else if (exponent <= 4) {
            data.get(i).put("y", "c4");
          } else if (exponent <= 6) {
            data.get(i).put("y", "c3");
          } else if (exponent <= 8) {
            data.get(i).put("y", "c2");
          } else {
            if (i % 96 >= 0 && i % 96 <= 23) {
              data.get(i).put("y", "c5");
            } else {
              data.get(i).put("y", "c1");
            }
          }
        } else {
          data.get(i).put("y", "c5");
        }
      }
    }
    result.put("data", data);
    result.put("filepath", filepath);
    return result;
  }

  /**
   * 5分钟.
   */
  public Map<String, Object> getJamSpaceTimeFiveMinData(Map<String, Object> map) {
    Map<String, Object> result = new LinkedHashMap();
    String filepath = "";
    List<Map<String, Object>> data = specialtyAnalysisDao.getJamSpaceTimeFiveMinData(map);
    if (data != null && data.size() != 0) {
      data = getStandardTimeSpace(data, "5分钟");
      String excelName = "拥堵时空图_" + map.get("startdate") + "-" + map.get("enddate") + "_表_";
      String fileName = Utils.getDownloadFileName(map, excelName, commonQueryDao);

      filepath = ExcelUtils
          .exportToExcel(FileColumnIndexConstants.keysForJamSpaceTimeData, data, fileName, "", "");
      double y = 0;
      int type;
      double exponent;
      for (int i = 0; i < data.size(); i++) {
        if (data.get(i).get("y") != null) {
          type = Integer.parseInt(data.get(i).get("type").toString());
          y = Double.parseDouble(data.get(i).get("y").toString());
          exponent = getExponent(y, type);
          if (exponent <= 2) {
            data.get(i).put("y", "c5");
          } else if (exponent <= 4) {
            data.get(i).put("y", "c4");
          } else if (exponent <= 6) {
            data.get(i).put("y", "c3");
          } else if (exponent <= 8) {
            data.get(i).put("y", "c2");
          } else {
            if (i % 96 >= 0 && i % 96 <= 23) {
              data.get(i).put("y", "c5");
            } else {
              data.get(i).put("y", "c1");
            }
          }
        } else {
          data.get(i).put("y", "c5");
        }
      }
    }
    result.put("data", data);
    result.put("filepath", filepath);
    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getJamRoadsectRanking(Map<String, Object> map) {
    Map<String, Object> result = new LinkedHashMap();
    // 转换行政区ID
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
    String index = map.get("index") + "";
    // 转换开始时间、结束时间
    map = PeriodUtils.time2period(map);
    // 如果是拥堵时间比
    if (index.equals("jam_time_rate")) {
      List<Map<String, Object>> list = specialtyAnalysisDao.getJamRanking_JamTimeRate(map);
      // ** 生成excel文件
      String excelName = "拥堵路段分析_拥堵时间比_" + map.get("startdate") + "_" + map.get("enddate") + "_表_";
      String fileName = Utils.getDownloadFileName(map, excelName, commonQueryDao);
      result.put("data", list);
      result.put("filepath", ExcelUtils
          .exportToExcel(FileColumnIndexConstants.keysForJamRoadsectRankingJamTimeRate, list,
              fileName, "", ""));
      return result;
    }
    // 如果是交通指数
    if (index.equals("tpi")) {
      // ** 设置样本量筛选规则 TPI-562
      String startDateString = String.valueOf(map.get("startdate"));
      String endDateString = String.valueOf(map.get("enddate"));
      int daysBetweenDates = TimeUtil.getDaysBetweenDates(startDateString, endDateString);
      int sampleSize = 5000;
      if (daysBetweenDates < 365) {
        sampleSize = 1000;
      }
      if (daysBetweenDates < 30) {
        sampleSize = 300;
      }
      if (daysBetweenDates < 7) {
        sampleSize = 100;
      }
      map.put("sampleSize", sampleSize);
      log.info("[设置样本量筛选规则 TPI-562,={},sampleSize={}]", daysBetweenDates, sampleSize);
      // sql 查询
      List<Map<String, Object>> list = specialtyAnalysisDao.getJamRanking_Tpi(map);
      // 生成excel文件
      String excelName = "拥堵路段分析_交通指数_" + map.get("startdate") + "_" + map.get("enddate") + "_表_";
      String fileName = Utils.getDownloadFileName(map, excelName, commonQueryDao);
      result.put("data", list);
      result.put("filepath", ExcelUtils
          .exportToExcel(FileColumnIndexConstants.keysForJamRoadsectRankingTpi, list, fileName, "",
              ""));
      return result;
    }
    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getParkingTollRoadData(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
    map = PeriodUtils.time2period(map);
    List<Map<String, Object>> list = specialtyAnalysisDao.getParkingTollRoad(map);
    List<Map<String, Object>> listMap = specialtyAnalysisDao.getParkingTollRoadMapData(map);
    String fileName = "停车收费路段_" + map.get("startdate") + "_" + map.get("enddate") + "_表_";
    String excelName = Utils.getDownloadFileName(map, fileName, commonQueryDao);

    result.put("filepath", ExcelUtils
        .exportToExcel(FileColumnIndexConstants.keysForParkingTollRoadData, list, excelName, "",
            ""));
    result.put("data", list);
    result.put("map", listMap);
    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getHolidayInitData(Map<String, Object> map) {
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("holiday", specialtyAnalysisDao.getAllHoiday(map));
    resultMap.put("spotindexcategory", specialtyAnalysisDao.getAllSpotindexCategory(map));
    resultMap.put("spotindex", specialtyAnalysisDao.getAllSpotindex(map));
    return resultMap;
  }

  /**
   * 转换成周变数据.
   */
  public List<String> changeWeekResult(List<Map<String, Object>> listMap, int id) {
    List<String> result = new ArrayList<String>();
    String key = "";
    String color = "";
    double exponent;
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 24; j++) {
        if (j < 9) {
          key = "w" + (i + 1) + "-0" + (j + 1) + "-";
        } else {
          key = "w" + (i + 1) + "-" + (j + 1) + "-";
        }
        //加此判断主要是为了防止由于数据缺失造成的错误。当某个时间片数据缺失时，此举可视为其指数为0，以保证代码的顺畅
        if ((24 * i + j) >= listMap.size()) {
          exponent = 0;
        } else {
          exponent = Double.parseDouble(listMap.get(24 * i + j).get("tpi").toString());
        }
        if (exponent >= 0 && exponent <= 2) {
          color = "s1";
        } else if (exponent > 2 && exponent <= 4) {
          color = "s2";
        } else if (exponent > 4 && exponent <= 6) {
          color = "s3";
        } else if (exponent > 6 && exponent <= 8) {
          color = "s4";
        } else if (exponent > 8 && exponent <= 10) {
          color = "s5";
        }
        result.add(key + color);
      }
    }
    return result;
  }

  /**
   * 周变数据转成标准24时间片.
   */
  public List<Map<String, Object>> getStandardRoadWeekData(List<Map<String, Object>> listMap) {
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    if (listMap.size() != 168) {
      int period = 0;
      int time = 0;
      int resultSize;
      int j = 1;
      for (int i = 0; i < listMap.size(); i++) {
        time = Integer.parseInt(listMap.get(i).get("time").toString());
        period = Integer.parseInt(listMap.get(i).get("period").toString());
        if (period == 1 && j != 1) {
          for (; j <= 24; j++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("time", time);
            map.put("period", j);
            map.put("speed", null);
            result.add(map);
          }
          j = 1;
        }
        while (period != j) {
          if (j > period) {
            period = j;
          }
          for (; j < period; j++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("time", time);
            map.put("period", j);
            map.put("speed", null);
            result.add(map);
          }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("time", time);
        map.put("period", j);
        if (listMap.get(i).containsKey("speed")) {
          map.put("speed", listMap.get(i).get("speed"));
        } else {
          map.put("speed", null);
        }
        result.add(map);
        if (j == 24) {
          j = 1;
        } else {
          j++;
        }
      }
      resultSize = result.size();
      int lostAll;
      if (resultSize < 168) {
        lostAll = 168 - resultSize;
        while (lostAll >= 0) {
          Map<String, Object> map = new HashMap<String, Object>();
          map.put("time", time);
          map.put("period", period);
          map.put("speed", null);
          result.add(map);
          lostAll--;
          if (period == 24) {
            period = 1;
          } else {
            period++;
          }
        }
      }
    } else {
      result = listMap;
    }
    return result;
  }

  /**
   * 拥堵时空转成标准时间片.
   */
  public List<Map<String, Object>> getStandardTimeSpace(List<Map<String, Object>> listMap,
      String timeprecision) {
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    if (listMap != null && listMap.size() != 0) {
      if (!"".equals(timeprecision)) {
        int standardSize = 0;
        if ("5分钟".equals(timeprecision)) {
          standardSize = 288;
        } else if ("15分钟".equals(timeprecision)) {
          standardSize = 96;
        } else if ("时".equals(timeprecision)) {
          standardSize = 24;
        }
        int x = 0;
        int j = 1;
        String nameTemp = listMap.get(0).get("name").toString();
        String dirTemp = listMap.get(0).get("dir").toString();
        String fromTemp = listMap.get(0).get("from").toString();
        String toTemp = listMap.get(0).get("to").toString();
        int count = 1;
        for (int i = 0; i < listMap.size(); i++) {
          if (!nameTemp.equals(listMap.get(i).get("name").toString())
              || !dirTemp.equals(listMap.get(i).get("dir").toString())
              || !fromTemp.equals(listMap.get(i).get("from").toString())
              || !toTemp.equals(listMap.get(i).get("to").toString())) {
            count++;
            nameTemp = listMap.get(i).get("name").toString();
            dirTemp = listMap.get(i).get("dir").toString();
            fromTemp = listMap.get(i).get("from").toString();
            toTemp = listMap.get(i).get("to").toString();
          }
        }

//        Set<String> stringSet = new HashSet<>();
//        listMap.forEach(map -> stringSet.add(new StringBuilder().append(map.get("name")).append(map.get("dir"))
//                .append(map.get("from")).append(map.get("to")).toString()));
//        int count = stringSet.size();
//
//        if(listMap.size() != count * standardSize){
//
//          Map<String, Object> firstElementMap = listMap.get(0);
//          StringBuilder sb = new StringBuilder();
//          sb.append(firstElementMap.get("name")).append(firstElementMap.get("dir"))
//                  .append(firstElementMap.get("from")).append(firstElementMap.get("to"));
//          StringBuilder sb2 = new StringBuilder();
//
//          for(int i = 1; i < listMap.size(); i++){
//
//            sb2.delete(0, sb2.length());
//            Map<String, Object> everyElementMap = listMap.get(i);
//            sb2.append(everyElementMap.get("name")).append(everyElementMap.get("dir"))
//                    .append(everyElementMap.get("from")).append(everyElementMap.get("to"));
//
//            if(!sb.toString().equals(sb2.toString())){
//              x = Integer.parseInt(listMap.get(i - 1).get("x").toString());
//              if(x < standardSize){
//                int lostSize = standardSize - x;
//                while (lostSize > 0) {
//                  //TODO 补偿数据
//                  Map<String, Object> map = new LinkedHashMap();
//                  map.put("name", listMap.get(i - 1).get("name"));
//                  map.put("dir", listMap.get(i - 1).get("dir"));
//                  map.put("from", listMap.get(i - 1).get("from"));
//                  map.put("to", listMap.get(i - 1).get("to"));
//                  map.put("type", listMap.get(i - 1).get("type"));
//                  map.put("conindex", 0);
//                  map.put("x", PeriodUtils.getTimeByPeriod(timeprecision, x + 1));
//                  map.put("y", null);
//                  result.add(map);
//                  lostSize--;
//                  x++;
//                }
//              }
//              x = Integer.parseInt(listMap.get(i).get("x").toString());
//              j = 1;
//              sb = sb2;
//            }
//          }
//        }

        nameTemp = listMap.get(0).get("name").toString();
        dirTemp = listMap.get(0).get("dir").toString();
        fromTemp = listMap.get(0).get("from").toString();
        toTemp = listMap.get(0).get("to").toString();
        if (listMap.size() != count * standardSize) {
          for (int i = 0; i < listMap.size(); i++) {
            if (nameTemp.equals(listMap.get(i).get("name").toString())
                && dirTemp.equals(listMap.get(i).get("dir").toString())
                && fromTemp.equals(listMap.get(i).get("from").toString())
                && toTemp.equals(listMap.get(i).get("to").toString())) {
              x = Integer.parseInt(listMap.get(i).get("x").toString());
            } else {
                if (x < standardSize) {
                int lostSize = standardSize - x;
                while (lostSize > 0) {
                    //TODO 补偿数据
                  Map<String, Object> map = new LinkedHashMap();
                  map.put("name", listMap.get(i - 1).get("name"));
                  map.put("dir", listMap.get(i - 1).get("dir"));
                  map.put("from", listMap.get(i - 1).get("from"));
                  map.put("to", listMap.get(i - 1).get("to"));
                  map.put("type", listMap.get(i - 1).get("type"));
//                  map.put("conindex", listMap.get(i - 1).get("conindex"));
                    //丢失的数据直接给0
                  map.put("conindex", 0);
                  map.put("x", PeriodUtils.getTimeByPeriod(timeprecision, x + 1));
                  map.put("y", null);
                  result.add(map);
                  lostSize--;
                  x++;
                  }
                }
              x = Integer.parseInt(listMap.get(i).get("x").toString());
              j = 1;
              nameTemp = listMap.get(i).get("name").toString();
              dirTemp = listMap.get(i).get("dir").toString();
              fromTemp = listMap.get(i).get("from").toString();
              toTemp = listMap.get(i).get("to").toString();
            }
            while (x != j) {
              for (; j < x; j++) {
                //TODO 补偿数据
                Map<String, Object> map = new LinkedHashMap();
                map.put("name", listMap.get(i).get("name"));
                map.put("dir", listMap.get(i).get("dir"));
                map.put("from", listMap.get(i).get("from"));
                map.put("to", listMap.get(i).get("to"));
                map.put("type", listMap.get(i).get("type"));
//                map.put("conindex", listMap.get(i).get("conindex"));
                map.put("conindex", 0);
                map.put("x", PeriodUtils.getTimeByPeriod(timeprecision, j));
                map.put("y", null);
                result.add(map);
              }
            }
            Map<String, Object> map = new LinkedHashMap();
              map.put("name", listMap.get(i).get("name"));
              map.put("dir", listMap.get(i).get("dir"));
              map.put("from", listMap.get(i).get("from"));
              map.put("to", listMap.get(i).get("to"));
              map.put("type", listMap.get(i).get("type"));
              map.put("conindex", listMap.get(i).get("conindex"));
              map.put("x", PeriodUtils.getTimeByPeriod(timeprecision, j));
            if (listMap.get(i).containsKey("y")) {
              map.put("y", listMap.get(i).get("y"));
            } else {
              map.put("y", null);
            }
            result.add(map);
            if (j == standardSize) {
              j = 1;
            } else {
              j++;
            }
          }
          if (result.size() != count * standardSize) {
            int lostDataSize = count * standardSize - result.size();
            while (lostDataSize > 0) {
              //TODO 少部分数据补偿
              Map<String, Object> map = new LinkedHashMap();
              map.put("name", listMap.get(listMap.size() - 1).get("name"));
              map.put("dir", listMap.get(listMap.size() - 1).get("dir"));
              map.put("from", listMap.get(listMap.size() - 1).get("from"));
              map.put("to", listMap.get(listMap.size() - 1).get("to"));
              map.put("type", listMap.get(listMap.size() - 1).get("type"));
              map.put("conindex", listMap.get(listMap.size() - 1).get("conindex"));
              map.put("x", PeriodUtils.getTimeByPeriod(timeprecision, x + 1));
              map.put("y", null);
              result.add(map);
              lostDataSize--;
              x++;
            }
          }
        } else {
          int k = 0;
          for (int i = 0; i < listMap.size(); i++) {
            Map<String, Object> map = new LinkedHashMap();
            map.put("name", listMap.get(i).get("name"));
            map.put("dir", listMap.get(i).get("dir"));
            map.put("from", listMap.get(i).get("from"));
            map.put("to", listMap.get(i).get("to"));
            map.put("type", listMap.get(i).get("type"));
            map.put("conindex", listMap.get(i).get("conindex"));
            map.put("x", PeriodUtils.getTimeByPeriod(timeprecision, k + 1));
            if (listMap.get(i).containsKey("y")) {
              map.put("y", listMap.get(i).get("y"));
            } else {
              map.put("y", null);
            }
            result.add(map);
            if (k == standardSize - 1) {
              k = 0;
            } else {
              k++;
            }
          }
        }
      } else {
        result = listMap;
      }
    }
    return result;
  }

  /**
   * 根据平均车速求指数.
   */
  public double getExponent(double speed, int type) {
    double exponent = 0;
    if (speed != 0) {
      if (type == 1 || type == 2) {
        if (speed <= 15) {
          exponent = 10 - ((speed - 0) / (15 - 0)) * 2;
        } else if (speed <= 30) {
          exponent = 8 - ((speed - 15) / (30 - 15)) * 2;
        } else if (speed <= 45) {
          exponent = 6 - ((speed - 30) / (45 - 30)) * 2;
        } else if (speed <= 60) {
          exponent = 4 - ((speed - 45) / (60 - 45)) * 2;
        } else if (speed > 60) {
          if (type == 1) {
            exponent = 2 - ((speed - 60) / (100 - 60)) * 2;
            if (exponent < 0) {
              exponent = 0;
            }
          } else {
            exponent = 2 - ((speed - 60) / (80 - 60)) * 2;
            if (exponent < 0) {
              exponent = 0;
            }
          }
        }
      } else if (type == 3) {
        if (speed <= 10) {
          exponent = 10 - ((speed - 0) / (10 - 0)) * 2;
        } else if (speed <= 20) {
          exponent = 8 - ((speed - 10) / (20 - 10)) * 2;
        } else if (speed <= 30) {
          exponent = 6 - ((speed - 20) / (30 - 20)) * 2;
        } else if (speed <= 40) {
          exponent = 4 - ((speed - 30) / (40 - 30)) * 2;
        } else if (speed > 40) {
          exponent = 2 - ((speed - 40) / (60 - 40)) * 2;
          if (exponent < 0) {
            exponent = 0;
          }
        }
      } else if (type == 4 || type == 5) {
        if (speed <= 10) {
          exponent = 10 - ((speed - 0) / (10 - 0)) * 2;
        } else if (speed <= 15) {
          exponent = 8 - ((speed - 10) / (15 - 10)) * 2;
        } else if (speed <= 20) {
          exponent = 6 - ((speed - 15) / (20 - 15)) * 2;
        } else if (speed <= 30) {
          exponent = 4 - ((speed - 20) / (30 - 20)) * 2;
        } else if (speed > 30) {
          if (type == 4) {
            exponent = 2 - ((speed - 30) / (50 - 30)) * 2;
            if (exponent < 0) {
              exponent = 0;
            }
          } else {
            exponent = 2 - ((speed - 30) / (40 - 30)) * 2;
            if (exponent < 0) {
              exponent = 0;
            }
          }
        }
      }
    }
    DecimalFormat df = new DecimalFormat("#.0");
    return Double.parseDouble(df.format(exponent));
  }

  /**
   * .
   */
  public String getHolidayName(String hids) {
    Map<String, Object> map = new HashMap<>();
    map.put("1", "元旦");
    map.put("2", "春节");
    map.put("3", "清明节");
    map.put("4", "劳动节");
    map.put("5", "端午节");
    map.put("6", "中秋节");
    map.put("7", "国庆节");
    map.put("8", "开学");
    return map.get(hids) + "";
  }

  /**
   * .
   */
  public String getRoadName(String roadTypes) {
    Map<String, Object> map = new HashMap<>();
    map.put("1", "高速公路");
    map.put("2", "快速路");
    map.put("3", "主干道");
    map.put("4", "次干道");
    String[] index = new String[4];
    int i = 0;
    if (roadTypes.contains("1")) {
      index[i] = "1";
      i = i + 1;
    }
    if (roadTypes.contains("2")) {
      index[i] = "2";
      i = i + 1;
    }
    if (roadTypes.contains("3")) {
      index[i] = "3";
      i = i + 1;
    }
    if (roadTypes.contains("4")) {
      index[i] = "4";
      i = i + 1;
    }
    String roadNames = "";
    for (int j = 0; j < index.length; j++) {
      String roadName = index[j];
      if (roadName != null && roadName != "") {
        roadNames = roadNames + map.get(roadName) + "_";
      }
    }
    return roadNames;
  }

  /**
   * .
   */
  public String getHolidayType(String shtp) {
    Map<String, Object> map = new HashMap<>();
    map.put("1", "节中");
    map.put("-1", "节前");
    return map.get(shtp) + "";
  }

  /**
   * .
   */
  public Map<String, Object> getHolidayQueryResult(Map<String, Object> map) {

    String rtps = map.get("road_type_fid") + "";
    if (!rtps.equals("null")) {
      map.put("road_type_fid_array", rtps.split(","));
    }
    String sppt = map.get("spot_type") + "";
    if (sppt.contains("0")) {
      map.put("otherFlag", 0);
    }
    String spfid = map.get("spot_fid") + "";
    if (!sppt.equals("null")) {
      map.put("spot_type_array", sppt.split(","));
    }
    if (!spfid.equals("null")) {
      map.put("spot_fid_array", spfid.split(","));
    }
    List<Map<String, Object>> list = specialtyAnalysisDao.getHolidayQueryResult(map);

    Object config_district_fid = map.get("config_district_fid");

    String excelFileName =
            getHolidayName(map.get("holiday_fid") + "") + "_" + map.get("year") + "_" + getRoadName(
                    rtps) + getHolidayType(map.get("stage") + "") + "_表_";
    String downloadName = Utils.getDownloadFileName(map, excelFileName, commonQueryDao);
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("data", list);

    List<String> keys = new ArrayList<String>() {
      {
        add("JAM_HOUR");
        add("ROADSECT_NAME");
        add("ROADSECT_TO");
        add("JAM_PERIOD");
        add("ROADSECT_FROM");
        add("ROADSECT_FID");
        add("JAM_WEIGHT");
        add("DIRNAME");
        add("JAM_SPEED");
        add("SPOTSNAME");
      }
    };

    result.put("filepath", ExcelUtils.exportToExcel(keys, list, downloadName, "", ""));
    return result;
  }

  /**
   *.
   */
  public List<Map<String, Object>> getSpotAroundJamRoadsectStatus(Map<String, Object> map) {
    String spotType = map.get("spot_type").toString();
    if (spotType.contains("0")) {
      map.put("otherFlag", 0);
    }
    map.put("road_type_fid", map.get("road_type_fid").toString().split(","));
    map.put("spot_type", spotType.split(","));
    Object config_district_fid = map.get("config_district_fid");
    List<Map<String, Object>> spotAroundJamRoadsectStatus = specialtyAnalysisDao.getSpotAroundJamRoadsectStatus(map);
    return spotAroundJamRoadsectStatus;
  }



  /**
   *.
   */
  public List<Map<String, Object>> getTaxiIndexData(Map<String, Object> map) {
    map.put("hh", new SimpleDateFormat("HH").format(new Date()));
    return specialtyAnalysisDao.getTaxiIndexData(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getNavigationCarIndexData(Map<String, Object> map) {
    map.put("hh", new SimpleDateFormat("HH").format(new Date()));
    return specialtyAnalysisDao.getNavigationCarIndexData(map);
  }

  /**
   * .
   */
  public Map<String, Object> getDistribution(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    Map<String, Object> tripDistance = specialtyAnalysisDao.getTripDistanceData(map);
    Map<String, Object> tripTime = specialtyAnalysisDao.getTripTimeData(map);
    Map<String, Object> tripSpeed = specialtyAnalysisDao.getTripSpeedData(map);
    result.put("tripDistance", tripDistance);
    result.put("tripTime", tripTime);
    result.put("tripSpeed", tripSpeed);
    return result;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getOdDistributionData(Map<String, Object> map) {
    String filepath = null;
    map = PeriodUtils.time2period(map);
    String ids = map.get("vehicle_type").toString();
    map.put("permIds", ids.split(","));
    return specialtyAnalysisDao.getOdDistributionData(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getTripsData(Map<String, Object> map) {
    String filepath = null;
    map = PeriodUtils.time2period(map);
    String ids = map.get("vehicle_type").toString();
    map.put("permIds", ids.split(","));
    return specialtyAnalysisDao.getTripsData(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadsectFlow(Map<String, Object> map) {
    String filepath = null;
    map = PeriodUtils.time2period(map);
    String ids = map.get("vehicle_type").toString();
    map.put("permIds", ids.split(","));
    return specialtyAnalysisDao.getRoadsectFlow(map);
  }

  /**
   * .
   */
  public Map<String, Object> getDistrictLeaveAndArrivalRanking(Map<String, Object> map) {
    String ids = map.get("vehicle_type").toString();
    map.put("permIds", ids.split(","));
    map.put("methods", "leave");
    List<Map<String, Object>> leaveList = specialtyAnalysisDao
        .getDistrictLeaveAndArrivalRanking(map);
    map.put("methods", "arrival");
    List<Map<String, Object>> arrivalList = specialtyAnalysisDao
        .getDistrictLeaveAndArrivalRanking(map);
    Map<String, Object> result = new HashMap<>();
    result.put("leave", leaveList);
    result.put("arrival", arrivalList);
    return result;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getPoiTripData(Map<String, Object> map) {
    String district = map.get("check_type").toString();
    if ("block".equals(district)) {
      map.put("check_type", "poi");
    } else {
      map.put("check_type", "poi_to_district");
    }
    map = PeriodUtils.time2period(map);
    String ids = map.get("vehicle_type").toString();
    map.put("permIds", ids.split(","));
    return specialtyAnalysisDao.getOdDistributionData(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getContrastiveData(Map<String, Object> map) {
    String index = map.get("index").toString();
    if ("tpi".equals(index)) {
      return specialtyAnalysisDao.getContrastiveTpi(map);
    } else if ("avg_speed".equals(index)) {
      return specialtyAnalysisDao.getContrastiveSpeed(map);
    }
    return null;
  }

  /**
   * .
   */
  public Map<String, Object> getTotalData(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    String index = map.get("index").toString();
    if ("tpi".equals(index)) {
      if (true) {
        map.put("timeproperty", "morning_peak");
        Map<String, Object> m = specialtyAnalysisDao.getTotalTpi(map);
        result.put("morning_peak", m.get("tpi"));
      }
      if (true) {
        map.put("timeproperty", "evening_peak");
        Map<String, Object> m = specialtyAnalysisDao.getTotalTpi(map);
        result.put("evening_peak", m.get("tpi"));
      }
      if (true) {
        map.put("timeproperty", "flat_peak");
        Map<String, Object> m = specialtyAnalysisDao.getTotalTpi(map);
        result.put("flat_peak", m.get("tpi"));
      }
      if (true) {
        map.put("timeproperty", "all_day");
        Map<String, Object> m = specialtyAnalysisDao.getTotalTpi(map);
        result.put("all_day", m.get("tpi"));
      }
    } else if ("avg_speed".equals(index)) {
      if (true) {
        map.put("timeproperty", "morning_peak");
        Map<String, Object> m = specialtyAnalysisDao.getTotalSpeed(map);
        result.put("morning_peak", m.get("speed"));
      }
      if (true) {
        map.put("timeproperty", "evening_peak");
        Map<String, Object> m = specialtyAnalysisDao.getTotalSpeed(map);
        result.put("evening_peak", m.get("speed"));
      }
      if (true) {
        map.put("timeproperty", "flat_peak");
        Map<String, Object> m = specialtyAnalysisDao.getTotalSpeed(map);
        result.put("flat_peak", m.get("speed"));
      }
      if (true) {
        map.put("timeproperty", "all_day");
        Map<String, Object> m = specialtyAnalysisDao.getTotalSpeed(map);
        result.put("all_day", m.get("speed"));
      }
    }
    return result;
  }

  /**
   * 获取道路运行状态饼状图信息.
   *
   * @param map 参数为空,AOP注入time,period
   */
  public Map<String, Object> getRoadRunState(Map<String, Object> map) {
    PeriodUtils.time2period(map);
    //获取各道路当前的状态
    List<Map<String, Object>> list = specialtyAnalysisDao.getRoadRunState(map);

    List<String> strList = dealRoadRunStateList(list);

    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("unblockedPercent", strList.get(0));
    resultMap.put("basicUnblockedPercent", strList.get(1));
    resultMap.put("slowMovePercent", strList.get(2));
    resultMap.put("congestionLittlePercent", strList.get(3));
    resultMap.put("congestionPercent", strList.get(4));

    return resultMap;
  }

  private List<String> dealRoadRunStateList(List<Map<String, Object>> list) {

    //使用lamada表达式将各状态值提取出来
    //畅通list
    List<Map<String, Object>> unblockedList = list.stream()
        .filter(temp -> ((BigDecimal) temp.get("index")).doubleValue() >= 0
            && ((BigDecimal) temp.get("index")).doubleValue() < 2)
        .distinct().collect(Collectors.toList());

    //基本畅通list
    List<Map<String, Object>> basicUnblockedList = list.stream().filter(temp ->
        ((BigDecimal) temp.get("index")).doubleValue() >= 2
            && ((BigDecimal) temp.get("index")).doubleValue() < 4)
        .distinct().collect(Collectors.toList());

    //缓行list
    List<Map<String, Object>> slowMoveList = list.stream()
        .filter(temp -> ((BigDecimal) temp.get("index")).doubleValue() >= 4
            && ((BigDecimal) temp.get("index")).doubleValue() < 6)
        .distinct().collect(Collectors.toList());

    //较拥堵list
    List<Map<String, Object>> congestionLittleList = list.stream()
        .filter(temp -> ((BigDecimal) temp.get("index")).doubleValue() >= 6
            && ((BigDecimal) temp.get("index")).doubleValue() < 8)
        .distinct().collect(Collectors.toList());

    //拥堵list
    List<Map<String, Object>> congestionList = list.stream()
        .filter(temp -> ((BigDecimal) temp.get("index")).doubleValue() >= 8
            && ((BigDecimal) temp.get("index")).doubleValue() <= 10)
        .distinct().collect(Collectors.toList());

    //获取百分比信息
    List<String> strList = getRoadStatePercent("", unblockedList, basicUnblockedList,
        slowMoveList, congestionLittleList, congestionList);

    return strList;
  }

  /**
   * 获取各list所占百分比.
   *
   * @param key 如果key为null或者key为空，则说明是对各个list的size进行百分比计算， 否则是对list中某个字段进行百分比计算
   */
  private List<String> getRoadStatePercent(String key, List<Map<String, Object>>... lists) {

    //获取总数
    int totalNum = 0;
    if (key == null || "".equals(key)) {
      for (List<Map<String, Object>> list : lists) {
        totalNum += list.size();
      }
    } else {
      for (List<Map<String, Object>> list : lists) {
        for (Map<String, Object> tempMap : list) {
          int num = 0;
          if (tempMap.get(key) instanceof BigDecimal) {
            BigDecimal numBig = (BigDecimal) tempMap.get(key);
            num = numBig.intValue();
          } else {
            num = (int) tempMap.get(key);
          }

          totalNum += num;
        }
      }
    }

    // 创建一个数值格式化对象
    NumberFormat numberFormat = NumberFormat.getInstance();
    // 设置精确到小数点后1位
    numberFormat.setMaximumFractionDigits(1);

    List<String> resultList = new ArrayList<String>();

    //计算各list占比
    if (key == null || "".equals(key)) {
      for (List<Map<String, Object>> list : lists) {
        String percent = numberFormat.format(((float) list.size() / (float) totalNum) * 100);
        if (totalNum == 0) {
          percent = "0";
        }
        resultList.add(percent);
      }
    } else {
      for (List<Map<String, Object>> list : lists) {
        for (Map<String, Object> tempMap : list) {
          int num = 0;
          if (tempMap.get(key) instanceof BigDecimal) {
            BigDecimal numBig = (BigDecimal) tempMap.get(key);
            num = numBig.intValue();
          } else {
            num = (int) tempMap.get(key);
          }
          String percent = numberFormat.format(((float) num / (float) totalNum) * 100);
          if (totalNum == 0) {
            percent = "0";
          }
          resultList.add(percent);
        }
      }
    }

    return resultList;
  }

  /**
   * 获取道路等级数量.
   *
   * @param map 为空
   */
  public List<Map<String, Object>> getRoadFlevelNum(Map<String, Object> map) {

    List<Map<String, Object>> list = specialtyAnalysisDao.getRoadFlevelNum(map);

    return list;
  }


  /**
   * 获取拥堵路段列表.
   *
   * @param map startdate,enddate,timeproperty,period,dateproperty
   */
  public StandardizedPageModel getCongestionRoadInfo(Map<String, Object> map) {

    if (map.containsKey("timeproperty") && "user_defined"
        .equals(map.get("timeproperty").toString())) {
      PeriodUtils.time2period(map);
    }
    map.put("pageSize", Integer.valueOf((String) map.get("pageSize")));
    map.put("pageNum", Integer.valueOf((String) map.get("pageNum")));
    //分页查询数据
    List<Map<String, Object>> list = specialtyAnalysisDao.getCongestionRoadInfoByPage(map);
    //获取记录总数
    List<Map<String, Object>> totalList = specialtyAnalysisDao.getCongestionRoadInfo(map);

    StandardizedPageModel pm = new StandardizedPageModel();
    pm.setList(list);
    pm.setTotalRecords(totalList.size());
    pm.setPageSize((int) map.get("pageSize"));
    pm.setPageNum((int) map.get("pageNum"));

    return pm;
  }

  /**
   * .
   */
  public Map<String, Object> getCongestionRoadInfoByDistrict(Map<String, Object> map) {

    if (map.containsKey("timeproperty") && "user_defined"
        .equals(map.get("timeproperty").toString())) {
      PeriodUtils.time2period(map);
    }

    //获取全市所有的拥堵路段
    List<Map<String, Object>> congestionRoadList = specialtyAnalysisDao.getCongestionRoadInfo(map);

    for (int i = 0; i < congestionRoadList.size(); i++) {
      int roadsectFid = ((BigDecimal) congestionRoadList.get(i).get("roadsectFid")).intValue();

      if (roadsectFid == 29301) {
        congestionRoadList.remove(i);
      }
    }

    Map<String, Object> tempMap = new HashMap<String, Object>();
    tempMap.put("roadsectFid", 39210);
    congestionRoadList.add(tempMap);

    //获取热点区域信息
    List<Map<String, Object>> hotpointList = specialtyAnalysisDao.getHotpointInfo(map);

    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("congestionRoads", congestionRoadList);
    resultMap.put("hotpoint", hotpointList);
    return resultMap;
  }

  /**
   * 获取货运车辆出发量/到达量接口.
   *
   * @param map startdate,enddate,timeproperty,vehicleType,dateproperty,check_type,timeprecision
   */
  public List<Map<String, Object>> getArrivalLeaveFreightTransportNum(Map<String, Object> map) {

    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    String spaceProperty = (String) map.get("check_type");

    String timeprecision = (String) map.get("timeprecision");

    if ("day".equals(timeprecision)) {

      if ("district".equals(spaceProperty)) {
        list = specialtyAnalysisDao.getArrivalLeaveFreightTransportNumByDistrict(map);
      } else if ("block".equals(spaceProperty)) {
        list = specialtyAnalysisDao.getArrivalLeaveFreightTransportNumByBlock(map);
      }
    } else if ("hour".equals(timeprecision)) {
      if ("district".equals(spaceProperty)) {
        list = specialtyAnalysisDao.getArrivalLeaveFreightTransportNumByDistrictByHour(map);
      } else if ("block".equals(spaceProperty)) {
        list = specialtyAnalysisDao.getArrivalLeaveFreightTransportNumByBlockByHour(map);
      }
    }

    return list;
  }


  /**
   * 获取货运车辆出发量/到达量分布信息.
   *
   * @param map startdate,enddate,timeproperty,vehicleType,dateproperty,check_type,timeprecision
   */
  public List<Map<String, Object>> getArrivalLeaveFreightTransportDistribution(
      Map<String, Object> map) {

    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    String spaceProperty = (String) map.get("check_type");

    String timeprecision = (String) map.get("timeprecision");

    if ("day".equals(timeprecision)) {

      if ("district".equals(spaceProperty)) {
        list = specialtyAnalysisDao.getArrivalLeaveFreightTransportNumByDistrict(map);

      } else if ("block".equals(spaceProperty)) {
        list = specialtyAnalysisDao.getArrivalLeaveFreightTransportNumByBlock(map);
      }
    }

    List<String> leaveList = getRoadStatePercent("LEAVE_VOLUME", list);

    List<String> arrivalList = getRoadStatePercent("ARRIVAL_VOLUME", list);

    for (int i = 0; i < list.size(); i++) {
      list.get(i).put("LEAVE_PERCENT", leaveList.get(i));
      list.get(i).put("ARRIVAL_PERCENT", arrivalList.get(i));
    }

    return list;
  }


  /**
   * 获取货运车辆热点区域出发量/到达量.
   *
   * @param map startdate,enddate,timeproperty,vehicle_type,dateproperty,timeprecision,fid
   */
  public List<Map<String, Object>> getFreightTransportHpArrivalLeave(Map<String, Object> map) {

    List<Map<String, Object>> list = specialtyAnalysisDao.getFreightTransportHpArrivalLeave(map);

    return list;
  }

  /**
   * 获取货运车辆货运通道信息.
   *
   * @param map startdate,enddate,timeproperty,vehicle_type,dateproperty
   */
  public List<Map<String, Object>> getFreightTransportPass(Map<String, Object> map) {

    List<Map<String, Object>> list = specialtyAnalysisDao.getFreightTransportPass(map);

    if (list.size() == 0) {
      //Integer.valueOf(TimeUtils.GetCurrentTimeString("yyyyMMdd"))
      //map.put("startdate", 20200201);
      //Integer.valueOf(TimeUtils.GetCurrentTimeString("yyyyMMdd"))
      //map.put("enddate", 20200201);
      //map.put("timeproperty", "user_defined");
      //PeriodUtils.getCurrentPeriod();
      //map.put("startperiod", 100);
      //PeriodUtils.getCurrentPeriod();
      //map.put("endperiod", 100);
      map.put("time", 20200902);
      list = specialtyAnalysisDao.getFreightTransportPass(map);
    }

    List<String> strList = getRoadStatePercent("volume", list);

    for (int i = 0; i < list.size(); i++) {
      list.get(i).put("percent", strList.get(i));
    }

    return list;
  }

  /**
   * 计算道路画像-六维特征.
   */
  public Object getRoadSixDData(RoadPortraitEntity dto) {
    List<RoadSixDDto> list = specialtyAnalysisDao
        .getRoadSixDData(dto.getDate(), dto.getTimeProperty());
    //高速路
    Map<String, Object> highMap = new HashMap<>();
    //快速路
    Map<String, Object> fastMap = new HashMap<>();
    //主干道
    Map<String, Object> mainMap = new HashMap<>();
    //次干道
    Map<String, Object> subMap = new HashMap<>();
    for (RoadSixDDto d : list) {
      List<Double> l = new ArrayList<>();
      l.add(d.getJamRatio());
      l.add(d.getPeakSpeedKt());
      l.add(d.getPeakSpeedSk());
      l.add(d.getPeakSpeedStd());
      l.add(d.getAvgTpi());
      l.add(d.getAvgJamTime());
      int id = d.getRoadTypeId();
      if (id == 1) {
        highMap.put("realValue", l);
        highMap.put("id", id);
        highMap.put("name", d.getRoadType());
      } else if (id == 2) {
        fastMap.put("realValue", l);
        fastMap.put("id", id);
        fastMap.put("name", d.getRoadType());
      } else if (id == 3) {
        mainMap.put("realValue", l);
        mainMap.put("id", id);
        mainMap.put("name", d.getRoadType());
      } else if (id == 4) {
        subMap.put("realValue", l);
        subMap.put("id", id);
        subMap.put("name", d.getRoadType());
      }
    }
    List<Double> highList = (List<Double>) highMap.get("realValue");
    List<Double> fastList = (List<Double>) fastMap.get("realValue");
    List<Double> mainList = (List<Double>) mainMap.get("realValue");
    List<Double> subList = (List<Double>) subMap.get("realValue");

    List<Integer> highValue = new ArrayList<>();
    List<Integer> fastValue = new ArrayList<>();
    List<Integer> mainValue = new ArrayList<>();
    List<Integer> subValue = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      double high = highList.get(i);
      double fast = fastList.get(i);
      double main = mainList.get(i);
      double sub = subList.get(i);
      if (high == 0 && fast == 0 && main == 0 && sub == 0) {
        highValue.add(1);
        fastValue.add(1);
        mainValue.add(1);
        subValue.add(1);
        continue;
      }
      List<Double> sortList = new ArrayList<>();
      sortList.add(high);
      sortList.add(fast);
      sortList.add(main);
      sortList.add(sub);
      Collections.sort(sortList);
      for (int j = 0; j < sortList.size(); j++) {
        double d = sortList.get(j);
        if (d == high) {
          highValue.add(j + 1);
        }
        if (d == fast) {
          fastValue.add(j + 1);
        }
        if (d == main) {
          mainValue.add(j + 1);
        }
        if (d == sub) {
          subValue.add(j + 1);
        }
      }
    }
    highMap.put("value", highValue);
    fastMap.put("value", fastValue);
    mainMap.put("value", mainValue);
    subMap.put("value", subValue);
    List<Map<String, Object>> data = new ArrayList<>();
    data.add(highMap);
    data.add(fastMap);
    data.add(mainMap);
    data.add(subMap);
    return data;
  }

  /**
   * 获取道路等级分布数据.
   */
  public Object getRoadLevelDistribution(RoadLevelDistributionEntity entity) {
    List<RoadLevelDistributionDto> list = specialtyAnalysisDao
        .getRoadLevelDistribution(entity.getDate());
    List<Map<String, Object>> data = new ArrayList<>();
    List<RoadLevelDistributionDto> jamList = new ArrayList<>();
    List<RoadLevelDistributionDto> subJamList = new ArrayList<>();
    List<RoadLevelDistributionDto> slowList = new ArrayList<>();
    List<RoadLevelDistributionDto> baseList = new ArrayList<>();
    List<RoadLevelDistributionDto> unimpededList = new ArrayList<>();
    for (RoadLevelDistributionDto dto : list) {
      String status = dto.getPeakStatus();
      if ("拥堵".equals(status)) {
        jamList.add(dto);
      }
      if ("较拥堵".equals(status)) {
        subJamList.add(dto);
      }
      if ("缓行".equals(status)) {
        slowList.add(dto);
      }
      if ("基本顺畅".equals(status)) {
        baseList.add(dto);
      }
      if ("顺畅".equals(status)) {
        unimpededList.add(dto);
      }
    }
    Map<String, Object> jamMap = calStatus("拥堵", jamList);
    Map<String, Object> subJamMap = calStatus("较拥堵", subJamList);
    Map<String, Object> slowMap = calStatus("缓行", slowList);
    Map<String, Object> baseMap = calStatus("基本畅通", baseList);
    Map<String, Object> unimpMap = calStatus("畅通", unimpededList);
    data.add(jamMap);
    data.add(subJamMap);
    data.add(slowMap);
    data.add(baseMap);
    data.add(unimpMap);
    return data;
  }

  /**
   * 计算道路画像数据.
   */
  public static Map<String, Object> calStatus(String status, List<RoadLevelDistributionDto> list) {
    Integer[] value = new Integer[]{0, 0, 0, 0};
    for (RoadLevelDistributionDto dto : list) {
      int id = dto.getRoadTypeId();
      int count = dto.getCount();
      if (id == 1) {
        value[0] = count;
      }
      if (id == 2) {
        value[1] = count;
      }
      if (id == 3) {
        value[2] = count;
      }
      if (id == 4) {
        value[3] = count;
      }
    }
    Map<String, Object> map = new HashMap<>();
    map.put("name", status);
    map.put("value", value);
    return map;
  }

  /**
   * 获取道路特征数据.
   */
  public RoadCharacteristicDto getRoadCharacteristicData(RoadPortraitEntity entity) {
    RoadCharacteristicDto thisDto = specialtyAnalysisDao
        .getRoadCharacteristicData(entity.getDate(), entity.getTimeProperty(), entity.getId());
    int thisDate = entity.getDate();
    int lastDate = Utils.getLastMonth(thisDate, 1);
    RoadCharacteristicDto lastDto = specialtyAnalysisDao
        .getRoadCharacteristicData(lastDate, entity.getTimeProperty(), entity.getId());
    double thisSpeed = thisDto.getSpeed();
    double thisTpi = thisDto.getTpi();
    double lastSpeed = lastDto.getSpeed();
    double lastTpi = lastDto.getTpi();
    double speedRatio = TpiUtils.calculateGrowth(lastSpeed, thisSpeed, 1);
    double tpiRatio = TpiUtils.calculateGrowth(lastTpi, thisTpi, 1);
    thisDto.setSpeedRatio(speedRatio);
    thisDto.setTpiRatio(tpiRatio);
    int id = thisDto.getId();
    int roadSet = specialtyAnalysisDao.getRoadSetId(id);
    thisDto.setRoadSet(roadSet);
    return thisDto;
  }

  /**
   * 画像标签.
   */
  public RoadPortrayalTagDto getRoadPortrayalTag(RoadPortrayalTagEntity entity) {
    RoadPortrayalTagDto dto = specialtyAnalysisDao
        .getRoadPortrayalTag(entity.getId(), entity.getDate());
    String congestionDegree = dto.getCongestionDegree();
    if (congestionDegree == null || "".equals(congestionDegree) || "null"
        .equals(congestionDegree)) {
      dto.setCongestionDegree("");
    }
    return dto;
  }

  /**
   * 中路段是否常发拥堵路段接口.
   */
  public List<RoadsectDistributionDto> getRoadsectDistribution(int date) {
    return specialtyAnalysisDao.getRoadsectDistribution(date);
  }

  /**
   * 指定中路段id，获取该中路id所在的大路段的道路六维画像以及全部路段平均画像数据的对比图数据.
   */
  public List<Map<String, Object>> getRoadSixDDataById(RoadPortrayalRoadsectEntity entity) {
    RoadSixDByIdDto roadDto = specialtyAnalysisDao
        .getRoadSixDDataById(entity.getId(), entity.getDate(), entity.getTimeProperty());
    List<Double> roadList = new ArrayList<>();
    if (roadDto != null) {
      roadList.add(roadDto.getJamRatio());
      roadList.add(roadDto.getPeakSpeedKt());
      roadList.add(roadDto.getPeakSpeedSk());
      roadList.add(roadDto.getPeakSpeedStd());
      roadList.add(roadDto.getAvgTpi());
      roadList.add(roadDto.getAvgJamTime());
    }
    Map<String, Object> roadMap = new HashMap<>();
    roadMap.put("realValue", roadList);
    roadMap.put("name", roadDto.getName());
    RoadSixDByIdDto allDto = specialtyAnalysisDao
        .getRoadSixDDataAll(entity.getDate(), entity.getTimeProperty());
    List<Double> allList = new ArrayList<>();
    if (allDto != null) {
      allList.add(allDto.getJamRatio());
      allList.add(allDto.getPeakSpeedKt());
      allList.add(allDto.getPeakSpeedSk());
      allList.add(allDto.getPeakSpeedStd());
      allList.add(allDto.getAvgTpi());
      allList.add(allDto.getAvgJamTime());
    }
    Map<String, Object> allMap = new HashMap<>();
    allMap.put("realValue", allList);
    allMap.put("name", "全市平均");

    List<Integer> roadValue = new ArrayList<>();
    List<Integer> allValue = new ArrayList<>();
    if (roadDto != null && allDto != null) {
      for (int i = 0; i < 6; i++) {
        double road = roadList.get(i);
        double all = allList.get(i);
        if (road == 0 && all == 0) {
          roadValue.add(1);
          allValue.add(1);
          continue;
        }
        List<Double> sortList = new ArrayList<>();
        sortList.add(road);
        sortList.add(all);
        Collections.sort(sortList);
        for (int j = 0; j < sortList.size(); j++) {
          double d = sortList.get(j);
          if (d == road) {
            roadValue.add(j + 1);
          }
          if (d == all) {
            allValue.add(j + 1);
          }
        }
      }
    }
    roadMap.put("value", roadValue);
    allMap.put("value", allValue);
    List<Map<String, Object>> data = new ArrayList<>();
    data.add(roadMap);
    data.add(allMap);
    return data;
  }

  /**
   * .
   */
  public RoadSectionJamCountDto getRoadSectionJamCounts(int date) {
    return specialtyAnalysisDao.getRoadSectionJamCounts(date);
  }


}
