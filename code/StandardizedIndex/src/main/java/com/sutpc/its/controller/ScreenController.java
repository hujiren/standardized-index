package com.sutpc.its.controller;

import com.sutpc.its.annotation.Handle;
import com.sutpc.its.annotation.Input;
import com.sutpc.its.annotation.Output;
import com.sutpc.its.annotation.Parameter;
import com.sutpc.its.annotation.Requirement;
import com.sutpc.its.annotation.Scanning;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.service.ScreenService;
import com.sutpc.its.vo.OldScreenAllRoadMidSectVo;
import com.sutpc.its.vo.OldScreenDistrictChartVo;
import com.sutpc.its.vo.OldScreenDistrictSpeedRatioVo;
import com.sutpc.its.vo.RoadJamVo;
import com.sutpc.its.vo.RoadStatusInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "screen")
@Api(tags = "各辖区大屏")
@Scanning
public class ScreenController {

  @Autowired
  private ScreenService screenService;

  @ResponseBody
  @PostMapping("getRealTimeDistrictSpeedAndTpi")
  @ApiOperation(value = "行政区：平均速度、交通指数、拥堵里程、公交速度")
  @Requirement(value = "获取各辖区整体的平均速度、交通指数、拥堵里程、公交速度")
  @Input(value = "行政区id")
  @Output(value = "平均速度、交通指数、拥堵里程、公交速度")
  @Handle(value = "")
  @Parameter(value = "config_district_fid")
  public HttpResult<Object> getRealTimeDistrictSpeedAndTpi(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(screenService.getRealTimeDistrictSpeedAndTpi(map));
  }

  @ResponseBody
  @PostMapping("getSpeedAndTpiInfoByRoadsectfid")
  @ApiOperation(value = "通过中路段id获取中路段速度和指数变化趋势数据")
  @Requirement(value = "通过中路段id获取中路段速度和指数变化趋势数据。")
  @Input(value = "中路段id")
  @Output(value = "时间片、指数、速度")
  @Handle
  @Parameter(value = "中路段id")
  public HttpResult<Object> getSpeedAndTpiInfoByRoadsectfid(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(screenService.getSpeedAndTpiInfoByRoadsectfid(map));
  }

  @ResponseBody
  @PostMapping("getRoadsectRealTimeJamWarning")
  @ApiOperation(value = "实时路段拥堵预警")
  @Requirement(value = "获取中路段实时的运行状态预警信息。")
  @Input(value = "行政区id")
  @Output(value = "")
  @Handle(value = "")
  @Parameter(value = "config_district_fid")
  public HttpResult<Object> getRoadsectRealTimeJamWarning(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(screenService.getRoadsectRealTimeJamWarning(map));
  }

  @ResponseBody
  @PostMapping("getLateseTime")
  @ApiOperation(value = "数据更新时间")
  @Requirement(value = "获取数据更新时间。")
  @Input(value = "无")
  @Output(value = "数据更新时间")
  @Handle(value = "")
  @Parameter(value = "无")
  public HttpResult<Object> getLateseTime() {
    return HttpResult.ok(screenService.getLateseTime());
  }

