package com.sutpc.its.tools.gps;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * .
 */
@Slf4j
public class GeoJsonUtils {

  /**
   * .
   */
  public static List<Map> setDistrictListByTwoMulti(String district) {

    String districtInfo = district;
    List<Map> points = null;

    try {
      /*获取边界信息*/
      points = new ArrayList<Map>();
      JSONObject jsonObject = JSONObject.parseObject(districtInfo);
      log.info("jsonObj:{}", jsonObject.toJSONString());
      JSONArray features = jsonObject.getJSONArray("features");
      for (int i = 0; i < features.size(); i++) {
        JSONObject feature = features.getJSONObject(i);
        JSONObject geometry = feature.getJSONObject("geometry");
        JSONArray coordinates = geometry.getJSONArray("coordinates");

        for (int j = 0; j < coordinates.size(); j++) {
          Map<String, Object> pointMap = new HashMap<String, Object>();
          JSONArray point = coordinates.getJSONArray(j);
          pointMap.put("lng", Double.valueOf(point.getString(0)));
          pointMap.put("lat", Double.valueOf(point.getString(1)));
          points.add(pointMap);
        }
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }

    return points;
  }
}
