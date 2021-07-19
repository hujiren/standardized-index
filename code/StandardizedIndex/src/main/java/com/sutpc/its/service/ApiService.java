package com.sutpc.its.service;

import com.sutpc.its.common.AbstractBaseRequest;
import com.sutpc.its.dao.IApiDao;
import com.sutpc.its.enums.TpEnum;
import com.sutpc.its.po.BaseParams;
import com.sutpc.its.po.DetectorBaseInfoPo;
import com.sutpc.its.po.DetectorSpeedAndPcuPo;
import com.sutpc.its.po.DistrictDayTpiPo;
import com.sutpc.its.po.DistrictJamLengthPo;
import com.sutpc.its.po.PoiInfoNowPo;
import com.sutpc.its.po.RoadJamLengthPo;
import com.sutpc.its.po.RoadSectionInfoEntity;
import com.sutpc.its.po.RoadSectionSpeedByDayPo;
import com.sutpc.its.tools.PeriodUtils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:59 2019/12/31.
 * @Description
 * @Modified By:
 */
@Service
public class ApiService extends AbstractBaseRequest {

  @Value("${transpaas.prefixUrl}")
  private String baseUrl;

  private RestTemplate restTemplate = new RestTemplate();

  @Autowired
  private IApiDao apiDao;

  /**
   * .
   */
  public List<Map<String, Object>> getRoadOperationMonitoringNow(Map<String, Object> map) {
    return apiDao.getRoadOperationMonitoringNow(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getBlockMonitoringNow(Map<String, Object> map) {
    return apiDao.getBlockMonitoringNow(map);
  }

  /**
   * 关于全市工作日早晚高峰当月、当月同比、自年初累计、自年初累计同比、去年年均等平均车速统计结果.
   */
  public Map<String, Object> getCitywideSpeedData(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    String currentTime = map.get("date") + "31";
    map.put("timeflag", "current_month");
    map.put("time", currentTime);
    //获取当月平均速度
    Map<String, Object> currentMonthMap = apiDao.getCitywideSpeedData(map);
    double currentMonthSpeed = Double.parseDouble(currentMonthMap.get("speed").toString());
    result.put("current_month_speed", currentMonthSpeed);

    String lastTime =
        (Integer.parseInt(currentTime.substring(0, 4)) - 1) + currentTime.substring(4, 8);
    map.put("time", lastTime);
    map.put("timeflag", "current_month_tb");
    //获取去年同比
    Map<String, Object> currentMonthTbMap = apiDao.getCitywideSpeedData(map);
    double currentMonthTbSpeed = Double.parseDouble(currentMonthTbMap.get("speed").toString());
    result.put("current_month_tb",
        formatDouble((currentMonthSpeed - currentMonthTbSpeed) / currentMonthTbSpeed));

    String startDate = currentTime.substring(0, 4) + "0101";
    String endDate = currentTime;
    map.put("timeflag", "current_year_total");
    map.put("startdate", startDate);
    map.put("enddate", currentTime);
    //自年初累计
    Map<String, Object> currentYearTotalMap = apiDao.getCitywideSpeedData(map);
    double thisYearAvgSpeed = Double.parseDouble(currentYearTotalMap.get("speed").toString());
    result.put("this_year_avg_speed", thisYearAvgSpeed);

    String lastStartDate =
        (Integer.parseInt(startDate.substring(0, 4)) - 1) + "0101";//获取去年1月1日的时间time
    String lastEndDate =
        (Integer.parseInt(currentTime.substring(0, 4)) - 1) + currentTime.substring(4, 8);
    map.put("startdate", lastStartDate);
    map.put("enddate", lastEndDate);
    //自年初累计同比
    Map<String, Object> lastYearTotalTbMap = apiDao.getCitywideSpeedData(map);
    double lastYearAvgSpeed = Double.parseDouble(lastYearTotalTbMap.get("speed").toString());
    result.put("year_tb_avg_speed",
        formatDouble((thisYearAvgSpeed - lastYearAvgSpeed) / lastYearAvgSpeed));

    String lastYear = (Integer.parseInt(currentTime.substring(0, 4)) - 1) + "";
    map.put("timeflag", "last_year");
    map.put("year", lastYear);
    //去年整年平均
    Map<String, Object> lastYearMap = apiDao.getCitywideSpeedData(map);
    result.put("last_year_avg_speed", lastYearMap.get("speed"));

    return result;
  }

  /**
   * 保留两位小数，四舍五入的一个老土的方法.
   */
  private static String formatDouble(double d) {
    DecimalFormat df = new DecimalFormat("#0.000");
    return df.format(d);
  }

  /**
   * 保留两位小数，四舍五入的一个老土的方法.
   */
  private static String formatDouble(double d, String pattern) {
    DecimalFormat df = new DecimalFormat(pattern);
    return df.format(d);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadsectPredictStatusByHour(Map<String, Object> map) {
    return apiDao.getRoadsectPredictStatusByHour(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getLinkDataForTGis(Map<String, Object> map) {
    return apiDao.getLinkDataForTGis(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRealTimeSectStatus(Map<String, Object> map) {
    return apiDao.getRealTimeSectStatus(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRealTimeRoadConIndex(Map<String, Object> map) {
    return apiDao.getRealTimeRoadConIndex(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRealTimeTotalConIndex(Map<String, Object> map) {
    return apiDao.getRealTimeTotalConIndex(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDistrictStatusNow(Map<String, Object> map) {
    return apiDao.getDistrictStatusNow(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDistrictExptHis(Map<String, Object> map) {
    String time = map.get("time").toString();
    if (!time.equals(PeriodUtils.getCurrentDate())) {
      map.put("period", 288);
    }
    return apiDao.getDistrictExptHis(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDistrictSpeedHis(Map<String, Object> map) {
    String time = map.get("time").toString();
    if (!time.equals(PeriodUtils.getCurrentDate())) {
      map.put("period", 288);
    }
    return apiDao.getDistrictSpeedHis(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getSubsectStatusNow(Map<String, Object> map) {
    return apiDao.getSubsectStatusNow(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getSubsectStatusHis(Map<String, Object> map) {
    String time = map.get("time").toString();
    if (!time.equals(PeriodUtils.getCurrentDate())) {
      map.put("period", 288);
    }
    return apiDao.getSubsectStatusHis(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadStatusNow(Map<String, Object> map) {
    return apiDao.getRoadStatusNow(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadStatusHis(Map<String, Object> map) {
    String time = map.get("time").toString();
    if (!time.equals(PeriodUtils.getCurrentDate())) {
      map.put("period", 288);
    }
    return apiDao.getRoadStatusHis(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadsectPredictStatus(Map<String, Object> map) {
    return apiDao.getRoadsectPredictStatus(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDistrictBusStatusNow(Map<String, Object> map) {
    return apiDao.getDistrictBusStatusNow(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDistrictBusCarStatusHis(Map<String, Object> map) {
    String time = map.get("time").toString();
    if (!time.equals(PeriodUtils.getCurrentDate())) {
      map.put("period", 288);
    }
    return apiDao.getDistrictBusCarStatusHis(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getSubsectBusStatusNow(Map<String, Object> map) {
    return apiDao.getSubsectBusStatusNow(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getSubsectBusCarStatusHis(Map<String, Object> map) {
    String time = map.get("time").toString();
    if (!time.equals(PeriodUtils.getCurrentDate())) {
      map.put("period", 288);
    }
    return apiDao.getSubsectBusCarStatusHis(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getBusLaneBusCarStatusHis(Map<String, Object> map) {
    String time = map.get("time").toString();
    if (!time.equals(PeriodUtils.getCurrentDate())) {
      map.put("period", 288);
    }
    return apiDao.getBusLaneBusCarStatusHis(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRealTimeRoadSectMidStatus(Map<String, Object> map) {
    return apiDao.getRealTimeRoadSectMidStatus(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDistrictTpiAndSpeedGroupByMonth(Map<String, Object> map) {
    return apiDao.getDistrictTpiAndSpeedGroupByMonth(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDistrictTpiAndSpeedGroupByYear(Map<String, Object> map) {
    return apiDao.getDistrictTpiAndSpeedGroupByYear(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getBlokcTpiAndSpeedGroupByMonth(Map<String, Object> map) {
    return apiDao.getBlokcTpiAndSpeedGroupByMonth(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getBlokcTpiAndSpeedGroupByYear(Map<String, Object> map) {
    return apiDao.getBlokcTpiAndSpeedGroupByYear(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadTpiAndSpeedGroupByMonth(Map<String, Object> map) {
    return apiDao.getRoadTpiAndSpeedGroupByMonth(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadTpiAndSpeedGroupByYear(Map<String, Object> map) {
    return apiDao.getRoadTpiAndSpeedGroupByYear(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getIntersectionDelayDataByMonth(Map<String, Object> map) {
    return apiDao.getIntersectionDelayDataByMonth(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getIntersectionInfo(Map<String, Object> map) {
    return apiDao.getIntersectionInfo(map);

  }

  /**
   * .
   */
  public List<Map<String, Object>> getRealTimeBusLaneSpeed(Map<String, Object> map) {
    return apiDao.getRealTimeBusLaneSpeed(map);
  }

  /**
   * 获取行政区当日实时指数曲线数据.
   */
  public List<DistrictDayTpiPo> getDistrictDayTpi(Map<String, Object> map) {
    List<DistrictDayTpiPo> data = apiDao.getDistrictDayTpi(map);
    return getSmoothData(data);
  }

  /**
   * 以下代码是为了做数据平滑，以达到指定的前端效果.
   */
  public List<DistrictDayTpiPo> getSmoothData(List<DistrictDayTpiPo> resultList) {
    String name = "";
    for (int i = 0; i < resultList.size(); i++) {
      DistrictDayTpiPo po = resultList.get(i);
      String thisName = po.getName();
      if (name.equals(thisName)) {
        DistrictDayTpiPo oomap = resultList.get(i - 1);
        if (po.containsKey("tpi")) {
          po.setTpi(
              Double.parseDouble(formatDouble(po.getTpi() * 0.3 + oomap.getTpi() * 0.7, "#0.00")));
        }
      } else {
        name = thisName;
      }
    }
    return resultList;
  }

  /**
   * 获取行政区拥堵长度.
   */
  public List<DistrictJamLengthPo> getDistrictJamLength(Map<String, Object> map) {
    return apiDao.getDistrictJamLength(map);
  }

  /**
   * 获取中路段id等信息.
   */
  public List<RoadSectionInfoEntity> getRoadSectionInfo() {
    return apiDao.getRoadSectionInfo();
  }

  /**
   * 获取中路段七天信息.
   */
  public List<RoadSectionSpeedByDayPo> getRoadSectionInfoBySevenDay(int yearMonth, int id) {
    if (yearMonth >= 202006) {
      String startDate = String.valueOf(yearMonth) + "01";
      String endDate = String.valueOf(yearMonth) + "07";
      List<RoadSectionSpeedByDayPo> list = apiDao
          .getRoadSectionInfoBySevenDay(startDate, endDate, id);
      return list;

    }
    return new ArrayList<>();
  }

  /**
   * 获取实时全市/行政区的热点运行状态.
   */
  public List<PoiInfoNowPo> getPoiInfoNow(Map<String, Object> map) {
    return apiDao.getPoiInfoNow(map);
  }

  /**
   * 获取道路拥堵长度.
   */
  public List<RoadJamLengthPo> getRoadJamLength(Map<String, Object> map) {
    map.put("period30", PeriodUtils.getCurrentPeriod15() / 2);
    return apiDao.getRoadJamLength(map);
  }

  /**
   * 获取检测器id信息.
   */
  public List<DetectorBaseInfoPo> getAllDetectorInfo() {
    TpEnum tpEnum = TpEnum.getAllDetectorInfo;
    Map<String, Object> map = new HashMap<>();
    List<DetectorBaseInfoPo> list = queryListEntity(new BaseParams(), tpEnum,
        DetectorBaseInfoPo.class);
    return list;
  }

  /**
   * 获取检测器速度流量信息.
   */
  public List<DetectorSpeedAndPcuPo> getDetectorSpeedAndPcuPo(int yearMonth, String id) {
    if (yearMonth >= 202006) {
      String startDate = String.valueOf(yearMonth) + "01";
      String endDate = String.valueOf(yearMonth) + "07";
      BaseParams baseParams = new BaseParams();
      baseParams.setStartDate(startDate);
      baseParams.setEndDate(endDate);
      baseParams.setId(id);
      return queryListEntity(baseParams, TpEnum.getDetectorSpeedAndPcuById,
          DetectorSpeedAndPcuPo.class);
    }
    return new ArrayList<>();
  }
}