package com.sutpc.its.tools.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeUtils {

	/**
	 * 字符串转时间
	 */
	public static Date String2Time(String timeStr) {
		return String2Time(timeStr, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 字符串转时间
	 */
	public static Date String2Time(String timeStr, String formatStr) {

		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);

		try {
			return sdf.parse(timeStr);

		} catch (Exception e) {

			return null;
		}
	}

	/**
	 * linux时间戳转日期
	 */
	public static Date TimeStamp2Date(String timestampString) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		return new Date(timestamp);

	}

	/**
	 * 当前时间字符串
	 */
	public static String GetCurrentTimeString() {
		return GetCurrentTimeString("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 当前Unix时间戳-毫秒
	 */
	public static long GetCurrentTimeStampMillis() {
		return System.currentTimeMillis();
	}
	
	/**
	 * 当前Unix时间戳-秒
	 */
	public static long GetCurrentTimeStampSeconds() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 当前时间字符串
	 */
	public static String GetCurrentTimeString(String format) {
		return new SimpleDateFormat(format).format(System.currentTimeMillis());
	}

	/**
	 * 获取星期几，星期一返回1，星期日返回0
	 */
	public static int getDayofWeek() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;

	}

	/**
	 * 获取星期几，星期一返回1，星期日返回0
	 */
	public static int getDayofWeek(String date) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		Date d = null;
		try {
			d = sd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		int currentdayofweek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		return currentdayofweek;// 周一=1，周日=0
	}

	/**
	 * 星期几的中文
	 */
	public static String getDayDesofWeek(String date) {
		int day = getDayofWeek(date);
		String[] weekdays = new String[] { "日", "一", "二", "三", "四", "五", "六" };
		return weekdays[day];
	}

	/**
	 * 获取该天的农历日期（中文）
	 */
	public static String getDayofLunar(String date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(String2Time(date, "yyyyMMdd"));

		LunarUtils lunar = new LunarUtils(calendar);

		return LunarUtils.getChinaDayString(lunar.day);
	}

	/**
	 * 获取该日期当月有多少天
	 */
	public static int getDaysofMonth(String date) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(String2Time(date, "yyyyMMdd"));

		return calendar.getActualMaximum(Calendar.DATE);

	}

	/**
	 * 获取时间为几分钟前
	 */
	public static Integer getMinsBefore(String timestr) {
		return getMinsMinus(String2Time(timestr), new Date());
	}

	/**
	 * 获取时间为几分钟前
	 */
	public static Integer getMinsBefore(Date time) {
		return getMinsMinus(time, new Date());
	}

	/**
	 * 获取时间为多少秒前
	 */
	public static Integer getSecsBefore(String timestr) {
		return getSecsMinus(String2Time(timestr), new Date());
	}

	/**
	 * 获取时间为多少秒前
	 */
	public static Integer getSecsBefore(Date time) {
		return getSecsMinus(time, new Date());
	}

	/**
	 * 获取时间差-分钟
	 */
	public static Integer getMinsMinus(Date timeStart, Date timeEnd) {
		if (timeStart == null || timeEnd == null) {
			return null;
		}
		long temp = timeEnd.getTime() - timeStart.getTime(); // 相差毫秒数

		long mins = temp / 1000 / 60; // 相差分钟数

		return (int) mins;
	}

	/**
	 * 获取时间差-秒
	 */
	public static Integer getSecsMinus(Date timeStart, Date timeEnd) {
		if (timeStart == null || timeEnd == null) {
			return null;
		}
		long temp = timeEnd.getTime() - timeStart.getTime(); // 相差毫秒数

		long secs = temp / 1000;

		return (int) secs;
	}

	/**
	 * 获取时间差-分钟
	 */
	public static Integer getMinsMinus(String timeStart, String timeEnd) {
		return getMinsMinus(String2Time(timeStart), String2Time(timeEnd));
	}

	/**
	 * 获取时间差-秒
	 */
	public static Integer getSecsMinus(String timestrStart, String timestrEnd) {
		return getSecsMinus(String2Time(timestrStart), String2Time(timestrEnd));
	}
	
	
	public static Date addSecsToTime(String time,int secs){
		Date date = String2Time(time);		
		return addSecsToTime(date,secs);
	}
	public static Date addSecsToTime(Date date,int secs){
		date.setTime(date.getTime() + secs*1000);
		return date;
	}
	
	

	/**
     * 为Unix时间戳（秒）转换日期
     *
     * @param time,   时间戳
     * @param format, 日期格式，如"yyyy-MM-dd HH:mm:ss"
     * @return date, 日期
     */
    public static String UnixTimestampToDate(long time, String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(time * 1000);
    }
    
    public static String GetCurrentTimeMillisString(){
		return GetCurrentTimeString("yyyyMMddHHmmssSSS");
	}
}
