//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sutpc.its.tools.gps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * .
 */
public class GpsConvertUtils {

  static double M_PI = 3.141592653589793D;
  static double RADIUS = 6378245.0D;
  static double EE = 0.006693421622965943D;
  static double X_PI;
  static double MAX_REP;
  static double MAX_RANGE;

  public GpsConvertUtils() {
  }


  /**
   * .
   *
   * @Author chensy
   * @Description 将百度坐标系转换为火星坐标系
   * @Modified by :
   * @params
   */
  public static List<Map<String, Object>> convertGps_BaiduToGcj(List<Map<String, Object>> list,
      String xc, String yc) {
    for (int i = 0; i < list.size(); ++i) {
      Map<String, Object> map = list.get(i);
      if (map.containsKey(xc) && map.containsKey(yc)) {
        double lon = Double.parseDouble(map.get(xc).toString());
        double lat = Double.parseDouble(map.get(yc).toString());
        Map<String, Double> xy = baiduToGcj(lon, lat);
        map.put(xc, xy.get("lon"));
        map.put(yc, xy.get("lat"));
      }
    }

    return list;
  }

  /**
   * .
   *
   * @Author chensy
   * @Description 将84坐标系转换为火星坐标系
   * @Modified by :
   * @params
   */
  public static List<Map<String, Object>> convertGps_WgsToGcj(List<Map<String, Object>> list,
      String xc, String yc) {
    for (int i = 0; i < list.size(); ++i) {
      Map<String, Object> map = (Map) list.get(i);
      if (map.containsKey(xc) && map.containsKey(yc)) {
        double lon = Double.parseDouble(map.get(xc).toString());
        double lat = Double.parseDouble(map.get(yc).toString());
        Map<String, Double> xy = wgs2Gcj(lon, lat);
        map.put(xc, xy.get("lon"));
        map.put(yc, xy.get("lat"));
      }
    }

    return list;
  }

  /**
   * .
   *
   * @Author chensy
   * @Description 将百度坐标系转换为火星坐标系
   * @Modified by :
   * @params
   */
  public static Map<String, Double> baiduToGcj(double inx, double iny) {
    double x = inx - 0.0065D;
    double y = iny - 0.006D;
    double z = Math.sqrt(x * x + y * y) - 2.0E-5D * Math.sin(y * X_PI);
    double theta = Math.atan2(y, x) - 3.0E-6D * Math.cos(x * X_PI);
    Map<String, Double> map = new HashMap();
    map.put("lon", z * Math.cos(theta));
    map.put("lat", z * Math.sin(theta));
    return map;
  }

  /**
   * .
   *
   * @Author chensy
   * @Description 84坐标系转换为火星坐标系
   * @Modified by :
   * @params
   */
  public static Map<String, Double> wgs2Gcj(double inx, double iny) {
    double dy = wgs2gcjLat(inx - 105.0D, iny - 35.0D);
    double dx = wgs2gcj_lng(inx - 105.0D, iny - 35.0D);
    double radLat = iny / 180.0D * M_PI;
    double magic = Math.sin(radLat);
    magic = 1.0D - EE * magic * magic;
    double sqrtMagic = Math.sqrt(magic);
    dy = dy * 180.0D / (RADIUS * (1.0D - EE) / (magic * sqrtMagic) * M_PI);
    dx = dx * 180.0D / (RADIUS / sqrtMagic * Math.cos(radLat) * M_PI);
    Map<String, Double> map = new HashMap();
    map.put("lon", inx + dx);
    map.put("lat", iny + dy);
    return map;
  }

  /**
   * .
   *
   * @Author chensy
   * @Description 84坐标系转换
   * @Modified by :
   * @params
   */
  public static double wgs2gcjLat(double x, double y) {
    double ret1 =
        -100.0D + 2.0D * x + 3.0D * y + 0.2D * y * y + 0.1D * x * y + 0.2D * Math.sqrt(Math.abs(x));
    ret1 += (20.0D * Math.sin(6.0D * x * M_PI) + 20.0D * Math.sin(2.0D * x * M_PI)) * 2.0D / 3.0D;
    ret1 += (20.0D * Math.sin(y * M_PI) + 40.0D * Math.sin(y / 3.0D * M_PI)) * 2.0D / 3.0D;
    ret1 +=
        (160.0D * Math.sin(y / 12.0D * M_PI) + 320.0D * Math.sin(y * M_PI / 30.0D)) * 2.0D / 3.0D;
    return ret1;
  }

  /**
   * .
   *
   * @Author chensy
   * @Description 84坐标系
   * @Modified by :
   * @params
   */
  public static double wgs2gcj_lng(double x, double y) {
    double ret2 =
        300.0D + x + 2.0D * y + 0.1D * x * x + 0.1D * x * y + 0.1D * Math.sqrt(Math.abs(x));
    ret2 += (20.0D * Math.sin(6.0D * x * M_PI) + 20.0D * Math.sin(2.0D * x * M_PI)) * 2.0D / 3.0D;
    ret2 += (20.0D * Math.sin(x * M_PI) + 40.0D * Math.sin(x / 3.0D * M_PI)) * 2.0D / 3.0D;
    ret2 +=
        (150.0D * Math.sin(x / 12.0D * M_PI) + 300.0D * Math.sin(x / 30.0D * M_PI)) * 2.0D / 3.0D;
    return ret2;
  }

  static {
    X_PI = M_PI * 3000.0D / 180.0D;
    MAX_REP = 100.0D;
    MAX_RANGE = 1.0E-8D;
  }

  /**
   * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换. 即谷歌、高德 转 百度
   *
   * @params
   * @returns
   */
  public static List<Map> gcjtobd(List<Map> list) {
    double gcjLon = 0.0;
    double gcjLat = 0.0;
    List<Map> resultList = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      Map<String, Double> m = list.get(i);
      gcjLon = m.get("lng");
      gcjLat = m.get("lat");
      double z = Math.sqrt(gcjLon * gcjLon + gcjLat * gcjLat) + 0.00002 * Math.sin(gcjLat * X_PI);
      double theta = Math.atan2(gcjLat, gcjLon) + 0.000003 * Math.cos(gcjLon * X_PI);
      double bdLon = z * Math.cos(theta) + 0.0065;
      double bdLat = z * Math.sin(theta) + 0.006;
      Map<String, Object> map = new HashMap<>();
      map.put("lng", bdLon);
      map.put("lat", bdLat);
      resultList.add(map);
    }
    return resultList;
  }

}
