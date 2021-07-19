package com.sutpc.its.tools.system;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public class JsonMapUtils {

  /**
   * json2Map.
   */
  public static Map<String, Object> json2Map(String json) {
    return JSON.parseObject(json, Map.class);
  }

  /**
   * map2Json.
   */
  public static String map2Json(Map<String, Object> map) {
    return JSON.toJSONString(map);
  }

  /**
   * getReqParamMap.
   */
  public static Map getReqParamMap(HttpServletRequest request) {

    Map map = new HashMap();
    Enumeration paramNames = request.getParameterNames();
    while (paramNames.hasMoreElements()) {
      String paramName = (String) paramNames.nextElement();

      String[] paramValues = request.getParameterValues(paramName);
      if (paramValues.length == 1) {
        String paramValue = paramValues[0];
        if (paramValue.length() != 0) {
          map.put(paramName, paramValue);
        }
      }
    }

    return map;
  }

  /**
   * mapKeys2List.
   */
  public static List<String> mapKeys2List(Map<String, Object> map) {
    List<String> keys = new ArrayList<String>();
    Set<String> set = map.keySet();
    Iterator<String> iter = set.iterator();
    while (iter.hasNext()) {
      keys.add(iter.next().toString());
    }
    return keys;
  }
}
