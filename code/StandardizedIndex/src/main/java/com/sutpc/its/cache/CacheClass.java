package com.sutpc.its.cache;

import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:22 2020/11/27.
 * @Description
 * @Modified By:
 */
@Data
public class CacheClass {

  /**
   * 全市指数曲线 earlyWarning
   */
  public static List<Map<String, Object>> getCityAndEveryDistrictTpi;

  /**
   * 实时中路段运行排名 panoramicTraffic
   */
  public static List<Map<String, Object>> getRealTimeRoadsect;

  /**
   * 实时街道运行排名（预警）earlyWarning
   */
  public static List<Map<String, Object>> getBlockRealTimeJamWarning;

  /**
   * 实时街道运行状态 earlyWarning
   */
  public static List<Map<String, Object>> getBlockRealTimeTrafficStatus;


}
