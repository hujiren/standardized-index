package com.sutpc.its.service;

import com.sutpc.its.cache.CacheClass;
import com.sutpc.its.config.DataConfig;
import com.sutpc.its.constant.DataMendingConstants;
import com.sutpc.its.dao.IPanoramicTrafficDao;
import com.sutpc.its.dto.DetectorChartDto;
import com.sutpc.its.dto.KeyAreaAlterDto;
import com.sutpc.its.dto.KeyAreaBaseDto;
import com.sutpc.its.mail.MailUtils;
import com.sutpc.its.model.IdEntity;
import com.sutpc.its.po.DetectorChartsEntity;
import com.sutpc.its.po.DetectorInfoEntity;
import com.sutpc.its.tools.PeriodUtils;
import com.sutpc.its.tools.TpiUtils;
import com.sutpc.its.tools.Utils;
import com.sutpc.its.tools.gps.GeoJsonUtils;
import com.sutpc.its.tools.gps.GpsTransformationUtils;
import com.sutpc.its.tools.gps.GpsUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PanoramicTrafficService {

  @Value("${tpi.busUrl}")
  private String busUrl;

  @Value("${tpi.busDistributionUrl}")
  private String busDistributionUrl;

  @Value("${tpi.taxiUrl}")
  private String taxiUrl;

  @Value("${tpi.taxiDistributionUrl}")
  private String taxiDistributionUrl;

  @Value("${tpi.netUrl}")
  private String netUrl;

  @Value("${tpi.netDistributionUrl}")
  private String netDistributionUrl;

  @Value("${tpi.mails}")
  private String mails;

  @Autowired
  private IPanoramicTrafficDao panoramicTrafficDao;

  @Autowired
  private DataMendingService dataMendingService;

  @Autowired
  private DataConfig config;


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
  public List<Map<String, Object>> getTpi(Map<String, Object> map) {
    return panoramicTrafficDao.getTpi(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRealTimeRoadsect(Map<String, Object> map) {
    List<Map<String, Object>> result = panoramicTrafficDao.getRealTimeRoadsect(map);
    if (result != null && result.size() > 0) {
      CacheClass.getRealTimeRoadsect = result;
    } else {
      MailUtils.sendMailByWarn(Utils.getMails(mails), "/panoramicTraffic/getRealTimeRoadsect");
      result = CacheClass.getRealTimeRoadsect;
    }
    if(null != result && result.size() > 0){
      for (Map<String, Object> stringObjectMap : result) {
        if(StringUtils.isEmpty(stringObjectMap.get("TPI")))
          continue;
        BigDecimal bigDecimal = TpiUtils.PreservedDecimal(stringObjectMap.get("TPI").toString(), 2);
        stringObjectMap.put("TPI", bigDecimal);
      }
    }
    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getBlockPeakStatus(Map<String, Object> map) {
    map.put("timeproperty", "all_peak");
    String yearMonth = map.get("year_month").toString();
    if (yearMonth.length() > 6) {
      Map<String, Object> mmm = new HashMap<>();
      mmm.put("code", 500);
      mmm.put("msg", "查询发生错误");
      return mmm;
    }
    String lastYearMonth = PeriodUtils.getAppointDateLastMonth(yearMonth);

    List<Map<String, Object>> thisList = panoramicTrafficDao.getBlockPeakStatus(map);
    if(null != thisList && thisList.size() > 0){
      for (Map<String, Object> stringObjectMap : thisList) {
        if(!StringUtils.isEmpty(stringObjectMap.get("TPI"))){
          BigDecimal tpiBigDecimal = TpiUtils.PreservedDecimal(stringObjectMap.get("TPI").toString(), 2);
          stringObjectMap.put("TPI", tpiBigDecimal);
        }
        if(!StringUtils.isEmpty(stringObjectMap.get("SPEED"))){
          BigDecimal speedBigDecimal = TpiUtils.PreservedDecimal(stringObjectMap.get("SPEED").toString(), 1);
          stringObjectMap.put("SPEED", speedBigDecimal);
        }
      }
    }

    map.put("year_month", lastYearMonth);
    List<Map<String, Object>> lastList = panoramicTrafficDao.getBlockPeakStatus(map);
    if(null != lastList && lastList.size() > 0){
      for (Map<String, Object> stringObjectMap : lastList) {
        if(!StringUtils.isEmpty(stringObjectMap.get("TPI"))){
          BigDecimal tpiBigDecimal = TpiUtils.PreservedDecimal(stringObjectMap.get("TPI").toString(), 2);
          stringObjectMap.put("TPI", tpiBigDecimal);
        }
        if(!StringUtils.isEmpty(stringObjectMap.get("SPEED"))){
          BigDecimal speedBigDecimal = TpiUtils.PreservedDecimal(stringObjectMap.get("SPEED").toString(), 1);
          stringObjectMap.put("SPEED", speedBigDecimal);
        }
      }
    }

    List<Map<String, Object>> dealList = new ArrayList<>();

    if (lastList != null && lastList.size() > 0) {
      for (int i = 0; i < thisList.size(); i++) {
        Map<String, Object> m = thisList.get(i);
        double thisTpi = Double.parseDouble(m.get("TPI").toString());
        int thisBlockFid = Integer.parseInt(m.get("BLOCK_FID").toString());
        if (thisTpi > 6) {
          for (Map<String, Object> lastM : lastList) {
            double lastTpi = Double.parseDouble(lastM.get("TPI").toString());
            int lastBlockFid = Integer.parseInt(lastM.get("BLOCK_FID").toString());
            if (thisBlockFid == lastBlockFid) {
              double ratio = (thisTpi - lastTpi) / lastTpi;
              if (ratio > 0.05) {
                Map<String, Object> dealMap = new HashMap<>();
                dealMap.put("RATIO", formatDouble(ratio));
                dealMap.put("FNAME", m.get("FNAME"));
                dealMap.put("BLOCK_FID", thisBlockFid);
                dealMap.put("STATUS", m.get("STATUS"));
                dealList.add(dealMap);
              }
            }
          }
        }
      }
    }
    for (int i = 0; i < dealList.size() - 1; i++) {
      for (int j = 1 + i; j < dealList.size() - 1; j++) {
        double ratio1 = Double.parseDouble(dealList.get(i).get("RATIO").toString());
        double ratio2 = Double.parseDouble(dealList.get(j).get("RATIO").toString());
        if (ratio2 > ratio1) {
          Map<String, Object> temp = dealList.get(i);
          dealList.set(i, dealList.get(j));
          dealList.set(j, temp);
        }
      }
    }
    Map<String, Object> result = new HashMap<>();
    result.put("data", thisList);
    result.put("worsen", dealList);
    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getCitywideOperationCase(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    map.put("timeproperty", "morning_peak");
    final Map<String, Object> thisMorningTpiMap = panoramicTrafficDao.getCitywideMonthAvgTpi(map);
    if(!StringUtils.isEmpty(thisMorningTpiMap.get("TPI"))){
      BigDecimal thisMorningTpi = TpiUtils.PreservedDecimal(thisMorningTpiMap.get("TPI").toString(), 2);
      thisMorningTpiMap.put("TPI", thisMorningTpi);
    }
    List<Map<String, Object>> thisMorningTpiList = panoramicTrafficDao
        .getCitywideTrafficTpi(map);
    if(null != thisMorningTpiList && thisMorningTpiList.size() > 0){
      for (Map<String, Object> thisMorningTpi2 : thisMorningTpiList) {
        if(!StringUtils.isEmpty(thisMorningTpi2.get("TPI"))){
          BigDecimal thisMorningTpi3 = TpiUtils.PreservedDecimal(thisMorningTpi2.get("TPI").toString(), 2);
          thisMorningTpi2.put("TPI", thisMorningTpi3);
        }
      }
    }
    map.put("timeproperty", "evening_peak");
    final Map<String, Object> thisEveningTpiMap = panoramicTrafficDao.getCitywideMonthAvgTpi(map);
    if(!StringUtils.isEmpty(thisEveningTpiMap.get("TPI"))){
      BigDecimal thisEveningTpi = TpiUtils.PreservedDecimal(thisEveningTpiMap.get("TPI").toString(), 2);
      thisEveningTpiMap.put("TPI", thisEveningTpi);
    }
    final List<Map<String, Object>> thisEveningTpiList = panoramicTrafficDao
        .getCitywideTrafficTpi(map);
    if(null != thisEveningTpiList && thisEveningTpiList.size() > 0){
      for (Map<String, Object> thiEveningTpi2Map : thisEveningTpiList) {
        if(!StringUtils.isEmpty(thiEveningTpi2Map.get("TPI"))){
          BigDecimal thiEveningTpi2 = TpiUtils.PreservedDecimal(thiEveningTpi2Map.get("TPI").toString(), 2);
          thiEveningTpi2Map.put("TPI", thiEveningTpi2);
        }
      }
    }

    /** 指数早晚高峰图表. **/
    result.put("morning_peak_tpi_data", thisMorningTpiList);
    result.put("evening_peak_tpi_data", thisEveningTpiList);

    /** 指数文字描述. **/
    String yearMonth = map.get("year_month").toString();
    String lastYearMonth = PeriodUtils.getAppointDateLastMonth(yearMonth);
    String lastYear = PeriodUtils.getAppointDateLastYear(yearMonth);
    map.put("year_month", lastYearMonth);
    map.put("timeproperty", "morning_peak");
    final Map<String, Object> lastMorningTpiMap = panoramicTrafficDao.getCitywideMonthAvgTpi(map);
    if(!StringUtils.isEmpty(lastMorningTpiMap.get("TPI"))){
      BigDecimal lastMorningTpi = TpiUtils.PreservedDecimal(lastMorningTpiMap.get("TPI").toString(), 2);
      lastMorningTpiMap.put("TPI", lastMorningTpi);
    }
    map.put("timeproperty", "evening_peak");
    Map<String, Object> lastEveningTpiMap = panoramicTrafficDao.getCitywideMonthAvgTpi(map);
    if(!StringUtils.isEmpty(lastEveningTpiMap.get("TPI"))){
      BigDecimal lastEveningTpi = TpiUtils.PreservedDecimal(lastEveningTpiMap.get("TPI").toString(), 2);
      lastEveningTpiMap.put("TPI", lastEveningTpi);
    }
    Map<String, Object> totalPeakMap = new HashMap<>();
    totalPeakMap.put("morning_peak_tpi", thisMorningTpiMap.get("TPI"));
    totalPeakMap.put("morning_peak_status", thisMorningTpiMap.get("STATUS"));
    totalPeakMap.put("evening_peak_tpi", thisEveningTpiMap.get("TPI"));
    totalPeakMap.put("evening_peak_status", thisEveningTpiMap.get("STATUS"));

    double thisMorningTpiNum = Double.parseDouble(thisMorningTpiMap.get("TPI").toString());
    double thisEveningTpiNum = Double.parseDouble(thisEveningTpiMap.get("TPI").toString());
    double lastMorningTpiNum = Double.parseDouble(lastMorningTpiMap.get("TPI").toString());
    double lastEveningTpiNum = Double.parseDouble(lastEveningTpiMap.get("TPI").toString());

    double morningTpiRatio = (thisMorningTpiNum - lastMorningTpiNum) / lastMorningTpiNum;
    double eveningTpiRatio = (thisEveningTpiNum - lastEveningTpiNum) / lastEveningTpiNum;

    if (morningTpiRatio > 0) {
      totalPeakMap
          .put("chain_morning_ratio", "上升" + formatDouble(morningTpiRatio * 100.00) + "%");
    } else if (morningTpiRatio < 0) {
      totalPeakMap
          .put("chain_morning_ratio", "下降" + formatDouble(morningTpiRatio * 100.00 * -1) + "%");
    }
    if (eveningTpiRatio > 0) {
      totalPeakMap
          .put("chain_evening_ratio", "上升" + formatDouble(eveningTpiRatio * 100.00) + "%");
    } else if (eveningTpiRatio < 0) {
      totalPeakMap
          .put("chain_evening_ratio", "下降" + formatDouble(eveningTpiRatio * 100.00 * -1) + "%");
    }
    double morningMaxTpi = 0.0;
    Object morningMaxTpiStatus = null;
    int morningMaxTpiMonth = 0;
    int morningMaxTpiDay = 0;
    for (int i = 0; i < thisMorningTpiList.size(); i++) {
      double tpi = Double.parseDouble(thisMorningTpiList.get(i).get("TPI").toString());
      if (tpi > morningMaxTpi) {
        morningMaxTpi = tpi;
        morningMaxTpiStatus = thisMorningTpiList.get(i).get("STATUS");
        String time = thisMorningTpiList.get(i).get("TIME").toString();
        morningMaxTpiMonth = Integer.parseInt(time.substring(4, 6));
        morningMaxTpiDay = Integer.parseInt(time.substring(6, 8));
      }

    }
    totalPeakMap.put("morning_max_tpi", morningMaxTpi);
    totalPeakMap.put("morning_max_tpi_status", morningMaxTpiStatus);
    totalPeakMap.put("morning_max_tpi_month", morningMaxTpiMonth);
    totalPeakMap.put("morning_max_tpi_day", morningMaxTpiDay);

    double eveningMaxTpi = 0.0;
    Object eveningMaxTpiStatus = null;
    int eveningMaxTpiMonth = 0;
    int eveningMaxTpiDay = 0;
    for (int i = 0; i < thisEveningTpiList.size(); i++) {
      double tpi = Double.parseDouble(thisEveningTpiList.get(i).get("TPI").toString());
      if (tpi > eveningMaxTpi) {
        eveningMaxTpi = tpi;
        eveningMaxTpiStatus = thisEveningTpiList.get(i).get("STATUS");
        String time = thisEveningTpiList.get(i).get("TIME").toString();
        eveningMaxTpiMonth = Integer.parseInt(time.substring(4, 6));
        eveningMaxTpiDay = Integer.parseInt(time.substring(6, 8));
      }
    }
    totalPeakMap.put("evening_max_tpi", eveningMaxTpi);
    totalPeakMap.put("evening_max_tpi_status", eveningMaxTpiStatus);
    totalPeakMap.put("evening_max_tpi_month", eveningMaxTpiMonth);
    totalPeakMap.put("evening_max_tpi_day", eveningMaxTpiDay);
    result.put("tpi_total_peak", totalPeakMap);

    /** 全市整体速度. **/
    map.put("year_month", yearMonth);
    map.put("timeproperty", "morning_peak");
    List<Map<String, Object>> thisMorningSpeedList = panoramicTrafficDao
        .getCitywideTrafficSpeed(map);
    if(null != thisMorningSpeedList && thisMorningSpeedList.size()>0){
      for (Map<String, Object> thisMorningSpeed : thisMorningSpeedList) {
        if(!StringUtils.isEmpty(thisMorningSpeed.get("SPEED"))){
          BigDecimal thisMorningSpeedVal = TpiUtils.PreservedDecimal(thisMorningSpeed.get("SPEED").toString(), 1);
          thisMorningSpeed.put("SPEED", thisMorningSpeedVal);
        }
      }
    }

    map.put("timeproperty", "evening_peak");
    List<Map<String, Object>> thisEveningSpeedList = panoramicTrafficDao
        .getCitywideTrafficSpeed(map);
    if(null != thisEveningSpeedList && thisEveningSpeedList.size() > 0){
      for (Map<String, Object> thisEveningSpeed : thisEveningSpeedList) {
        if(!StringUtils.isEmpty(thisEveningSpeed.get("SPEED"))){
          BigDecimal thisEveningSpeedVal = TpiUtils.PreservedDecimal(thisEveningSpeed.get("SPEED").toString(), 1);
          thisEveningSpeed.put("SPEED", thisEveningSpeedVal);
        }
      }
    }

    result.put("morning_peak_speed_data", thisMorningSpeedList);
    result.put("evening_peak_speed_data", thisEveningSpeedList);

    map.put("year_month", lastYearMonth);
    map.put("timeproperty", "morning_peak");
    final List<Map<String, Object>> lastMorningSpeedList = panoramicTrafficDao
        .getCitywideTrafficSpeed(map);
    if(null != lastMorningSpeedList && lastMorningSpeedList.size() > 0){
      for (Map<String, Object> lastMorningSpeed : lastMorningSpeedList) {
        if(!StringUtils.isEmpty(lastMorningSpeed.get("SPEED"))){
          BigDecimal lastMorningSpeedVal = TpiUtils.PreservedDecimal(lastMorningSpeed.get("SPEED").toString(), 1);
          lastMorningSpeed.put("SPEED", lastMorningSpeedVal);
        }
      }
    }


    map.put("timeproperty", "evening_peak");
    final List<Map<String, Object>> lastEveningSpeedList = panoramicTrafficDao
        .getCitywideTrafficSpeed(map);
    if(null != lastEveningSpeedList && lastEveningSpeedList.size() > 0){
      for (Map<String, Object> lastEveningSpeed : lastEveningSpeedList) {
        if(!StringUtils.isEmpty(lastEveningSpeed.get("SPEED"))){
          BigDecimal lastEveningSpeedVal = TpiUtils.PreservedDecimal(lastEveningSpeed.get("SPEED").toString(), 1);
          lastEveningSpeed.put("SPEED", lastEveningSpeedVal);
        }
      }
    }

    double thisMorningMinSpeed = 200.0;
    double thisEveningMinSpeed = 200.0;
    double lastMorningSpeed = 0.0;
    double lastEveningSpeed = 0.0;
    String thisMorningSpeedDistrict = null;
    String thisEveningSpeedDistrict = null;
    String lastMorningSpeedDistrict = null;
    String lastEveningSpeedDistrict = null;
    for (int i = 0; i < thisMorningSpeedList.size(); i++) {
      double speed = Double.parseDouble(thisMorningSpeedList.get(i).get("SPEED").toString());
      if (speed < thisMorningMinSpeed) {
        thisMorningMinSpeed = speed;
        thisMorningSpeedDistrict = thisMorningSpeedList.get(i).get("FNAME") + "";
      }
    }
    for (int i = 0; i < thisEveningSpeedList.size() - 1; i++) {
      double speed = Double.parseDouble(thisEveningSpeedList.get(i).get("SPEED").toString());
      if (speed < thisEveningMinSpeed) {
        thisEveningMinSpeed = speed;
        thisEveningSpeedDistrict = thisEveningSpeedList.get(i).get("FNAME") + "";
      }
    }
    for (int i = 0; i < lastMorningSpeedList.size() - 1; i++) {
      String district = thisMorningSpeedList.get(i).get("FNAME") + "";
      if (thisMorningSpeedDistrict.equals(district)) {
        lastMorningSpeed = Double
            .parseDouble(lastMorningSpeedList.get(i).get("SPEED").toString());
      }
    }
    for (int i = 0; i < lastEveningSpeedList.size() - 1; i++) {
      String district = thisMorningSpeedList.get(i).get("FNAME") + "";
      if (thisEveningSpeedDistrict.equals(district)) {
        lastEveningSpeed = Double
            .parseDouble(lastEveningSpeedList.get(i).get("SPEED").toString());
      }
    }
    Map<String, Object> speedTotalPeak = new HashMap<String, Object>();
    BigDecimal thisMorningMinSpeedDecimal = TpiUtils.PreservedDecimal(String.valueOf(thisMorningMinSpeed), 1);
    speedTotalPeak.put("morning_min_speed", thisMorningMinSpeedDecimal);
    speedTotalPeak.put("morning_min_speed_district", thisMorningSpeedDistrict);
    BigDecimal thisEveningMinSpeedDecimal = TpiUtils.PreservedDecimal(String.valueOf(thisEveningMinSpeed), 1);
    speedTotalPeak.put("evening_min_speed", thisEveningMinSpeedDecimal);
    speedTotalPeak.put("evening_min_speed_district", thisEveningSpeedDistrict);

    double morningSpeedRatio = (thisMorningMinSpeed - lastMorningSpeed) / lastMorningSpeed;
    double eveningSpeedRatio = (thisEveningMinSpeed - lastEveningSpeed) / lastEveningSpeed;
    if (morningSpeedRatio > 0) {
      speedTotalPeak
          .put("chain_morning_ratio", "上升" + formatDouble(morningSpeedRatio * 100.00) + "%");
    } else if (morningSpeedRatio < 0) {
      speedTotalPeak
          .put("chain_morning_ratio", "下降" + formatDouble(morningSpeedRatio * 100.00 * -1) + "%");
    }
    if (eveningSpeedRatio > 0) {
      speedTotalPeak
          .put("chain_evening_ratio", "上升" + formatDouble(eveningSpeedRatio * 100.00) + "%");
    } else if (eveningSpeedRatio < 0) {
      speedTotalPeak
          .put("chain_evening_ratio", "下降" + formatDouble(eveningSpeedRatio * 100.00 * -1) + "%");
    }
    result.put("speed_total_peak", speedTotalPeak);

    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getMonthRoadsectWorsen(Map<String, Object> map) {
    final List<Map<String, Object>> roadsectList = panoramicTrafficDao.getMonthRoadsectWorsen(map);
    List<Map<String, Object>> districtNumList = panoramicTrafficDao
        .getMonthRoadsectInDistrictNum(map);
    int num = 0;
    String district = "";
    for (int i = 0; i < districtNumList.size() - 1; i++) {
      for (int j = i + 1; j < districtNumList.size() - 1 - i; j++) {
        int num1 = Integer.parseInt(districtNumList.get(i).get("NUM") + "");
        int num2 = Integer.parseInt(districtNumList.get(j).get("NUM") + "");
        if (num2 > num1) {
          num = num2;
          district = districtNumList.get(j).get("FNAME") + "";
        }
      }
    }
    Map<String, Object> m = new HashMap<>();
    m.put("max_num", num);
    m.put("max_num_district", district);
    m.put("total_num", roadsectList.size());
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("data", roadsectList);
    result.put("info", m);
    return result;
  }

  /**
   * .
   */
  public int setTpiTrafficEvent(Map<String, Object> map) {
    map.put("timestamp", PeriodUtils.getCurrentTimeStamp());
    int num = panoramicTrafficDao.setTpiTrafficEvent(map);
    return num;
  }

  /**
   * .
   */
  public int updateTpiTrafficEvent(Map<String, Object> map) {
    map.put("timestamp", PeriodUtils.getCurrentTimeStamp());
    int num = panoramicTrafficDao.updateTpiTrafficEvent(map);
    return num;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getTpiTrafficEvent(Map<String, Object> map) {
    return panoramicTrafficDao.getTpiTrafficEvent(map);
  }

  /**
   * .
   */
  public int deleteTpiTrafficEvent(Map<String, Object> map) {
    int num = panoramicTrafficDao.deleteTpiTrafficEvent(map);
    return num;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getYearTpiAndTrafficEventData(Map<String, Object> map) {
    return panoramicTrafficDao.getYearTpiAndTrafficEventData(map);
  }

  /**
   * .
   */
  public Map<String, Object> getTrafficTpiData(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<String, Object>();
    List<Map<String, Object>> nowList = panoramicTrafficDao.getTrafficTpiData(map);

    //如果data的size小于当前的时间片，则说明数据缺失
    if (nowList.size() < (Integer) map.get("period15")) {
      dataMendingService.dataMendingByPeriod15(nowList, map,
          DataMendingConstants.getTrafficTpiData);
    }

    result.put("todayData", nowList);
    String currentDate = PeriodUtils.getCurrentDate();
    String yesterday = PeriodUtils.getAppointDateAnyDayBeforeDate(1, currentDate);
    map.put("time", yesterday);
    List<Map<String, Object>> yesterdayList = panoramicTrafficDao.getTrafficTpiData(map);
    result.put("yesterdayData", yesterdayList);
    String lastWeekSamePeriod = PeriodUtils.getAppointDateAnyDayBeforeDate(7, currentDate);
    map.put("time", lastWeekSamePeriod);
    List<Map<String, Object>> lastWeekData = panoramicTrafficDao.getTrafficTpiData(map);
    result.put("lastWeekData", lastWeekData);
    return result;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getTrafficRealTimeJamRanking(Map<String, Object> map) {
    return panoramicTrafficDao.getTrafficRealTimeJamRanking(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getMainRoadRealTimeJamRanking(Map<String, Object> map) {
    return panoramicTrafficDao.getMainRoadRealTimeJamRanking(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getMainBuslaneLineDataByBuslaneId(Map<String, Object> map) {

    List<Map<String, Object>> data = panoramicTrafficDao.getMainBuslaneLineDataByBuslaneId(map);
    /*if (data.size() / 2 < (Integer) map.get("period15")) {
      dataMendingService.dataMendingByPeriod15(data, map,
          DataMendingConstants.getMainBuslaneLineDataByBuslaneId);
    }*/
    return data;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getMainPoiRealTimeJamRanking(Map<String, Object> map) {
    return panoramicTrafficDao.getMainPoiRealTimeJamRanking(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getPoiRealTimeTrafficStatus(Map<String, Object> map) {
    return panoramicTrafficDao.getPoiRealTimeTrafficStatus(map);
  }

  /**
   * 获取指定热点区域基础信息.
   */
  public KeyAreaBaseDto getKeyAreaInfo(double lng, double lat, int id) {
    //获取热点区域速度和指数
    KeyAreaBaseDto dto = panoramicTrafficDao
        .getKeyAreaSpeedAndTpi(Integer.parseInt(PeriodUtils.getCurrentDate()),
            PeriodUtils.getCurrentPeriod(), id);
    if (dto == null) {
      dto = new KeyAreaBaseDto();
    }
    double[] point = GpsTransformationUtils.gcj02_To_Gps84(lat, lng);
    double longitude = point[1];
    double latitude = point[0];
    String str = config.getStringById(id);
    List<Map> points = GeoJsonUtils.setDistrictListByTwoMulti(str);
    //获取交通运力数据（公交车、出租车、网约车）
    List<Map<String, Double>> netList = GpsUtils
        .getAreaList(netDistributionUrl, longitude, latitude, config, id, "tpNet", points);
    List<Map<String, Double>> busList = GpsUtils
        .getAreaList(busDistributionUrl, longitude, latitude, config, id, "tpBus", points);
    List<Map<String, Double>> taxiList = GpsUtils
        .getAreaList(taxiDistributionUrl, longitude, latitude, config, id, "tpTaxi", points);
    dto.setBusList(busList);
    dto.setNetList(netList);
    dto.setTaxiList(taxiList);
    List<DetectorInfoEntity> detectorInfoEntityList = panoramicTrafficDao.getDetectorPointsInfo();
    List<DetectorInfoEntity> detector = new ArrayList<>();
    for (DetectorInfoEntity entity : detectorInfoEntityList) {
      double thisLng = entity.getLng();
      double thisLat = entity.getLat();
      double[] p = GpsTransformationUtils.gps84_To_Gcj02(thisLat, thisLng);
      if (GpsUtils.isExistsArea(p[1], p[0], points)) {
        entity.setLng(p[1]);
        entity.setLat(p[0]);
        detector.add(entity);
      }
    }
    dto.setDetectorList(detector);
    return dto;
  }

  /**
   * 获取重点区域预警信息.
   */
  public KeyAreaAlterDto getKeyAreaAlertInfo(IdEntity entity) {
    KeyAreaAlterDto dto = panoramicTrafficDao
        .getKeyAreaAlertInfo(PeriodUtils.getCurrentPeriod(), entity.getId());
    return dto;
  }

  /**
   * .
   */
  public DetectorChartDto getDetectorInfoById(int id) {
    List<DetectorChartsEntity> entity = panoramicTrafficDao
        .getDetectorCharts(PeriodUtils.getCurrentDate(), PeriodUtils.getCurrentPeriod() / 12, id);
    int capacity = panoramicTrafficDao.getCapacityById(id);
    DetectorChartDto dto = new DetectorChartDto();
    dto.setCapacity(capacity);
    dto.setList(entity);
    return dto;
  }
}
