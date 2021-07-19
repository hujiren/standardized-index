package com.sutpc.its.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:19 2020/5/12.
 * @Description
 * @Modified By:
 */
public class GpsUrlUtils {

  /**
   * getCollectsUrl.
   */
  public static String getCollectsUrl(String districtFid, String type) {
    if ("transit".equals(type)) {
      String baseUrl = "http://10.10.180.19:8184/gps/bus/getCollects/";
      return baseUrl + districtFid;
    } else {
      Map<String, String> mapDistrict = new HashMap<>();
      mapDistrict.put("111", "all");
      mapDistrict.put("1", "futian");//福田区
      mapDistrict.put("2", "luohu");
      mapDistrict.put("3", "nanshan");
      mapDistrict.put("4", "yantian");
      mapDistrict.put("5", "baoan");
      mapDistrict.put("6", "longgang");
      mapDistrict.put("7", "guangming");
      mapDistrict.put("8", "pingshan");
      mapDistrict.put("9", "longhua");
      mapDistrict.put("10", "dapeng");
      String baseUrl = "http://10.10.180.19/deepview-web-gpsdata/gps/getCollects/";
      baseUrl = baseUrl + mapDistrict.get(districtFid) + "/";
      Map<String, String> mapType = new HashMap<>();
      mapType.put("coachBus", "tpCoachBus");//  班车客运
      mapType.put("taxi", "tpTaxi");// 出租（非空重车）
      mapType.put("freight", "tpFreight");// 货运
      mapType.put("driving", "tpDriving");// 驾培
      mapType.put("charterBus", "tpCharterBus");// 包车客运
      mapType.put("transit", "tpTransit");// 公交（数据少，不使用）
      mapType.put("dangerous", "tpDangerous");// 危险品
      mapType.put("dumper", "tpDumper");// 泥头车
      mapType.put("other", "tpOthers");// 其他
      baseUrl = baseUrl + mapType.get(type);
      return baseUrl;
    }
  }

  /**
   * getCollectsByRangeUrl.
   */
  public static String getCollectsByRangeUrl(String districtFid, String type) {
    Map<String, String> mapDistrict = new HashMap<>();
    mapDistrict.put("111", "all");
    // 福田区
    mapDistrict.put("1", "futian");
    mapDistrict.put("2", "luohu");
    mapDistrict.put("3", "nanshan");
    mapDistrict.put("4", "yantian");
    mapDistrict.put("5", "baoan");
    mapDistrict.put("6", "longgang");
    mapDistrict.put("7", "guangming");
    mapDistrict.put("8", "pingshan");
    mapDistrict.put("9", "longhua");
    mapDistrict.put("10", "dapeng");
    String baseUrl = "http://10.10.180.19/deepview-web-gpsdata/gps/getCollectsByRange/";
    baseUrl = baseUrl + mapDistrict.get(districtFid) + "/";
    Map<String, String> mapType = new HashMap<>();
    mapType.put("coachBus", "tpCoachBus");//  班车客运
    mapType.put("taxi", "tpTaxi");// 出租（非空重车）
    mapType.put("freight", "tpFreight");// 货运
    mapType.put("driving", "tpDriving");// 驾培
    mapType.put("charterBus", "tpCharterBus");// 包车客运
    mapType.put("transit", "tpTransit");// 公交（数据少，不使用）
    mapType.put("dangerous", "tpDangerous");// 危险品
    mapType.put("dumper", "tpDumper");// 泥头车
    mapType.put("other", "tpOthers");// 其他
    baseUrl = baseUrl + mapType.get(type);
    return baseUrl;
  }
}
