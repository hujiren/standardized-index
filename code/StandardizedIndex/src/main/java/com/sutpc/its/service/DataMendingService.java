package com.sutpc.its.service;

import com.sutpc.its.constant.DataMendingConstants;
import com.sutpc.its.dao.ICommonQueryDao;
import com.sutpc.its.dao.IFtCenterDao;
import com.sutpc.its.dao.IMonitorEarlyWarningDao;
import com.sutpc.its.dao.IPanoramicTrafficDao;
import com.sutpc.its.tools.DataMissUtils;
import com.sutpc.its.tools.PeriodUtils;
import com.sutpc.its.tools.Utils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * .
 *
 * @Author:chensy
 * @Date:14:13 2019/11/29
 * @Description 数据修补service
 * @Modified By:ztw
 */
@Service
public class DataMendingService {

  @Autowired
  private ICommonQueryDao commonQueryDao;

  @Autowired
  private IMonitorEarlyWarningDao earlyWarningDao;

  @Autowired
  private IPanoramicTrafficDao panoramicTrafficDao;

  @Autowired
  private IFtCenterDao ftCenterDao;

  /**
   * 中路段曲线信息补齐.
   */
  public void doCompletionDataForRoadsect(
                                          List<Map<String,Object>> data,
                                          Map<String,Object> map,
                                          IMonitorEarlyWarningDao earlyWarningDao) {
    map.put("time", Utils.getLastDay(map.get("time") + "", 1));
    List<Map<String, Object>> list = earlyWarningDao.getSpeedAndTpiInfoByRoadsectfid(map);//0.06秒
    if (!CollectionUtils.isEmpty(list)) {
      list.removeIf(map1 -> {
        int period = Integer.parseInt(String.valueOf(map1.get("PERIOD")));
        if (period > PeriodUtils.getCurrentPeriod15()) {
          return true;
        }
        return false;
      });
    } else {
      doCompletionDataForRoadsect(data, map, earlyWarningDao);
      return;
    }
    for (Map<String, Object> m1 : data) {
      Object period1 = m1.get("PERIOD");
      Iterator<Map<String, Object>> iterator = list.iterator();
      while (iterator.hasNext()) {
        Map<String, Object> m2 = iterator.next();
        Object period2 = m2.get("PERIOD");
        if (period1.equals(period2)) {
          iterator.remove();
        }
      }
    }
    data.addAll(list);
    if (data.size() < PeriodUtils.getCurrentPeriod15()) {
      doCompletionDataForRoadsect(data, map, earlyWarningDao);
    }
  }

  /**
   * .
   */
  public void missDataMendingByPeriod15(List<Map<String, Object>> data, Map<String, Object> map,
      String mendingMethod, int comNum) {

    //获取缺失的时间片
    List<Integer> period15MissArray = null;
    if ((map.get("time") + "").equals(PeriodUtils.getCurrentDate()) || comNum == 1) {
      period15MissArray = DataMissUtils.getPeriod15Miss(data, (Integer) map.get("period15"));
    } else {
      period15MissArray = DataMissUtils.getPeriod15Miss(data, 96);
    }
    //获取前一天的日期及其is_workday
    String yesterday = PeriodUtils.getAppointDateAnyDayBeforeDate(1, map.get("time") + "");
    Map<String, Object> dateMap = new HashMap<String, Object>();
    dateMap.put("time", yesterday);
    Map<String, Object> yesterdayMap = commonQueryDao.getBaseDateListInfo(dateMap);
    //获取当天is_workday
    Map<String, Object> todayMap = commonQueryDao.getBaseDateListInfo(map);
    map.put("time", yesterday);

    if (((BigDecimal) yesterdayMap.get("isWorkday")).intValue() == ((BigDecimal) todayMap
        .get("isWorkday")).intValue()) {
      //获取指定日期昨天的数据
      List<Map<String, Object>> yesterdayData = getReplenishData(mendingMethod, map);
      //判断昨天的数据是否含有当天缺失的时间片数据
      for (int period15 : period15MissArray) {
        if (yesterdayData != null && yesterdayData.size() > 0) {    //如果有，则补齐
          //yesterdayData循环标记
          for (int i = 0; i < yesterdayData.size(); i++) {
            int periodYesterday = 0;
            if (yesterdayData.get(i).containsKey("period")) {
              periodYesterday = ((BigDecimal) yesterdayData.get(i).get("period")).intValue();
            } else if (yesterdayData.get(i).containsKey("PERIOD")) {
              periodYesterday = ((BigDecimal) yesterdayData.get(i).get("PERIOD")).intValue();
            }
            if (period15 == periodYesterday) {
              data.add(yesterdayData.get(i));
              break;
            }
          }
        }
      }
      if (data.size() < Integer.parseInt(map.get("period15").toString())) {
        missDataMendingByPeriod15(data, map, mendingMethod, comNum);
      }
    } else {
      missDataMendingByPeriod15(data, map, mendingMethod, comNum);
    }

  }

