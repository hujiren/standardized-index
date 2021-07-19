package com.sutpc.its.controller;

import com.sutpc.its.annotation.*;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.service.MonitorEarlyWarningService;
import com.sutpc.its.vo.CongestionWarnRealVo;
import com.sutpc.its.vo.RoadsectChartInfoVo;
import com.sutpc.its.vo.RoadsectMapVolumeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 监测预警控制器.
 */
@RestController
@RequestMapping(value = "/earlyWarning")
@Scanning
@Api(tags = "监测预警模块")
public class MonitorEarlyWarningController {

  @Autowired
  private MonitorEarlyWarningService earlyWarningService;


  @ResponseBody
  @PostMapping(value = "getCityAndEveryDistrictTpi")
  @ApiOperation(value = "全市概况-全市和各行政区指数变化", notes = "全市概况")
  @Requirement(value = "获取全市和各行政区全天指数变化数据，默认按照15分钟时间片来统计。支持切换日期，查看历史数据。")
  @Input(value = "时间日期")
  @Output(value = "行政区id、时间片（15分钟）、指数、行政区名称")
  @Handle
  @Parameter(value = "time")
  public HttpResult<Object> getCityAndEveryDistrictTpi(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getCityAndEveryDistrictTpi(map));
  }

  @ResponseBody
  @PostMapping(value = "getRealTimeTrafficOperationRanking")
  @ApiOperation(value = "全市概况-区域实时交通运行情况排名", notes = "全市概况")
  @Requirement(value = "获取全市各行政区域实时指数和速度情况，并且按照指数排名。指数越大排名靠前。")
  @Input
  @Output(value = "行政区id，速度，时间片，指数，行政区名称")
  @Handle
  @Parameter
  public List<Map<String, Object>> getRealTimeTrafficOperationRanking(
      @RequestParam Map<String, Object> map) {
    return earlyWarningService.getRealTimeTrafficOperationRanking(map);
  }

  @ResponseBody
  @PostMapping(value = "getDistrictCarOrBusData")
  @ApiOperation(value = "全市概况-行政区公交速度曲线", notes = "公交速度")
  @Requirement(value = "获取全市和各行政区公交运行速度变化")
  @Input(value = "时间日期、行政区id，车辆类型")
  @Output(value = "时间片、速度")
  @Handle
  @Parameter(value = "time,district_fid,vehicle_type")
  public HttpResult<Object> getDistrictCarOrBusData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getDistrictCarOrBusData(map));
  }

  @ResponseBody
  @PostMapping(value = "getRealtimeBusOperationRanking")
  @ApiOperation(value = "全市概况-区域实时公交运行排名", notes = "公交速速")
  @Requirement(value = "获取行政区实时公交运行排名，按照公交速度从小到大排序。")
  @Input
  @Output(value = "行政区id、速度比、公交速度、小汽车速度、行政区名称")
  @Handle
  @Parameter
  public HttpResult<Object> getRealtimeBusOperationRanking(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getRealtimeBusOperationRanking(map));
  }

  @ResponseBody
  @PostMapping(value = "getBuslaneRealTimeOperationStatus")
  @ApiOperation(value = "全市概况-专用道地图数据", notes = "公交速度")
  @Requirement(value = "获取公交专用道的实时运行信息，并分布在地图中展示，并按照指数等级来分配对应颜色。")
  @Input
  @Output(value = "拥堵状态、速度比、专用道id、时间片、方向、小汽车速度、公交车速度、专用道路名")
  @Handle
  @Parameter
  public HttpResult<Object> getBuslaneRealTimeOperationStatus(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getBuslaneRealTimeOperationStatus(map));
  }

  @ResponseBody
  @PostMapping(value = "getSubsectRealTimeJamWarning")
  @ApiOperation(value = "片区路况-实时拥堵预警列表", notes = "交通小区")
  @Requirement(value = "获取全市或指定行政区交通小区实时拥堵情况。")
  @Input
  @Output(value = "速度、指数、时间片、交通小区编号、交通小区名称、交通运行状态")
  @Handle
  @Parameter
  public HttpResult<List<Map<String, Object>>> getSubsectRealTimeJamWarning(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getSubsectRealTimeJamWarning(map));
  }

  @ResponseBody
  @PostMapping(value = "getSpeedAndTpiInfoBySubsectfid")
  @ApiOperation(value = "片区路况-交通小区-指数与速度曲线信息", notes = "交通小区")
  @Requirement(value = "获取指定交通小区一天的指数和速度的变化情况，按15分钟时间片统计。")
  @Input(value = "交通小区id")
  @Output(value = "时间片、速度、指数")
  @Handle
  @Parameter
  public HttpResult<Map<String, Object>> getSpeedAndTpiInfoBySubsectfid(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getSpeedAndTpiInfoBySubsectfid(map));
  }

  @ResponseBody
  @PostMapping(value = "getSubsectRealTimeTrafficStatus")
  @ApiOperation(value = "片区路况-交通小区-地图-交通小区实时交通状态", notes = "交通小区")
  @Requirement(value = "获取交通小区实时交通状态（也可以传入时间参数，查看历史时间片）。")
  @Input(value = "开始时间、结束时间")
  @Output(value = "时间片、速度、交通运行状态、交通小区id、指数、交通小区名称")
  @Handle
  @Parameter(value = "startQueryTime,endQueryTime")
  public HttpResult<Map<String, Object>> getSubsectRealTimeTrafficStatus(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getSubsectRealTimeTrafficStatus(map));
  }

  @ResponseBody
  @PostMapping(value = "getPoiRealTimeJamWarning")
  @ApiOperation(value = "片区路况-热点区域-实时拥堵预警列表", notes = "热点区域")
  @Requirement(value = "获取热点区域实时拥堵预警列表。展示热点指数、速度、状态等信息。")
  @Input(value = "展示数据量、查询类别")
  @Output(value = "速度、时间片、指数、热点id、热点名称")
  @Handle
  @Parameter(value = "isJam,dataNum")
  public HttpResult<List<Map<String, Object>>> getPoiRealTimeJamWarning(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getPoiRealTimeJamWarning(map));
  }

  @ResponseBody
  @PostMapping(value = "getSpeedAndTpiInfoByPoifid")
  @ApiOperation(value = "片区路况-热点区域-指数与速度曲线信息", notes = "热点区域")
  @Requirement(value = "获取热点区域的一天的指数与速度变化信息，按照15分钟时间片来统计。")
  @Input(value = "热点区域id")
  @Output(value = "时间片、指数、速度")
  @Handle
  @Parameter(value = "poi_fid")
  public HttpResult<Map<String, Object>> getSpeedAndTpiInfoByPoifid(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getSpeedAndTpiInfoByPoifid(map));
  }

  @ResponseBody
  @PostMapping(value = "getPoiRealTimeTrafficStatus")
  @ApiOperation(value = "片区路况-热点区域-地图-热点区域实时交通状态", notes = "热点区域")
  @Requirement(value = "获取热点区域实时交通状态（支持传参时间范围查看历史时间片的状态）。")
  @Input(value = "开始时间、结束时间")
  @Output(value = "时间片、指数、速度")
  @Handle
  @Parameter(value = "startQueryTime,endQueryTime")
  public HttpResult<Map<String, Object>> getPoiRealTimeTrafficStatus(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getPoiRealTimeTrafficStatus(map));
  }

  @ResponseBody
  @PostMapping(value = "getBlockRealTimeJamWarning")
  @ApiOperation(value = "片区路况-街道-实时拥堵预警列表", notes = "街道")
  @Requirement(value = "获取街道实时运行状态列表，并以指数从打到小排列。")
  @Input(value = "展示数据量、查询类别")
  @Output(value = "速度、时间片、指数、街道所在行政区id、行政区名称、街道id、街道名称")
  @Handle
  @Parameter(value = "isJam,dataNum")
  public HttpResult<Object> getBlockRealTimeJamWarning(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getBlockRealTimeJamWarning(map));
  }

  @ResponseBody
  @PostMapping(value = "getSpeedAndTpiInfoByBlockfid")
  @ApiOperation(value = "片区路况-街道-指数与速度曲线信息", notes = "街道")
  @Requirement(value = "获取街道的一天的指数与速度变化信息，按照15分钟时间片来统计。")
  @Input(value = "热点区域id")
  @Output(value = "时间片、指数、速度")
  @Handle
  @Parameter(value = "poi_fid")
  public HttpResult<Object> getSpeedAndTpiInfoByBlockfid(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getSpeedAndTpiInfoByBlockfid(map));
  }

  @ResponseBody
  @PostMapping(value = "getBlockRealTimeTrafficStatus")
  @ApiOperation(value = "片区路况-街道-地图-街道实施交通状态", notes = "街道")
  @Requirement(value = "获取街道实时交通状态（支持传参时间范围查看历史时间片的状态）。")
  @Input(value = "开始时间、结束时间")
  @Output(value = "时间片、指数、速度")
  @Handle
  @Parameter(value = "startQueryTime,endQueryTime")
  public HttpResult<Object> getBlockRealTimeTrafficStatus(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getBlockRealTimeTrafficStatus(map));
  }

  @ResponseBody
  @PostMapping(value = "getRoadsectRealTimeJamWarning")
  @ApiOperation(value = "道路路况-市内路况-实时拥堵预警", notes = "市内路况")
  @Requirement(value = "获取市内中路段预警消息，并以指数从大到小的顺序排列。")
  @Input(value = "展示数据量、查询类别")
  @Output(value = "速度、指数、中路段id，中路段起点，中路段终点，")
  @Handle
  @Parameter(value = "isJam,dataNum")
  public HttpResult<List<Map<String, Object>>> getRoadsectRealTimeJamWarning(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getRoadsectRealTimeJamWarning(map));
  }

  @ResponseBody
  @PostMapping(value = "getSpeedAndTpiInfoByRoadsectfid")
  @ApiOperation(value = "道路路况-市内路况-指数与速度曲线信息", notes = "市内路况")
  @Requirement(value = "获取中路段的一天的指数与速度变化信息，按照15分钟时间片来统计。")
  @Input(value = "热点区域id")
  @Output(value = "时间片、指数、速度")
  @Handle
  @Parameter(value = "roadsect_fid")
  public HttpResult<Map<String, Object>> getSpeedAndTpiInfoByRoadsectfid(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getSpeedAndTpiInfoByRoadsectfid(map));

  }

  @ResponseBody
  @PostMapping(value = "getRoadsectRealTimeTrafficStatus")
  @ApiOperation(value = "道路路况-市内路况-地图-市内路况实时交通状态", notes = "市内路况")
  @Requirement(value = "获取中路段实时交通状态（支持传参时间范围查看历史时间片的状态）。")
  @Input(value = "开始时间、结束时间")
  @Output(value = "时间片、指数、速度")
  @Handle
  @Parameter(value = "startQueryTime,endQueryTime")
  public HttpResult<List<Map<String, Object>>> getRoadsectRealTimeTrafficStatus(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getRoadsectRealTimeTrafficStatus(map));
  }

  @ResponseBody
  @PostMapping(value = "getRoadsectRealTimeTrafficStatusPredict")
  @ApiOperation(value = "道路路况-市内路况-市内路况交通状态-预测数据", notes = "市内路况")
  @Requirement(value = "获取中路段未来两小时预测数据。")
  @Input(value = "中路段id")
  @Output(value = "时间片、指数、速度")
  @Handle
  @Parameter(value = "roadsect_fid")
  public HttpResult<List<Map<String, Object>>> getRoadsectRealTimeTrafficStatusPredict(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getRoadsectRealTimeTrafficStatusPredict(map));
  }

  @ResponseBody
  @PostMapping(value = "getPredictSpeedAndTpiInfoByRoadsectfid")
  @ApiOperation(value = "道路路况-市内路况-指数与速度曲线信息-预测信息", notes = "市内路况")
  @Requirement(value = "获取中路段未来两小时预测数据。")
  @Input(value = "中路段id")
  @Output(value = "时间片、指数、速度")
  @Handle
  @Parameter(value = "roadsect_fid")
  public HttpResult<List<Map<String, Object>>> getPredictSpeedAndTpiInfoByRoadsectfid(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getPredictSpeedAndTpiInfoByRoadsectfid(map));
  }

  @ResponseBody
  @PostMapping(value = "getHighSpeedRealTimeJamWarning")
  @ApiOperation(value = "道路路况-全省高速-实时拥堵预警", notes = "全省高速")
  @Requirement(value = "获取全省高速预警消息，并以指数从大到小的顺序排列。")
  @Input(value = "展示数据量、查询类别")
  @Output(value = "速度、指数、中路段id，中路段起点，中路段终点，")
  @Handle
  @Parameter(value = "isJam,dataNum")
  public HttpResult<Object> getHighSpeedRealTimeJamWarning(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getHighSpeedRealTimeJamWarning(map));
  }

  @ResponseBody
  @PostMapping(value = "getHighSpeedRealTimeTrafficStatus")
  @ApiOperation(value = "道路路况-全省高速-地图-全身高速实时交通状态", notes = "全省高速")
  @Requirement(value = "获取全省高速实时交通状态信息。并在地图中用不同颜色代表状态信息。")
  @Input(value = "开始时间、结束时间")
  @Output(value = "时间片、指数、速度")
  @Handle
  @Parameter(value = "startQueryTime,endQueryTime")
  public HttpResult<Object> getHighSpeedRealTimeTrafficStatus(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getHighSpeedRealTimeTrafficStatus(map));
  }

  @ResponseBody
  @PostMapping(value = "getHighSpeedLineDataByRoadsectfid")
  @ApiOperation(value = "道路路况-高身高速-指数与速度曲线信息", notes = "全省高速")
  @Requirement(value = "获取高速公路一天指数和速度的变化情况信息。")
  @Input(value = "高速路段id")
  @Output(value = "时间片、指数、速度")
  @Handle
  @Parameter(value = "roadsect_fid")
  public HttpResult<Object> getHighSpeedLineDataByRoadsectfid(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getHighSpeedLineDataByRoadsectfid(map));
  }

  @ResponseBody
  @PostMapping(value = "getRoadSectionFlowRanking")
  @ApiOperation(value = "断面流量-道路断面流量排名", notes = "断面流量")
  @Requirement(value = "获取断面流量排名信息，并且按照流量从大到小的顺序排列。")
  @Input
  @Output(value = "道路名称、区段、方向、流量（辆/小时）")
  @Handle
  @Parameter(value = "")
  public HttpResult<Object> getRoadSectionFlowRanking(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getRoadSectionFlowRanking(map));
  }

  @ResponseBody
  @PostMapping(value = "getRoadSectionFlowsByLinkFid")
  @ApiOperation(value = "断面流量-流量曲线信息", notes = "断面流量")
  @Requirement(value = "获取指定断面的全天流量信息。并且可以选择查看任意方向。按小时统计。")
  @Input(value = "断面id")
  @Output(value = "时间片（hour）、流量（volume)")
  @Handle
  @Parameter
  public HttpResult<Object> getRoadSectionFlowsByLinkFid(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getRoadSectionFlowsByLinkFid(map));
  }

  @ResponseBody
  @PostMapping(value = "getDCInfo")
  @ApiOperation(value = "断面流量-获取地磁点信息", notes = "断面流量")
  @Requirement(value = "获取全市地磁点位信息。")
  @Input
  @Output(value = "地磁点位id、地磁点位名称、经度、纬度、小路段id、路段名称")
  @Handle
  @Parameter(value = "")
  public HttpResult<Object> getDcInfo(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getDcInfo(map));
  }

  @ResponseBody
  @PostMapping(value = "getDetailInfoByLinkFid")
  @ApiOperation(value = "断面流量-点击地图地磁点获得信息", notes = "断面流量")
  @Requirement(value = "获取指定地磁点位一天的流量变化信息。按小时时间片统计。")
  @Input(value = "地磁点位id")
  @Output(value = "时间片（hour）、流量（volume)")
  @Handle
  @Parameter(value = "")
  public HttpResult<Object> getDetailInfoByLinkFid(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getDetailInfoByLinkFid(map));
  }

  @ResponseBody
  @PostMapping(value = "getIntersectionDelayStatus")
  @ApiOperation(value = "节点延误- 地图打点信息", notes = "节点延误")
  @Requirement(value = "页面地图上，需要打点出交叉口的点位信息。")
  @Input
  @Output(value = "时间片、交叉口名称、交叉口id、经度、纬度、延误时间")
  @Handle
  @Parameter
  public HttpResult<Object> getIntersectionDelayStatus(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getIntersectionDelayStatus(map));
  }

  @ResponseBody
  @PostMapping(value = "getIntersectionDelayDetails")
  @ApiOperation(value = "节点延误-交叉口详细信息", notes = "节点延误")
  @Requirement(value = "获取某一交叉口各个方向的延误信息。")
  @Input(value = "交叉口id")
  @Output(value = "交叉口名称、方向、转向、延误时间")
  @Handle
  @Parameter(value = "intersection_fid")
  public HttpResult<Object> getIntersectionDelayDetails(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getIntersectionDelayDetails(map));
  }

  @ResponseBody
  @PostMapping(value = "getAllIntersectionInformation")
  @ApiOperation(value = "节点延误- 所有交叉口", notes = "节点延误")
  @Input
  @Output(value = "时间片、交叉口名称、交叉口id、经度、纬度、延误时间")
  @Handle
  @Parameter
  public HttpResult<Object> getAllIntersectionInformation(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getAllIntersectionInformation(map));
  }
  
  @ResponseBody
  @PostMapping(value = "getIntersectionDelayDayData")
  @ApiOperation(value = "节点延误-交叉口曲线数据", notes = "节点延误")
  @Requirement(value = "获取某一交叉口一天的延误时间变化趋势。")
  @Input(value = "交叉口id")
  @Output(value = "时间片（15分钟）、延误时间")
  @Handle
  @Parameter(value = "intersection_fid")
  public HttpResult<Object> getIntersectionDelayDayData(@RequestParam Map<String, Object> map) {

    return HttpResult.ok(earlyWarningService.getIntersectionDelayDayData(map));
  }

  @ResponseBody
  @PostMapping(value = "getIntersectinDelayRanking")
  @ApiOperation(value = "节点延误-交叉口流量排名（新）", notes = "节点延误-新")
  @Requirement(value = "获取交叉口流量排名（新）。")
  @Input
  @Output(value = "交叉口名、方向、流量")
  @Handle
  @Parameter
  public HttpResult<Object> getIntersectinDelayRanking(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getIntersectinDelayRanking(map));
  }

  @ResponseBody
  @PostMapping(value = "getRoadSectFlowDistribution")
  @ApiOperation(value = "断面流量-断面流量地图点位", notes = "断面流量")
  @Requirement(value = "获取断面的id、流量、名称等，并在地图上标注。")
  @Input
  @Output(value = "id、名称、流量")
  @Handle
  @Parameter
  public HttpResult<Object> getRoadSectFlowDistribution(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getRoadSectFlowDistribution(map));
  }

  @ResponseBody
  @PostMapping(value = "getDetectorDirFlowByDetectorFid")
  @ApiOperation(value = "断面流量-断面流量地图点击检测器查询", notes = "断面流量")
  @Requirement(value = "获取指定断面的流量信息。")
  @Input(value = "断面id")
  @Output(value = "道路名、方向id、方向、流量")
  @Handle
  @Parameter(value = "fid")
  public HttpResult<Object> getDetectorDirFlowByDetectorFid(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getDetectorDirFlowByDetectorFid(map));
  }

  @ResponseBody
  @PostMapping(value = "getRoadSectionFlows")
  @ApiOperation(value = "断面流量-道路断面流量列表", notes = "断面流量")
  @Requirement(value = "获取道路断面流量列表，剔除匝道数据并按流量从大到小排序。")
  @Input(value = "展示数量")
  @Output(value = "道路名称、流量、断面id、中路段范围、方向")
  @Handle
  @Parameter(value = "dataNum")
  public HttpResult<Object> getRoadSectionFlows(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getRoadSectionFlows(map));
  }

  @ResponseBody
  @PostMapping(value = "getDetectorHourLineData")
  @ApiOperation(value = "断面流量-检测器曲线", notes = "断面流量")
  @Requirement(value = "获取某一断面一天的流量变化曲线数据，数据按照一小时为统计粒度。")
  @Input(value = "断面id")
  @Output(value = "时间片（hour）、流量（volume)")
  @Handle
  @Parameter(value = "断面id")
  public HttpResult<Object> getDetectorHourLineData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getDetectorHourLineData(map));
  }

  /**
   * 获取全市拥堵指标:拥堵路段数，拥堵里程数，全市指数，拥堵路段速度.
   *
   * @param map 空
   */
  @ResponseBody
  @PostMapping(value = "getCongestionRoadInfo")
  @ApiOperation(value = "道路路况-市内路况（五期、六期）-全市拥堵指标", notes = "道路路况-市内路况")
  @Requirement(value = "获取全市拥堵指标。全市指数、拥堵路段数、拥堵总里程、拥堵路段速度等")
  @Input
  @Output(value = "全市指数、拥堵路段属、拥堵总里程、拥堵路段速度")
  @Handle
  @Parameter(value = "")
  public HttpResult<Object> getCongestionRoadInfo(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getCongestionRoadInfo(map));
  }

  /**
   * 获取实时拥堵预警信息.
   *
   * @param map 空
   */
  @ResponseBody
  @PostMapping(value = "getCongestionWarnReal")
  @ApiOperation(value = "道路路况-市内路况（五期）-实时拥堵预警信息", notes = "道路路况-市内路况")
  @Requirement(value = "获取全市道路实时拥堵信息，包括信息有，道路名称，方向，起点，终点，速度等信息。")
  @Input
  @Output(value = "道路名称、方向、起点、终点、速度")
  @Handle
  @Parameter(value = "")
  public HttpResult<Object> getCongestionWarnReal(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getCongestionWarnReal(map));
  }

  @GetMapping(value = "getCongestionWarnRealV2")
  @ApiOperation(value = "道路路况-市内路况（六期）-实时拥堵预警信息-添加路段流量")
  @Requirement(value = "获取全市道路实时拥堵信息，包括信息有，道路名称，方向，起点，终点，速度等信息。添加通道流量信息。")
  @Input
  @Output(value = "道路名称、方向、起点、终点、速度、路段流量")
  @Handle
  @Parameter(value = "")
  public HttpResult<List<CongestionWarnRealVo>> getCongestionWarnRealV2() {
    return HttpResult.ok(earlyWarningService.getCongestionWarnRealV2());
  }

  @GetMapping(value = "getRoadsectChartInfo")
  @ApiOperation(value = "通过路段id，获取弹出框曲线信息")
  @ApiImplicitParam(value = "路段id", name = "id", required = true, example = "125207")
  @Requirement(value = "获取指定路段的一天速度和指数信息。按照15分钟时间片统计。")
  @Input(value = "路段id")
  @Output(value = "时间片（15分钟）、指数、速度")
  @Handle
  @Parameter(value = "roadsect_fid")
  public HttpResult<RoadsectChartInfoVo> getRoadsectChartInfo(int id) {
    return HttpResult.ok(earlyWarningService.getRoadsectChartInfo(id));
  }

  @GetMapping(value = "getMapRealVolume")
  @ApiOperation(value = "地图-路段流量")
  @Requirement(value = "获取中路段的实时流量信息，在地图中标注，流量越大，线段越粗。")
  @Input
  @Output(value = "流量、路段id")
  @Handle
  @Parameter(value = "内置日期、内置时间片")
  public HttpResult<List<RoadsectMapVolumeVo>> getMapRealVolume() {
    return HttpResult.ok(earlyWarningService.getMapRealVolume());
  }

  @ResponseBody
  @PostMapping(value = "getCongestionWarnDetail")
  @ApiOperation(value = "实时中路段（某一）拥堵预警详细信息", notes = "道路路况-市内路况")
  @Requirement(value = "获取某一中路段实时拥堵详细信息。")
  @Input(value = "中路段id")
  @Output(value = "开始时间（startTime)、持续时间(duration)、事件id(causeFid)、事件描述(causeDescribe)、处理措施id(countermeasureFid)、处理措施描述(countermeasureDescribe)、概率(probability)、概要(causeBrief)")
  @Handle
  @Parameter(value = "roadsectFid")
  public HttpResult<Object> getCongestionWarnDetail(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getCongestionWarnDetail(map));
  }

  @ResponseBody
  @PostMapping(value = "getGDHighSpeedLinkStatus")
  @ApiOperation(value = "高速路实时小路段信息(六期)")
  @Requirement(value = "获取广东省高速公路实时小路段运行信息。")
  @Input
  @Output(value = "小路点id，小路段起点，小路段终点，中路段名称，中路段起点，中路段终点，")
  @Handle
  @Parameter(value = "内置日期、内置时间片")
  public HttpResult<Object> getGdHighSpeedLinkStatus(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(earlyWarningService.getGdHighSpeedLinkStatus(map));
  }


}
