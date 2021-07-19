package com.sutpc.its.controller;

import com.sutpc.its.annotation.Handle;
import com.sutpc.its.annotation.Input;
import com.sutpc.its.annotation.Output;
import com.sutpc.its.annotation.Parameter;
import com.sutpc.its.annotation.Requirement;
import com.sutpc.its.annotation.Scanning;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.service.DataQueryService;
import com.sutpc.its.tools.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dataQuery")
@Scanning
@Api(tags = "数据查询模块")
public class DataQueryController {

  @Autowired
  private DataQueryService dataQueryService;

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getCalenderMonthData")
  @ApiOperation(value = "交通日历数据", notes = "交通日历")
  @Requirement(value = "顶上标题可以支持选择年、月、和区域，区域包括全市、中心城区、特区外、10个行政区。中间两个日历分别为早高峰、晚高峰指数日历，日历标题：早高峰交通指数、晚高峰交通指数。")
  @Input(value = "查询输入：年份、月份、行政区id、早高峰或晚高峰")
  @Output(value = "特殊json串。")
  @Handle(value = "后台处理过的独特json串。每一天都会有独特的数据结构存在。并且排列成日历形式，从一号开始。")
  @Parameter(value = "y,m,district_fid,timeproperty")
  public HttpResult<Object> getCalenderMonthData(@RequestParam Map<String, Object> map) {
    boolean flag = Utils.checkMapChar(map);
    if (flag) {
      return HttpResult.error();
    }
    return HttpResult.ok(dataQueryService.getCalenderMonthData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getCalendarMonthLineData")
  @ApiOperation(value = "每日高峰指数", notes = "交通日历")
  @Requirement(value = "前端页面展示当月每日早、晚高峰指数信息。")
  @Input(value = "年月、月份、行政区id")
  @Output(value = "早、晚高峰指数、日期")
  @Handle(value = "")
  @Parameter(value = "y,m,district_fid")
  public HttpResult<Object> getCalendarMonthLineData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getCalendarMonthLineData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getCalendarDayData")
  @ApiOperation(value = "点击某日获取当日曲线数据", notes = "交通日历")
  @Requirement(value = "点击日日期弹出详情页面，左侧上方为日期、天气图标，左侧下方为当天早高峰或者晚高峰的指数、速度；右侧为当天速度和指数的时变曲线。")
  @Input(value = "当前选定日期、行政区id")
  @Output(value = "速度、时间片、指数")
  @Handle
  @Parameter(value = "fdate,district_fid")
  public HttpResult<Object> getCalendarDayData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getCalendarDayData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getCityQueryData")
  @ApiOperation(value = "全市查询", notes = "数据查询-全市查询")
  @Requirement(value = "通过各种条件组合 ，可以查询全市、中心城区、原特区外等行政区的交通指数、平均速度、拥堵里程比例、拥堵时空值、平均拥堵里程等指标的结果值。按各种时间颗粒不同，来确定统计的范围大小以及显示的样式等。")
  @Input(value = "指标、时间颗粒、工作日类型、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围等")
  @Output(value = "时间片（按查询条件来定范围大小）、速度/指数（y)、fid、fname")
  @Handle(value = "根据条件不同、范围不同、进行sql拼接，然后完成查询后，把结果组装返回。")
  @Parameter(value = "index,timeprecision,dateproperty,id,startdate,enddate,starttime,endtime,timeproperty")
  public HttpResult<Object> getCityQueryData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getCityQueryData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getDistrictQueryData")
  @ApiOperation(value = "行政区查询", notes = "数据查询-片区查询")
  @Requirement(value = "通过各种条件组合 ，可以查询行政区的交通指数、平均速度、拥堵里程比例、拥堵时空值、平均拥堵里程等指标的结果值。按各种时间颗粒不同，来确定统计的范围大小以及显示的样式等。")
  @Input(value = "指标、时间颗粒、工作日类型、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围等")
  @Output(value = "时间片（按查询条件来定范围大小）、速度/指数（y)、fid、fname")
  @Handle(value = "根据条件不同、范围不同、进行sql拼接，然后完成查询后，把结果组装返回。")
  @Parameter(value = "index,timeprecision,dateproperty,id,startdate,enddate,starttime,endtime,timeproperty")
  public HttpResult<Object> getDistrictQueryData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getDistrictQueryData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getBlockQueryData")
  @ApiOperation(value = "街道查询", notes = "数据查询-片区查询")
  @Requirement(value = "通过各种条件组合 ，可以查询街道的交通指数、平均速度、拥堵里程比例、拥堵时空值、平均拥堵里程等指标的结果值。按各种时间颗粒不同，来确定统计的范围大小以及显示的样式等。")
  @Input(value = "指标、时间颗粒、工作日类型、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围等")
  @Output(value = "时间片（按查询条件来定范围大小）、速度/指数（y)、fid、fname")
  @Handle(value = "根据条件不同、范围不同、进行sql拼接，然后完成查询后，把结果组装返回。")
  @Parameter(value = "index,timeprecision,dateproperty,id,startdate,enddate,starttime,endtime,timeproperty")
  public HttpResult<Object> getBlockQueryData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getBlockQueryData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getSubsectQueryData")
  @ApiOperation(value = "交通小区查询", notes = "数据查询-片区查询")
  @Requirement(value = "通过各种条件组合 ，可以查询交通小区的交通指数、平均速度、拥堵里程比例、拥堵时空值、平均拥堵里程等指标的结果值。按各种时间颗粒不同，来确定统计的范围大小以及显示的样式等。")
  @Input(value = "指标、统计方式、时间颗粒、工作日类型、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围等")
  @Output(value = "时间片（按查询条件来定范围大小）、速度/指数（y)、fid、fname")
  @Handle(value = "根据条件不同、范围不同、进行sql拼接，然后完成查询后，把结果组装返回。")
  @Parameter(value = "index,methods,timeprecision,dateproperty,id,startdate,enddate,starttime,endtime,timeproperty")
  public HttpResult<Object> getSubsectQueryData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getSubsectQueryData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getPoiQueryData")
  @ApiOperation(value = "热点查询", notes = "数据查询-片区查询")
  @Requirement(value = "通过各种条件组合 ，可以查询街道的交通指数、平均速度等指标的结果值。按各种时间颗粒不同，来确定统计的范围大小以及显示的样式等。")
  @Input(value = "指标、统计方式、时间颗粒、工作日类型、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围等")
  @Output(value = "时间片（按查询条件来定范围大小）、速度/指数（y)、fid、fname")
  @Handle(value = "根据条件不同、范围不同、进行sql拼接，然后完成查询后，把结果组装返回。")
  @Parameter(value = "index,methods,timeprecision,dateproperty,id,startdate,enddate,starttime,endtime,timeproperty")
  public HttpResult<Object> getPoiQueryData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getPoiQueryData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getRoadQueryData")
  @ApiOperation(value = "道路查询", notes = "数据查询-道路查询")
  @Requirement(value = "通过各种条件组合 ，可以查询道路（大路段）的平均速度、拥堵里程比、拥堵时空值、平均拥堵里程比例等指标的结果值。按各种时间颗粒不同，来确定统计的范围大小以及显示的样式等。")
  @Input(value = "指标、统计方式、时间颗粒、工作日类型、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围等")
  @Output(value = "时间片（按查询条件来定范围大小）、速度/指数（y)、fid、fname")
  @Handle(value = "根据条件不同、范围不同、进行sql拼接，然后完成查询后，把结果组装返回。")
  @Parameter(value = "index,methods,timeprecision,dateproperty,id,startdate,enddate,starttime,endtime,timeproperty")
  public HttpResult<Object> getRoadQueryData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getRoadQueryData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getRoadsectQueryData")
  @ApiOperation(value = "路段查询", notes = "数据查询-路段查询")
  @Requirement(value = "通过各种条件组合 ，可以查询道路（中路段）的平均速度结果值。按各种时间颗粒不同，来确定统计的范围大小以及显示的样式等。")
  @Input(value = "指标、统计方式、时间颗粒、工作日类型、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围等")
  @Output(value = "时间片（按查询条件来定范围大小）、速度/指数（y)、fid、fname")
  @Handle(value = "根据条件不同、范围不同、进行sql拼接，然后完成查询后，把结果组装返回。")
  @Parameter(value = "index,methods,timeprecision,dateproperty,id,startdate,enddate,starttime,endtime,timeproperty")
  public HttpResult<Object> getRoadsectQueryData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getRoadsectQueryData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getHighSpeedQueryData")
  @ApiOperation(value = "道路查询-全省高速", notes = "数据查询-全省高速")
  @Requirement(value = "通过各种条件组合 ，可以查询全省高速的平均速度结果值。按各种时间颗粒不同，来确定统计的范围大小以及显示的样式等。")
  @Input(value = "指标、统计方式、时间颗粒、工作日类型、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围等")
  @Output(value = "时间片（按查询条件来定范围大小）、速度/指数（y)、fid、fname")
  @Handle(value = "根据条件不同、范围不同、进行sql拼接，然后完成查询后，把结果组装返回。")
  @Parameter(value = "index,methods,timeprecision,dateproperty,id,startdate,enddate,starttime,endtime,timeproperty")
  public HttpResult<Object> getHighSpeedQueryData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getHighSpeedQueryData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getCheckLineQueryData")
  @ApiOperation(value = "道路查询-核查线", notes = "数据查询-核查线")
  @Requirement(value = "通过各种条件组合 ，可以查询全省高速的流量结果值。按各种时间颗粒不同，来确定统计的范围大小以及显示的样式等。")
  @Input(value = "指标、统计方式、时间颗粒、工作日类型、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围等")
  @Output(value = "时间片（按查询条件来定范围大小）、速度/指数（y)、fid、fname")
  @Handle(value = "根据条件不同、范围不同、进行sql拼接，然后完成查询后，把结果组装返回。")
  @Parameter(value = "index,methods,timeprecision,dateproperty,id,startdate,enddate,starttime,endtime,timeproperty")
  public HttpResult<Object> getCheckLineQueryData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getCheckLineQueryData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getBuslaneCheckData")
  @ApiOperation(value = "公交查询-专用道查询", notes = "数据查询-公交查询")
  @Requirement(value = "通过各种条件组合 ，可以查询公交专用道的公交速度、速度比等结果值。按各种时间颗粒不同，来确定统计的范围大小以及显示的样式等。")
  @Input(value = "指标、统计方式、时间颗粒、工作日类型、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围等")
  @Output(value = "时间片（按查询条件来定范围大小）、速度/指数（y)、fid、fname")
  @Handle(value = "根据条件不同、范围不同、进行sql拼接，然后完成查询后，把结果组装返回。")
  @Parameter(value = "index,methods,timeprecision,dateproperty,id,startdate,enddate,starttime,endtime,timeproperty")
  public HttpResult<Object> getBuslaneCheckData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getBuslaneCheckData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getAreaCheckData")
  @ApiOperation(value = "公交查询-片区查询", notes = "数据查询-公交查询")
  @Requirement(value = "通过各种条件组合 ，可以查询公交专用道的公交速度、速度比等结果值。按各种时间颗粒不同，来确定统计的范围大小以及显示的样式等。")
  @Input(value = "指标、统计方式、时间颗粒、工作日类型、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围等")
  @Output(value = "时间片（按查询条件来定范围大小）、速度/指数（y)、fid、fname")
  @Handle(value = "根据条件不同、范围不同、进行sql拼接，然后完成查询后，把结果组装返回。")
  @Parameter(value = "index,methods,timeprecision,dateproperty,id,startdate,enddate,starttime,endtime,timeproperty")
  public HttpResult<Object> getAreaCheckData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getAreaCheckData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getCrossQueryData")
  @ApiOperation(value = "关口-二线关", notes = "节点查询")
  @Requirement(value = "通过各种条件组合 ，可以查询二线关的平均速度值。按各种时间颗粒不同，来确定统计的范围大小以及显示的样式等。")
  @Input(value = "指标、统计方式、时间颗粒、工作日类型、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围等")
  @Output(value = "时间片（按查询条件来定范围大小）、速度/指数（y)、fid、fname")
  @Handle(value = "根据条件不同、范围不同、进行sql拼接，然后完成查询后，把结果组装返回。")
  @Parameter(value = "index,methods,timeprecision,dateproperty,id,startdate,enddate,starttime,endtime,timeproperty")
  public HttpResult<Object> getCrossQueryData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getCrossQueryData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getRoadSectionFlow")
  @ApiOperation(value = "道路断面-流量", notes = "节点查询")
  @Requirement(value = "通过各种条件组合 ，可以查询道路断面的流量、流量小时数等值。按各种时间颗粒不同，来确定统计的范围大小以及显示的样式等。")
  @Input(value = "指标、统计方式、时间颗粒、工作日类型、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围等")
  @Output(value = "时间片（按查询条件来定范围大小）、速度/指数（y)、fid、fname")
  @Handle(value = "根据条件不同、范围不同、进行sql拼接，然后完成查询后，把结果组装返回。")
  @Parameter(value = "index,methods,timeprecision,dateproperty,id,startdate,enddate,starttime,endtime,timeproperty")
  public HttpResult<Object> getRoadSectionFlow(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getRoadSectionFlow(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getIntersectionData")
  @ApiOperation(value = "交叉口", notes = "节点查询")
  @Requirement(value = "通过各种条件组合 ，可以查询交叉口延误值                                。按各种时间颗粒不同，来确定统计的范围大小以及显示的样式等。")
  @Input(value = "指标、统计方式、时间颗粒、工作日类型、行政区id、开始日期、结束日期、开始时间、结束时间、时段范围等")
  @Output(value = "时间片（按查询条件来定范围大小）、速度/指数（y)、fid、fname")
  @Handle(value = "根据条件不同、范围不同、进行sql拼接，然后完成查询后，把结果组装返回。")
  @Parameter(value = "index,methods,timeprecision,dateproperty,id,startdate,enddate,starttime,endtime,timeproperty")
  public HttpResult<Object> getIntersectionData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getIntersectionData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getBusTrajectoryData")
  @ApiOperation(value = "公交轨迹", notes = "公交评估")
  @Requirement(value = "获取公交线路全天时段，各站点停靠时间等信息。")
  @Input(value = "时间日期、方向、线路名称")
  @Output(value = "车牌号、线路id、方向、到达时间、线路名称、站点名称、站点顺序。")
  @Handle
  @Parameter(value = "arrival_date，trip_direction，route_fname")
  public HttpResult<Object> getBusTrajectoryData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getBusTrajectoryData(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getBusArrivalInterval")
  @ApiOperation(value = "公交轨迹到站间隔", notes = "公交评估")
  @Requirement(value = "获取公交轨迹到站间隔车辆数。")
  @Input(value = "时间日期、站台名称、线路id")
  @Output(value = "间隔时间字段、间隔时间对应车辆数")
  @Handle(value = "后台根据各种间隔，计算出毫秒值分差，然后再挨个查询出总数量。")
  @Parameter(value = "arrival_date，stop_name，trip_fid")
  public HttpResult<Object> getBusArrivalInterval(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getBusArrivalInterval(map));
  }

  /**
   * .
   */
  @ResponseBody
  @PostMapping(value = "getBusStopInfo")
  @ApiOperation(value = "公交站点信息", notes = "公交评估-公交轨迹")
  @Requirement(value = "获取公交站点信息，并按顺序输出。")
  @Input(value = "线路id")
  @Output(value = "公交线路站点序号、站点名称")
  @Handle
  @Parameter(value = "trip_fid")
  public HttpResult<Object> getBusStopInfo(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(dataQueryService.getBusStopInfo(map));
  }
}