  /**
   * 修补15分钟缺失的时间片数据.
   *
   * @Author chensy
   * @Description 修补15分钟缺失的时间片数据
   * @Modified by :
   * @params
   */
  public void dataMendingByPeriod15(List<Map<String, Object>> data, Map<String, Object> map,
      String mendingMethod) {
    //获取缺失的时间片
    List<Integer> period15MissArray = null;
    if ((map.get("time") + "").equals(PeriodUtils.getCurrentDate())) {
      period15MissArray = DataMissUtils.period15Miss(data, (Integer) map.get("period15"));
    } else {
      period15MissArray = DataMissUtils.period15Miss(data, 96);
    }
    //获取前一天的日期及其is_workday
    String yesterday = PeriodUtils.getAppointDateAnyDayBeforeDate(1, map.get("time") + "");
    Map<String, Object> dateMap = new HashMap<String, Object>();
    dateMap.put("time", yesterday);
    Map<String, Object> yesterdayMap = commonQueryDao.getBaseDateListInfo(dateMap);
    //获取当天is_workday
    Map<String, Object> todayMap = commonQueryDao.getBaseDateListInfo(map);

    //如果昨天与当天的is_workday一致，则取昨天的数据
    if (null != yesterdayMap
        && ((BigDecimal) yesterdayMap.get("isWorkday")).intValue() == ((BigDecimal) todayMap
        .get("isWorkday")).intValue()) {

      //获取指定日期昨天的数据
      map.put("time", yesterday);
      List<Map<String, Object>> yesterdayData = getReplenishData(mendingMethod, map);

      //获取上周同期的日期及其is_workday
      String beforeSevenDay = PeriodUtils.getAnyDayBeforeDate(7);
      dateMap.put("time", beforeSevenDay);
      Map<String, Object> beforeSevenDayMap = commonQueryDao.getBaseDateListInfo(dateMap);
      map.put("time", beforeSevenDay);

      List<Map<String, Object>> beforeSevenDayData = getReplenishData(mendingMethod, map);

      //判断昨天的数据是否含有当天缺失的时间片数据
      for (int period15 : period15MissArray) {

        if (yesterdayData != null && yesterdayData.size() > 0) {    //如果有，则补齐

          //yesterdayData循环标记
          int count = 0;
          for (int i = 0; i < yesterdayData.size(); i++) {

            int periodYesterday = 0;
            if (yesterdayData.get(i).containsKey("period")) {
              periodYesterday = ((BigDecimal) yesterdayData.get(i).get("period")).intValue();
            } else if (yesterdayData.get(i).containsKey("PERIOD")) {
              periodYesterday = ((BigDecimal) yesterdayData.get(i).get("PERIOD")).intValue();
            }

            if (period15 == periodYesterday) {
              data.add(yesterdayData.get(i));
              break;
              //找到period15在data中对应的位置
              /*for (int tempi = 0; tempi < data.size(); tempi++) {
                //获取当前时间片
                int periodDataI = 0;
                if (data.get(tempi).containsKey("period")) {
                  periodDataI = ((BigDecimal) data.get(tempi).get("period")).intValue();
                } else if (data.get(i).containsKey("PERIOD")) {
                  periodDataI = ((BigDecimal) data.get(tempi).get("PERIOD")).intValue();
                }
                if (period15 < periodDataI) {
                  data.add(tempi, yesterdayData.get(i));
                  break;
                }
              }
              break;*/
            }
            count++;
          }

          if (count == yesterdayData
              .size()) {  //如果count = yesterdayData.size(),则说明 在昨天的数据中未找到缺失的数据，则应该去上周的数据中查询
            //如果一致，则取上一周同期数据
            if (((BigDecimal) beforeSevenDayMap.get("isWorkday")).intValue()
                == ((BigDecimal) todayMap.get("isWorkday")).intValue()) {

              //判断昨天的数据是否含有当天缺失的时间片数据
              if (beforeSevenDayData != null && beforeSevenDayData.size() > 0) {    //如果有，则补齐

                for (int i = 0; i < beforeSevenDayData.size(); i++) {
                  int periodbeforeSevenDay = 0;
                  if (beforeSevenDayData.get(i).containsKey("period")) {
                    periodbeforeSevenDay = ((BigDecimal) beforeSevenDayData.get(i).get("period"))
                        .intValue();
                  } else if (beforeSevenDayData.get(i).containsKey("PERIOD")) {
                    periodbeforeSevenDay = ((BigDecimal) beforeSevenDayData.get(i).get("PERIOD"))
                        .intValue();
                  }

                  if (period15 == periodbeforeSevenDay) {
                    data.add(beforeSevenDayData.get(i));
                    //找到period15在data中对应的位置
                    /*for (int tempi = 0; tempi < data.size(); tempi++) {
                      //获取当前时间片
                      int periodDataI = 0;
                      if (data.get(tempi).containsKey("period")) {
                        periodDataI = ((BigDecimal) data.get(tempi).get("period")).intValue();
                      } else if (data.get(i).containsKey("PERIOD")) {
                        periodDataI = ((BigDecimal) data.get(tempi).get("PERIOD")).intValue();
                      }
                      if (period15 < periodDataI) {
                        data.add(tempi, beforeSevenDayData.get(i));
                        break;
                      }
                    }*/
                    break;
                  }

                }

              }

            } else {   //如果上一周同期与当天的is_workday不一致，则不补
              //获取指定日期昨天的数据
              map.put("time", yesterday);
              dataMendingByPeriod15(data, map, mendingMethod);
            }
          }

        }
      }

    } else {       //如果昨天与当天的is_workday不一致，则取上一周同期的数据
      data = getIntersectionDelayDayDataByDataMendingForLastWeek(dateMap, todayMap, map,
          period15MissArray, data, mendingMethod);
    }
  }

