package com.sutpc.its.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * .
 *
 * @Author:chensy
 * @Date:
 * @Description excel文件字段排序模板
 * @Modified By:
 */
public class FileColumnIndexConstants {

  /**
   * 交通日历excel专用.
   */
  public static List<String> keysForTrafficCalendar = new ArrayList<String>() {
    {
      add("Month");
      add("Day");
      add("Lunar-Month");
      add("Lunar");
      add("Weekday");
      add("Workday");
      add("SPEED");
      add("EXPONENT");
      add("WeatherFlag");
    }
  };

  public static List<String> keysForCityDistrictRoadsect = new ArrayList<String>() {
    {
      add("FNAME");
      add("FID");
      add("x");
      add("y");
    }
  };

  public static List<String> keysForRoadsectQueryData = new ArrayList<String>() {
    {
      add("FNAME");
      add("FID");
      add("ROADSECT_FROM");
      add("ROADSECT_TO");
      add("x");
      add("y");
    }
  };

  public static List<String> keysForHighSpeedQueryData = new ArrayList<String>() {
    {
      add("FNAME");
      add("FID");
      add("ROADSECT_FROM");
      add("ROADSECT_TO");
      add("x");
      add("y");
      add("TPI");
    }
  };

  public static List<String> keysForRoadSectionFlow = new ArrayList<String>() {
    {
      add("FNAME");
      add("x");
      add("y");
    }
  };

  public static List<String> keysForCrossQueryData = new ArrayList<String>() {
    {
      add("FID");
      add("FNAME");
      add("x");
      add("y");

    }
  };

  public static List<String> keysForJamSpaceTimeData = new ArrayList<String>() {
    {
      add("name");
      add("dir");
      add("from");
      add("to");
      add("type");
      add("x");
      add("y");
    }
  };

  public static List<String> keysForJamRoadsectRankingJamTimeRate = new ArrayList<String>() {
    {
      add("ROADSECT_FNAME");
      add("ROADSECT_FID");
      add("ROAD_TYPE_FID");
      add("DISTRICT_FNAME");
      add("DIR_FNAME");
      add("ROADSECT_FROM");
      add("ROADSECT_TO");
      add("JAM_SPEED");
      add("ALLPERIOD");
      add("SPEED");
      add("JAM_TIME_RATE");
    }
  };

  public static List<String> keysForJamRoadsectRankingTpi = new ArrayList<String>() {
    {
      add("ROADSECT_FNAME");
      add("ROADSECT_FID");
      add("ROAD_TYPE_FID");
      add("DISTRICT_FNAME");
      add("DIR_FNAME");
      add("ROADSECT_FROM");
      add("ROADSECT_TO");
      add("SAMPLE_VEHICLE");
      add("SPEED");
      add("TPI");
    }
  };

  public static List<String> keysForRoadWeekChange = new ArrayList<String>() {
    {
      add("time");
      add("period");
      add("tpi");
      add("speed");
    }
  };

  public static List<String> keysForParkingTollRoadData = new ArrayList<String>() {
    {
      add("DISTRICT_FNAME");
      add("SPEED");
    }
  };
}