  @ResponseBody
  @PostMapping("getKeyAreaData")
  @ApiOperation(value = "重点区域内数据")
  @Requirement(value = "获取重点区域内热点区域实时信息，经纬度，指数，速度等定位信息。")
  @Input(value = "无")
  @Output(value = "最新时间片、热点id、热点对应云抵id、经纬度、指数、速度")
  @Handle
  @Parameter(value = "")
  public HttpResult<Object> getKeyAreaData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(screenService.getKeyAreaData(map));
  }

  @ResponseBody
  @PostMapping("getRealTimeAreaPeopleCharts")
  @ApiOperation(value = "实时热点区域人流变化趋势")
  @Requirement(value = "获取实时热点区域人流变化曲线数据。")
  @Input(value = "")
  @Output(value = "")
  @Handle(value = "数据源TP")
  @Parameter(value = "")
  public HttpResult<Object> getRealTimeAreaPeopleCharts(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(screenService.getRealTimeAreaPeopleCharts(map));
  }

  @ResponseBody
  @PostMapping("getCollects")
  @ApiOperation(value = "GPS点位信息")
  @Requirement(value = "根据车辆类型获取GPS实时原始点位信息。")
  @Input(value = "行政区id")
  @Output(value = "")
  @Handle(value = "调用GPS工程接口")
  @Parameter(value = "")
  public HttpResult<Object> getCollects(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(screenService.getCollects(map));
  }

  @ResponseBody
  @PostMapping("getCollectsByRange")
  @ApiOperation(value = "GPS点位信息（指定范围）")
  @Requirement(value = "根据点位信息获取指定范围的GPS信息")
  @Input(value = "行政区id，汽车类型")
  @Output(value = "GPS坐标集")
  @Handle(value = "")
  @Parameter(value = "config_district_fid,carType")
  public HttpResult<Object> getCollectsByRange(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(screenService.getCollectsByRange(map));
  }

  @ResponseBody
  @PostMapping("getBusCapacity")
  @ApiOperation(value = "公交运力")
  @Requirement(value = "获取出租车网约车公交车的运力信息。")
  @Input(value = "行政区id")
  @Output(value = "小汽车运力、公交车运力、网约车运力")
  @Handle(value = "")
  @Parameter(value = "")
  public HttpResult<Object> getBusCapacity(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(screenService.getBusCapacity(map));
  }

  @ResponseBody
  @PostMapping("getCityAndEveryDistrictTpi")
  @ApiOperation(value = "辖区大屏-全市和各行政区指数变化", notes = "全市概况")
  @Requirement(value = "获取全市和各行政区全天指数变化数据，默认按照15分钟时间片来统计。支持切换日期，查看历史数据。")
  @Input(value = "时间日期")
  @Output(value = "行政区id、时间片（15分钟）、指数、行政区名称")
  @Handle
  @Parameter(value = "time")
  public HttpResult<Object> getCityAndEveryDistrictTpi(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(screenService.getCityAndEveryDistrictTpi(map));
  }

  @ResponseBody
  @ApiOperation(value = "指数大屏-实时道路拥堵预警")
  @GetMapping(value = "getRealTimeJamRoad")
  @Requirement(value = "获取实时道路拥堵预警信息。")
  @Input(value = "无")
  @Output(value = "道路名、方向、速度、指数、状态")
  @Handle
  @Parameter(value = "无")
  public HttpResult<List<RoadJamVo>> getRealTimeJamRoad() {
    return HttpResult.ok(screenService.getRealTimeJamRoad());
  }

  @GetMapping(value = "getAllAndDistrictExponentComparisonData")
  @ApiOperation(value = "旧指数大屏接口-指数速度曲线图（getAllAndDistrictExponentComparisonData）")
  @ApiImplicitParam(value = "行政区id", name = "id", example = "1", required = true)
  @Requirement(value = "获取全市指数速度全天的变化趋势。")
  @Input(value = "行政区id")
  @Output(value = "速度、指数、时间片")
  @Handle
  @Parameter(value = "id")
  public HttpResult<List<OldScreenDistrictChartVo>> getAllAndDistrictExponentComparisonData(
      @RequestParam String id) {
    return HttpResult.ok(screenService.getAllAndDistrictExponentComparisonData(id));
  }

  @GetMapping(value = "getAllRoadMidSectExp")
  @ApiOperation(value = "旧指数大屏接口-全市所有中路段实时指数信息(getAllRoadMidSectExp)")
  @Requirement(value = "获取全市所有中路段实时的运行状态信息。")
  @Input(value = "无")
  @Output(value = "中路段id、中路段名称、起点、终点、指数")
  @Handle
  @Parameter(value = "")
  public HttpResult<List<OldScreenAllRoadMidSectVo>> getAllRoadMidSectExp() {
    return HttpResult.ok(screenService.getAllRoadMidSectExp());
  }

  @GetMapping(value = "getDistrictCarAndBusRatio")
  @ApiOperation(value = "旧指数大屏接口-实时公交运行（小汽车与公交速度比）")
  @Requirement(value = "获取全市实时公交运行状态情况。并且算出小汽车与公交速度比。")
  @Input(value = "无")
  @Output(value = "公交速度、小汽车速度、速度比")
  @Handle
  @Parameter(value = "")
  public HttpResult<OldScreenDistrictSpeedRatioVo> getDistrictCarAndBusRatio() {
    return HttpResult.ok(screenService.getDistrictCarAndBusRatio());
  }

  @GetMapping(value = "getRoadStatus")
  @ApiOperation(value = "指数公众版-道路路况列表")
  @Requirement(value = "获取道路路况列表信息。")
  @Input(value = "无")
  @Output(value = "道路id、道路名、方向、速度、状态")
  @Handle
  @Parameter(value = "")
  public HttpResult<List<RoadStatusInfoVo>> getRoadStatus() {
    return HttpResult.ok(screenService.getRoadStatus());
  }
}
