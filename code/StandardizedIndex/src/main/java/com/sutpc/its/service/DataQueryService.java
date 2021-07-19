package com.sutpc.its.service;

import com.sutpc.its.constant.FileColumnIndexConstants;
import com.sutpc.its.constant.FileNameConstant;
import com.sutpc.its.dao.ICommonQueryDao;
import com.sutpc.its.dao.IDataQueryDao;
import com.sutpc.its.tools.ExcelUtils;
import com.sutpc.its.tools.PeriodUtils;
import com.sutpc.its.tools.TpiUtils;
import com.sutpc.its.tools.Utils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DataQueryService {

  @Autowired
  private IDataQueryDao dataQueryDao;

  @Autowired
  private ICommonQueryDao commonQueryDao;

  /**
   * getCalenderMonthData.
   */
  public Map<String, Object> getCalenderMonthData(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<String, Object>();
    String ym = map.get("y").toString() + map.get("m");
    String monthBegin = ym + "01";
    result.put("monthBegin", monthBegin);
    int currentdayofweek = Utils.getDayOfWeek(monthBegin);//该月的第一天是周几
    int currentdaysofMonth = Utils.getDaysOfMonth(monthBegin);//该月有多少天
    String monthEnd = ym + currentdaysofMonth;
    Map<String, Object> pmap = new HashMap<String, Object>();
    pmap.put("monthBegin", monthBegin);
    pmap.put("monthEnd", monthEnd);
    pmap.put("timeproperty", map.get("timeproperty"));
    pmap.put("district_fid", map.get("district_fid"));
    pmap.putAll(map);
    Map<String, Map<String, Object>> monthDataMap = new HashMap();

    List<Map<String, Object>> list = dataQueryDao.getCalenderMonthData(pmap);
    for (int i = 0; i < list.size(); i++) {
      String currentdate = Utils.getCurrentDateSqlString();
      String rtime = list.get(i).get("TIME").toString();

      /*if (rtime.equals(currentdate)) {
        Map<String, Object> cdmap = new HashMap<String, Object>();
        cdmap.put("fdate", currentdate);
        cdmap.put("district_fid", map.get("district_fid"));
        cdmap.put("period15", map.get("period15"));
        cdmap.put("calender_road_type_fid", map.get("calender_road_type_fid"));
        List<Map<String, Object>> cdlist = dataQueryDao.getCalendarDayData(cdmap);
        if (cdlist.size() > 0) {
          list.get(i).put("EXPONENT", cdlist.get(cdlist.size() - 1).get("TPI"));
          list.get(i).put("SPEED", cdlist.get(cdlist.size() - 1).get("SPEED"));
        }
      }*/

      monthDataMap.put(rtime, list.get(i));
    }
    //工作日/非工作日
    List<Map<String, Object>> nworkdaylist = dataQueryDao.getCalendarNoWorkdayData(pmap);
    Map<String, Object> nworkdaymap = new HashMap<String, Object>();
    for (int i = 0; i < nworkdaylist.size(); i++) {
      nworkdaymap.put(nworkdaylist.get(i).get("FDATE").toString(), "");
    }
    //天气数据
    Map<String, Object> weathermap = new HashMap<String, Object>();
    /*try {
      List<Map<String, Object>> weatherlist = tindexDao.selectCalendarWeatherData(pmap);
      for (int i = 0; i < weatherlist.size(); i++) {
        weathermap
            .put(weatherlist.get(i).get("TIME").toString(), weatherlist.get(i).get("WEATHERLEVEL"));
      }
    } catch (Exception ex) {
    }*/
    int day = 0;

    List<Map<String, Object>> dclist = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();

    for (int i = 1; i <= 42; i++) {
      //6*7个方格
      if (i >= currentdayofweek) {
        day++;
        String dayStr = day + "";
        if (day < 10) {
          dayStr = "0" + day;
        }
        if (day > currentdaysofMonth) {
          //不能大于该月总天数
          //31号所在的行决定是5行还是6行div
          if (i < 37) {
            result.put("lessrow", 1);
            break;
          } else {
            result.put("lessrow", 0);
          }
          Map<String, Object> mmp = new HashMap<>();
          resultData.add(mmp);

        } else {

          //生成excel的map
          Map<String, Object> rowmap = new LinkedHashMap<String, Object>();

          String lday = Utils.getDayOfLunar(ym + dayStr);
          String lmonth = Utils.getMonthOfLunar(ym + dayStr);
          rowmap.put("Month", ym);
          rowmap.put("Day", day);
          //rowmap.put("Lunar", lday);//农历阴历
          //rowmap.put("Lunar-Month",lmonth);
          //newTest
          rowmap.put("Lunar-Month", lmonth);
          rowmap.put("Lunar", lday);//农历阴历
          //返回前端的map
          Map<String, Object> mmp = new HashMap<>();
          mmp.put("day", day);   //几号
          mmp.put("lunar", lday);//农历
          mmp.put("lunar_month", lmonth);

          rowmap.put("Weekday", "星期" + Utils.getDayDesOfWeek(ym + dayStr));

          String nworkdayflag = "";
          String workdayFlag = "1";
          if (nworkdaymap.containsKey(ym + dayStr)) {
            workdayFlag = "0";
            nworkdayflag = "color:red;";
          }

          rowmap.put("Workday", (workdayFlag == "1") ? "是" : "否");

          mmp.put("workday", workdayFlag);//是否为工作日，工作日为1，非工作日为0

          /*String wimg = "";
          String weatherFlag = "";
          if (weathermap.containsKey(ym + dayStr)) {
            weatherFlag = weathermap.get(ym + dayStr).toString();
          }

          rowmap.put("WeatherFlag", weatherFlag);
          mmp.put("weather", weatherFlag);*/

          String speedValue = "";
          String indexValue = "";

          if (monthDataMap.containsKey(ym + dayStr)) {
            String tpi = monthDataMap.get(ym + dayStr).get("EXPONENT") + "";
            double index = 0.0;
            if (!tpi.equals("null")) {
              index = Double.parseDouble(tpi);
            }
            indexValue = index + "";
            speedValue = monthDataMap.get(ym + dayStr).get("SPEED") + "";

            mmp.put("bgcolor", Utils.getRgbaColorByIndex(index));
          } else {
            mmp.put("bgcolor", null);
          }
          rowmap.put("SPEED", speedValue);
          rowmap.put("EXPONENT", indexValue);
          mmp.put("speed", speedValue);
          mmp.put("exponent", indexValue);

          //newTest
          String wimg = "";
          String weatherFlag = "";
          if (weathermap.containsKey(ym + dayStr)) {
            weatherFlag = weathermap.get(ym + dayStr).toString();
          }
          rowmap.put("WeatherFlag", weatherFlag);
          mmp.put("weather", weatherFlag);

          resultData.add(mmp);
          dclist.add(rowmap);
        }
      } else {
        Map<String, Object> mmp = new HashMap<>();
        resultData.add(mmp);
      }
    }
    String districtFid = map.get("district_fid") + "";
    String timeproperty = map.get("timeproperty") + "";
    //String excelFileName = getMap(district_fid)+"_"+timeproperty+"_"+ym;
    String excelFileName =
        getMap(districtFid) + "_" + FileNameConstant.indexMap.get("tpi") + "_" + ym + "_" + map
            .get("timeproperty");

    result.put("excelpath", ExcelUtils.exportToExcel(
        FileColumnIndexConstants.keysForTrafficCalendar, dclist, excelFileName, "", ""));
    result.put("resultData", resultData);
    result.put("todaydate", Utils.getCurrentDateSqlString());
    return result;
  }

  /**
   * getMap.
   */
  public String getMap(String sect) {
    Map<String, Object> map = new HashMap<>();
    map.put("1", "福田区");
    map.put("2", "罗湖区");
    map.put("3", "南山区");
    map.put("4", "盐田区");
    map.put("5", "宝安区");
    map.put("6", "龙岗区");
    map.put("7", "光明区");
    map.put("8", "坪山区");
    map.put("9", "龙华区");
    map.put("10", "大鹏新区");
    map.put("111", "全市");
    map.put("222", "中心城区");
    map.put("333", "原特区外");
    return map.get(sect) + "";
  }

  /**
   * getCalendarMonthLineData.
   */
  public List<Map<String, Object>> getCalendarMonthLineData(Map<String, Object> map) {
    Map<String, Object> paramap = new HashMap<String, Object>();
    List<Map<String, Object>> list = new ArrayList<>();
    if (!map.containsKey("timeproperty")) {
      map.put("timeproperty", "morning_peak");
      paramap.put("morning_peak", dealCalendarMonthLineData(map));
      map.put("timeproperty", "evening_peak");
      paramap.put("evening_peak", dealCalendarMonthLineData(map));
      list.add(paramap);
      return list;
    }
    return list;
  }

  /**
   * dealCalendarMonthLineData.
   */
  public List<Map<String, Object>> dealCalendarMonthLineData(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<String, Object>();
    String ym = map.get("y").toString() + map.get("m");

    String monthBegin = ym + "01";

    result.put("monthBegin", monthBegin);

    int currentdayofweek = Utils.getDayOfWeek(monthBegin);//该月的第一天是周几
    int currentdaysofMonth = Utils.getDaysOfMonth(monthBegin);//该月有多少天
    String monthEnd = ym + currentdaysofMonth;
    Map<String, Object> pmap = new HashMap<String, Object>();
    pmap.put("monthBegin", monthBegin);
    pmap.put("monthEnd", monthEnd);
    pmap.put("timeproperty", map.get("timeproperty"));
    pmap.put("district_fid", map.get("district_fid"));
    pmap.putAll(map);

    List<Map<String, Object>> list = dataQueryDao.getCalenderMonthData(pmap);
    for (int i = 0; i < list.size(); i++) {
      Map<String, Object> mapSon = list.get(i);
      mapSon.put("TIME", mapSon.get("TIME").toString().substring(6, 8));
    }
    return list;
  }

  /**
   * getCalendarDayData.
   */
  public List<Map<String, Object>> getCalendarDayData(Map<String, Object> map) {
    return dataQueryDao.getCalendarDayData(map);
  }

  /**
   * 全市查询.
   */
  public Map<String, Object> getCityQueryData(Map<String, Object> map) {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    String type = "city";
    map.put("type", type);//在sql查询中是联系在一起，用type分开。
    map.put("methods", "1");
    String index = map.get("index") + "";
    map = PeriodUtils.time2period(map);
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
    if (!index.equals("null") && index.equals("tpi")) {
      list = dataQueryDao.getTpiQueryData(map);

    }
    if (!index.equals("null") && index.equals("avg_speed")) {
      list = dataQueryDao.getSpeedQueryData(map);

    }
    if (index.equals("jam_length_ratio")) {
      list = dataQueryDao.getDistrictJamLengthRatio(map);
    }
    if (index.equals("jam_space_time")) {
      list = dataQueryDao.getJamSpaceTime(map);
    }
    if (index.equals("avg_jam_length")) {
      list = dataQueryDao.getAvgJamLength(map);
    }

    //String  fileName=type+"_"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
    String fileName =
        FileNameConstant.rangeMap.get(type) + "_" + FileNameConstant.indexMap.get(index) + "_" + map
            .get("startdate") + "-" + map.get("enddate") + "_表_";
    String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);
    String filepath = ExcelUtils
        .exportToExcel(FileColumnIndexConstants.keysForCityDistrictRoadsect, list, name, index,
            map.get("timeprecision").toString());
    Map<String, Object> result = new HashMap();
    result.put("data", list);
    result.put("filepath", filepath);

    return result;
  }

  /**
   * 片区查询 start.
   **/
  public Map<String, Object> getDistrictQueryData(Map<String, Object> map) {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    String type = "district";
    map.put("type", type);//在sql查询中是联系在一起，用type分开。
    String index = map.get("index") + "";
    map = PeriodUtils.time2period(map);
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
    String timePrecision = map.get("timeprecision").toString();
    Integer timePrecisionValue = TpiUtils.getTimePrecisionValue(timePrecision);
    String flag = TpiUtils.getFlag(timePrecision);
    map.put("timePrecisionValue", timePrecisionValue);
    if (index.equals("tpi")) {
      list = dataQueryDao.getTpiQueryData(map);
    }
    if (index.equals("avg_speed")) {
      list = dataQueryDao.getSpeedQueryData(map);
    }
    if (index.equals("jam_length_ratio")) {
      list = dataQueryDao.getDistrictJamLengthRatio2(map);
        list.forEach(m -> {
            m.put("x", m.get(flag));
        });
    }
    if (index.equals("jam_space_time")) {
      list = dataQueryDao.getJamSpaceTime(map);
    }
    if (index.equals("avg_jam_length")) {
      list = dataQueryDao.getAvgJamLength(map);
    }
    List<Map<String, Object>> animaList = getAnimationList(map, list);
    //String  fileName=type+"_"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
    String fileName =
        FileNameConstant.rangeMap.get(type) + "_" + FileNameConstant.indexMap.get(index) + "_" + map
            .get("startdate") + "-" + map.get("enddate") + "_表_";
    String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);

    String filepath = ExcelUtils
        .exportToExcel(FileColumnIndexConstants.keysForCityDistrictRoadsect, list, name, index,
            map.get("timeprecision").toString());
    Map<String, Object> result = new HashMap<>();
    result.put("data", list);
    result.put("filepath", filepath);
    if ("1".equals(map.get("methods") + "")) {
      result.put("animationData", animaList);
    }

    return result;
  }

  /**
   * getBlockQueryData.
   */
  public Map<String, Object> getBlockQueryData(Map<String, Object> map) {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    String type = "block";
    map.put("type", type);//在sql查询中是联系在一起，用type分开。
    String index = map.get("index") + "";
    map = PeriodUtils.time2period(map);
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
    String timePrecision = map.get("timeprecision").toString();
    Integer timePrecisionValue = TpiUtils.getTimePrecisionValue(timePrecision);
    String flag = TpiUtils.getFlag(timePrecision);
    map.put("timePrecisionValue", timePrecisionValue);
    if (index.equals("tpi")) {
      list = dataQueryDao.getTpiQueryData(map);
    }
    if (index.equals("avg_speed")) {
      list = dataQueryDao.getSpeedQueryData(map);
    }
    if (index.equals("jam_length_ratio")) {
      list = dataQueryDao.getBlockJamLengthRatio2(map);
        list.forEach(m -> {
            m.put("x", m.get(flag));
        });
    }
    if (index.equals("jam_space_time")) {
      list = dataQueryDao.getJamSpaceTime(map);
    }
    if (index.equals("avg_jam_length")) {
      list = dataQueryDao.getAvgJamLength(map);
    }
    List<Map<String, Object>> animaList = getAnimationList(map, list);
    //String  fileName=type+"_"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
    String fileName =
        FileNameConstant.rangeMap.get(type) + "_" + FileNameConstant.indexMap.get(index) + "_" + map
            .get("startdate") + "-" + map.get("enddate") + "_表_";
    String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);

    String filepath = ExcelUtils
        .exportToExcel(FileColumnIndexConstants.keysForCityDistrictRoadsect, list, name, index,
            map.get("timeprecision").toString());
    Map<String, Object> result = new HashMap<>();
    result.put("data", list);
    result.put("filepath", filepath);
    if ("1".equals(map.get("methods") + "")) {
      result.put("animationData", animaList);
    }

    return result;
  }

  /**
   * getSubsectQueryData.
   */
  public Map<String, Object> getSubsectQueryData(Map<String, Object> map) {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    String type = "subsect";
    map.put("type", type);//在sql查询中是联系在一起，用type分开。
    String index = map.get("index") + "";
    map = PeriodUtils.time2period(map);
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
      String timePrecision = map.get("timeprecision").toString();
      Integer timePrecisionValue = TpiUtils.getTimePrecisionValue(timePrecision);
      String flag = TpiUtils.getFlag(timePrecision);
      map.put("timePrecisionValue", timePrecisionValue);
    if (index.equals("tpi")) {
      list = dataQueryDao.getTpiQueryData(map);
    }
    if (index.equals("avg_speed")) {
      list = dataQueryDao.getSpeedQueryData(map);
    }
    if (index.equals("jam_length_ratio")) {
      list = dataQueryDao.getSubsectJamLengthRatio2(map);
        list.forEach(m -> {
            m.put("x", m.get(flag));
        });
    }
    if (index.equals("jam_space_time")) {
      list = dataQueryDao.getJamSpaceTime(map);
    }
    if (index.equals("avg_jam_length")) {
      list = dataQueryDao.getAvgJamLength(map);
    }
    List<Map<String, Object>> animaList = getAnimationList(map, list);
    //String  fileName=type+"_"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
    String fileName =
        FileNameConstant.rangeMap.get(type) + "_" + FileNameConstant.indexMap.get(index) + "_" + map
            .get("startdate") + "-" + map.get("enddate") + "_表_";
    String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);

    String filepath = ExcelUtils
        .exportToExcel(FileColumnIndexConstants.keysForCityDistrictRoadsect, list, name, index,
            map.get("timeprecision").toString());
    Map<String, Object> result = new HashMap<>();
    result.put("data", list);
    result.put("filepath", filepath);
    if ("1".equals(map.get("methods") + "")) {
      result.put("animationData", animaList);
    }

    return result;
  }

  /**
   * getPoiQueryData.
   */
  public Map<String, Object> getPoiQueryData(Map<String, Object> map) {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    String type = "poi";
    map.put("type", type);//在sql查询中是联系在一起，用type分开。
    String index = map.get("index") + "";
    map = PeriodUtils.time2period(map);
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
    if (index.equals("tpi")) {
      list = dataQueryDao.getTpiQueryData(map);
    }
    if (index.equals("avg_speed")) {
      list = dataQueryDao.getSpeedQueryData(map);
    }
    List<Map<String, Object>> animaList = getAnimationList(map, list);
    //String  fileName=type+"_"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
    String fileName =
        FileNameConstant.rangeMap.get(type) + "_" + FileNameConstant.indexMap.get(index) + "_" + map
            .get("startdate") + "-" + map.get("enddate") + "_表_";
    String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);

    String filepath = ExcelUtils
        .exportToExcel(FileColumnIndexConstants.keysForCityDistrictRoadsect, list, name, index,
            map.get("timeprecision").toString());
    Map<String, Object> result = new HashMap<>();
    result.put("data", list);
    result.put("filepath", filepath);
    if ("1".equals(map.get("methods") + "")) {
      result.put("animationData", animaList);
    }
    return result;
  }


  /**
   * 道路查询 start.
   **/
  public Map<String, Object> getRoadQueryData(Map<String, Object> map) {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    String type = "road";
    map.put("type", type);//在sql查询中是联系在一起，用type分开。
    String index = map.get("index") + "";
    map = PeriodUtils.time2period(map);
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
    if (index.equals("avg_speed")) {
      list = dataQueryDao.getSpeedQueryData(map);
    }
    if (index.equals("jam_length_ratio")) {
        String timePrecision = map.get("timeprecision").toString();
        Integer timePrecisionValue = TpiUtils.getTimePrecisionValue(timePrecision);
        String flag = TpiUtils.getFlag(timePrecision);
        map.put("timePrecisionValue", timePrecisionValue);
      list = dataQueryDao.getRoadJamLengthRatio2(map);
        list.forEach(m -> {
            m.put("x", m.get(flag));
        });
    }
    if (index.equals("jam_space_time")) {
      list = dataQueryDao.getJamSpaceTime(map);
    }
    if (index.equals("avg_jam_length")) {
      list = dataQueryDao.getAvgJamLength(map);
    }
    List<Map<String, Object>> animaList = getAnimationList(map, list);
    //String  fileName=type+"_"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
    String fileName =
        FileNameConstant.rangeMap.get(type) + "_" + FileNameConstant.indexMap.get(index) + "_" + map
            .get("startdate") + "-" + map.get("enddate") + "_表_";
    String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);

    String filepath = ExcelUtils
        .exportToExcel(FileColumnIndexConstants.keysForCityDistrictRoadsect, list, name, index,
            map.get("timeprecision").toString());
    Map<String, Object> result = new HashMap<>();
    result.put("data", list);
    result.put("filepath", filepath);
    if ("1".equals(map.get("methods") + "")) {
      result.put("animationData", animaList);
    }

    return result;
  }

  /**
   * getRoadsectQueryData.
   */
  public Map<String, Object> getRoadsectQueryData(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    String type = "roadsect";
    map.put("type", type);//在sql查询中是联系在一起，用type分开。
    String index = map.get("index") + "";
    String filepath = null;
    map = PeriodUtils.time2period(map);
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
    if (index.equals("avg_speed")) {
      // 性能优化前的代码
      List<Map<String, Object>> list = dataQueryDao.getSpeedQueryData(map);
      // 性能优化后的代码
      //List<Map<String, Object>> list = parseSpeedQueryData(map);
      List<Map<String, Object>> animaList = getAnimationList(map, list);
      //String  fileName=type+"_"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
      String fileName =
          FileNameConstant.rangeMap.get(type) + "_" + FileNameConstant.indexMap.get(index) + "_"
              + map.get("startdate") + "-" + map.get("enddate") + "_表_";
      String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);

      filepath = ExcelUtils
          .exportToExcel(FileColumnIndexConstants.keysForRoadsectQueryData, list, name, index,
              map.get("timeprecision").toString());
      result.put("data", list);
      result.put("filepath", filepath);
      if ("1".equals(map.get("methods") + "")) {
        result.put("animationData", animaList);
      }
    }
    return result;
  }

  /**
   * 遍历每天查询.
   *
   * @param map 参数
   * @return 结果
   */
  private List<Map<String, Object>> parseSpeedQueryData(Map<String, Object> map) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
    LocalDate startDate = LocalDate.parse(map.get("startdate").toString(), dtf);
    LocalDate endDate = LocalDate.parse(map.get("enddate").toString(), dtf);
    List<Map<String, Object>> values = new ArrayList<>();
    while (!startDate.isAfter(endDate)) {
      int date = Integer.parseInt(startDate.format(dtf));
      map.put("startdate", date);
      map.put("enddate", date);
      map.put("nowdate", date);
      List<Map<String, Object>> sources = dataQueryDao.getSpeedQueryDataNew(map);
      if (CollectionUtils.isEmpty(values)) {
        values.addAll(sources);
      } else {
        for (Map<String, Object> source : sources) {
          boolean flag = false;
          int x = Integer.parseInt(source.get("x").toString());
          for (Map<String, Object> value : values) {
            int ox = Integer.parseInt(value.get("x").toString());
            if (x == ox) {
              flag = false;
              double dividend = Double.parseDouble(source.get("dividend").toString()) + Double
                  .parseDouble(value.get("dividend").toString());
              value.put("dividend", dividend);
              double divisor = Double.parseDouble(source.get("divisor").toString()) + Double
                  .parseDouble(value.get("divisor").toString());
              value.put("divisor", divisor);
              if (source.containsKey("tpiCount")) {
                int count = Integer.parseInt(source.get("tpiCount").toString()) + Integer
                    .parseInt(value.get("tpiCount").toString());
                value.put("tpiCount", count);
                double tpiLength = Double.parseDouble(source.get("tpiLength").toString()) + Double
                    .parseDouble(value.get("tpiLength").toString());
                value.put("tpiLength", tpiLength);
                double tpiTime = Double.parseDouble(source.get("tpiTime").toString()) + Double
                    .parseDouble(value.get("tpiTime").toString());
                value.put("tpiTime", tpiTime);
                int tpiType = Integer.parseInt(source.get("tpiType").toString()) + Integer
                    .parseInt(value.get("tpiType").toString());
                value.put("tpiType", count);
              }
              break;
            } else {
              flag = true;
            }
          }
          if (flag) {
            values.add(source);
          }
        }

      }
      startDate = startDate.plusDays(1);
    }
    for (Map<String, Object> value : values) {
      double dividend = Double.parseDouble(value.get("dividend").toString());
      double divisor = Double.parseDouble(value.get("divisor").toString());
      double y = divisor == 0 ? 0 : dividend / divisor;
      value.put("y", TpiUtils.getByDigit(y, 1));
      if (value.containsKey("tpiCount")) {
        int tpiCount = Integer.parseInt(value.get("tpiCount").toString());
        double tpiLength = Double.parseDouble(value.get("tpiLength").toString());
        double tpiTime = Double.parseDouble(value.get("tpiTime").toString());
        int tpiType = Integer.parseInt(value.get("tpiType").toString());
        double speed = tpiTime == 0 ? 0 : tpiLength / tpiTime;
        double type = tpiCount == 0 ? 0 : (tpiType * 1.0) / tpiCount;
        double tpi = TpiUtils.speedToTpi(speed, type);
        value.put("TPI", TpiUtils.getByDigit(tpi, 2));
      }
      value.remove("dividend");
      value.remove("divisor");
      value.remove("tpiCount");
      value.remove("tpiLength");
      value.remove("tpiTime");
      value.remove("tpiType");
    }
    return values;
  }

  /**
   * getHighSpeedQueryData.
   */
  public Map<String, Object> getHighSpeedQueryData(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    String type = "highspeed";
    map.put("type", type);//在sql查询中是联系在一起，用type分开。
    String index = map.get("index") + "";
    String filepath = null;
    map = PeriodUtils.time2period(map);
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
    if (index.equals("avg_speed")) {
      List<Map<String, Object>> list = dataQueryDao.getSpeedQueryData(map);
      List<Map<String, Object>> animaList = getAnimationList(map, list);
      //String  fileName=type+"_"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
      String fileName =
          FileNameConstant.rangeMap.get(type) + "_" + FileNameConstant.indexMap.get(index) + "_"
              + map.get("startdate") + "-" + map.get("enddate") + "_表_";
      String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);

      filepath = ExcelUtils
          .exportToExcel(FileColumnIndexConstants.keysForHighSpeedQueryData, list, name, index,
              map.get("timeprecision").toString());
      result.put("data", list);
      result.put("filepath", filepath);
      if ("1".equals(map.get("methods") + "")) {
        result.put("animationData", animaList);
      }
    }
    return result;
  }

  /**
   * getCheckLineQueryData.
   */
  public Map<String, Object> getCheckLineQueryData(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    String type = "check_line";
    map.put("type", type);//在sql查询中是联系在一起，用type分开。
    String index = map.get("index") + "";
    String filepath = null;
    map = PeriodUtils.time2period(map);
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
    if (index.equals("check_line")) {
      List<Map<String, Object>> list = dataQueryDao.getCheckLineQueryData(map);
      //String  fileName=type+"_"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
      String fileName =
          FileNameConstant.rangeMap.get(type) + "_" + FileNameConstant.indexMap.get(index) + "_"
              + map.get("startdate") + "-" + map.get("enddate") + "_表_";
      String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);

      filepath = ExcelUtils
          .exportToExcel(FileColumnIndexConstants.keysForCityDistrictRoadsect, list, name, index,
              map.get("timeprecision").toString());
      result.put("data", list);
      result.put("filepath", filepath);
    }
    return result;
  }


  /**
   * 公交查询 start.
   **/
  public Map<String, Object> getBuslaneCheckData(Map<String, Object> map) {
    /*PeriodUtils.time2period(map);
    List<Map<String, Object>> list = dataQueryDao.getBuslaneSpeed(map);
    Map<String, Object> result = new HashMap<String, Object>();
    List al_y = new ArrayList();
    List al_x = new ArrayList();
    List al_ids = new ArrayList();
    for (int i = 0; i < list.size(); i++) {
      if (map.get("sindex").equals("speed")) {
        al_x.add(list.get(i).get("SPEED"));
      }
      if (map.get("sindex").equals("speedrate")) {
        al_x.add(list.get(i).get("RATE"));
      }
      if (map.get("qtype").equals("area")) {
        al_y.add(list.get(i).get("SECTNAME").toString().trim());
        al_ids.add(list.get(i).get("SECTID"));
      }
      if (map.get("qtype").equals("buslane")) {
        al_y.add(list.get(i).get("ROADNAME") + "（" + list.get(i).get("DIR") + "）");
        al_ids.add(list.get(i).get("ROADNO"));
      }
    }
    result.put("ex", al_x);
    result.put("ey", al_y);
    result.put("ids", al_ids);*/
    // result.put("filepath", ExcelUtils.exportToExcel(list));
    Map<String, Object> result = new HashMap<>();
    String index = map.get("index") + "";
    String filepath = null;
    map = PeriodUtils.time2period(map);
    if (map.containsKey("id")) {
      String ids = map.get("id").toString();
      map.put("permIds", ids.split(","));
    }
    if (index.equals("bus_speed")) {
      List<Map<String, Object>> list = dataQueryDao.getBuslaneSpeed(map);
      List<Map<String, Object>> animaList = getAnimationList(map, list);
      //String  fileName="专用道"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
      String fileName =
          FileNameConstant.rangeMap.get("buslane") + "_" + FileNameConstant.indexMap.get(index)
              + "_" + map.get("startdate") + "-" + map.get("enddate") + "_表_";
      String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);

      filepath = ExcelUtils
          .exportToExcel(FileColumnIndexConstants.keysForCityDistrictRoadsect, list, name, index,
              map.get("timeprecision").toString());
      result.put("data", list);
      if ("1".equals(map.get("methods") + "")) {
        result.put("animationData", animaList);
      }
      result.put("filepath", filepath);
    }
    if (index.equals("speed_rate")) {
      List<Map<String, Object>> list = dataQueryDao.getBuslaneSpeedRate(map);
      List<Map<String, Object>> animaList = getAnimationList(map, list);
      //String  fileName="专用道"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
      String fileName =
          FileNameConstant.rangeMap.get("buslane") + "_" + FileNameConstant.indexMap.get(index)
              + "_" + map.get("startdate") + "-" + map.get("enddate") + "_表_";
      String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);

      List<String> keys = new ArrayList<String>() {
        {
          add("FNAME");
          add("FID");
          add("x");
          add("y");
        }
      };

      filepath = ExcelUtils
          .exportToExcel(keys, list, name, index, map.get("timeprecision").toString());
      result.put("data", list);
      if ("1".equals(map.get("methods") + "")) {
        result.put("animationData", animaList);
      }
      result.put("filepath", filepath);
    }
    if (index.equals("speed_status_realtime")) {
      List<Map<String, Object>> list = dataQueryDao.getBuslaneRealTimeStatus(map);
      result.put("data", list);
    }
    return result;
  }

  /**
   * getAreaCheckData.
   */
  public Map<String, Object> getAreaCheckData(Map<String, Object> map) {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    String index = map.get("index") + "";
    map = PeriodUtils.time2period(map);
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
    if (index.equals("bus_speed")) {
      list = dataQueryDao.getAreaSpeed(map);
    }
    if (index.equals("speed_rate")) {
      list = dataQueryDao.getAreaSpeedRate(map);
    }
    List<Map<String, Object>> animaList = getAnimationList(map, list);
    //String  fileName="片区"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
    String fileName =
        FileNameConstant.rangeMap.get("zone") + "_" + FileNameConstant.indexMap.get(index) + "_"
            + map.get("startdate") + "-" + map.get("enddate") + "_表_";
    String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);

    String filepath = ExcelUtils
        .exportToExcel(FileColumnIndexConstants.keysForCityDistrictRoadsect, list, name, index,
            map.get("timeprecision").toString());
    Map<String, Object> result = new HashMap<>();
    result.put("data", list);
    if ("1".equals(map.get("methods") + "")) {
      result.put("animationData", animaList);
    }
    result.put("filepath", filepath);
    return result;
  }

  /**
   * 节点查询 start.
   **/
  public Map<String, Object> getCrossQueryData(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    String type = "cross";
    map.put("type", type);//在sql查询中是联系在一起，用type分开。
    String index = map.get("index") + "";
    map = PeriodUtils.time2period(map);
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
    if (index.equals("avg_speed")) {
      List<Map<String, Object>> list = dataQueryDao.getCrossQueryData(map);
      //String  fileName="交叉口_"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
      String fileName =
          FileNameConstant.rangeMap.get(type) + "_" + FileNameConstant.indexMap.get(index) + "_"
              + map.get("startdate") + "-" + map.get("enddate") + "_表_";
      String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);

      String filepath = ExcelUtils
          .exportToExcel(FileColumnIndexConstants.keysForCrossQueryData, list, name, index,
              map.get("timeprecision").toString());
      result.put("data", list);
      result.put("filepath", filepath);
    }
    return result;
  }

  /**
   * getRoadSectionFlow.
   */
  public Map<String, Object> getRoadSectionFlow(Map<String, Object> map) {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    String type = "roadsection";
    map.put("type", type);//在sql查询中是联系在一起，用type分开。
    String index = map.get("index") + "";
    map = PeriodUtils.time2period(map);
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
    if (index.equals("flow")) {
      list = dataQueryDao.getRoadSectionFlow(map);
    }
    if (index.equals("flow_hour_coefficient")) {
      list = dataQueryDao.getRoadSectionFlowHourCoefficient(map);
    }
    //String  fileName="道路断面_"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
    String fileName =
        FileNameConstant.rangeMap.get(type) + "_" + FileNameConstant.indexMap.get(index) + "_" + map
            .get("startdate") + "-" + map.get("enddate") + "_表_";
    String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);

    String filepath = ExcelUtils
        .exportToExcel(FileColumnIndexConstants.keysForRoadSectionFlow, list, name, index,
            map.get("timeprecision").toString());
    Map<String, Object> result = new HashMap<>();
    result.put("data", list);
    result.put("filepath", filepath);
    return result;
  }

  /**
   * getIntersectionData.
   */
  public Map<String, Object> getIntersectionData(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    String type = "intersection";
    map.put("type", type);//在sql查询中是联系在一起，用type分开。
    String index = map.get("index") + "";
    String filepath = null;
    map = PeriodUtils.time2period(map);
    String ids = map.get("id").toString();
    map.put("permIds", ids.split(","));
    if (index.equals("delay")) {
      List<Map<String, Object>> list = dataQueryDao.getIntersectionDelayData(map);
      //String  fileName="交叉口"+index+"_"+map.get("startdate")+"-"+map.get("enddate")+"_表_";
      String fileName =
          FileNameConstant.rangeMap.get(type) + "_" + FileNameConstant.indexMap.get(index) + "_"
              + map.get("startdate") + "-" + map.get("enddate") + "_表_";
      String name = Utils.getDownloadFileName(map, fileName, commonQueryDao);

      filepath = ExcelUtils
          .exportToExcel(FileColumnIndexConstants.keysForCityDistrictRoadsect, list, name, index,
              map.get("timeprecision").toString());
      result.put("data", list);
      result.put("filepath", filepath);
    }
    return result;
  }

  /**
   * 节点查询  end.
   */
  public List<Map<String, Object>> getAnimationList(Map<String, Object> map,
      List<Map<String, Object>> list) {
    List<Map<String, Object>> animaList = null;
    if ("1".equals(map.get("methods") + "")) {
      List<Map<String, Object>> animationList = new ArrayList<>();
      for (Map<String, Object> m : list) {
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("period", m.get("x"));
        animationList.add(newMap);
      }
      animaList = removeDuplicate(animationList);
      for (Map<String, Object> map1 : animaList) {
        List<Map<String, Object>> l = new ArrayList<>();
        for (Map<String, Object> map2 : list) {
          if (map2.get("x").equals(map1.get("period"))) {
            Map<String, Object> newMap = new HashMap<>();
            for (String key : map2.keySet()) {
              newMap.put(key, map2.get(key));
            }
            l.add(newMap);
          }
        }
        map1.put("data", l);
      }
    }
    return animaList;
  }

  /**
   * removeDuplicate.
   */
  public List removeDuplicate(List list) {
    for (int i = 0; i < list.size() - 1; i++) {
      for (int j = list.size() - 1; j > i; j--) {
        if (list.get(j).equals(list.get(i))) {
          list.remove(j);
        }
      }
    }
    return list;
  }

  /**
   * getBusTrajectoryData.
   */
  public Map<String, Object> getBusTrajectoryData(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    int tripDirection = Integer.parseInt(map.get("trip_direction").toString());
    if (tripDirection == 2) {
      map.put("trip_direction", 0);
      List<Map<String, Object>> toList = dataQueryDao.getBusTrajectoryData(map);
      List<List<Map<String, Object>>> toResultList = dataDealWith(toList);
      result.put("to_data", toResultList);

      map.put("trip_direction", 1);
      List<Map<String, Object>> returnList = dataQueryDao.getBusTrajectoryData(map);
      List<List<Map<String, Object>>> returnListResultList = dataDealWith(returnList);
      result.put("return_data", returnListResultList);
    } else {
      List<Map<String, Object>> list = dataQueryDao.getBusTrajectoryData(map);
      List<List<Map<String, Object>>> resultList = dataDealWith(list);
      if (tripDirection == 0) {
        result.put("to_data", resultList);
      } else {
        result.put("return_data", resultList);
      }
    }
    return result;
  }

  /**
   * dataDealWith.
   */
  public List<List<Map<String, Object>>> dataDealWith(List<Map<String, Object>> list) {

    /*int base_index = Integer.parseInt(list.get(0).get("STOP_INDEX").toString());
    List<List<Map<String, Object>>> all_lsit = new ArrayList<>();
    int stop_index;
    String vehicle_fid;
    Map<String, Object> m;
    for (int i = 0; i < list.size(); i++) {
      m = list.get(i);
      stop_index = Integer.parseInt(m.get("STOP_INDEX").toString());
      vehicle_fid = m.get("VEHICLE_FID").toString();
      if (stop_index == base_index) {
        List<Map<String, Object>> resultlist = new ArrayList<>();
        resultlist.add(m);
        Map<String, Object> mson;
        int index;
        String vehicle;
        for (int j = 0; j < list.size(); j++) {
          mson = list.get(j);
          Map<String, Object> mmm = new HashMap<>();
          mmm.putAll(mson);
          index = Integer.parseInt(mmm.get("STOP_INDEX").toString());
          vehicle = mmm.get("VEHICLE_FID").toString();
          if (stop_index != index && vehicle.equals(vehicle_fid)) {
            if (!resultlist.contains(mmm)) {
              resultlist.add(mmm);
              stop_index = index;
            }
          }
        }
        all_lsit.add(resultlist);
      } else {
        break;
      }
    }
    return all_lsit;*/
    List<List<Map<String, Object>>> allLsit = new ArrayList<>();
    List<Map<String, Object>> resultList = new ArrayList<>();
    String vehicleFid = "";
    for (int i = 0; i < list.size(); i++) {
      Map<String, Object> m = list.get(i);
      if (m.containsKey("STOP_INDEX")) {
        Map<String, Object> mson = new HashMap<>();
        int stopIndex = Integer.parseInt(m.get("STOP_INDEX").toString());
        if (stopIndex == 1) {
          if (resultList.size() != 0) {
            allLsit.add(resultList);
          }
          vehicleFid = m.get("VEHICLE_FID").toString();
          resultList = new ArrayList<>();
        }
        if (m.get("VEHICLE_FID").toString().equals(vehicleFid)) {
          mson.putAll(m);
          resultList.add(mson);
        }
      }
    }
    return allLsit;
  }

  /**
   * getBusArrivalInterval.
   */
  public List<Map<String, Object>> getBusArrivalInterval(Map<String, Object> map) {
    List<Map<String, Object>> list = new ArrayList<>();
    if (true) {
      map.put("start_sec", 0);
      map.put("end_sec", 120);
      Map<String, Object> m = dataQueryDao.getBusArrivalInterval(map);
      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("min0-2", m != null ? m.get("veh_num") : "");
      list.add(resultMap);
    }
    if (true) {
      map.put("start_sec", 120);
      map.put("end_sec", 300);
      Map<String, Object> m = dataQueryDao.getBusArrivalInterval(map);
      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("min2-5", m != null ? m.get("veh_num") : "");
      list.add(resultMap);
    }
    if (true) {
      map.put("start_sec", 300);
      map.put("end_sec", 480);
      Map<String, Object> m = dataQueryDao.getBusArrivalInterval(map);
      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("min5-8", m != null ? m.get("veh_num") : "");
      list.add(resultMap);
    }
    if (true) {
      map.put("start_sec", 480);
      map.put("end_sec", 600);
      Map<String, Object> m = dataQueryDao.getBusArrivalInterval(map);
      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("min8-10", m != null ? m.get("veh_num") : "");
      list.add(resultMap);
    }
    if (true) {
      map.put("start_sec", 600);
      map.put("end_sec", 900);
      Map<String, Object> m = dataQueryDao.getBusArrivalInterval(map);
      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("min10-15", m != null ? m.get("veh_num") : "");
      list.add(resultMap);
    }
    if (true) {
      map.put("start_sec", 900);
      map.put("end_sec", 1200);
      Map<String, Object> m = dataQueryDao.getBusArrivalInterval(map);
      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("min15-20", m != null ? m.get("veh_num") : "");
      list.add(resultMap);
    }
    if (true) {
      map.put("start_sec", 1200);
      map.put("end_sec", -1);
      Map<String, Object> m = dataQueryDao.getBusArrivalInterval(map);
      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("min>20", m != null ? m.get("veh_num") : "");
      list.add(resultMap);
    }

    return list;
  }

  /**
   * getBusStopInfo.
   */
  public List<Map<String, Object>> getBusStopInfo(Map<String, Object> map) {
    return dataQueryDao.getBusStopInfo(map);
  }
}
