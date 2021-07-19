package com.sutpc.its.tools;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @Author:chensy
 * @Date:9:32 2019/11/28
 * @Description 时间片缺失数据计算
 * @Modified By:
 */
public class DataMissUtils {

  /**
   * getPeriod15Miss.
   */
  public static List<Integer> getPeriod15Miss(List<Map<String, Object>> list, int period15) {
    /** 15分钟时间片初始化. */
    List<Integer> period15Array = new ArrayList<Integer>() {
      {
        for (int i = 1; i <= period15; i++) {
          add(i);
        }
      }
    };

    //存储缺失的时间片
    List<Integer> period15MissArray = new ArrayList<Integer>();

    for (int pp : period15Array) {
      boolean flag = false;
      for (Map<String, Object> map : list) {
        int havePeriod = 0;
        if (map.containsKey("period")) {
          havePeriod = Integer.parseInt(map.get("period") + "");
        } else if (map.containsKey("PERIOD")) {
          havePeriod = Integer.parseInt(map.get("PERIOD") + "");
        }
        if (pp != havePeriod) {
          flag = true;
        } else {
          flag = false;
          break;
        }
      }
      if (flag) {
        period15MissArray.add(pp);
      }
    }
    return period15MissArray;
  }

  /**
   * .
   *
   * @Author chensy
   * @Description 获取15分钟缺失的时间片数据
   * @Modified by :
   * @params 需检查的数据
   */
  public static List<Integer> period15Miss(List<Map<String, Object>> list, int period15) {

    /** 15分钟时间片初始化. */
    List<Integer> period15Array = new ArrayList<Integer>() {
      {
        for (int i = 1; i <= period15; i++) {
          add(i);
        }
      }
    };

    //存储缺失的时间片
    List<Integer> period15MissArray = new ArrayList<Integer>();

    int listIndex = 0;
    //15分钟
    for (int i = 0; i < period15Array.size(); i++) {
      int period = 0;

      //如果listIndex > list.size(),则说明最后一条数据缺失(主要发生在算法延迟，未入库最新时间片数据时)
      if (listIndex >= list.size()) {
        period15MissArray.add(period15Array.get(i));
        continue;
      }

      if (list.get(listIndex).containsKey("period")) {
        period = ((BigDecimal) list.get(listIndex).get("period")).intValue();
      } else if (list.get(listIndex).containsKey("PERIOD")) {
        period = ((BigDecimal) list.get(listIndex).get("PERIOD")).intValue();
      }

      //将对应索引的值进行比较,如果不相等，则说明list缺少该时间片的值
      if (period15Array.get(i) != period) {
        period15MissArray.add(period15Array.get(i));
      } else {
        listIndex++;
      }

    }

    //15分钟
    /*for (int i = 0; i < list.size(); i++) {
      //将对应索引的值进行比较,如果不相等，则说明list缺少该时间片的值
      if (period15Array.get(listIndex) != list.get(i).get("PERIOD")) {
        period15MissArray.add(period15Array.get(i));
      } else {
        listIndex++;
      }

    }*/

    return period15MissArray;

  }

}