  /**
   * 获取历史数据.
   */
  public List<Map<String, Object>> getReplenishData(String mendingMethod, Map<String, Object> map) {
    List<Map<String, Object>> list = new ArrayList<>();
    if (DataMendingConstants.getSpeedAndTpiInfoByBlockfid.equals(mendingMethod)) {
      //片区概况-街道
      list = earlyWarningDao.getSpeedAndTpiInfoByBlockfid(map);
    } else if (DataMendingConstants.getIntersectionDelayDayData.equals(mendingMethod)) {
      list = earlyWarningDao.getIntersectionDelayDayDataRefill(map);
    } else if (DataMendingConstants.getEveryBlockTpi.equals(mendingMethod)) {
      list = earlyWarningDao.getEveryBlockTpi(map);
    } else if (DataMendingConstants.getCityAndEveryDistrictTpi.equals(mendingMethod)) {
      //全市概况-全市指数
      list = earlyWarningDao.getCityAndEveryDistrictTpi(map);
    } else if (DataMendingConstants.getSpeedAndTpiInfoByRoadsectfid
        .equals(mendingMethod)) { //道路路况-市内路况
      list = earlyWarningDao.getSpeedAndTpiInfoByRoadsectfid(map);
    } else if (DataMendingConstants.getTrafficTpiData.equals(mendingMethod)) {
      list = panoramicTrafficDao.getTrafficTpiData(map);
    } else if (DataMendingConstants.getDistrictCarOrBusData.equals(mendingMethod)) {
      //全市概况-公交速度
      list = earlyWarningDao.getDistrictCarOrBusData(map);
    } else if (DataMendingConstants.getSpeedAndTpiInfoBySubsectfid
        .equals(mendingMethod)) {
      //片区概况交通小区补齐数据操作
      list = earlyWarningDao.getSpeedAndTpiInfoBySubsectfid(map);
    } else if (DataMendingConstants.getHighSpeedLineDataByRoadsectfid
        .equals(mendingMethod)) {
      //道路路况-全省高速
      list = earlyWarningDao.getHighSpeedLineDataByRoadsectfid(map);
    } else if (DataMendingConstants.getCenterDayStatus.equals(mendingMethod)) {
      list = ftCenterDao.getCenterDayStatus(map);
    }
    return list;
  }

