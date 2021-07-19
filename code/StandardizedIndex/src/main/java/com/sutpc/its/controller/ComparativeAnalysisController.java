package com.sutpc.its.controller;

import com.sutpc.its.model.HttpResult;
import com.sutpc.its.service.ComparativeAnalysisService;
import com.sutpc.its.service.DataQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/comparativeanalysis")
@Api(tags = "对比分析")
public class ComparativeAnalysisController {

  @Autowired
  private ComparativeAnalysisService comparativeAnalysisService;
  @Autowired
  private DataQueryService dataQueryService;

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getComparativeAnalysisData")
  @ApiModelProperty(value = "对比分析-折线图", notes = "对比分析-折线图")
  public HttpResult<Object> getComparativeAnalysisData(@RequestParam Map<String, Object> map) {
    map = getparam(map);
    return HttpResult.ok(getdata(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getComparativeAnalysisList")
  @ApiModelProperty(value = "对比分析-列表信息", notes = "对比分析-列表信息")
  public HttpResult<Object> getComparativeAnalysisList(@RequestParam Map<String, Object> map) {
    map = getparam(map);
    map.put("timeprecision", "year");
    map.put("timeproperty", "user_defined");

    Map<String, Object> paramap1 = new HashMap<String, Object>();
    Map<String, Object> paramap2 = new HashMap<String, Object>();
    Map<String, Object> paramap3 = new HashMap<String, Object>();
    paramap1.putAll(map);
    paramap2.putAll(map);
    paramap3.putAll(map);
    Map<String, Object> paramap4 = new HashMap<String, Object>();
    paramap4.putAll(map);

    paramap1.put("starttime", "00:00");
    paramap1.put("endtime", "23:59");
    List<Map<String, Object>> list = (List<Map<String, Object>>) getdata(paramap1).get("data");
    double value = Double.parseDouble(((Map<String, Object>) list.get(0)).get("y").toString());
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("totaldayvalue", value);

    paramap2.put("starttime", "07:00");
    paramap2.put("endtime", "09:00");
    list = (List<Map<String, Object>>) getdata(paramap2).get("data");
    value = Double.parseDouble(((Map<String, Object>) list.get(0)).get("y").toString());
    result.put("morningvalue", value);

    paramap3.put("starttime", "17:30");
    paramap3.put("endtime", "19:30");
    list = (List<Map<String, Object>>) getdata(paramap3).get("data");
    value = Double.parseDouble(((Map<String, Object>) list.get(0)).get("y").toString());
    result.put("eveningvalue", value);

    paramap4.put("starttime", "09:00");
    paramap4.put("endtime", "17:30");
    list = (List<Map<String, Object>>) getdata(paramap4).get("data");
    value = Double.parseDouble(((Map<String, Object>) list.get(0)).get("y").toString());
    result.put("dayvalue", value);

    return HttpResult.ok(result);
  }

  /**
   * .
   */
  public Map<String, Object> getparam(Map<String, Object> map) {
    String statistics = map.get("statistics").toString();
    switch (statistics) {
      case "year":
        String year = map.get("date").toString();
        map.put("startdate", Integer.parseInt(year + "0101"));
        map.put("enddate", Integer.parseInt(year + "1231"));
        break;
      case "month":
        String month = map.get("date").toString();
        Date date = new Date();
        try {
          date = new SimpleDateFormat("yyyyMM").parse(month);
        } catch (ParseException e) {
          e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int monthendday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        map.put("startdate", Integer.parseInt(month + "01"));
        map.put("enddate", Integer.parseInt(month + monthendday));
        break;
      case "day":
        String day = map.get("date").toString();
        map.put("startdate", Integer.parseInt(day));
        map.put("enddate", Integer.parseInt(day));
        break;
      default:
        break;
    }
    return map;
  }

  /**
   * .
   */
  public Map<String, Object> getdata(Map<String, Object> map) {
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


  /*@ResponseBody
  @PostMapping(value = "getComparativeAnalysisList")
  @ApiModelProperty(value = "对比分析-列表信息", notes = "对比分析-列表信息")
  public HttpResult<Object> getComparativeAnalysisList(@RequestParam Map<String, Object> map) {
    map.put("timeprecision", "fifteen_min");
    map.put("timeproperty", "user_defined");
    map.put("starttime", "00:00");
    map.put("endtime", "23:59");
    List<Map<String, Object>> list = (List<Map<String, Object>>) getdata(map).get("data");
    double[] value = new double[4];
    value[0] = getAverageY(list, 29, 36, map);
    value[1] = getAverageY(list, 71, 78, map);
    value[2] = getAverageY(list, 37, 70, map);
    value[3] = getAverageY(list, 1, 96, map);
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("morningvalue", value[0]);
    result.put("eveningvalue", value[1]);
    result.put("dayvalue", value[2]);
    result.put("totaldayvalue", value[3]);
    return HttpResult.ok(result);
  }

  public double getAverageY(List<Map<String, Object>> list, int startindex, int endindex,
      Map<String, Object> map) {
    double y = 0;
    int listnum = 0;
    if (list != null && list.size() > 0) {
      for (Map<String, Object> listli : list) {
        int listindex = Integer.parseInt(listli.get("x").toString());
        if (listindex >= startindex) {
          if (listindex <= endindex) {
            y += Double.parseDouble(listli.get("y").toString());
            listnum++;
          } else {
            break;
          }
        }
      }
    }
    if (map.get("index").toString().equals("jam_length_ratio")) {
      y = (double) Math.round(y / listnum * 1000) / 1000;
    } else {
      y = (double) Math.round(y / listnum * 100) / 100;
    }
    return y;
  }*/


}
