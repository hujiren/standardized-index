package com.sutpc.its.controller;

import com.sutpc.its.model.HttpResult;
import com.sutpc.its.service.ReportService;
import com.sutpc.its.tools.GeoserverUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/report")
@Api(tags = "监测报告")
public class ReportController {

  @Value("${tpi.waterImagePath}")
  private String waterImagePath;

  @Value("${tpi.tpiBlockWaterImagePath}")
  private String blockWaterImagePath;

  @Value("${tpi.tpiMainRoadWaterImagePath}")
  private String mainRoadWaterImagePath;

  @Value("${tpi.tpiParkWaterImagePath}")
  private String parkWaterImagePath;

  @Value("${tpi.geoserverUrl}")
  private String geoserverUrl;

  @Autowired
  private ReportService reportService;

  /**
   * .
   */
  @ResponseBody
  @RequestMapping("/getMonthReportData")
  @ApiModelProperty(value = "月报数据源", notes = "监测报告")
  public HttpResult<Object> getMonthReportData(@RequestParam Map<String, Object> map) {
    Object date = map.get("date");
    int year = Integer.parseInt(date.toString().substring(0, 4));
    int month = Integer.parseInt(date.toString().substring(4, 6));
    reportService.getMonthReportData(map);
    return reportService.getMonthReportData(map);
  }

  @ResponseBody
  @PostMapping(value = "/getWeekReportData")
  @ApiModelProperty(value = "周报数据", notes = "监测报告")
  public HttpResult<Object> getWeekReportData(@RequestParam Map<String, Object> map) {
    return reportService.getWeekReportData(map);
  }

  @ResponseBody
  @PostMapping("/getMonthReportDoc")
  public HttpResult<Object> getMonthReportDoc(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(reportService.getMonthReportDoc(map));
  }

  @ResponseBody
  @RequestMapping("/getWeekReportDoc")
  public HttpResult<Object> getWeekReportDoc(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(reportService.getWeekReportDoc(map));
  }

  /**
   * 根據地圖服務，返回給前端對應的地圖服務出的圖片-月報.
   */
  @ResponseBody
  @RequestMapping("/getMonthImages")
  public void getMonthImages(@RequestParam Map<String, Object> map, HttpServletResponse response) {
    String type = map.get("type") + "";
    String year = map.get("year") + "";
    String month = map.get("month") + "";
    GeoserverUtils.makeImages(response,
        GeoserverUtils.getMonthImages(type, year, month, geoserverUrl, waterImagePath));
  }

  /**
   * 根據地圖服務，返回給前端對應的地圖服務出的圖片-月報 深圳定制.
   */
  @ResponseBody
  @RequestMapping("/getSzMonthImages")
  public void getSzMonthImages(@RequestParam Map<String, Object> map,
      HttpServletResponse response) {
    map.put("blockWaterImagePath", blockWaterImagePath);
    map.put("mainRoadWaterImagePath", mainRoadWaterImagePath);
    map.put("parkWaterImagePath", parkWaterImagePath);
    GeoserverUtils
        .makeImages(response, GeoserverUtils.getSzWordImages(map, geoserverUrl, response));
  }

  /**
   * 根據前端請求，返回給前端對應的地圖服務出的圖片-週報.
   */
  @ResponseBody
  @RequestMapping("/getWeekImages")
  public void getWeekImages(@RequestParam Map<String, Object> map, HttpServletResponse response) {
    System.out.println("获取到的服务器地址：" + geoserverUrl);
    String type = map.get("type") + "";
    String year = map.get("year") + "";
    String week = map.get("week") + "";
    GeoserverUtils.makeImages(response,
        GeoserverUtils.getWeekImages(type, year, week, geoserverUrl, waterImagePath));

  }

  @ResponseBody
  @PostMapping(value = "setSpecialReportInfo")
  @ApiModelProperty(value = "专题报告信息")
  public HttpResult<Object> setSpecialReportInfo(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(reportService.setSpecialReportInfo(map));
  }

  @ResponseBody
  @PostMapping(value = "getSpecialReportInfo")
  @ApiModelProperty(value = "获取专题报告列表")
  public HttpResult<Object> getSpecialReportInfo(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(reportService.getSpecialReportInfo(map));
  }

  @ResponseBody
  @PostMapping(value = "updateSpecialReportInfo")
  @ApiModelProperty(value = "修改专题报告")
  public HttpResult<Object> updateSpecialReportInfo(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(reportService.updateSpecialReportInfo(map));
  }

  @ResponseBody
  @PostMapping(value = "deleteSpecialReportInfo")
  @ApiModelProperty(value = "删除专题报告")
  public HttpResult<Object> deleteSpecialReportInfo(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(reportService.deleteSpecialReportInfo(map));
  }

  @ResponseBody
  @PostMapping(value = "getSubmitReportInfo")
  @ApiModelProperty(value = "获取专题报告列表(送审)")
  public HttpResult<Object> getSubmitReportInfo(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(reportService.getSubmitReportInfo(map));
  }
}
