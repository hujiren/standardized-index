package com.sutpc.its.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * .
 *
 * @Author:chensy
 * @Date:
 * @Description 文件名常量类
 * @Modified By:
 */
public class FileNameConstant {

  /**
   * 查询范围信息.
   */
  public static Map<String, Object> rangeMap = new HashMap<String, Object>() {
    {
      put("city", "全市");
      put("district", "行政区");
      put("block", "街道");
      put("subsect", "交通小区");
      put("poi", "热点");
      put("road", "道路");
      put("roadsect", "路段");
      put("highspeed", "全省高速");
      put("check_line", "核查线");
      put("buslane", "公交专用道");
      put("zone", "片区");
      put("cross", "二线关");
      put("roadsection", "道路断面");
      put("intersection", "交叉口");
    }
  };

  /**
   * 指标信息.
   */
  public static Map<String, Object> indexMap = new HashMap<String, Object>() {
    {
      put("tpi", "指数");
      put("avg_speed", "平均速度");
      put("jam_length_ratio", "拥堵里程比例");
      put("jam_space_time", "拥堵时空值");
      put("avg_jam_length", "平均拥堵里程");
      put("bus_speed", "公交速度");
      put("speed_rate", "速度比");
      put("flow", "流量");
      put("flow_hour_coefficient", "流量小时系数");
      put("delay", "延误");
      put("check_line", "流量");
    }
  };

}
