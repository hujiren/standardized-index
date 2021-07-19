package com.sutpc.its.service;

import com.sutpc.its.config.Config;
import com.sutpc.its.constant.DataMendingConstants;
import com.sutpc.its.dao.IMonitorEarlyWarningDao;
import com.sutpc.its.tools.DateUtils;
import com.sutpc.its.tools.PeriodUtils;
import com.sutpc.its.tools.TpiUtils;
import com.sutpc.its.tools.gps.GpsConvertUtils;
import com.sutpc.its.vo.CongestionWarnRealVo;
import com.sutpc.its.vo.RoadsectChartInfoVo;
import com.sutpc.its.vo.RoadsectMapVolumeVo;
import com.sutpc.its.vo.SpeedAndTpiChartVo;
import com.sutpc.its.vo.SpeedChartVo;
import com.sutpc.its.vo.TpiChartVo;
import com.sutpc.its.vo.VolumeChartVo;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MonitorEarlyWarningService {

  @Value("${tpi.mails}")
  private String mails;

  @Autowired
  private IMonitorEarlyWarningDao earlyWarningDao;

  @Autowired
  private DataMendingService dataMendingService;

  /**
   * .
   */
  public List<Map<String, Object>> getCityAndEveryDistrictTpi(Map<String, Object> map) {
    String dataFlag = map.get("dataFlag") + "";
    List<Map<String, Object>> data;
    if ("block".equals(dataFlag)) {
      data = earlyWarningDao.getEveryBlockTpi(map);
    }else{
      data = earlyWarningDao.getCityAndEveryDistrictTpi(map);
    }
    if(null != data && data.size() > 0){
      for (Map<String, Object> datum : data) {
        if(StringUtils.isEmpty(datum.get("TPI")))
          continue;
        BigDecimal bigDecimal = TpiUtils.PreservedDecimal(datum.get("TPI").toString(), 2);
        datum.put("TPI", bigDecimal);
      }
    }

    //return earlyWarningDao.getCityAndEveryDistrictTpi(map);

    /*if (data == null || data.size() == 0) {
      MailUtils.sendMailByWarn(Utils.getMails(mails), "/earlyWarning/getCityAndEveryDistrictTpi");
    }
    String[] configDistrictFid = (String[]) map.get("config_district_fid");
    *//*if (!map.containsKey("config_district_fid_flag") || !map.get("config_district_fid_flag")
        .toString().equals("1")) {
      String[] all_district_fid = new String[config_district_fid.length + 5];
      String[] other_district_fid = new String[]{"111", "222", "333", "1101", "2201"};
      System.arraycopy(config_district_fid, 0, all_district_fid, 0,
          config_district_fid.length);//将a数组内容复制新数组b
      System.arraycopy(other_district_fid, 0, all_district_fid, config_district_fid.length,
          other_district_fid.length);//将a数组内容复制新数组b
      config_district_fid = all_district_fid;
    }*//*

    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

    //循环取出每个行政区的数据
    for (String districtFid : configDistrictFid) {
      List<Map<String, Object>> districtFidList = new ArrayList<Map<String, Object>>();
      for (Map<String, Object> tempMap : data) {
        if (districtFid.equals(((BigDecimal) tempMap.get("FID")).toString())) {
          districtFidList.add(tempMap);
        }
      }

      if ((map.get("time") + "").equals(PeriodUtils.getCurrentDate())) {
        //如果data的size小于当前的时间片，则说明数据缺失
        if (districtFidList.size() < (Integer) map.get("period15")) {
          map.put("config_district_fid", districtFid.split(","));
          dataMendingService.dataMendingByPeriod15(districtFidList, map,
              DataMendingConstants.getCityAndEveryDistrictTpi);
        }
        for (Map<String, Object> tempMap : districtFidList) {
          resultList.add(tempMap);
        }
      } else {
        if (districtFidList.size() < 96) {
          map.put("config_district_fid", districtFid.split(","));
          dataMendingService.dataMendingByPeriod15(districtFidList, map,
              DataMendingConstants.getCityAndEveryDistrictTpi);
        }
        for (Map<String, Object> tempMap : districtFidList) {
          resultList.add(tempMap);
        }
      }


    }
    if (resultList != null) {
      CacheClass.getCityAndEveryDistrictTpi = resultList;
    } else {
      resultList = CacheClass.getCityAndEveryDistrictTpi;
    }*/
    return data;
    //return getSmoothData(resultList);
  }

  /**
   * 以下代码是为了做数据平滑，以达到指定的前端效果.
   */
  public List<Map<String, Object>> getSmoothData(List<Map<String, Object>> resultList) {
    String name = "";
    for (int i = 0; i < resultList.size(); i++) {
      Map<String, Object> omap = resultList.get(i);
      String thisName = omap.get("FNAME") + "";
      if (name.equals(thisName)) {
        Map<String, Object> oomap = resultList.get(i - 1);
        if (omap.containsKey("TPI")) {
          omap.put("TPI", formatDouble(Double.parseDouble(omap.get("TPI") + "") * 0.3
              + Double.parseDouble(oomap.get("TPI") + "") * 0.7));
        }
        if (omap.containsKey("SPEED")) {
          omap.put("SPEED", formatDouble(Double.parseDouble(omap.get("SPEED") + "") * 0.3
              + Double.parseDouble(oomap.get("SPEED") + "") * 0.7));
        }
      } else {
        name = thisName;
      }
    }
    return resultList;
  }

  /**
   * 保留两位小数，四舍五入的一个老土的方法.
   */
  public static String formatDouble(double d) {
    DecimalFormat df = new DecimalFormat("#0.00");
    return df.format(d);
  }


  /**
   * .
   */
  public List<Map<String, Object>> getRealTimeTrafficOperationRanking(Map<String, Object> map) {
    String dataFlag = map.get("dataFlag") + "";
    List<Map<String, Object>> list = new ArrayList<>();
    if ("block".equals(dataFlag)) {
      list = earlyWarningDao.getBlockRealTimeTrafficOperationRanking(map);
    }else{
      list = earlyWarningDao.getRealTimeTrafficOperationRanking(map);
    }
      if(list.size() > 0){
        for (Map<String, Object> entry : list) {
          if(StringUtils.isEmpty(entry.get("TPI")))
            continue;
          BigDecimal bigDecimal = TpiUtils.PreservedDecimal(entry.get("TPI").toString(), 2);
          entry.put("TPI", bigDecimal);
        }
      }
    return list;
  }


  /**
   * .
   */
  public List<Map<String, Object>> getDistrictCarOrBusData(Map<String, Object> map) {
    /*Map<String, Object> result = new HashMap();
    map.put("vehicle_type", "car");
    List<Map<String, Object>> carList = earlyWarningDao.getDistrictCarOrBusData(map);
    map.put("vehicle_type", "bus");
    List<Map<String, Object>> busList = earlyWarningDao.getDistrictCarOrBusData(map);*/

    if (!PeriodUtils.getCurrentDate().equals(map.get("time") + "")) {
      map.put("period15", 96);
    }
    List<Map<String, Object>> list = earlyWarningDao.getDistrictCarOrBusData(map);
    dataMendingService
        .dataMendingByPeriod15(list, map, DataMendingConstants.getDistrictCarOrBusData);

    return list;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRealtimeBusOperationRanking(Map<String, Object> map) {
    return earlyWarningDao.getRealtimeBusOperationRanking(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getBuslaneRealTimeOperationStatus(Map<String, Object> map) {
    return earlyWarningDao.getBuslaneRealTimeOperationStatus(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getSubsectRealTimeJamWarning(Map<String, Object> map) {
    List<Map<String, Object>> list = earlyWarningDao.getSubsectRealTimeJamWarning(map);
    if (list.size() == 0) {
      //根据需求，如果没有预警，则显示当前指数排名前十的数据，只需加一个参数，xml中sql语句便会改变
      map.put("isJam", 1);
      list = earlyWarningDao.getSubsectRealTimeJamWarning(map);
    }
    if(null != list && list.size() > 0){
      for (Map<String, Object> stringObjectMap : list) {
        if(StringUtils.isEmpty(stringObjectMap.get("TPI")))
          continue;
        BigDecimal bigDecimal = TpiUtils.PreservedDecimal(stringObjectMap.get("TPI").toString(), 2);
        stringObjectMap.put("TPI", bigDecimal);
      }
    }

    return list;
  }

  /**
   * .
   */
  public Map<String, Object> getSpeedAndTpiInfoBySubsectfid(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    List<Map<String, Object>> data = earlyWarningDao.getSpeedAndTpiInfoBySubsectfid(map);
    dataMendingService
        .dataMendingByPeriod15(data, map, DataMendingConstants.getSpeedAndTpiInfoBySubsectfid);
    result.put("data", data);
    /*List<Map<String, Object>> predictData = earlyWarningDao
        .getPredictSpeedAndTpiInfoByRoadsectfid(map);
    result.put("predict_data", predictData);*/
    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getSubsectRealTimeTrafficStatus(Map<String, Object> map) {
    if (map.containsKey("subsect_real_time_status_fid")) {
      map.put("subsect_real_time_status_fid",
          map.get("subsect_real_time_status_fid").toString().split(","));
    }
    Map<String, Object> resultMap = new HashMap<String, Object>();
    if (map.containsKey("lastWeekNow")) {
      map.put("time", PeriodUtils.getAnyDayBeforeDate(7));
      int startperiod = 1;
      int endperiod = 288;
      int startperiod15 = (startperiod - 1) / 3 + 1;
      int endperiod15 = (endperiod - 1) / 3 + 1;
      List<Map<String, Object>> listSon = new ArrayList<>();
      while (startperiod15 <= endperiod15) {
        map.put("period15", startperiod15);
        List<Map<String, Object>> resultList = setSubsectMap(
            earlyWarningDao.getSubsectRealTimeTrafficStatus(map));
        Map<String, Object> sonMap = new HashMap<>();
        sonMap.put("data", resultList);
        sonMap.put("period", startperiod15);
        sonMap.put("time", PeriodUtils.getTimeByPeriod((startperiod15 - 1) * 3));
        listSon.add(sonMap);
        startperiod15 = startperiod15 + 1;
      }
      resultMap.put("data", listSon);
      return resultMap;
    }
    if (map.containsKey("startQueryTime")) {
      int startperiod = PeriodUtils.time2period(map.get("startQueryTime") + "");
      int endperiod = PeriodUtils.time2period(map.get("endQueryTime") + "");
      int startperiod15 = (startperiod - 1) / 3 + 1;
      int endperiod15 = (endperiod - 1) / 3 + 1;
      List<Map<String, Object>> listSon = new ArrayList<>();
      while (startperiod15 <= endperiod15) {
        map.put("period15", startperiod15);
        List<Map<String, Object>> resultList = setSubsectMap(
            earlyWarningDao.getSubsectRealTimeTrafficStatus(map));
        if (resultList.size() != 0) {
          Map<String, Object> sonMap = new HashMap<>();
          sonMap.put("data", resultList);
          sonMap.put("period", startperiod15);
          sonMap.put("time", PeriodUtils.getTimeByPeriod((startperiod15 - 1) * 3));
          listSon.add(sonMap);
        }
        startperiod15 = startperiod15 + 1;
      }
      resultMap.put("data", listSon);
      return resultMap;
    }
    List<Map<String, Object>> list = earlyWarningDao.getSubsectRealTimeTrafficStatus(map);
    if(null != list && list.size() > 0){
      for (Map<String, Object> stringObjectMap : list) {
        if(StringUtils.isEmpty(stringObjectMap.get("TPI")))
          continue;
        BigDecimal bigDecimal = TpiUtils.PreservedDecimal(stringObjectMap.get("TPI").toString(), 2);
        stringObjectMap.put("TPI", bigDecimal);
      }
    }
    resultMap.put("data", setSubsectMap(list));
    return resultMap;
  }

  /**
   * .
   */
  public List<Map<String, Object>> setSubsectMap(List<Map<String, Object>> data) {
    Map<String, Object> map1 = new HashMap<>();
    map1.put("SUBSECT_FID", 113);
    map1.put("FNAME", "圆墩林场");
    map1.put("SPEED", 46.5);
    map1.put("STATUS", "畅通");
    map1.put("TPI", 0.79);
    data.add(map1);
    Map<String, Object> map2 = new HashMap<>();
    map2.put("SUBSECT_FID", 114);
    map2.put("FNAME", "圆墩村");
    map2.put("SPEED", 46.5);
    map2.put("STATUS", "畅通");
    map2.put("TPI", 1.69);
    data.add(map2);
    Map<String, Object> map3 = new HashMap<>();
    map3.put("SUBSECT_FID", 115);
    map3.put("FNAME", "民新村");
    map3.put("SPEED", 47.3);
    map3.put("STATUS", "畅通");
    map3.put("TPI", 1.21);
    data.add(map3);
    Map<String, Object> map4 = new HashMap<>();
    map4.put("SUBSECT_FID", 116);
    map4.put("FNAME", "百安村");
    map4.put("SPEED", 47.0);
    map4.put("STATUS", "畅通");
    map4.put("TPI", 1.75);
    data.add(map4);
    Map<String, Object> map5 = new HashMap<>();
    map5.put("SUBSECT_FID", 117);
    map5.put("FNAME", "红源村");
    map5.put("SPEED", 47.7);
    map5.put("STATUS", "畅通");
    map5.put("TPI", 1.18);
    data.add(map5);
    Map<String, Object> map6 = new HashMap<>();
    map6.put("SUBSECT_FID", 118);
    map6.put("FNAME", "红泉村");
    map6.put("SPEED", 48.6);
    map6.put("STATUS", "产沟通");
    map6.put("TPI", 1.17);
    data.add(map6);
    Map<String, Object> map7 = new HashMap<>();
    map7.put("SUBSECT_FID", 119);
    map7.put("FNAME", "江牡岛\n");
    map7.put("SPEED", 48.5);
    map7.put("STATUS", "畅通");
    map7.put("TPI", 1.32);
    data.add(map7);
    Map<String, Object> map8 = new HashMap<>();
    map8.put("SUBSECT_FID", 120);
    map8.put("FNAME", "赤石村");
    map8.put("SPEED", 48);
    map8.put("STATUS", "畅通");
    map8.put("TPI", 1.32);
    data.add(map8);
    Map<String, Object> map9 = new HashMap<>();
    map9.put("SUBSECT_FID", 121);
    map9.put("FNAME", "新联村");
    map9.put("SPEED", 49.4);
    map9.put("STATUS", "畅通");
    map9.put("TPI", 1.35);
    data.add(map9);
    Map<String, Object> map10 = new HashMap<>();
    map10.put("SUBSECT_FID", 122);
    map10.put("FNAME", "新里村");
    map10.put("SPEED", 48.3);
    map10.put("STATUS", "畅通");
    map10.put("TPI", 1.77);
    data.add(map10);
    Map<String, Object> map11 = new HashMap<>();
    map11.put("SUBSECT_FID", 123);
    map11.put("FNAME", "新城村");
    map11.put("SPEED", 50.2);
    map11.put("STATUS", "畅通");
    map11.put("TPI", 1.39);
    data.add(map11);
    Map<String, Object> map12 = new HashMap<>();
    map12.put("SUBSECT_FID", 124);
    map12.put("FNAME", "大安村");
    map12.put("SPEED", 49.1);
    map12.put("STATUS", "畅通");
    map12.put("TPI", 1.14);
    data.add(map12);
    Map<String, Object> map13 = new HashMap<>();
    map13.put("SUBSECT_FID", 125);
    map13.put("FNAME", "民生村");
    map13.put("SPEED", 50.7);
    map13.put("STATUS", "畅通");
    map13.put("TPI", 1.76);
    data.add(map13);
    Map<String, Object> map14 = new HashMap<>();
    map14.put("SUBSECT_FID", 126);
    map14.put("FNAME", "碗窑村");
    map14.put("SPEED", 49.7);
    map14.put("STATUS", "畅通");
    map14.put("TPI", 1.01);
    data.add(map14);
    Map<String, Object> map15 = new HashMap<>();
    map15.put("SUBSECT_FID", 127);
    map15.put("FNAME", "明热村");
    map15.put("SPEED", 51.3);
    map15.put("STATUS", "畅通");
    map15.put("TPI", 1.75);
    data.add(map15);
    Map<String, Object> map16 = new HashMap<>();
    map16.put("SUBSECT_FID", 128);
    map16.put("FNAME", "明溪村");
    map16.put("SPEED", 50.6);
    map16.put("STATUS", "畅通");
    map16.put("TPI", 1.73);
    data.add(map16);
    Map<String, Object> map17 = new HashMap<>();
    map17.put("SUBSECT_FID", 129);
    map17.put("FNAME", "朝面山村");
    map17.put("SPEED", "");
    map17.put("STATUS", "");
    map17.put("TPI", "");
    data.add(map17);
    Map<String, Object> map18 = new HashMap<>();
    map18.put("SUBSECT_FID", 130);
    map18.put("FNAME", "西南村");
    map18.put("SPEED", 50.8);
    map18.put("STATUS", "畅通");
    map18.put("TPI", 1.22);
    data.add(map18);
    Map<String, Object> map19 = new HashMap<>();
    map19.put("SUBSECT_FID", 131);
    map19.put("FNAME", "旺官圩社区");
    map19.put("SPEED", 52.3);
    map19.put("STATUS", "畅通");
    map19.put("TPI", 1.13);
    data.add(map19);
    Map<String, Object> map20 = new HashMap<>();
    map20.put("SUBSECT_FID", 132);
    map20.put("FNAME", "旺渔村");
    map20.put("SPEED", 51.0);
    map20.put("STATUS", "畅通");
    map20.put("TPI", 1.02);
    data.add(map20);
    Map<String, Object> map21 = new HashMap<>();
    map21.put("SUBSECT_FID", 133);
    map21.put("FNAME", "南香村");
    map21.put("SPEED", 52.5);
    map21.put("STATUS", "畅通");
    map21.put("TPI", "1.61");
    data.add(map21);
    Map<String, Object> map22 = new HashMap<>();
    map22.put("SUBSECT_FID", 134);
    map22.put("FNAME", "东旺村");
    map22.put("SPEED", 51.8);
    map22.put("STATUS", "畅通");
    map22.put("TPI", 0.91);
    data.add(map22);
    Map<String, Object> map23 = new HashMap<>();
    map23.put("SUBSECT_FID", 135);
    map23.put("FNAME", "元新村");
    map23.put("SPEED", 53.4);
    map23.put("STATUS", "畅通");
    map23.put("TPI", 1.34);
    data.add(map23);
    Map<String, Object> map24 = new HashMap<>();
    map24.put("SUBSECT_FID", 136);
    map24.put("FNAME", "云新村");
    map24.put("SPEED", 52.1);
    map24.put("STATUS", "畅通");
    map24.put("TPI", 1.22);
    data.add(map24);
    Map<String, Object> map25 = new HashMap<>();
    map25.put("SUBSECT_FID", 137);
    map25.put("FNAME", "大澳村");
    map25.put("SPEED", "54.4");
    map25.put("STATUS", "畅通");
    map25.put("TPI", 1.01);
    data.add(map25);
    Map<String, Object> map26 = new HashMap<>();
    map26.put("SUBSECT_FID", 138);
    map26.put("FNAME", "鹅埠社区");
    map26.put("SPEED", 52.7);
    map26.put("STATUS", "畅通");
    map26.put("TPI", 1.66);
    data.add(map26);
    Map<String, Object> map27 = new HashMap<>();
    map27.put("SUBSECT_FID", 139);
    map27.put("FNAME", "水美村");
    map27.put("SPEED", 55.3);
    map27.put("STATUS", "畅通");
    map27.put("TPI", 1.18);
    data.add(map27);
    Map<String, Object> map28 = new HashMap<>();
    map28.put("SUBSECT_FID", 140);
    map28.put("FNAME", "民安村");
    map28.put("SPEED", 53.4);
    map28.put("STATUS", "畅通");
    map28.put("TPI", 1.73);
    data.add(map28);
    Map<String, Object> map29 = new HashMap<>();
    map29.put("SUBSECT_FID", 141);
    map29.put("FNAME", "蛟湖村");
    map29.put("SPEED", 56.0);
    map29.put("STATUS", "畅通");
    map29.put("TPI", 0.83);
    data.add(map29);
    Map<String, Object> map30 = new HashMap<>();
    map30.put("SUBSECT_FID", 142);
    map30.put("FNAME", "西湖村");
    map30.put("SPEED", 54.4);
    map30.put("STATUS", "畅通");
    map30.put("TPI", 1.30);
    data.add(map30);
    Map<String, Object> map31 = new HashMap<>();
    map31.put("SUBSECT_FID", 143);
    map31.put("FNAME", "上北村\n");
    map31.put("SPEED", 56.3);
    map31.put("TPI", 1.14);
    map31.put("STATUS", "畅通");
    data.add(map31);
    Map<String, Object> map32 = new HashMap<>();
    map32.put("SUBSECT_FID", 144);
    map32.put("FNAME", "下北村");
    map32.put("SPEED", 54.5);
    map32.put("TPI", 1.31);
    map32.put("STATUS", "畅通");
    data.add(map32);
    Map<String, Object> map33 = new HashMap<>();
    map33.put("SUBSECT_FID", 145);
    map33.put("FNAME", "冰深村");
    map33.put("SPEED", 57.1);
    map33.put("TPI", 0.90);
    map33.put("STATUS", "畅通");
    data.add(map33);
    Map<String, Object> map34 = new HashMap<>();
    map34.put("SUBSECT_FID", 146);
    map34.put("FNAME", "洛坑村");
    map34.put("SPEED", 54.6);
    map34.put("TPI", 1.09);
    map34.put("STATUS", "畅通");
    data.add(map34);
    Map<String, Object> map35 = new HashMap<>();
    map35.put("SUBSECT_FID", 147);
    map35.put("FNAME", "芒屿岛");
    map35.put("SPEED", 57.2);
    map35.put("TPI", 1.59);
    map35.put("STATUS", "畅通");
    data.add(map35);
    Map<String, Object> map36 = new HashMap<>();
    map36.put("SUBSECT_FID", 148);
    map36.put("FNAME", "新园村");
    map36.put("SPEED", 54.7);
    map36.put("TPI", 1.13);
    map36.put("STATUS", "畅通");
    data.add(map36);
    Map<String, Object> map37 = new HashMap<>();
    map37.put("SUBSECT_FID", 149);
    map37.put("FNAME", "田寮村");
    map37.put("SPEED", 57.3);
    map37.put("TPI", 1.33);
    map37.put("STATUS", "畅通");
    data.add(map37);

    return data;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getPoiRealTimeJamWarning(Map<String, Object> paramap) {
    List<Map<String, Object>> list = earlyWarningDao.getPoiRealTimeJamWarning(paramap);
    if (list.size() == 0) {
      //根据需求，如果没有预警，则显示当前指数排名前十的数据，只需加一个参数，xml中sql语句便会改变
      paramap.put("isJam", 1);
      list = earlyWarningDao.getPoiRealTimeJamWarning(paramap);
    }
    return list;
  }

  /**
   * .
   */
  public Map<String, Object> getSpeedAndTpiInfoByPoifid(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    List<Map<String, Object>> data = earlyWarningDao.getSpeedAndTpiInfoByPoifid(map);
    result.put("data", data);
    dataMendingService
        .dataMendingByPeriod15(data, map, DataMendingConstants.getSpeedAndTpiInfoByPoifid);
    /*List<Map<String, Object>> predictData = earlyWarningDao
        .getPredictSpeedAndTpiInfoByRoadsectfid(map);
    result.put("predict_data", predictData);*/
    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getPoiRealTimeTrafficStatus(Map<String, Object> map) {
    if (map.containsKey("poi_real_time_status_fid")) {
      map.put("poi_real_time_status_fid",
          map.get("poi_real_time_status_fid").toString().split(","));
    }
    Map<String, Object> resultMap = new HashMap<String, Object>();
    if (map.containsKey("lastWeekNow")) {
      map.put("time", PeriodUtils.getAnyDayBeforeDate(7));
      int startperiod = 1;
      int endperiod = 288;
      int startperiod15 = (startperiod - 1) / 3 + 1;
      int endperiod15 = (endperiod - 1) / 3 + 1;
      List<Map<String, Object>> listSon = new ArrayList<>();
      while (startperiod15 <= endperiod15) {
        map.put("period15", startperiod15);
        List<Map<String, Object>> resultList = earlyWarningDao.getPoiRealTimeTrafficStatus(map);
        resultList = GpsConvertUtils.convertGps_WgsToGcj(resultList, "POI_LNG", "POI_LAT");
        Map<String, Object> sonMap = new HashMap<>();
        sonMap.put("data", resultList);
        sonMap.put("period", startperiod15);
        sonMap.put("time", PeriodUtils.getTimeByPeriod((startperiod15 - 1) * 3));
        listSon.add(sonMap);
        startperiod15 = startperiod15 + 1;
      }
      resultMap.put("data", listSon);
      return resultMap;
    }
    if (map.containsKey("startQueryTime")) {
      int startperiod = PeriodUtils.time2period(map.get("startQueryTime") + "");
      int endperiod = PeriodUtils.time2period(map.get("endQueryTime") + "");
      int startperiod15 = (startperiod - 1) / 3 + 1;
      int endperiod15 = (endperiod - 1) / 3 + 1;
      List<Map<String, Object>> listSon = new ArrayList<>();
      while (startperiod15 <= endperiod15) {
        map.put("period15", startperiod15);
        List<Map<String, Object>> resultList = earlyWarningDao.getPoiRealTimeTrafficStatus(map);
        resultList = GpsConvertUtils.convertGps_WgsToGcj(resultList, "POI_LNG", "POI_LAT");
        if (resultList.size() != 0) {
          Map<String, Object> sonMap = new HashMap<>();
          sonMap.put("data", resultList);
          sonMap.put("period", startperiod15);
          sonMap.put("time", PeriodUtils.getTimeByPeriod((startperiod15 - 1) * 3));
          listSon.add(sonMap);
        }
        startperiod15 = startperiod15 + 1;
      }
      resultMap.put("data", listSon);
      return resultMap;
    }
    resultMap.put("data", GpsConvertUtils
        .convertGps_WgsToGcj(earlyWarningDao.getPoiRealTimeTrafficStatus(map), "POI_LNG",
            "POI_LAT"));
    return resultMap;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getBlockRealTimeJamWarning(Map<String, Object> map) {
    List<Map<String, Object>> list = earlyWarningDao.getBlockRealTimeJamWarning(map);
    if (list.size() == 0) {
      //根据需求，如果没有预警，则显示当前指数排名前十的数据，只需加一个参数，xml中sql语句便会改变
      map.put("isJam", 1);
      list = earlyWarningDao.getBlockRealTimeJamWarning(map);
    }
    return list;
  }

  /**
   * .
   */
  public Map<String, Object> getSpeedAndTpiInfoByBlockfid(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    List<Map<String, Object>> data = earlyWarningDao.getSpeedAndTpiInfoByBlockfid(map);

    //如果data的size小于当前的时间片，则说明数据缺失
    if (data.size() < (Integer) map.get("period15")) {
      dataMendingService.dataMendingByPeriod15(data, map,
          DataMendingConstants.getSpeedAndTpiInfoByBlockfid);
    }

    result.put("data", data);
    /*List<Map<String, Object>> predictData = earlyWarningDao
        .getPredictSpeedAndTpiInfoByRoadsectfid(map);
    result.put("predict_data", predictData);*/
    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getBlockRealTimeTrafficStatus(Map<String, Object> map) {
    if (map.containsKey("block_real_time_status_fid")) {
      map.put("block_real_time_status_fid",
          map.get("block_real_time_status_fid").toString().split(","));
    }
    Map<String, Object> resultMap = new HashMap<String, Object>();
    if (map.containsKey("lastWeekNow")) {
      map.put("time", PeriodUtils.getAnyDayBeforeDate(7));
      int startperiod = 1;
      int endperiod = 288;
      int startperiod15 = (startperiod - 1) / 3 + 1;
      int endperiod15 = (endperiod - 1) / 3 + 1;
      List<Map<String, Object>> listSon = new ArrayList<>();
      while (startperiod15 <= endperiod15) {
        map.put("period15", startperiod15);
        List<Map<String, Object>> resultList = earlyWarningDao.getBlockRealTimeTrafficStatus(map);
        resultList = putShenshan(earlyWarningDao.getBlockRealTimeTrafficStatus(map));
        Map<String, Object> sonMap = new HashMap<>();
        sonMap.put("data", resultList);
        sonMap.put("period", startperiod15);
        sonMap.put("time", PeriodUtils.getTimeByPeriod((startperiod15 - 1) * 3));
        listSon.add(sonMap);
        startperiod15 = startperiod15 + 1;
      }
      resultMap.put("data", listSon);
      return resultMap;
    }
    if (map.containsKey("startQueryTime")) {
      int startperiod = PeriodUtils.time2period(map.get("startQueryTime") + "");
      int endperiod = PeriodUtils.time2period(map.get("endQueryTime") + "");
      int startperiod15 = (startperiod - 1) / 3 + 1;
      int endperiod15 = (endperiod - 1) / 3 + 1;
      List<Map<String, Object>> listSon = new ArrayList<>();
      while (startperiod15 <= endperiod15) {
        map.put("period15", startperiod15);
        List<Map<String, Object>> resultList = earlyWarningDao.getBlockRealTimeTrafficStatus(map);
        if (resultList != null && resultList.size() > 0) {
          resultList = putShenshan(earlyWarningDao.getBlockRealTimeTrafficStatus(map));
        }
        if (resultList.size() != 0) {
          Map<String, Object> sonMap = new HashMap<>();
          sonMap.put("data", resultList);
          sonMap.put("period", startperiod15);
          sonMap.put("time", PeriodUtils.getTimeByPeriod((startperiod15 - 1) * 3));
          listSon.add(sonMap);
        }
        startperiod15 = startperiod15 + 1;
      }
      resultMap.put("data", listSon);
      return resultMap;
    }
    List<Map<String, Object>> data = earlyWarningDao.getBlockRealTimeTrafficStatus(map);
    if (data != null && data.size() > 0) {
      data = putShenshan(data);
    }
    resultMap.put("data", data);
    return resultMap;
  }

  /**
   * 深汕合作区街道临时假数据.
   */
  public List<Map<String, Object>> putShenshan(List<Map<String, Object>> data) {
    Map<String, Object> map1 = new HashMap<>();
    map1.put("BLOCK_FID", 75);
    map1.put("FNAME", "鹅埠镇");
    map1.put("PERIOD", PeriodUtils.getCurrentPeriod15());
    map1.put("STATUS", "畅通");
    map1.put("SPEED", 49.5 + new Random().nextInt(5 - (-5) + 1) + (-5));
    map1.put("TPI", 1.15);

    Map<String, Object> map2 = new HashMap<>();
    map2.put("BLOCK_FID", 76);
    map2.put("FNAME", "小漠镇");
    map2.put("PERIOD", PeriodUtils.getCurrentPeriod15());
    map2.put("STATUS", "畅通");
    map2.put("SPEED", 51.2 + new Random().nextInt(5 - (-5) + 1) + (-5));
    map2.put("TPI", 1.01);

    Map<String, Object> map3 = new HashMap<>();
    map3.put("BLOCK_FID", 77);
    map3.put("FNAME", "鮜门镇");
    map3.put("PERIOD", PeriodUtils.getCurrentPeriod15());
    map3.put("STATUS", "畅通");
    map3.put("SPEED", 49.8 + new Random().nextInt(5 - (-5) + 1) + (-5));
    map3.put("TPI", 0.98);

    Map<String, Object> map4 = new HashMap<>();
    map4.put("BLOCK_FID", 78);
    map4.put("FNAME", "赤石镇");
    map4.put("PERIOD", PeriodUtils.getCurrentPeriod15());
    map4.put("STATUS", "畅通");
    map4.put("SPEED", 50.5 + new Random().nextInt(5 - (-5) + 1) + (-5));
    map4.put("TPI", 1.09);

    data.add(map1);
    data.add(map2);
    data.add(map3);
    data.add(map4);
    return data;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadsectRealTimeJamWarning(Map<String, Object> map) {
    List<Map<String, Object>> list = earlyWarningDao.getRoadsectRealTimeJamWarning(map);
    if (list.size() == 0) {
      //根据需求，如果没有预警，则显示当前指数排名前十的数据，只需加一个参数，xml中sql语句便会改变
      map.put("isJam", 1);
      list = earlyWarningDao.getRoadsectRealTimeJamWarning(map);
    }
    return list;
  }

  /**
   * .
   */
  public Map<String, Object> getSpeedAndTpiInfoByRoadsectfid(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    List<Map<String, Object>> data = earlyWarningDao.getSpeedAndTpiInfoByRoadsectfid(map);
    result.put("data", data);
    List<Map<String, Object>> predictData = null;
    if (!map.get("od_flag").equals("tongji")) {
      predictData = earlyWarningDao.getPredictSpeedAndTpiInfoByRoadsectfid(map);

      result.put("predict_data", predictData);

      /* 编辑预测数据的最后一条数据 */
      if (data != null && data.size() > 0) {
        int periodActual = ((BigDecimal) data.get(data.size() - 1).get("PERIOD")).intValue();
        if (predictData != null && predictData.size() > 0) {
          int periodPredict = ((BigDecimal) predictData.get(0).get("PERIOD")).intValue();
          if (periodActual == periodPredict) {   //如果实际值与预测值的时间片相同,
            predictData.remove(0);
          }
          if (predictData.size() > 8) {
            predictData.remove(0);
          }
          Map<String, Object> tempMap = new HashMap<String, Object>();
          tempMap.put("PERIOD", data.get(data.size() - 1).get("PERIOD"));
          tempMap.put("SPEED", data.get(data.size() - 1).get("SPEED"));
          tempMap.put("TPI", data.get(data.size() - 1).get("TPI"));
          predictData.add(0, tempMap);
        }

      }

    }

    //如果data的size小于当前的时间片，则说明数据缺失
    //    if (data.size() < (Integer) map.get("period15")) {
    //      dataMendingService.dataMendingByPeriod15(data, map,
    //          DataMendingConstants.getSpeedAndTpiInfoByRoadsectfid);
    //    }
    dataMendingService.doCompletionDataForRoadsect(data, map, earlyWarningDao);
    data = getSmoothData(data);

    /**
     * 以下操作是为了处理晚上十点后，预测数据会和真实数据重叠的问题.
     */
    final List<Map<String, Object>> dd = data;
    if (predictData != null) {
      predictData.removeIf(mm -> {
        String period = mm.get("PERIOD") + "";
        for (int i = 0; i < dd.size() - 1; i++) {
          Map<String, Object> ms = dd.get(i);
          String periodreal = ms.get("PERIOD") + "";
          if (period.equals(periodreal)) {
            return true;
          }
        }
        return false;
      });

    }

    return result;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadsectRealTimeTrafficStatus(Map<String, Object> map) {

    if (map.containsKey("queryTime")) {
      int queryPeriod = PeriodUtils.time2period(map.get("queryTime") + "");
      int startperiod15 = queryPeriod / 3;
      map.put("period15", startperiod15);
      List<Map<String, Object>> resultList = earlyWarningDao.getRoadsectRealTimeTrafficStatus(map);
      if (resultList.size() == 0) {
        map.put("predict_period", startperiod15);
        List<Map<String, Object>> predictList = earlyWarningDao
            .getRoadsectRealTimeTrafficStatusPredict(map);
        return predictList;
      }
      return resultList;
    }
    List<Map<String, Object>> list = earlyWarningDao.getRoadsectRealTimeTrafficStatus(map);
    return list;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadsectRealTimeTrafficStatusPredict(
      Map<String, Object> paramap) {
    if (paramap.containsKey("queryTime")) {
      int queryPeriod = PeriodUtils.time2period(paramap.get("queryTime") + "");
      int startperiod15 = queryPeriod / 3;
      paramap.put("predict_period", startperiod15);
      List<Map<String, Object>> resultList = earlyWarningDao
          .getRoadsectRealTimeTrafficStatusPredict(paramap);
      return resultList;
    }
    List<Map<String, Object>> list = earlyWarningDao
        .getRoadsectRealTimeTrafficStatusPredict(paramap);
    return list;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getHighSpeedRealTimeJamWarning(Map<String, Object> map) {
    List<Map<String, Object>> list = earlyWarningDao.getHighSpeedRealTimeJamWarning(map);
    if (list.size() == 0) {
      //根据需求，如果没有预警，则显示当前指数排名前十的数据，只需加一个参数，xml中sql语句便会改变
      map.put("isJam", 1);
      list = earlyWarningDao.getHighSpeedRealTimeJamWarning(map);
    }
    return list;
  }

  /**
   * .
   */
  public Map<String, Object> getHighSpeedRealTimeTrafficStatus(Map<String, Object> map) {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    if (map.containsKey("lastWeekNow")) {
      map.put("time", PeriodUtils.getAnyDayBeforeDate(7));
      int startperiod = PeriodUtils.time2period(map.get("startQueryTime") + "");
      int endperiod = PeriodUtils.time2period(map.get("endQueryTime") + "");
      int startperiod15 = (startperiod - 1) / 3 + 1;
      int endperiod15 = (endperiod - 1) / 3 + 1;
      List<Map<String, Object>> listSon = new ArrayList<>();
      while (startperiod15 <= endperiod15) {
        map.put("period15", startperiod15);
        List<Map<String, Object>> resultList = earlyWarningDao
            .getHighSpeedRealTimeTrafficStatus(map);
        if (resultList.size() != 0) {
          Map<String, Object> sonMap = new HashMap<>();
          sonMap.put("data", resultList);
          sonMap.put("period", startperiod15);
          sonMap.put("time", PeriodUtils.getTimeByPeriod((startperiod15 - 1) * 3));
          listSon.add(sonMap);
        }
        startperiod15 = startperiod15 + 1;
      }
      resultMap.put("data", listSon);
      return resultMap;
    }
    if (map.containsKey("startQueryTime")) {
      int startperiod = PeriodUtils.time2period(map.get("startQueryTime") + "");
      int endperiod = PeriodUtils.time2period(map.get("endQueryTime") + "");
      int startperiod15 = (startperiod - 1) / 3 + 1;
      int endperiod15 = (endperiod - 1) / 3 + 1;
      List<Map<String, Object>> listSon = new ArrayList<>();
      while (startperiod15 <= endperiod15) {
        map.put("period15", startperiod15);
        List<Map<String, Object>> resultList = earlyWarningDao
            .getHighSpeedRealTimeTrafficStatus(map);
        if (resultList.size() != 0) {
          Map<String, Object> sonMap = new HashMap<>();
          sonMap.put("data", resultList);
          sonMap.put("period", startperiod15);
          sonMap.put("time", PeriodUtils.getTimeByPeriod((startperiod15 - 1) * 3));
          listSon.add(sonMap);
        }
        startperiod15 = startperiod15 + 1;
      }
      resultMap.put("data", listSon);
      return resultMap;
    }
    if (map.containsKey("queryTime")) {
      int period = PeriodUtils.time2period(map.get("queryTime") + "");
      int period15 = (period - 1) / 3 + 1;
      map.put("period15", period15);
    }
    List<Map<String, Object>> resultList = earlyWarningDao.getHighSpeedRealTimeTrafficStatus(map);
    if (resultList.size() == 0) {
      map.put("period15", Integer.parseInt(map.get("period15").toString()) - 1);
      resultList = earlyWarningDao.getHighSpeedRealTimeTrafficStatus(map);
    }
    resultMap.put("data", resultList);
    return resultMap;
  }

  /**
   * .
   */
  public Map<String, Object> getHighSpeedLineDataByRoadsectfid(Map<String, Object> map) {
    List<Map<String, Object>> list = earlyWarningDao.getHighSpeedLineDataByRoadsectfid(map);
    dataMendingService
        .dataMendingByPeriod15(list, map, DataMendingConstants.getHighSpeedLineDataByRoadsectfid);
    Map<String, Object> resultmap = new HashMap<>();
    resultmap.put("data", list);
    return resultmap;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getPredictSpeedAndTpiInfoByRoadsectfid(
      Map<String, Object> paramap) {
    return earlyWarningDao.getPredictSpeedAndTpiInfoByRoadsectfid(paramap);
  }

  /**
   * .
   */
  public Map<String, Object> getRoadSectionFlowRanking(Map<String, Object> map) {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    if (map.containsKey("startQueryTime")) {
      int startperiod = PeriodUtils.time2period(map.get("startQueryTime") + "");
      int endperiod = PeriodUtils.time2period(map.get("endQueryTime") + "");
      int startperiod15 = (startperiod - 1) / 3 + 1;
      int endperiod15 = (endperiod - 1) / 3 + 1;
      List<Map<String, Object>> listSon = new ArrayList<>();
      while (startperiod15 <= endperiod15) {
        map.put("period15", startperiod15);
        List<Map<String, Object>> resultList = earlyWarningDao.getRoadSectionFlowRanking(map);
        if (resultList.size() != 0) {
          Map<String, Object> sonMap = new HashMap<>();
          sonMap.put("data", resultList);
          sonMap.put("period", startperiod15);
          sonMap.put("time", PeriodUtils.getTimeByPeriod((startperiod15 - 1) * 3));
          listSon.add(sonMap);
        }
        startperiod15 = startperiod15 + 1;
      }
    }
    resultMap.put("data", earlyWarningDao.getRoadSectionFlowRanking(map));
    return resultMap;
  }

  /**
   * .
   */
  public Map<String, Object> getRoadSectionFlowsByLinkFid(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    List<Map<String, Object>> capacityList = earlyWarningDao.getCapacityByLinkFid(map);
    List<Map<String, Object>> sectionFlow = earlyWarningDao.getRoadSectionFlowsByLinkFid(map);
    result.put("capacityList", capacityList);
    result.put("sectionFlow", sectionFlow);
    return result;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDcInfo(Map<String, Object> map) {
    return earlyWarningDao.getDcInfo(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDetailInfoByLinkFid(Map<String, Object> map) {
    return earlyWarningDao.getDetailInfoByLinkFid(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getIntersectionDelayStatus(Map<String, Object> map) {
    return earlyWarningDao.getIntersectionDelayStatus(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getIntersectionDelayDetails(Map<String, Object> map) {
    return earlyWarningDao.getIntersectionDelayDetails(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getIntersectionDelayDayData(Map<String, Object> map) {
    List<Map<String, Object>> data = earlyWarningDao.getIntersectionDelayDayData(map);

    //如果data的size小于当前的时间片，则说明数据缺失,补齐所缺失的数据
    if (data.size() < (Integer) map.get("period15")) {
      if (map.get("time").toString().equals(PeriodUtils.getCurrentDate())) {
        dataMendingService
            .missDataMendingByPeriod15(data, map, DataMendingConstants.getIntersectionDelayDayData,
                1);
      } else {
        dataMendingService
            .missDataMendingByPeriod15(data, map, DataMendingConstants.getIntersectionDelayDayData,
                2);
      }

    }
    return data;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getIntersectinDelayRanking(Map<String, Object> map) {
    return earlyWarningDao.getIntersectinDelayRanking(map);
  }

  /**
   * .
   */
  public Map<String, Object> getRoadSectFlowDistribution(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<String, Object>();
    String date = map.get("roadsect_flow_time") + "";
    date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
    String hour = PeriodUtils.getCurrentPeriod() / 12 + "";
    String time = date + " " + hour + ":00";
    result.put("data_time", time);
    List<Map<String, Object>> roadSectFlowDistribution = earlyWarningDao.getRoadSectFlowDistribution(map);
    result.put("distribution_info", roadSectFlowDistribution);
    return result;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDetectorDirFlowByDetectorFid(Map<String, Object> map) {
    return earlyWarningDao.getDetectorDirFlowByDetectorFid(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadSectionFlows(Map<String, Object> map) {
    return earlyWarningDao.getRoadSectionFlows(map);
  }

  /**
   * .
   */
  public Map<String, Object> getDetectorHourLineData(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<String, Object>();
    List<Map<String, Object>> l = earlyWarningDao.getDetectorDirByFid(map);
    for (int i = 0; i < l.size(); i++) {
      Map<String, Object> mapResult = new HashMap<String, Object>();
      Map<String, Object> m = l.get(i);
      map.putAll(m);
      m.put("fid", map.get("fid"));
      String detectDirFid = m.get("detect_dir_fid") + "";
      String detectDirName = m.get("detect_dir") + "";
      String roadName = m.get("road_name") + "";
      if (!detectDirFid.equals("null")) {
        Map<String, Object> mapSon = earlyWarningDao.getCapacity(m);
        List<Map<String, Object>> list = earlyWarningDao.getDetectorHourLineData(map);
        if (list == null || list.size() == 0) {
          continue;
        }
        mapResult.put("data", list);
        mapResult.put("capacity", mapSon.get("capacity"));
      }
      result.put(roadName + "(" + detectDirName + ")", mapResult);
    }
    return result;
  }


  /**
   * 获取全市拥堵指标:拥堵路段数，拥堵里程数.
   *
   * @param map 为空
   */
  public Map<String, Object> getCongestionRoadInfo(Map<String, Object> map) {
    // 创建一个数值格式化对象
    NumberFormat numberFormat = NumberFormat.getInstance();
    // 设置精确到小数点后2位
    numberFormat.setMaximumFractionDigits(1);

    map.put("time", PeriodUtils.getCurrentDate());
    int currentPeriod = (int) map.get("period");
    int period30 = (currentPeriod - 1) / 6;
    map.put("period30", period30);

    //获取当前时间片拥堵的道路数:指数大于6以上的都是拥堵，这个在中路段表定义，就可以筛选路段数
    List<Map<String, Object>> roadNumlist = earlyWarningDao.getCongestionRoadNum(map);
    final int congestionRoadNum = roadNumlist.size();
    //获取总里程数
    map.put("roadsectFidlist", roadNumlist);
    Map<String, Object> lengthMap = earlyWarningDao.getCongestionRoadTotalLength(map);
    final Double totalLength = ((BigDecimal) lengthMap.get("totalLength")).doubleValue();

    //获取全市指数
    Map<String, Object> indexMap = earlyWarningDao.getCityIndex(map);
    double index = 0.0;
    if (indexMap != null && !indexMap.isEmpty()) {
      index = ((BigDecimal) indexMap.get("tpi")).doubleValue();
    }

    //获取拥堵路段速度
    List<Map<String, Object>> roadSpeedlist = earlyWarningDao.getCongestionRoadNum(map);
    Double speed = 0.0;
    for (Map<String, Object> tempMap : roadSpeedlist) {
      speed += ((BigDecimal) tempMap.get("speed")).doubleValue();
    }
    String speedAvg = 0.0 + "";
    if (!CollectionUtils.isEmpty(roadSpeedlist)) {
      speedAvg = numberFormat.format(Float.valueOf(speed + "") / (float) roadSpeedlist.size());
    }

    //获取7天前的时间
    String time = PeriodUtils.getAppointDateAnyDayBeforeDate(7, (String) map.get("time"));
    map.put("time", time);
    //获取7天前当前时间片拥堵的里程数:指数大于6以上的都是拥堵，这个在中路段表定义，就可以筛选路段数
    List<Map<String, Object>> listBeforeSeven = earlyWarningDao.getCongestionRoadNum(map);
    final int congestionRoadNumSevenDaysBefore = listBeforeSeven.size();
    //获取7天前当前时间片总里程数
    map.put("roadsectFidlist", listBeforeSeven);
    Map<String, Object> lengthBeforeSevenMap = earlyWarningDao.getCongestionRoadTotalLength(map);
    final Float totalLengthSevenDaysBefore = ((BigDecimal) lengthBeforeSevenMap.get("totalLength"))
        .floatValue();

    //获取7天前的全市指数
    Map<String, Object> indexBeforeSevenMap = earlyWarningDao.getCityIndex(map);
    double indexBeforeSeven = 0.0;
    if (indexBeforeSevenMap != null && !indexBeforeSevenMap.isEmpty()) {
      indexBeforeSeven = ((BigDecimal) indexBeforeSevenMap.get("tpi")).doubleValue();
    }

    //获取7天前的拥堵路段速度
    List<Map<String, Object>> roadSpeedBeforeSevenList = earlyWarningDao.getCongestionRoadNum(map);
    Double speedBeforeSeven = 0.0;
    for (Map<String, Object> tempMap : roadSpeedBeforeSevenList) {
      speedBeforeSeven += ((BigDecimal) tempMap.get("speed")).doubleValue();
    }
    String speedBeforeSevenAvg = 0.0 + "";
    if (roadSpeedBeforeSevenList != null && roadSpeedBeforeSevenList.size() > 0) {
      speedBeforeSevenAvg = numberFormat
          .format(Float.valueOf(speedBeforeSeven + "") / (float) roadSpeedBeforeSevenList.size());
    }

    String congestionPercent = congestionRoadNumSevenDaysBefore == 0 ? "0" : numberFormat.format(
        ((float) (congestionRoadNum - congestionRoadNumSevenDaysBefore)
            / (float) congestionRoadNumSevenDaysBefore) * 100);
    String totalLengthPercent = totalLengthSevenDaysBefore == 0 ? "0" : numberFormat.format(
        ((float) (totalLength - totalLengthSevenDaysBefore) / (float) totalLengthSevenDaysBefore)
            * 100);

    String indexPercent = indexBeforeSeven == 0.0 ? "0" : numberFormat
        .format(((float) (index - indexBeforeSeven) / (float) indexBeforeSeven) * 100);
    String speedPercent = Float.valueOf(speedBeforeSevenAvg) == 0 ? "0" : numberFormat.format(
        ((Float.valueOf(speedAvg) - Float.valueOf(speedBeforeSevenAvg)) / Float
            .valueOf(speedBeforeSevenAvg)) * 100);

    Map<String, Object> resultMap = new HashMap<String, Object>();
    resultMap.put("congestionRoadNum", congestionRoadNum);
    resultMap.put("congestionPercent", Double.valueOf(congestionPercent));
    resultMap.put("totalLength", totalLength.intValue());
    resultMap.put("totalLengthPercent", Double.valueOf(totalLengthPercent));
    resultMap.put("tpi", index);
    resultMap.put("tpiPercent", Double.valueOf(indexPercent));
    resultMap.put("speedAvg", Double.valueOf(speedAvg));
    resultMap.put("speedPercent", Double.valueOf(speedPercent));

    return resultMap;
  }


  /**
   * 获取实时拥堵预警信息.
   *
   * @param map 空
   */
  public List<Map<String, Object>> getCongestionWarnReal(Map<String, Object> map) {

    map.put("time", PeriodUtils.getCurrentDate());

    List<Map<String, Object>> list = earlyWarningDao.getCongestionWarnReal(map);

    return list;
  }

  /**
   * 新-实时拥堵预警信息. 添加路段流量信息.
   */
  public List<CongestionWarnRealVo> getCongestionWarnRealV2() {
    List<CongestionWarnRealVo> thisList = earlyWarningDao
        .getCongestionWarnRealV2(DateUtils.currentDate(), DateUtils.currentPeriod15(),
            Config.config_district_fid);
    List<CongestionWarnRealVo> lastList = earlyWarningDao
        .getCongestionWarnRealV2(DateUtils.dayBefore(-7), DateUtils.currentPeriod15(),
            Config.config_district_fid);
    for (CongestionWarnRealVo vo : thisList) {
      int thisId = vo.getRoadsectId();
      double thisSpeed = vo.getSpeed();
      if (lastList != null && lastList.size() > 0) {
        for (CongestionWarnRealVo po : lastList) {
          int lastId = po.getRoadsectId();
          double lastSpeed = po.getSpeed();
          if (lastId == thisId) {
            vo.setRatio(TpiUtils.getByDigit((thisSpeed - lastSpeed) / lastSpeed, 3));
            break;
          }
        }
      }
    }
    return thisList;
  }

  public RoadsectChartInfoVo getRoadsectChartInfo(int roadsectId) {
    //当日曲线信息
    String thisTime = DateUtils.currentDate();
    int thisPeriod = DateUtils.currentPeriod15();
    List<SpeedAndTpiChartVo> thisSpeedAndTpi = earlyWarningDao
        .getSpeedAndTpiById(thisTime, thisPeriod, roadsectId);
    List<SpeedChartVo> thisSpeedChart = new ArrayList<>();
    List<TpiChartVo> thisTpiChart = new ArrayList<>();
    for (SpeedAndTpiChartVo vo : thisSpeedAndTpi) {
      SpeedChartVo speedChartVo = new SpeedChartVo();
      TpiChartVo tpiChartVo = new TpiChartVo();
      BeanUtils.copyProperties(vo, speedChartVo);
      BeanUtils.copyProperties(vo, tpiChartVo);
      thisSpeedChart.add(speedChartVo);
      thisTpiChart.add(tpiChartVo);
    }
    List<VolumeChartVo> thisVolumeChart = earlyWarningDao
        .getVolumeById(thisTime, thisPeriod, roadsectId);
    if (thisVolumeChart == null || thisVolumeChart.size() == 0) {
      thisVolumeChart = earlyWarningDao.getVolumeById("20200928", thisPeriod, roadsectId);
    }
    //上周同期曲线信息
    String lastTime = DateUtils.dayBefore(-7);
    int lastPeriod = thisPeriod;
    List<SpeedAndTpiChartVo> lastSpeedAndTpi = earlyWarningDao
        .getSpeedAndTpiById(lastTime, lastPeriod, roadsectId);
    List<SpeedChartVo> lastSpeedChart = new ArrayList<>();
    List<TpiChartVo> lastTpiChart = new ArrayList<>();
    for (SpeedAndTpiChartVo vo : lastSpeedAndTpi) {
      SpeedChartVo speedChartVo = new SpeedChartVo();
      TpiChartVo tpiChartVo = new TpiChartVo();
      BeanUtils.copyProperties(vo, speedChartVo);
      BeanUtils.copyProperties(vo, tpiChartVo);
      lastSpeedChart.add(speedChartVo);
      lastTpiChart.add(tpiChartVo);
    }
    List<VolumeChartVo> lastVolumeChart = earlyWarningDao
        .getVolumeById(lastTime, lastPeriod, roadsectId);
    if (thisVolumeChart == null || thisVolumeChart.size() == 0) {
      lastVolumeChart = earlyWarningDao.getVolumeById("20200930", thisPeriod, roadsectId);
    }

    RoadsectChartInfoVo roadsectChartInfoVo = new RoadsectChartInfoVo();
    roadsectChartInfoVo.setThisSpeedChart(thisSpeedChart);
    roadsectChartInfoVo.setThisTpiChart(thisTpiChart);
    roadsectChartInfoVo.setThisVolumeChart(thisVolumeChart);
    roadsectChartInfoVo.setLastSpeedChart(lastSpeedChart);
    roadsectChartInfoVo.setLastTpiChart(lastTpiChart);
    roadsectChartInfoVo.setLastVolumeChart(lastVolumeChart);
    return roadsectChartInfoVo;
  }

  public List<RoadsectMapVolumeVo> getMapRealVolume() {
    List<RoadsectMapVolumeVo> list = earlyWarningDao
        .getMapRealVolume(DateUtils.currentDate(), DateUtils.currentPeriod15());
    if (list == null || list.size() == 0) {
      list = earlyWarningDao.getMapRealVolume("20200928", 91);
    }
    return list;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getCongestionWarnDetail(Map<String, Object> map) {

    map.put("time", PeriodUtils.getCurrentDate());

    List<Map<String, Object>> list = earlyWarningDao.getCongestionWarnDetail(map);

    return list;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getGdHighSpeedLinkStatus(Map<String, Object> map) {
    List<Map<String, Object>> list = earlyWarningDao.getGdHighSpeedLinkStatus(map);
    return list;
  }

  /**
   *获取所有交叉口信息
   * @param map
   * @return
   */
  public Object getAllIntersectionInformation(Map<String, Object> map) {
    List<Map<String, Object>> resultList = earlyWarningDao.getAllIntersectionInformation(map);
    return resultList;
  }
}
