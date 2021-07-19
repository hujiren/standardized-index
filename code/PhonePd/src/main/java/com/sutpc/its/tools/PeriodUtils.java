package com.sutpc.its.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class PeriodUtils {

  public static String getCurrentDate() {
    Calendar calendar = Calendar.getInstance();

    if (getCurrentPeriod() == 288) {
      calendar.add(Calendar.DAY_OF_MONTH, -1);
    }

    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
    return sf.format(calendar.getTime());

  }

  public static int getCurrentPeriod() {
    Calendar calendar = Calendar.getInstance();
    int cp = calendar.getTime().getHours() * 12 + calendar.getTime().getMinutes() / 5 - 1;
    //System.out.println("period:"+cp);

    if (cp == 0) {
      return 288;
    }
    return cp;
  }

  /**
   * getCurrentPeriod15.
   */
  public static int getCurrentPeriod15() {
    return (getCurrentPeriod() + 1) / 3 - 1;
  }
	/*
	public static Map<String,Object> buildPeriodMapParams(Map<String,Object> map){
		map.put("time", getCurrentDate());
		map.put("period", getCurrentPeriod());
		return map;
	}*/

  /**
   * 时间片转时、分
   */
  public static String getTimeByPeriod(int x) {

    return getTimeByPeriod("5分钟", x);
  }

  /**
   * 时间片转时、分
   */
  public static String getTimeByPeriod(String timeprecision, int x, Boolean isshowzero) {

    String result = "";
    String hourStr = "";
    String minStr = "";
    int hour;
    int min;
    int minTemp;
    if ("5分钟".equals(timeprecision)) {
      hour = x / 12;
      min = x % 12;
      minTemp = min * 5;
    } else if ("15分钟".equals(timeprecision)) {
      hour = x / 4;
      min = x % 4;
      minTemp = min * 15;
    } else {
      hour = -1;
      min = -1;
      minTemp = -1;
    }
    hourStr = hour + "";
    if (minTemp < 10) {
      minStr = "0" + minTemp;
    } else {
      minStr = minTemp + "";
    }
    if (!"时".equals(timeprecision)) {
      if (min == 0) {
        result = hourStr;
        if (isshowzero) {
          result = result + ":00";
        }
      } else {
        result = hourStr + ":" + minStr;
      }
    } else {
      result = x + "";
    }
    return result;
  }

  public static String getTimeByPeriod(String timeprecision, int x) {
    return getTimeByPeriod(timeprecision, x, false);
  }

  public static Map<String, Object> time2period(Map<String, Object> map) {

    Object starttime = map.get("starttime");
    Object endtime = map.get("endtime");
    int startperiod = 0;
    int endperiod = 0;
    if (starttime != null && !"".equals(starttime.toString())) {
      int hour = Integer.parseInt(starttime.toString().split(":")[0]);
      int min = Integer.parseInt(starttime.toString().split(":")[1]);
      if (min % 5 == 0) {
        startperiod = hour * 12 + min / 5 + 1;
      } else {
        startperiod = hour * 12 + min / 5 + 2;
      }
    }
    if (endtime != null && !"".equals(endtime.toString())) {
      int hour = Integer.parseInt(endtime.toString().split(":")[0]);
      int min = Integer.parseInt(endtime.toString().split(":")[1]);
      if (min % 5 == 0) {
        endperiod = hour * 12 + min / 5;
      } else {
        endperiod = hour * 12 + min / 5 + 1;
      }
    }
    map.put("startperiod", startperiod);
    map.put("endperiod", endperiod);

    return map;
  }

  public static Map<String, Object> time2newperiod(Map<String, Object> map) {
    Object starttime = map.get("starttime");
    Object endtime = map.get("endtime");
    int startperiod = 0;
    int endperiod = 0;
    if (starttime != null && !"".equals(starttime.toString())) {
      int hour = Integer.parseInt(starttime.toString().split(":")[0]);
      int min = Integer.parseInt(starttime.toString().split(":")[1]);
      startperiod = hour * 2 + min / 30;
    }
    if (endtime != null && !"".equals(endtime.toString())) {
      int hour = Integer.parseInt(endtime.toString().split(":")[0]);
      int min = Integer.parseInt(endtime.toString().split(":")[1]);
      endperiod = hour * 2 + min / 30;
    }
    map.put("startperiod", startperiod);
    map.put("endperiod", endperiod);

    return map;
  }

  public static int time2period(String time) {
    int period = 0;
    if (time != null && !"".equals(time.toString())) {
      int hour = Integer.parseInt(time.toString().split(":")[0]);
      int min = Integer.parseInt(time.toString().split(":")[1]);
      if (min % 5 == 0) {
        period = hour * 12 + min / 5 + 1;
      } else {
        period = hour * 12 + min / 5 + 2;
      }
    }
    return period;
  }

  public static String getAnyDayBeforeDate(int anyDay) {
    Calendar c = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    //过去七天
    c.setTime(new Date());
    c.add(Calendar.DATE, -anyDay);
    Date d = c.getTime();
    String day = format.format(d);
    return day;
  }

  /**
   * getAppointDateAnyDayBeforeDate.
   */
  public static String getAppointDateAnyDayBeforeDate(int anyDay, String date) {
    Calendar c = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    //过去七天
    try {
      c.setTime(format.parse(date));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    c.add(Calendar.DATE, -anyDay);
    Date d = c.getTime();
    String day = format.format(d);
    return day;
  }
}