  private List<Map<String, Object>> getIntersectionDelayDayDataByDataMendingForLastWeek(
      Map<String, Object> dateMap, Map<String, Object> todayMap,
      Map<String, Object> map, List<Integer> period15MissArray, List<Map<String, Object>> data,
      String mendingMethod) {
    //获取上周同期的日期及其is_workday
    String beforeSevenDay = PeriodUtils.getAnyDayBeforeDate(7);
    dateMap.put("time", beforeSevenDay);
    Map<String, Object> beforeSevenDayMap = commonQueryDao.getBaseDateListInfo(dateMap);
    //如果一致，则取上一周同期数据
    if (null != beforeSevenDayMap && ((BigDecimal) beforeSevenDayMap.get("isWorkday")).intValue() == ((BigDecimal) todayMap
        .get("isWorkday")).intValue()) {

      map.put("time", beforeSevenDay);

      List<Map<String, Object>> beforeSevenDayData = new ArrayList<Map<String, Object>>();
      if (DataMendingConstants.getSpeedAndTpiInfoByBlockfid.equals(mendingMethod)) {
        beforeSevenDayData = earlyWarningDao.getSpeedAndTpiInfoByBlockfid(map);
      } else if (DataMendingConstants.getIntersectionDelayDayData.equals(mendingMethod)) {
        beforeSevenDayData = earlyWarningDao.getIntersectionDelayDayData(map);
      } else if (DataMendingConstants.getEveryBlockTpi.equals(mendingMethod)) {
        beforeSevenDayData = earlyWarningDao.getEveryBlockTpi(map);
      } else if (DataMendingConstants.getCityAndEveryDistrictTpi.equals(mendingMethod)) {
        beforeSevenDayData = earlyWarningDao.getCityAndEveryDistrictTpi(map);
      } else if (DataMendingConstants.getSpeedAndTpiInfoByRoadsectfid.equals(mendingMethod)) {
        beforeSevenDayData = earlyWarningDao.getSpeedAndTpiInfoByRoadsectfid(map);
      } else if (DataMendingConstants.getTrafficTpiData.equals(mendingMethod)) {
        beforeSevenDayData = panoramicTrafficDao.getTrafficTpiData(map);
      }
      //判断昨天的数据是否含有当天缺失的时间片数据
      for (int period15 : period15MissArray) {
        if (beforeSevenDayData != null && beforeSevenDayData.size() > 0) {    //如果有，则补齐

          for (int i = 0; i < beforeSevenDayData.size(); i++) {
            int periodbeforeSevenDay = 0;
            if (beforeSevenDayData.get(i).containsKey("period")) {
              periodbeforeSevenDay = ((BigDecimal) beforeSevenDayData.get(i).get("period"))
                  .intValue();
            } else if (beforeSevenDayData.get(i).containsKey("PERIOD")) {
              periodbeforeSevenDay = ((BigDecimal) beforeSevenDayData.get(i).get("PERIOD"))
                  .intValue();
            }
            if (period15 == periodbeforeSevenDay) {
              //找到period15在data中对应的位置
              for (int tempi = 0; tempi < data.size(); tempi++) {
                //获取当前时间片
                int periodDataI = 0;
                if (data.get(tempi).containsKey("period")) {
                  periodDataI = ((BigDecimal) data.get(tempi).get("period")).intValue();
                } else if (data.get(i).containsKey("PERIOD")) {
                  periodDataI = ((BigDecimal) data.get(tempi).get("PERIOD")).intValue();
                }
                if (period15 < periodDataI) {
                  data.add(tempi, beforeSevenDayData.get(i));
                  break;
                }
              }
              break;
            }

          }
        }

      }

    } else {   //如果上一周同期与当天的is_workday不一致，则不补

    }

    return data;
  }


}
