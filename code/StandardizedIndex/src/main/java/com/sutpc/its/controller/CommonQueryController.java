package com.sutpc.its.controller;

import com.sutpc.its.annotation.Handle;
import com.sutpc.its.annotation.Input;
import com.sutpc.its.annotation.Output;
import com.sutpc.its.annotation.Parameter;
import com.sutpc.its.annotation.Requirement;
import com.sutpc.its.annotation.Scanning;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.service.CommonQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/commonQuery")
@Scanning
@Api(tags = "系统通用基础接口")
public class CommonQueryController {

  @Autowired
  private CommonQueryService commonQueryService;

  @ResponseBody
  @PostMapping(value = "getRoadBig")
  @ApiOperation(value = "获取大路段基础信息", notes = "大路段基础信息")
  @Requirement(value = "获取全市的所有大路段基础信息")
  @Input(value = "支持模糊查询功能，输入道路名称。")
  @Output(value = "大路段id、名称、方向等基础信息")
  @Handle(value = "直接查询数据库。")
  @Parameter(value = "fname，模糊查询名称。非必填。")
  public HttpResult<Object> getRoadBig(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getRoadBig(map));
  }

  @ResponseBody
  @PostMapping(value = "getRoadBigName")
  @ApiOperation(value = "获取大路段名称信息", notes = "大路段基础信息")
  @Requirement(value = "获取全市的所有大路段名称信息")
  @Input(value = "系统内部注入行政区id")
  @Output(value = "大路段名称列表")
  @Handle(value = "直接查询数据库")
  @Parameter(value = "内置行政区参数")
  public HttpResult<Object> getRoadBigName(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getRoadBigName(map));
  }

  @ResponseBody
  @PostMapping(value = "getRoadBigDir")
  @ApiOperation(value = "获取大路段基础信息(含方西）", notes = "大路段基础信息")
  @Requirement(value = "获取全市的所有大路段名称信息、方向信息、方向id等。")
  @Input(value = "系统内部注入行政区id")
  @Output(value = "大路段名称列表")
  @Handle(value = "直接查询数据库")
  @Parameter(value = "内置行政区参数")
  public HttpResult<Object> getRoadBigDir(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getRoadBigDir(map));
  }

  @ResponseBody
  @PostMapping(value = "getRoadMid")
  @ApiOperation(value = "获取中路段基础信息", notes = "中路段基础信息")
  @Requirement(value = "获取全市的所有中路段基础信息")
  @Input(value = "提供模糊查询的参数。中路段名称。系统内部注入行政区id")
  @Output(value = "中路段名称、id、起点、重点。")
  @Handle(value = "直接查询数据库")
  @Parameter(value = "内置行政区参数")
  public HttpResult<Object> getRoadMid(@RequestParam Map<String, Object> paramap) {
    return HttpResult.ok(commonQueryService.getRoadMid(paramap));
  }

  @ResponseBody
  @PostMapping(value = "getDistrict")
  @ApiOperation(value = "获取行政区基础信息", notes = "行政区")
  @Requirement(value = "获取全市行政区的id和名称。多数用于下拉框位置。")
  @Input(value = "无")
  @Output(value = "行政区id，行政区名称。")
  @Handle(value = "直接查询数据库")
  @Parameter(value = "内置行政区参数")
  public HttpResult<Object> getDistrict(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getDistrict(map));
  }

  @ResponseBody
  @PostMapping(value = "getDistrictForBus")
  @ApiOperation(value = "获取行政区基础信息-公交速度专用", notes = "行政区")
  @Requirement(value = "获取全市行政区下拉框基础信息，行政区id和行政区名称。包含了全市、中心城区、原特区外。")
  @Input(value = "无")
  @Output(value = "行政区id，行政区名称")
  @Handle(value = "直接查询数据库即可")
  @Parameter(value = "内置行政区参数")
  public HttpResult<Object> getDistrictForBus(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getDistrictForBus(map));
  }

  @ResponseBody
  @PostMapping(value = "getDistrictForCity")
  @ApiOperation(value = "获取全市行政区下拉框基础信息-全市查询专用", notes = "行政区")
  @Requirement(value = "获取全市行政区下拉框基础信息，行政区id和行政区名称。只包含全市、中心城区、原特区外。")
  @Input(value = "无")
  @Output(value = "行政区id，行政区名称")
  @Handle(value = "直接查询数据库即可")
  @Parameter(value = "内置行政区参数")
  public HttpResult<Object> getDistrictForCity(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getDistrictForCity(map));
  }

  @ResponseBody
  @PostMapping(value = "getBlock")
  @ApiOperation(value = "获取街道基础信息", notes = "街道")
  @Requirement(value = "获取街道基础信息，需要包含街道id，街道名称，所属行政区id等")
  @Input(value = "无")
  @Output(value = "街道id，街道名称，行政区id")
  @Handle(value = "直接查询数据库")
  @Parameter(value = "内置行政区参数")
  public HttpResult<Object> getBlock(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getBlock(map));
  }

  @ResponseBody
  @PostMapping(value = "getSubsect")
  @ApiOperation(value = "获取交通小区基础信息", notes = "交通小区")
  @Requirement(value = "获取全市交通小区基础信息，需要包含交通小区id，交通小区名称，所属行政区id等。同时支持模糊查询，按名称搜索。")
  @Input(value = "fname，模糊查询名称")
  @Output(value = "交通小区id，交通小区名称，行政区id。")
  @Handle(value = "直接查询数据库")
  @Parameter(value = "fname，模糊查询名称。")
  public HttpResult<Object> getSubsect(Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getSubsect(map));
  }

  @ResponseBody
  @PostMapping(value = "getPoi")
  @ApiOperation(value = "获取热点基础信息", notes = "热点")
  @Requirement(value = "获取热点区域基础信息，包含热点id，热点名称，经纬度，所属行政区id。同时支持模糊查询，按名称搜索。")
  @Input(value = "fname，模糊查询名称")
  @Output(value = "热点id，热点名称，行政区id，经纬度。")
  @Handle(value = "直接查询数据库")
  @Parameter(value = "fname，模糊查询名称。")
  public HttpResult<Object> getPoi(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getPoi(map));
  }

  @ResponseBody
  @PostMapping(value = "getPoiCategory")
  @ApiOperation(value = "获取热点类型", notes = "热点")
  @Requirement(value = "获取热点类型基础信息，包含类型id，类型名称。")
  @Input(value = "无")
  @Output(value = "热点类型id，类型名称。")
  @Handle
  @Parameter(value = "内置行政区参数。")
  public HttpResult<Object> getPoiCategory(Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getPoiCategory(map));
  }

  @ResponseBody
  @PostMapping(value = "getInterestPoi")
  @ApiOperation(value = "获取热点基础信息", notes = "兴趣点")
  @Requirement(value = "获取所有热点区域信息，包含热点id，经纬度。")
  @Input(value = "无")
  @Output(value = "热点id，热点名称，经纬度信息。")
  @Handle
  @Parameter
  public HttpResult<Object> getInterestPoi() {
    return HttpResult.ok(commonQueryService.getInterestPoi());
  }

  @ResponseBody
  @PostMapping(value = "getDetector")
  @ApiOperation(value = "获取检测器信息", notes = "流量检测器")
  @Requirement(value = "获取检测器信息基础信息，包含检测器id，名称，方向id，名称。")
  @Input(value = "无")
  @Output(value = "检测器id，名称，方向，方向id")
  @Handle
  @Parameter(value = "内置行政区id")
  public HttpResult<Object> getDetector(Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getDetector(map));
  }

  @ResponseBody
  @PostMapping(value = "getBuslane")
  @ApiOperation(value = "公交专用道道路", notes = "公交专用道路")
  @Requirement(value = "获取公交专用道基础信息，包含专用道id，名称加方向")
  @Input(value = "无")
  @Output(value = "专用道id，名称加方向")
  @Handle
  @Parameter
  public HttpResult<Object> getBusLane() {
    return HttpResult.ok(commonQueryService.getBusLane());
  }

  @ResponseBody
  @PostMapping(value = "getGDRoadBig")
  @ApiOperation(value = "全省高速-获取广东高速大路段基础信息", notes = "道路查询")
  @Requirement(value = "获取广东高速公路大路段基础信息 ，包含了大路段id，大路段名称，方向等信息。")
  @Input(value = "无")
  @Output(value = "高速公路大路段id，大路段名称，方向。")
  @Handle
  @Parameter
  public HttpResult<Object> getGdRoadBig() {
    return HttpResult.ok(commonQueryService.getGdRoadBig());
  }

  @ResponseBody
  @PostMapping(value = "getGDRoadMid")
  @ApiOperation(value = "全省高速-获取广东高速中路段基础信息", notes = "道路查询")
  @Requirement(value = "获取广东高速公路中路段基础信息，包含了中路段id，中路段名称，中路段起点，中路段终点，以及所在大路段id。")
  @Input(value = "无")
  @Output(value = "中路段id，中路段名称，中路段起点，中路段终点，中路段所在大路段id。")
  @Handle
  @Parameter
  public HttpResult<Object> getGdRoadMid(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getGdRoadMid(map));
  }

  @ResponseBody
  @PostMapping(value = "getCheckLine")
  @ApiOperation(value = "获取核查线基础信息", notes = "道路查询")
  @Requirement(value = "获取核查线基础信息，核查线id，核查线名称。")
  @Input(value = "无")
  @Output(value = "核查线id，核查线名称。")
  @Handle
  @Parameter
  public HttpResult<Object> getCheckLine() {
    return HttpResult.ok(commonQueryService.getCheckLine());
  }

  @ResponseBody
  @PostMapping(value = "getCross")
  @ApiOperation(value = "获取二线关基础信息", notes = "节点查询")
  @Requirement(value = "获取二线关基础信息，包含二线关id，二线关名称。")
  @Input(value = "无")
  @Output(value = "二线关id，二线关名称。")
  @Handle
  @Parameter
  public HttpResult<Object> getCross() {
    return HttpResult.ok(commonQueryService.getCross());
  }

  @ResponseBody
  @PostMapping(value = "getIntersectionDelay")
  @ApiOperation(value = "获取交叉口-延误下拉框条件信息", notes = "节点查询")
  @Requirement(value = "获取交叉口id，交叉口名称信息。")
  @Input(value = "无")
  @Output(value = "交叉口id，交叉口名称。")
  @Handle
  @Parameter(value = "内置行政区id")
  public HttpResult<Object> getIntersectionDelay(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getIntersectionDelay(map));
  }

  @ResponseBody
  @PostMapping(value = "getVehicle")
  @ApiOperation(value = "获取车辆运行-车辆类型", notes = "车辆运行")
  @Requirement(value = "获取车辆类型id，名称。")
  @Input(value = "无")
  @Output(value = "车辆类型id，名称。")
  @Handle
  @Parameter(value = "内置行政区id")
  public HttpResult<Object> getVehicle(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getVehicle(map));
  }

  @ResponseBody
  @PostMapping(value = "getBusRouteFname")
  @ApiOperation(value = "获取公交车线路", notes = "公交轨迹")
  @Requirement(value = "获取公交车线路id，线路名称等基础信息，用于下拉框条件选择。")
  @Input(value = "无")
  @Output(value = "公交车线路id，线路名称。")
  @Handle
  @Parameter
  public HttpResult<Object> getBusRouteFname(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getBusRouteFname(map));
  }

  @ResponseBody
  @PostMapping(value = "getBusRouteDirection")
  @ApiOperation(value = "获取公交车线路方向", notes = "公交轨迹")
  @Requirement(value = "通过公交车线路名称获取公交车线路方向信息。")
  @Input(value = "route_fname，线路名称。")
  @Output(value = "公交车线路方向")
  @Handle
  @Parameter(value = "route_fname")
  public HttpResult<Object> getBusRouteDirection(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getBusRouteDirection(map));
  }

  @ResponseBody
  @PostMapping(value = "getTrafficEventType")
  @ApiOperation(value = "获取事件类型", notes = "交通大事记")
  @Requirement(value = "获取事件类型的基础信息，包含事件类型id，事件类型名称。")
  @Input(value = "无")
  @Output(value = "事件类型id，事件类型名称。")
  @Handle
  @Parameter
  public HttpResult<Object> getTrafficEventType() {
    return HttpResult.ok(commonQueryService.getTrafficEventType());
  }

  @ResponseBody
  @PostMapping(value = "getParkRoadBaseInfo")
  @ApiOperation(value = "获取停车收费路段片区信息", notes = "停车收费路段")
  @Requirement(value = "获取停车收费路段片区id，名称等基础信息。")
  @Input(value = "无")
  @Output(value = "停车收费路段片区id，名称。")
  @Handle
  @Parameter(value = "内置行政区id。")
  public HttpResult<Object> getParkRoadBaseInfo(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getParkRoadBaseInfo(map));
  }

  @ResponseBody
  @PostMapping(value = "getFreightTransportInfo")
  @ApiOperation(value = "获取货运热点区域信息", notes = "货运车辆")
  @Requirement(value = "获取货运热点区域信息，包含id，名称，经度，纬度。")
  @Input(value = "无")
  @Output(value = "热点区域id，名称，经度，纬度。")
  @Handle
  @Parameter
  public HttpResult<Object> getFreightTransportInfo(@RequestParam Map<String, Object> map) {

    return HttpResult.ok(commonQueryService.getFreightTransportInfo(map));
  }

  @ResponseBody
  @PostMapping(value = "getRoadType")
  @ApiOperation(value = "获取道路类型")
  @Requirement(value = "获取道路类型id，道路类型名称。")
  @Input(value = "无")
  @Output(value = "道路类型id，道路类型。")
  @Handle
  @Parameter(value = "内置行政区id")
  public HttpResult<Object> getRoadType(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(commonQueryService.getRoadType(map));
  }
}
