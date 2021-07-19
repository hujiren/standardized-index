package com.sutpc.its.service;

import com.sutpc.its.dao.IAppletDao;
import com.sutpc.its.tools.PeriodUtils;
import com.sutpc.its.tools.Utils;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class AppletService {

  @Autowired
  private IAppletDao appletDao;

  /**
   * .
   */
  public Map<String, Object> getCityRealTimeInfo(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    List<Map<String, Object>> list = appletDao.getCityRealTimeInfo(map);
    result.put("data", list);
    map.put("time", PeriodUtils.getAnyDayBeforeDate(7));
    List<Map<String, Object>> lastList = appletDao.getCityRealTimeInfo(map);
    int beforeDay = 7;
    while (lastList == null || lastList.size() == 0) {
      map.put("time", PeriodUtils.getAnyDayBeforeDate(++beforeDay));
      lastList = appletDao.getCityRealTimeInfo(map);
    }
    result.put("last_data", lastList);
    return result;

  }

  /**
   * .
   */
  public List<Map<String, Object>> getKeyAreaAvgSpeed(Map<String, Object> map) {
    return appletDao.getKeyAreaAvgSpeed(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getKeyCrossAvgSpeed(Map<String, Object> map) {
    return appletDao.getKeyCrossAvgSpeed(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getKeyPoiInfo(Map<String, Object> map) {
    return appletDao.getKeyPoiINfo(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getKeyPoiStatusByPoiFid(Map<String, Object> map) {
    return appletDao.getKeyPoiStatusByPoiFid(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getKeyPoiSpeedByPoiFid(Map<String, Object> map) {
    String range = map.get("range").toString();
    if ("today".equals(range)) {
      map.put("startdate", PeriodUtils.getCurrentDate());
      map.put("enddate", PeriodUtils.getCurrentDate());
    } else if ("lastweek".equals(range)) {
      map.put("startdate", Utils.getTodayBeforeDate(-7));
      map.put("enddate", PeriodUtils.getCurrentDate());
    } else if ("lastmonth".equals(range)) {
      map.put("startdate", Utils.getTodayBeforeDate(-30));
      map.put("enddate", PeriodUtils.getCurrentDate());
    }
    return appletDao.getKeyPoiSpeedByPoiFid(map);
  }

  /**
   * .
   */
  public Map<String, Object> getPeakTpi(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    map.put("timeproperty", "morning_peak");
    String dateStr = map.get("time").toString();
    Map<String, Object> morningPeakMap = appletDao.getPeakTpi(map);
    map.put("timeproperty", "evening_peak");
    Map<String, Object> eveningPeakMap = appletDao.getPeakTpi(map);

    String newDateStr = getSpecificDate(dateStr, 7);
    map.put("time", newDateStr);
    map.put("timeproperty", "morning_peak");
    Map<String, Object> lastMorningPeakMap = appletDao.getPeakTpi(map);
    map.put("timeproperty", "evening_peak");
    Map<String, Object> lastEveningPeakMap = appletDao.getPeakTpi(map);

    double mptpi = 0.0;
    if (!Objects.isNull(morningPeakMap)) {
      mptpi = Double.parseDouble(morningPeakMap.get("TPI") + "");
    }
    double eptpi = 0.0;
    if (!Objects.isNull(eveningPeakMap)) {
      eptpi = Double.parseDouble(eveningPeakMap.get("TPI") + "");
    }
    double lmptpi = 0.0;
    if (!Objects.isNull(lastMorningPeakMap)) {
      lmptpi = Double.parseDouble(lastMorningPeakMap.get("TPI") + "");
    }
    double lemtpi = 0.0;
    if (!Objects.isNull(lastEveningPeakMap)) {
      Double.parseDouble(lastEveningPeakMap.get("TPI") + "");
    }
    double ratiomp = (mptpi - lmptpi) / lmptpi;
    double ratioep = (eptpi - lemtpi) / lemtpi;

    if (ratiomp >= 0) {
      result.put("ratiomp", "上升" + formatDouble(ratiomp * 100.00) + "%");
    } else if (ratiomp < 0) {
      result.put("ratiomp", "下降" + formatDouble(ratiomp * 100.00 * -1) + "%");
    }

    if (ratioep >= 0) {
      result.put("ratioep", "上升" + formatDouble(ratioep * 100) + "%");
    } else if (ratioep < 0) {
      result.put("ratioep", "下降" + formatDouble(ratioep * 100 * -1) + "%");
    }

    result.put("mptpi", mptpi);
    result.put("eptpi", eptpi);

    return result;

  }

  /**
   * .
   */
  public String getSpecificDate(String dateStr, int num) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String result = "";
    try {

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(sdf.parse(dateStr));
      calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - num);
      Date date = calendar.getTime();
      result = sdf.format(date);
      System.out.println(result);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 保留两位小数，四舍五入的一个老土的方法.
   */
  private static String formatDouble(double d) {
    DecimalFormat df = new DecimalFormat("#0.00");
    return df.format(d);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDayReportAvgSpeed(Map<String, Object> map) {
    return appletDao.getDayReportAvgSpeed(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDayReportAvgTpi(Map<String, Object> map) {
    return appletDao.getDayReportAvgTpi(map);
  }

  /**
   * .
   */
  public Map<String, Object> getEveryDistrictSpeed(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<String, Object>();
    map.put("timeproperty", "morning_peak");
    List<Map<String, Object>> mlist = appletDao.getEveryDistrictSpeed(map);
    map.put("timeproperty", "evening_peak");
    List<Map<String, Object>> elist = appletDao.getEveryDistrictSpeed(map);
    result.put("morning_peak", mlist);
    result.put("evening_peak", elist);
    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getMajorRoadSpeed(Map<String, Object> map) {
    map.put("timeproperty", "morning_peak");
    List<Map<String, Object>> morningPeakList = appletDao.getMajorRoadSpeed(map);

    if (!CollectionUtils.isEmpty(morningPeakList)) {
      List<Map<String, Object>> morningHongList = new ArrayList<>();
      for (Map<String, Object> m : morningPeakList) {
        String roadName = m.get("ROAD_NAME").toString();
        if (roadName.startsWith("红岭")) {
          morningHongList.add(m);
        }
      }
      morningPeakList.removeIf(map1 -> {
        String roadName = map1.get("ROAD_NAME").toString();
        return roadName.startsWith("红岭");
      });

      Map<String, Object> morningHongNbMap = new HashMap<>();
      double morningHongNbSpeeds = 0.0;
      double morningHongBnSpeeds = 0.0;
      int indexNb = 0;
      int indexBn = 0;
      for (Map<String, Object> m : morningHongList) {
        if ((m.get("ROAD_NAME") + "").endsWith("(南-北)")) {
          morningHongNbSpeeds = morningHongNbSpeeds + Double.parseDouble(m.get("SPEED") + "");
          indexNb = indexNb + 1;
        }
        if ((m.get("ROAD_NAME") + "").endsWith("(北-南)")) {
          morningHongBnSpeeds = morningHongBnSpeeds + Double.parseDouble(m.get("SPEED") + "");
          indexBn = indexBn + 1;
        }
      }
      morningHongNbMap.put("ROAD_FID", 10000001);
      morningHongNbMap.put("ROAD_NAME", "红岭路(南-北)");
      morningHongNbMap
          .put("SPEED", Double.parseDouble(formatDouble(morningHongNbSpeeds / indexNb)));
      Map<String, Object> morningHongBnMap = new HashMap<>();
      morningHongBnMap.put("ROAD_FID", 10000001);
      morningHongBnMap.put("ROAD_NAME", "红岭路(北-南)");
      morningHongBnMap
          .put("SPEED", Double.parseDouble(formatDouble(morningHongBnSpeeds / indexBn)));
      morningPeakList.add(morningHongNbMap);
      morningPeakList.add(morningHongBnMap);
    }

    map.put("timeproperty", "evening_peak");
    List<Map<String, Object>> eveningPeakList = appletDao.getMajorRoadSpeed(map);

    if (!CollectionUtils.isEmpty(eveningPeakList)) {
      List<Map<String, Object>> eveningHongList = new ArrayList<>();
      for (Map<String, Object> m : eveningPeakList) {
        String roadName = m.get("ROAD_NAME").toString();
        if (roadName.startsWith("红岭")) {
          eveningHongList.add(m);
        }
      }
      eveningPeakList.removeIf(map1 -> {
        String roadName = map1.get("ROAD_NAME").toString();
        return roadName.startsWith("红岭");
      });
      Map<String, Object> eveningHongNbMap = new HashMap<>();
      double eveningHongNbSpeeds = 0.0;
      double eveningHongBnSpeeds = 0.0;
      int indexNb = 0;
      int indexBn = 0;
      for (Map<String, Object> m : eveningHongList) {
        if ((m.get("ROAD_NAME") + "").endsWith("(南-北)")) {
          eveningHongNbSpeeds = eveningHongNbSpeeds + Double.parseDouble(m.get("SPEED") + "");
          indexNb = indexNb + 1;
        }
        if ((m.get("ROAD_NAME") + "").endsWith("(北-南)")) {
          eveningHongBnSpeeds = eveningHongBnSpeeds + Double.parseDouble(m.get("SPEED") + "");
          indexBn = indexBn + 1;
        }
      }
      eveningHongNbMap.put("ROAD_FID", 10000001);
      eveningHongNbMap.put("ROAD_NAME", "红岭路(南-北)");
      eveningHongNbMap
          .put("SPEED", Double.parseDouble(formatDouble(eveningHongNbSpeeds / indexNb)));
      Map<String, Object> eveningHongBnMap = new HashMap<>();
      eveningHongBnMap.put("ROAD_FID", 10000001);
      eveningHongBnMap.put("ROAD_NAME", "红岭路(北-南)");
      eveningHongBnMap
          .put("SPEED", Double.parseDouble(formatDouble(eveningHongBnSpeeds / indexBn)));
      eveningPeakList.add(eveningHongNbMap);
      eveningPeakList.add(eveningHongBnMap);
    }
    Map<String, Object> result = new HashMap<>();
    result.put("morning_peak", morningPeakList);
    result.put("evening_peak", eveningPeakList);
    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getMajorCrossSpeed(Map<String, Object> map) {
    map.put("timeproperty", "morning_peak");
    map.put("cross_type", 1);//进关
    List<Map<String, Object>> morningCrossList = appletDao.getMajorCrossSpeed(map);
    map.put("timeproperty", "evening_peak");
    map.put("cross_type", 2);//出关
    List<Map<String, Object>> eveningCrossList = appletDao.getMajorCrossSpeed(map);
    Map<String, Object> result = new HashMap<>();
    result.put("morning_peak", morningCrossList);
    result.put("evening_peak", eveningCrossList);
    return result;

  }

  /**
   * .
   */
  public Map<String, Object> getWeekTotalOperationData(Map<String, Object> map) {
    String startDate = map.get("startdate") + "";
    String endDate = map.get("enddate") + "";
    String lastWeekStartDate = PeriodUtils.getAppointDateAnyDayBeforeDate(7, startDate);
    String lastWeekEndDate = PeriodUtils.getAppointDateAnyDayBeforeDate(7, endDate);
    map.put("timeproperty", "all_peak");
    map.put("rank", 3);
    List<Map<String, Object>> tpiRankingList = appletDao.getWeekDistrictTpiRanking(map);

    map.put("timeproperty", "morning_peak");
    final List<Map<String, Object>> morningPeakList = appletDao.getWeekCityPeak(map);
    map.put("timeproperty", "evening_peak");
    final List<Map<String, Object>> eveningPeakList = appletDao.getWeekCityPeak(map);
    map.put("rank", 10);
    List<Map<String, Object>> eveningPeakTpiRankingList = appletDao.getWeekDistrictTpiRanking(map);
    map.put("timeproperty", "morning_peak");
    List<Map<String, Object>> morningPeakTpiRankingList = appletDao.getWeekDistrictTpiRanking(map);

    map.put("startdate", lastWeekStartDate);
    map.put("enddate", lastWeekEndDate);
    map.put("timeproperty", "morning_peak");
    List<Map<String, Object>> lastMorningPeakList = appletDao.getWeekCityPeak(map);
    map.put("timeproperty", "evening_peak");
    List<Map<String, Object>> lastEveningPeakList = appletDao.getWeekCityPeak(map);
    for (int i = 0; i < morningPeakList.size(); i++) {
      Map<String, Object> morningPeakMap = morningPeakList.get(i);
      String fid = morningPeakMap.get("FID") + "";
      if (fid.equals("111")) {
        double morningPeakTpi = Double.parseDouble(morningPeakMap.get("TPI") + "");
        Map<String, Object> lastMorningPeakMap = lastMorningPeakList.get(i);
        double lastMorningPeakTpi = Double.parseDouble(lastMorningPeakMap.get("TPI") + "");
        Double morningPeakRate = (morningPeakTpi - lastMorningPeakTpi) / lastMorningPeakTpi;
        if (morningPeakRate >= 0) {
          morningPeakMap.put("RATIO", "上升" + formatDouble(morningPeakRate * 100.00) + "%");
        } else {
          morningPeakMap.put("RATIO", "下降" + formatDouble(morningPeakRate * 100.00 * -1) + "%");
        }
      }
      if (fid.equals("222")) {
        double morningPeakTpi = Double.parseDouble(morningPeakMap.get("TPI") + "");
        Map<String, Object> lastMorningPeakMap = lastMorningPeakList.get(i);
        double lastMorningPeakTpi = Double.parseDouble(lastMorningPeakMap.get("TPI") + "");
        Double morningPeakRate = (morningPeakTpi - lastMorningPeakTpi) / lastMorningPeakTpi;
        if (morningPeakRate >= 0) {
          morningPeakMap.put("RATIO", "上升" + formatDouble(morningPeakRate * 100.00) + "%");
        } else {
          morningPeakMap.put("RATIO", "下降" + formatDouble(morningPeakRate * 100.00 * -1) + "%");
        }
      }
    }
    for (int i = 0; i < eveningPeakList.size(); i++) {
      Map<String, Object> eveningPeakMap = eveningPeakList.get(i);
      String fid = eveningPeakMap.get("FID") + "";
      if (fid.equals("111")) {
        double eveningPeakTpi = Double.parseDouble(eveningPeakMap.get("TPI") + "");
        Map<String, Object> lastEveningPeakMap = lastEveningPeakList.get(i);
        double lastEveningPeakTpi = Double.parseDouble(lastEveningPeakMap.get("TPI") + "");
        Double eveningPeakRate = (eveningPeakTpi - lastEveningPeakTpi) / lastEveningPeakTpi;
        if (eveningPeakRate >= 0) {
          eveningPeakMap.put("RATIO", "上升" + formatDouble(eveningPeakRate * 100.00) + "%");
        } else {
          eveningPeakMap.put("RATIO", "下降" + formatDouble(eveningPeakRate * 100.00 * -1) + "%");
        }
      }
      if (fid.equals("222")) {
        double eveningPeakTpi = Double.parseDouble(eveningPeakMap.get("TPI") + "");
        Map<String, Object> lastEveningPeakMap = lastEveningPeakList.get(i);
        double lastEveningPeakTpi = Double.parseDouble(lastEveningPeakMap.get("TPI") + "");
        Double eveningPeakRate = (eveningPeakTpi - lastEveningPeakTpi) / lastEveningPeakTpi;
        if (eveningPeakRate >= 0) {
          eveningPeakMap.put("RATIO", "上升" + formatDouble(eveningPeakRate * 100.00) + "%");
        } else {
          eveningPeakMap.put("RATIO", "下降" + formatDouble(eveningPeakRate * 100.00 * -1) + "%");
        }
      }
    }
    Map<String, Object> result = new HashMap<>();
    result.put("morning_peak", morningPeakList);
    result.put("evening_peak", eveningPeakList);
    /*result.put("tpi_ranking", tpi_ranking_list);
    result.put("morning_peak_tpi_ranking", morning_peak_tpi_ranking_list);
    result.put("evening_peak_tpi_ranking", evening_peak_tpi_ranking_list);*/

    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getWeekBlockTpiRanking(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    map.put("timeproperty", "morning_peak");
    List<Map<String, Object>> morningPeakList = appletDao.getWeekBlockTpiRanking(map);
    map.put("timeproperty", "evening_peak");
    List<Map<String, Object>> eveningPeakList = appletDao.getWeekBlockTpiRanking(map);
    result.put("morning_ranking", morningPeakList);
    result.put("evening_ranking", eveningPeakList);
    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getWeekPoiSpeedRanking(Map<String, Object> map) {
    String startdate = map.get("startdate") + "";
    String enddate = map.get("enddate") + "";
    String lastWeekStartdate = PeriodUtils.getAppointDateAnyDayBeforeDate(7, startdate);
    String lastWeekEnddate = PeriodUtils.getAppointDateAnyDayBeforeDate(7, enddate);

    map.put("timeproperty", "morning_peak");
    final List<Map<String, Object>> morningPeakList = appletDao.getWeekPoiSpeedRanking(map);
    map.put("timeproperty", "evening_peak");
    final List<Map<String, Object>> eveningPeakList = appletDao.getWeekPoiSpeedRanking(map);

    map.put("startdate", lastWeekStartdate);
    map.put("enddate", lastWeekEnddate);
    map.put("timeproperty", "morning_peak");
    List<Map<String, Object>> lastMorningPeakList = appletDao.getWeekPoiSpeedRanking(map);
    map.put("timeproperty", "evening_peak");
    List<Map<String, Object>> lastEveningPeakList = appletDao.getWeekPoiSpeedRanking(map);

    for (int i = 0; i < morningPeakList.size(); i++) {
      Map<String, Object> morningPeakMap = morningPeakList.get(i);
      String fid = morningPeakMap.get("FID") + "";
      double morningPeakTpi = 0.0;
      if (!Objects.isNull(morningPeakMap) && morningPeakMap.size() > 0) {
        morningPeakTpi = Double.parseDouble(morningPeakMap.get("SPEED") + "");
      }
      Map<String, Object> lastMorningPeakMap = new HashMap<>();
      if (!CollectionUtils.isEmpty(lastMorningPeakList) && lastMorningPeakList.size() > 0) {
        lastMorningPeakMap = lastMorningPeakList.get(i);
      }
      double lastMorningPeakTpi = 0.0;
      if (!Objects.isNull(lastMorningPeakMap) && lastMorningPeakMap.size() > 0) {
        lastMorningPeakTpi = Double.parseDouble(lastMorningPeakMap.get("SPEED") + "");
      }
      Double morningPeakRate = (morningPeakTpi - lastMorningPeakTpi) / lastMorningPeakTpi;
      if (morningPeakRate >= 0) {
        morningPeakMap.put("RATIO", "上升" + formatDouble(morningPeakRate * 100.00) + "%");
      } else {
        morningPeakMap.put("RATIO", "下降" + formatDouble(morningPeakRate * 100.00 * -1) + "%");
      }
    }
    for (int i = 0; i < eveningPeakList.size(); i++) {
      Map<String, Object> eveningPeakMap = eveningPeakList.get(i);
      String fid = eveningPeakMap.get("FID") + "";
      double eveningPeakTpi = 0.0;
      if (!Objects.isNull(eveningPeakMap) && eveningPeakMap.size() > 0) {
        eveningPeakTpi = Double.parseDouble(eveningPeakMap.get("SPEED") + "");
      }
      double lastEveningPeakTpi =0.0;
      Map<String, Object> lastEveningPeakMap = new HashMap<>();
      if (!CollectionUtils.isEmpty(lastEveningPeakList)) {
        lastEveningPeakMap = lastEveningPeakList.get(i);
        lastEveningPeakTpi= Double.parseDouble(lastEveningPeakMap.get("SPEED") + "");
      }

      Double eveningPeakRate = (eveningPeakTpi - lastEveningPeakTpi) / lastEveningPeakTpi;
      if (eveningPeakRate >= 0) {
        eveningPeakMap.put("RATIO", "上升" + formatDouble(eveningPeakRate * 100.00) + "%");
      } else {
        eveningPeakMap.put("RATIO", "下降" + formatDouble(eveningPeakRate * 100.00 * -1) + "%");
      }
    }
    Map<String, Object> result = new HashMap<>();
    result.put("morning_peak_poi_ranking", morningPeakList);
    result.put("evening_peak_poi_ranking", eveningPeakList);

    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getWeekCrossPeak(Map<String, Object> map) {
    String startdate = map.get("startdate") + "";
    String enddate = map.get("enddate") + "";
    String lastWeekStartdate = PeriodUtils.getAppointDateAnyDayBeforeDate(7, startdate);
    String lastWeekEnddate = PeriodUtils.getAppointDateAnyDayBeforeDate(7, enddate);

    map.put("timeproperty", "morning_peak");
    map.put("dir_name", "in_cross");
    final List<Map<String, Object>> morningPeakList = appletDao.getWeekCrossPeak(map);
    map.put("timeproperty", "evening_peak");
    map.put("dir_name", "out_cross");
    final List<Map<String, Object>> eveningPeakList = appletDao.getWeekCrossPeak(map);

    map.put("startdate", lastWeekStartdate);
    map.put("enddate", lastWeekEnddate);
    map.put("timeproperty", "morning_peak");
    map.put("dir_name", "in_cross");
    List<Map<String, Object>> lastMorningPeakList = appletDao.getWeekCrossPeak(map);
    map.put("timeproperty", "evening_peak");
    map.put("dir_name", "out_cross");
    List<Map<String, Object>> lastEveningPeakList = appletDao.getWeekCrossPeak(map);

    double morningPeakSpeeds = 0.0;
    double lastMorningPeakSpeeds = 0.0;
    for (int i = 0; i < morningPeakList.size(); i++) {
      morningPeakSpeeds =
          morningPeakSpeeds + Double.parseDouble(morningPeakList.get(i).get("SPEED") + "");
      lastMorningPeakSpeeds = lastMorningPeakSpeeds + Double
          .parseDouble(lastMorningPeakList.get(i).get("SPEED") + "");
    }
    double morningPeakAvgSpeed = morningPeakSpeeds / morningPeakList.size();
    double lastMorningPeakAvgSpeed = lastMorningPeakSpeeds / lastEveningPeakList.size();

    double eveningPeakSpeeds = 0.0;
    double lastEveningPeakSpeeds = 0.0;
    for (int i = 0; i < eveningPeakList.size(); i++) {
      eveningPeakSpeeds =
          eveningPeakSpeeds + Double.parseDouble(eveningPeakList.get(i).get("SPEED") + "");
      lastEveningPeakSpeeds = lastEveningPeakSpeeds + Double
          .parseDouble(lastEveningPeakList.get(i).get("SPEED") + "");
    }
    double eveningPeakAvgSpeed = eveningPeakSpeeds / eveningPeakList.size();
    double lastEveningPeakAvgSpeed = lastEveningPeakSpeeds / lastEveningPeakList.size();

    double morningPeakRatio =
        (morningPeakAvgSpeed - lastMorningPeakAvgSpeed) / lastMorningPeakAvgSpeed;
    double eveningPeakRatio =
        (eveningPeakAvgSpeed - lastEveningPeakAvgSpeed) / lastEveningPeakAvgSpeed;
    Map<String, Object> totalMap = new HashMap<>();
    if (morningPeakRatio >= 0) {
      totalMap.put("MORNING_PEAK_RATIO", "上升" + formatDouble(morningPeakRatio * 100.00) + "%");
    } else if (morningPeakRatio < 0) {
      totalMap
          .put("MORNING_PEAK_RATIO", "下降" + formatDouble(morningPeakRatio * 100.00 * -1) + "%");
    }
    if (eveningPeakRatio >= 0) {
      totalMap.put("EVENING_PEAK_RATIO", "上升" + formatDouble(eveningPeakRatio * 100.00) + "%");
    } else if (eveningPeakRatio < 0) {
      totalMap
          .put("EVENING_PEAK_RATIO", "下降" + formatDouble(eveningPeakRatio * 100.00 * -1) + "%");
    }
    totalMap.put("MORNING_PEAK_AVG_SPEED", formatDouble(morningPeakAvgSpeed));
    totalMap.put("EVENING_PEAK_AVG_SPEED", formatDouble(eveningPeakAvgSpeed));

    double morningMajorTpi = 0.0;
    double eveningMajorTpi = 0.0;
    for (Map<String, Object> m : morningPeakList) {
      double tpi = Double.parseDouble(m.get("TPI") + "");
      if (tpi > morningMajorTpi) {
        morningMajorTpi = tpi;
        totalMap.put("MORNING_MAJOR_SPEED", m.get("SPEED"));
        totalMap.put("MORNING_MAJOR_NAME", m.get("FNAME"));
        totalMap.put("MORNING_MAJOR_STATUS", m.get("STATUS"));
      }
    }
    for (Map<String, Object> m : eveningPeakList) {
      double tpi = Double.parseDouble(m.get("TPI") + "");
      if (tpi > eveningMajorTpi) {
        eveningMajorTpi = tpi;
        totalMap.put("EVENING_MAJOR_SPEED", m.get("SPEED"));
        totalMap.put("EVENING_MAJOR_NAME", m.get("FNAME"));
        totalMap.put("EVENING_MAJOR_STATUS", m.get("STATUS"));
      }
    }
    Map<String, Object> result = new HashMap<>();
    result.put("total_data", totalMap);
    result.put("morning_peak", morningPeakList);
    result.put("evening_peak", eveningPeakList);

    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getWeekJamRoadsectRanking(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<String, Object>();
    map.put("timeproperty", "morning_peak");
    List<Map<String, Object>> morningPeakList = appletDao.getWeekJamRoadsectRanking(map);
    map.put("timeproperty", "evening_peak");
    List<Map<String, Object>> eveningPeakList = appletDao.getWeekJamRoadsectRanking(map);
    //map.put("timeproperty","all_peak");
    //List<Map<String, Object> > all_list = appletDao.getWeekJamRoadsectRanking(map);

    result.put("morning_peak", morningPeakList);
    result.put("evening_peak", eveningPeakList);
    //result.put("all_peak", all_list);

    return result;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getUpdateDataTime(Map<String, Object> map) {
    return appletDao.getUpdateDataTime(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadsectOperationDistribute(Map<String, Object> map) {
    return appletDao.getRoadsectOperationDistribute(map);
  }
}
