package com.sutpc.its.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:57 2020/9/1.
 * @Description
 * @Modified By:
 */
public class DateUtils {

  /**
   * 获取某天的开始日期.
   *
   * @param offset 0今天，1明天，-1昨天，依次类推
   */
  public static LocalDateTime dayStart(int offset) {
    return LocalDate.now().plusDays(offset).atStartOfDay();
  }

  /**
   * 获取此刻与相对当天第day天的起始时间相隔的秒数。day为0表示今天的起始时间；1明天，2后天，-1昨天，-2前天等，依次例推.
   */
  public static int ttl(int day) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime time = LocalDate.now().plusDays(day).atStartOfDay();
    int ttl = (int) Duration.between(now, time).toMillis() / 1000;
    return ttl;
  }

  /**
   * 获取某周的周一日期.
   *
   * @param offset 0本周，1下周，-1上周，依次类推
   */
  public static LocalDate weekStart(int offset) {
    LocalDate localDate = LocalDate.now().plusWeeks(offset);
    return localDate.with(DayOfWeek.MONDAY);
  }

  /**
   * 获取某周的周五日期.
   *
   * @param offset 0本周，1下周，-1上周，依次类推
   */
  public static LocalDate weekFriday(int offset) {
    LocalDate localDate = LocalDate.now().plusWeeks(offset);
    return localDate.with(DayOfWeek.FRIDAY);
  }

  /**
   * 获取某月的开始日期.
   *
   * @param offset 0本月，1下个月，-1上个月，依次类推
   */
  public static LocalDate monthStart(int offset) {
    return LocalDate.now().plusMonths(offset).with(TemporalAdjusters.firstDayOfMonth());
  }

  /**
   * 获取某月的开始日期.
   *
   * @param offset 0本月，1下个月，-1上个月，依次类推
   * @return 字符串类型
   */
  public static String monthStartStr(int offset) {
    LocalDate localDate = monthStart(offset);
    return localDate.toString().replace("-", "");
  }

  /**
   * 获取某月的结束日期.
   *
   * @param offset 0本月，1下个月，-1上个月，依次类推
   * @return 字符串类型
   */
  public static String monthEndStr(int offset) {
    LocalDate localDate = monthEnd(offset);
    return localDate.toString().replace("-", "");
  }

  /**
   * 获取某月的结束日期.
   *
   * @param offset 0本月，1下个月，-1上个月，依次类推
   */
  public static LocalDate monthEnd(int offset) {
    return LocalDate.now().plusMonths(offset).with(TemporalAdjusters.lastDayOfMonth());
  }

  /**
   * 获取某季度的开始日期. 季度一年四季， 第一季度：2月-4月， 第二季度：5月-7月， 第三季度：8月-10月， 第四季度：11月-1月.
   *
   * @param offset 0本季度，1下个季度，-1上个季度，依次类推
   */
  public static LocalDate quarterStart(int offset) {
    final LocalDate date = LocalDate.now().plusMonths(offset * 3);
    int month = date.getMonth().getValue();//当月
    int start = 0;
    //第一季度
    if (month >= 2 && month <= 4) {
      start = 2;
      //第二季度
    } else if (month >= 5 && month <= 7) {
      start = 5;
      //第三季度
    } else if (month >= 8 && month <= 10) {
      start = 8;
      //第四季度
    } else if ((month >= 11 && month <= 12)) {
      start = 11;
      //第四季度
    } else if (month == 1) {
      start = 11;
      month = 13;
    }
    return date.plusMonths(start - month).with(TemporalAdjusters.firstDayOfMonth());
  }

  /**
   * 获取某年的开始日期.
   *
   * @param offset 0今年，1明年，-1去年，依次类推
   */
  public static LocalDate yearStart(int offset) {
    return LocalDate.now().plusYears(offset).with(TemporalAdjusters.firstDayOfYear());
  }

  /**
   * 获取当前localDate.
   */
  public static LocalDate currentLocalDate() {
    return LocalDate.now();
  }

  /**
   * 获取当前日期字符串 20200808.
   */
  public static String currentDate() {
    return currentLocalDate().toString().replace("-", "");
  }

  /**
   * 获取当前日期的前几天：day -1 昨天，-2 前天，以此类推.
   */
  public static String dayBefore(int day) {
    return currentLocalDate().plusDays(day).atStartOfDay().toString().replace("-", "")
        .substring(0, 8);
  }

  /**
   * 获取五分钟时间片.
   */
  public static int currentPeriod() {
    LocalDateTime localDateTime = LocalDateTime.now();
    int hour = localDateTime.getHour();
    int min = localDateTime.getMinute();
    int period = hour * 12 + min / 5 - 1;
    if (period == 0) {
      period = 288;
    }
    return period;
  }

  /**
   * 获取15分钟时间片.
   */
  public static int currentPeriod15() {
    LocalDateTime localDateTime = LocalDateTime.now();
    int hour = localDateTime.getHour();
    int min = localDateTime.getMinute();
    int period15 = hour * 4 + min / 15 - 1;
    if (period15 == 0) {
      period15 = 96;
    }
    return period15;
  }

  /**
   * 获取半小时时间片.
   */
  public static int currentPeriod30() {
    return currentPeriod15() / 2;
  }

  /**
   * 获取小时时间片.
   */
  public static int currentHour() {
    return currentPeriod30() / 2;
  }

  /**
   * 获取指定日期下个月的第一天
   */
  public static String getFirstDayOfNextMonth(String dateStr, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    try {
      Date date = sdf.parse(dateStr);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.set(Calendar.DAY_OF_MONTH, 1);
      calendar.add(Calendar.MONTH, 1);
      return sdf.format(calendar.getTime());
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args) {
    String time = monthEnd(1).atStartOfDay().toString().replace("-","").substring(0, 8);
    LocalDate date = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyyMMdd"));
    LocalDate first = date.with(TemporalAdjusters.firstDayOfMonth());
    LocalDate last = date.with(TemporalAdjusters.lastDayOfMonth());
    long days = first.until(last, ChronoUnit.DAYS)+1;
    System.out.println(days);
    System.out.println(first.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    System.out.println(last.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
  }
}
