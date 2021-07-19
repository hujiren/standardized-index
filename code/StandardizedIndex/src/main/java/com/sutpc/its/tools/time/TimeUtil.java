package com.sutpc.its.tools.time;

import java.util.Date;

/**
 * 时间转换工具类.
 */
public class TimeUtil {

  /**
   * 测试代码.
   */
  public static void main(String[] args) {
    int daysBetweenDates = getDaysBetweenDates("20200317", "20200415");
    System.out.println(daysBetweenDates);
  }

  /**
   * 计算2日期之间相差的天数.
   *
   * @param dateString1 如 "19881122"
   * @param dateString2 如 "19881122"
   */
  public static int getDaysBetweenDates(String dateString1, String dateString2) {
    Date date1 = string2Date(dateString1);
    Date date2 = string2Date(dateString2);
    return differentDaysByMillisecond(date1, date2) + 1;
  }

  /**
   * 字符串转日期对象.
   *
   * @param dateString 如 "19881122"
   */
  private static Date string2Date(String dateString) {
    Integer yearInt = Integer.valueOf(dateString.substring(0, 4));
    Integer monthInt = Integer.valueOf(dateString.substring(4, 6));
    Integer dayInt = Integer.valueOf(dateString.substring(6, 8));
    Date date = new Date(yearInt - 1900, monthInt - 1, dayInt);
    System.out.println(date);
    return date;
  }

  /**
   * 通过时间秒毫秒数判断两个时间的间隔（天数）.
   */
  private static int differentDaysByMillisecond(Date date1, Date date2) {
    int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    return days;
  }
}
