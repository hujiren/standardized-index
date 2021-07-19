package com.sutpc.its.controller;

import com.sutpc.its.model.HttpResult;
import com.sutpc.its.service.AppletService;
import com.sutpc.its.tools.WeixinUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/applet")
@Api(tags = "小程序接口")
public class AppletController {

  @Autowired
  private AppletService appletService;

  /**
   * 获取微信的配置信息.
   */
  @ResponseBody
  @RequestMapping(value = "/getConfig")
  public HttpResult<Object> getConfig(HttpServletRequest request, HttpServletResponse response,
      @RequestParam Map<String, Object> pmap) throws Exception {
    // getLogger().error("开始获取微信配置信息:"+request.getParameter("url"));
    Map<String, Object> resultMap = WeixinUtil.getWxConfig(request);
    // getLogger().error("微信配置resultMap:"+resultMap);
    return HttpResult.ok(resultMap);
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getCityRealTimeInfo")
  @ApiOperation(value = "全市路网实时运行", notes = "小程序")
  public HttpResult<Object> getCityRealTimeInfo(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getCityRealTimeInfo(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getKeyAreaAvgSpeed")
  @ApiOperation(value = "重点区域平均速度", notes = "小程序")
  public HttpResult<Object> getKeyAreaAvgSpeed(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getKeyAreaAvgSpeed(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getKeyCrossAvgSpeed")
  @ApiOperation(value = "重点关口平均速度", notes = "小程序")
  public HttpResult<Object> getKeyCrossAvgSpeed(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getKeyCrossAvgSpeed(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getKeyPoiINfo")
  @ApiOperation(value = "获取重点热点区域信息", notes = "小程序")
  public HttpResult<Object> getKeyPoiInfo(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getKeyPoiInfo(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getKeyPoiStatusByPoiFid")
  @ApiOperation(value = "获取指定热点实时交通运行指数", notes = "小程序")
  public HttpResult<Object> getKeyPoiStatusByPoiFid(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getKeyPoiStatusByPoiFid(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getKeyPoiSpeedByPoiFid")
  @ApiOperation(value = "获取指定热点运行交通速度趋势数据", notes = "小程序")
  public HttpResult<Object> getKeyPoiSpeedByPoiFid(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getKeyPoiSpeedByPoiFid(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getPeakTpi")
  @ApiOperation(value = "获取指数以及环比指数", notes = "小程序-掌上快报")
  public HttpResult<Object> getPeakTpi(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getPeakTpi(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getDayReportAvgSpeed")
  @ApiOperation(value = "监测日报-中心城区、全市平均速度曲线图数据", notes = "小程序-掌上日报")
  public HttpResult<Object> getDayReportAvgSpeed(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getDayReportAvgSpeed(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getDayReportAvgTpi")
  @ApiOperation(value = "监测日报-中心城区、全市平均指数曲线图数据", notes = "小程序-掌上日报")
  public HttpResult<Object> getDayReportAvgTpi(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getDayReportAvgTpi(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getEveryDistrictSpeed")
  @ApiOperation(value = "监测日报-各行政区高峰速度", notes = "小程序-掌上日报")
  public HttpResult<Object> getEveryDistrictSpeed(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getEveryDistrictSpeed(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getMajorRoadSpeed")
  @ApiOperation(value = "监测日报-主要道路速度", notes = "小程序-掌上日报")
  public HttpResult<Object> getMajorRoadSpeed(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getMajorRoadSpeed(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getMajorCrossSpeed")
  @ApiOperation(value = "监测日报-关口速速", notes = "小程序-掌上日报")
  public HttpResult<Object> getMajorCrossSpeed(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getMajorCrossSpeed(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getWeekTotalOperationData")
  @ApiOperation(value = "全市总体运行", notes = "小程序-掌上周报")
  public HttpResult<Object> getWeekTotalOperationData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getWeekTotalOperationData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getWeekBlockTpiRanking")
  @ApiOperation(value = "街道片区数据", notes = "小程序-监测周报")
  public HttpResult<Object> getWeekBlockTpiRanking(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getWeekBlockTpiRanking(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getWeekPoiSpeedRanking")
  @ApiOperation(value = "热点片区运行", notes = "小程序-热点片区运行")
  public HttpResult<Object> getWeekPoiSpeedRanking(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getWeekPoiSpeedRanking(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getWeekCrossPeak")
  @ApiOperation(value = "主要二线关运行情况", notes = "小程序-监测周报")
  public HttpResult<Object> getWeekCrossPeak(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getWeekCrossPeak(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getWeekJamRoadsectRanking")
  @ApiOperation(value = "早晚高峰拥堵路段排名", notes = "小程序-监测周报")
  public HttpResult<Object> getWeekJamRoadsectRanking(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getWeekJamRoadsectRanking(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getUpdateDataTime")
  @ApiOperation(value = "数据更新时间", notes = "小程序")
  public HttpResult<Object> getUpdateDataTime(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getUpdateDataTime(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getRoadsectOperationDistribute")
  @ApiOperation(value = "路段运行分布", notes = "小程序-重点区域")
  public HttpResult<Object> getRoadsectOperationDistribute(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(appletService.getRoadsectOperationDistribute(map));
  }
}