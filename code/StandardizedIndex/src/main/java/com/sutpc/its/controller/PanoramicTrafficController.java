package com.sutpc.its.controller;

import com.sutpc.its.annotation.Handle;
import com.sutpc.its.annotation.Input;
import com.sutpc.its.annotation.Output;
import com.sutpc.its.annotation.Parameter;
import com.sutpc.its.annotation.Requirement;
import com.sutpc.its.annotation.Scanning;
import com.sutpc.its.dto.DetectorChartDto;
import com.sutpc.its.dto.KeyAreaAlterDto;
import com.sutpc.its.dto.KeyAreaBaseDto;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.model.IdEntity;
import com.sutpc.its.service.PanoramicTrafficService;
import com.sutpc.its.tools.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/panoramicTraffic")
@Api(tags = "全景交通")
@Scanning
public class PanoramicTrafficController {

  @Autowired
  private PanoramicTrafficService panoramicTrafficService;


  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getRealTimeRoadsect")
  @ApiOperation(value = "运行一张图-实时中路段", notes = "运行一张图")
  @Requirement(value = "获取实时中路段数据。")
  @Input(value = "无。")
  @Output(value = "中路段id、速度、起点、 终点、时间片、中路段名称、指数")
  @Handle
  @Parameter(value = "")
  public HttpResult<Object> getRealTimeRoadsect(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(panoramicTrafficService.getRealTimeRoadsect(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getBlockPeakStatus")
  @ApiOperation(value = "月度简报-片区街道", notes = "月度简报")
  @Requirement(value = "获取指定年月的街道片区的平均运行情况。并计算出相比上一月的恶化的街道。")
  @Input(value = "年月（year_month）")
  @Output(value = "街道名、速度、指数、运行状态、街道id")
  @Handle(value = "后台会查询指定年月的平均运行情况和上一月的平均运行情况，并计算两个月的差值统计出恶化的街道数和明细。")
  @Parameter(value = "year_month")
  public HttpResult<Object> getBlockPeakStatus(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(panoramicTrafficService.getBlockPeakStatus(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getCitywideOperationCase")
  @ApiOperation(value = "月度简报-全市整体运行", notes = "月度简报")
  @Requirement(value = "获取全市整体运行情况。包含：早、晚高峰速度、指数数据包。全天速度信息、全天指数信息。")
  @Input(value = "年月（year_month）")
  @Output(value = "早高峰速度数据包、晚高峰速度数据包、早高峰指数数据包、晚高峰指数数据包、全天速度信息包、全天指数信息包。")
  @Handle(value = "")
  @Parameter(value = "year_month")
  public HttpResult<Object> getCitywideOperationCase(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(panoramicTrafficService.getCitywideOperationCase(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getMonthRoadsectWorsen")
  @ApiOperation(value = "月度简报-路段运行情况", notes = "月度简报")
  @Requirement(value = "获取全市路段运行情况，计算出路段恶化信息等。")
  @Input(value = "年月")
  @Output(value = "恶化路段最多行政区、总共多少恶化路段、最大恶化量")
  @Handle(value = "")
  @Parameter(value = "")
  public HttpResult<Object> getMonthRoadsectWorsen(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(panoramicTrafficService.getMonthRoadsectWorsen(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "setTpiTrafficEvent")
  @ApiOperation(value = "交通大事件-上传", notes = "交通大事记")
  @Requirement(value = "保存交通事件接口")
  @Input(value = "事件名称、发生日期、事件类型、录入人、附件保存的id")
  @Output(value = "成功码")
  @Handle(value = "")
  @Parameter(value = "")
  public HttpResult<Object> setTpiTrafficEvent(@RequestParam Map<String, Object> map) {
    int num = panoramicTrafficService.setTpiTrafficEvent(map);
    if (num == 1) {
      return HttpResult.ok();
    } else {
      return HttpResult.error();
    }
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "updateTpiTrafficEvent")
  @ApiOperation(value = "交通大事记-修改", notes = "交通大事记")
  @Requirement(value = "获取传入信息，修改交通大事件已有信息。")
  @Input(value = "交通大事件附件id、名称、事件事件、事件类型、类型名称、修改人、修改时间")
  @Output(value = "返回成功码或失败码")
  @Handle(value = "")
  @Parameter(value = "new_id,fname,event_date,event_category,event_type,upload_person,old_id")
  public HttpResult<Object> updateTpiTrafficEvent(@RequestParam Map<String, Object> map) {
    int num = panoramicTrafficService.updateTpiTrafficEvent(map);
    if (num == 1) {
      return HttpResult.ok();
    } else {
      return HttpResult.error();
    }
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getTpiTrafficEvent")
  @ApiOperation(value = "交通大事记-交通大事记-查询列表", notes = "交通大事记")
  @Requirement(value = "获取交通大事记列表")
  @Input(value = "年份")
  @Output(value = "大事件id、你名称、发生时间")
  @Handle(value = "")
  @Parameter(value = "year")
  public HttpResult<Object> getTpiTrafficEvent(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(panoramicTrafficService.getTpiTrafficEvent(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "deleteTpiTrafficEvent")
  @ApiOperation(value = "交通大事记-删除交通大事件", notes = "交通大事记")
  @Requirement(value = "通过交通大事件id删除交通大事件。删除之后，列表接口不再返回对应的事件信息。")
  @Input(value = "交通大事件id")
  @Output(value = "返回删除成功码或者失败码")
  @Handle(value = "")
  @Parameter(value = "id")
  public HttpResult<Object> deleteTpiTrafficEvent(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(panoramicTrafficService.deleteTpiTrafficEvent(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getYearTpiAndTrafficEventData")
  @ApiOperation(value = "交通大事记-年度早晚高峰曲线", notes = "交通大事记")
  @Requirement(value = "获取年度的早晚高峰分别的年度信息。有事件的日期，在曲线展示上面直接显示。")
  @Input(value = "年份")
  @Output(value = "日期、指数、事件id")
  @Handle
  @Parameter(value = "year")
  public HttpResult<Object> getYearTpiAndTrafficEventData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(panoramicTrafficService.getYearTpiAndTrafficEventData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getTrafficTpiData")
  @ApiOperation(value = "重点区域-指数变化曲线数据", notes = "重点区域")
  @Requirement(value = "获取指定（热点）重点区域的一天的指数变化趋势数据。")
  @Input(value = "热点区域（重点区域）id")
  @Output(value = "时间片、指数、上周同期数据包、昨天数据包")
  @Handle(value = "后台通过当前时间，查询当下的值，然后再查询出昨天喝上周同期的数据。最后组装成一个对象返回给调用方。")
  @Parameter(value = "hotspot_fid")
  public HttpResult<Object> getTrafficTpiData(@RequestParam Map<String, Object> map) {
    boolean flag = Utils.checkMapChar(map);
    if (flag) {
      return HttpResult.error();
    }
    return HttpResult.ok(panoramicTrafficService.getTrafficTpiData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getTrafficRealTimeJamRanking")
  @ApiOperation(value = "重点区域-热点周围路段实施拥堵排名", notes = "重点区域")
  @Requirement(value = "获取指定热点周围的路段实时拥堵情况信息，并按指数从大到小的顺序排列。")
  @Input(value = "热点id")
  @Output(value = "路段名称、路段起点名称、路段终点名称、指数、速度")
  @Handle(value = "")
  @Parameter(value = "hotsport_fid")
  public HttpResult<Object> getTrafficRealTimeJamRanking(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(panoramicTrafficService.getTrafficRealTimeJamRanking(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getMainRoadRealTimeJamRanking")
  @ApiOperation(value = "交通播报-主要道路实时拥堵运行", notes = "道路")
  @Requirement(value = "获取内定主要道路的实时拥堵信息详情，并展示在列表中，按指数从小到大排列。")
  @Input(value = "数据条数")
  @Output(value = "中路段id、大路段id、名称（含方向）、速度、指数")
  @Handle(value = "")
  @Parameter(value = "dataNum")
  public HttpResult<Object> getMainRoadRealTimeJamRanking(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(panoramicTrafficService.getMainRoadRealTimeJamRanking(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getMainBuslaneLineDataByBuslaneId")
  @ApiOperation(value = "公交专用道变化趋势")
  @Requirement(value = "获取指定专用道的运行变化趋势。")
  @Input(value = "公交专用道id")
  @Output(value = "专用道名称、专用道id、时间片、速度")
  @Handle(value = "")
  @Parameter(value = "fid")
  public HttpResult<Object> getMainBuslaneLineDataByBuslaneId(
      @RequestParam Map<String, Object> map) {
    return HttpResult.ok(panoramicTrafficService.getMainBuslaneLineDataByBuslaneId(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getMainPoiRealTimeJamRanking")
  @ApiOperation(value = "重点区域实时拥堵运行。")
  @Requirement(value = "获取重点区域（热点）实时的运行状态信息。并按照指数排名。")
  @Input(value = "数据条数")
  @Output(value = "热点id、热点名称、速度、指数")
  @Handle(value = "")
  @Parameter(value = "dataNum(非必填），默认返回10条数。")
  public HttpResult<Object> getMainPoiRealTimeJamRanking(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(panoramicTrafficService.getMainPoiRealTimeJamRanking(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getPoiRealTimeTrafficStatus")
  @ApiOperation(value = "热点实时交通状态")
  @Requirement(value = "获取内定的热点的实时交通状态信息")
  @Input(value = "无")
  @Output(value = "时间片、经纬度、热点id、热点名称、热点类型、速度、指数")
  @Handle(value = "")
  @Parameter(value = "内置日期参数")
  public HttpResult<Object> getPoiRealTimeTrafficStatus(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(panoramicTrafficService.getPoiRealTimeTrafficStatus(map));
  }

  @ApiImplicitParams({
      @ApiImplicitParam(value = "经度", name = "lng", defaultValue = "114.029175", required = true),
      @ApiImplicitParam(value = "纬度", name = "lat", defaultValue = "22.609748", required = true),
      @ApiImplicitParam(value = "热点区域id", name = "id", defaultValue = "122", required = true)
  })
  @ResponseBody
  @PostMapping(value = "getKeyAreaInfo")
  @ApiOperation(value = "重点区域-指数、速度、公交运力等信息")
  public HttpResult<KeyAreaBaseDto> getKeyAreaInfo(@RequestParam double lng,
      @RequestParam double lat, @RequestParam int id) {
    return HttpResult.ok(panoramicTrafficService.getKeyAreaInfo(lng, lat, id));
  }

  @ResponseBody
  @PostMapping(value = "getKeyAreaAlertInfo")
  @ApiOperation(value = "重点区域-热点预警信息")
  @Requirement(value = "获取热点预警信息。")
  @Input(value = "无")
  @Output(value = "是否预警字段、出租车、小汽车、公交车、片区")
  @Handle
  @Parameter(value = "无")
  public HttpResult<KeyAreaAlterDto> getKeyAreaAlertInfo(IdEntity entity) {
    return HttpResult.ok(panoramicTrafficService.getKeyAreaAlertInfo(entity));
  }

  @GetMapping(value = "getDetectorInfoById")
  @ApiOperation(value = "（通过id）获取断面流量曲线、阈值、实时流量等信息")
  @ApiImplicitParam(value = "断面检测器id", name = "id", required = true)
  @Requirement(value = "获取指定的断面检测器的端流量曲线、阈值以及实时流量信息。")
  @Input(value = "断面检测器id")
  @Output(value = "流量曲线数据列表、阈值、实时流量")
  @Handle(value = "通过断面检测器id分别查询出三类值，然后组装成一个对象返回。")
  @Parameter(value = "id")
  public HttpResult<DetectorChartDto> getDetectorInfoById(@RequestParam int id) {
    return HttpResult.ok(panoramicTrafficService.getDetectorInfoById(id));
  }
}
