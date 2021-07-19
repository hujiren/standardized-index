package com.sutpc.its.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComparativeAnalysisService {

  @Autowired
  private DataQueryService dataQueryService;

  /**
   * 对比分析-折线图.
   *
   * @param map 参数为空,AOP注入time,period
   */
  public Map<String, Object> getComparativeAnalysisData(Map<String, Object> map) {
    String index = map.get("index").toString();
    String type = map.get("type").toString();
    Map<String, Object> result = new HashMap<String, Object>();
    switch (type) {
      case "district":
        switch (index) {
          case "tpi":
          case "avg_speed":
          case "jam_length_ratio":
          case "jam_space_time":
          case "avg_jam_length":
            result = dataQueryService.getCityQueryData(map);
            break;
          case "bus_speed":
          case "speed_rate":
            result = dataQueryService.getAreaCheckData(map);
            break;
          default:
            break;
        }
        break;
      case "block":
        switch (index) {
          case "tpi":
          case "avg_speed":
          case "jam_length_ratio":
          case "jam_space_time":
          case "avg_jam_length":
            result = dataQueryService.getBlockQueryData(map);
            break;
          case "bus_speed":
          case "speed_rate":
            result = dataQueryService.getAreaCheckData(map);
            break;
          default:
            break;
        }
        break;
      case "subsect":
        switch (index) {
          case "tpi":
          case "avg_speed":
          case "jam_length_ratio":
          case "jam_space_time":
          case "avg_jam_length":
            result = dataQueryService.getSubsectQueryData(map);
            break;
          case "bus_speed":
          case "speed_rate":
            result = dataQueryService.getAreaCheckData(map);
            break;
          default:
            break;
        }
        break;
      case "poi":
        result = dataQueryService.getPoiQueryData(map);
        break;
      case "road":
        result = dataQueryService.getRoadQueryData(map);
        break;
      case "roadsect":
        result = dataQueryService.getRoadsectQueryData(map);
        break;
      case "cross":
        result = dataQueryService.getCrossQueryData(map);
        break;
      case "intersection":
        result = dataQueryService.getIntersectionData(map);
        break;
      case "roadsection":
        result = dataQueryService.getRoadSectionFlow(map);
        break;
      case "buslane":
        result = dataQueryService.getBuslaneCheckData(map);
        break;
      default:
        result = null;
        break;
    }
    return result;
  }

  /**
   * 对比分析-列表信息.
   *
   * @param map 参数为空,AOP注入time,period
   */
  public Map<String, Object> getComparativeAnalysisList(Map<String, Object> map) {
    String a = map.get("calender_road_type_fid").toString();
    map.put("timeprecision", "fifteen_min");
    map.put("timeproperty", "user_defined");
    map.put("starttime", "00:00");
    map.put("endtime", "23:59");
    List<Map<String, Object>> list = (List<Map<String, Object>>) getComparativeAnalysisData(map)
        .get("data");
    String index = map.get("index").toString();
    double morningvalue = getAverageY(list, 29, 36, index);
    double eveningvalue = getAverageY(list, 71, 78, index);
    double dayvalue = getAverageY(list, 37, 70, index);
    double totaldayvalue = getAverageY(list, 1, 96, index);

    Map<String, Object> result = new HashMap<String, Object>();
    result.put("morningvalue", morningvalue);
    result.put("eveningvalue", eveningvalue);
    result.put("dayvalue", dayvalue);
    result.put("totaldayvalue", totaldayvalue);
    return result;
  }

  /**
   * .
   */
  public double getAverageY(List<Map<String, Object>> list, int startindex, int endindex,
      String index) {
    double y = 0;
    if (list != null && list.size() != 0) {
      int kong = 0;
      for (int i = startindex - 1; i < endindex; i++) {
        if (Integer.parseInt(list.get(i).get("x").toString()) != i + 1) {
          kong++;
          continue;
        }
        y += Double.parseDouble(list.get(i).get("y").toString());
      }
      //y=y/(endindex-startindex+1-kong);
      y = (double) Math.round(y / (endindex - startindex + 1 - kong) * 100) / 100;
      if (index.equals("jam_length_ratio")) {
        y = (double) Math.round(y / (endindex - startindex + 1 - kong) * 10) / 10;
      }
    }
    return y;
  }

}
