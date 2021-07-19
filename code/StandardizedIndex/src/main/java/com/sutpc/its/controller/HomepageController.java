package com.sutpc.its.controller;

import com.sutpc.its.annotation.Handle;
import com.sutpc.its.annotation.Input;
import com.sutpc.its.annotation.Output;
import com.sutpc.its.annotation.Parameter;
import com.sutpc.its.annotation.Requirement;
import com.sutpc.its.annotation.Scanning;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.po.BlockInfoAllInfoPo;
import com.sutpc.its.po.CrossInfoPo;
import com.sutpc.its.po.DistrictStatusPo;
import com.sutpc.its.po.DistrictTpiChartsPo;
import com.sutpc.its.po.JamLengthInfoPo;
import com.sutpc.its.po.JamRoadsectInfoPo;
import com.sutpc.its.po.PoiAvgTpiPo;
import com.sutpc.its.po.RealTimeJamLengthPo;
import com.sutpc.its.service.IHomepageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:38 2020/8/26.
 * @Description
 * @Modified By:
 */
@RestController
@Api(tags = "首页（六期）")
@RequestMapping(value = "homepage")
@Scanning
public class HomepageController {

  @Autowired
  private IHomepageService service;

  @GetMapping(value = "getBlockInfo")
  @ApiOperation(value = "街道片区-恶化数、十大拥堵、所有街道信息")
  @Requirement(value = "获取街道片区的恶化数、十大拥堵片区、所有街道片区信息。")
  @Input(value = "无")
  @Output(value = "恶化片区数、十大拥堵片区列表、街道片区信息")
  @Handle(value = "恶化片区需要计算当前的值和上周同期的值，指数大于6，环比大于5%的为恶化片区，然后计数。十大拥堵片区直接区前十。")
  @Parameter(value = "内置一些时间信息、行政区信息，前端不需要参数。")
  public HttpResult<BlockInfoAllInfoPo> getBlockInfo() {
    return HttpResult.ok(service.getBlockInfo());
  }

  @GetMapping(value = "getDistrictTpiCharts")
  @ApiOperation(value = "全市交通运行")
  @Requirement(value = "获取全市交通运行状态。用15分钟时间颗粒来统计指数。")
  @Input(value = "无。")
  @Output(value = "行政区名称、行政区id、时间片（15分钟）、指数")
  @Handle
  @Parameter(value = "内置当前日期和当前时间片参数")
  public HttpResult<List<DistrictTpiChartsPo>> getDistrictTpiCharts() {
    return HttpResult.ok(service.getDistrictTpiCharts());
  }

  @GetMapping(value = "getCrossInfo")
  @ApiOperation(value = "获取关口信息")
  @Requirement(value = "获取全市关口运行状态列表信息。")
  @Input(value = "无")
  @Output(value = "关口id、关口名称、关口方向、速度、运行状态")
  @Handle
  @Parameter(value = "内置当前日期和当前时间片参数")
  public HttpResult<List<CrossInfoPo>> getCrossInfo() {
    return HttpResult.ok(service.getCrossInfoPo());
  }

  @GetMapping(value = "getJamRoadsectInfo")
  @ApiOperation(value = "获取十大拥堵路段")
  @Requirement(value = "获取全市/内置行政区的十大拥堵路段详细信息。")
  @Input(value = "无。")
  @Output(value = "路段id、路段名称、路段起点、路段终点、速度、环比")
  @Handle(value = "后台会同时查询出当前实时日期数据和上周同期的数据，然后循环检查做对比，计算出环比信息。")
  @Parameter(value = "内置当前日期和当前时间片参数")
  public HttpResult<List<JamRoadsectInfoPo>> getJamRoadsectInfo() {
    return HttpResult.ok(service.getJamRoadsectInfo());
  }

  @GetMapping(value = "getPoiAvgTpiAndRatio")
  @ApiOperation(value = "获取热点平均指数和环比")
  @Requirement(value = "获取全市热点平均指数和环比情况。")
  @Input(value = "无。")
  @Output(value = "指数、热点运行状态、环比")
  @Handle(value = "后台会同时查询出当前实时日期数据和上周同期的数据，然后循环检查做对比，计算出环比信息。")
  @Parameter(value = "内置当前日期和当前时间片参数")
  public HttpResult<PoiAvgTpiPo> getPoiAvgTpiAndRatio() {
    return HttpResult.ok(service.getPoiAvgTpiAndRatio());
  }

  @GetMapping(value = "getJamLength")
  @ApiOperation(value = "获取拥堵里程及环比")
  @Requirement(value = "获取拥堵里程以及环比。")
  @Input(value = "无。")
  @Output(value = "拥堵里程比例、环比")
  @Handle(value = "后台会同时查询出当前实时日期数据和上周同期的数据，然后循环检查做对比，计算出环比信息。")
  @Parameter(value = "内置当前日期和当前时间片参数。")
  public HttpResult<JamLengthInfoPo> getJamLength() {
    return HttpResult.ok(service.getJamLength());
  }

  @GetMapping(value = "getRealTimeJamLength")
  @ApiOperation(value = "实时拥堵里程与上周拥堵里程对比")
  @Requirement(value = "获取实时拥堵里程以及上周拥堵里程，在页面展示对比。")
  @Input(value = "无。")
  @Output(value = "当前拥堵里程列表、上周平均拥堵里程列表")
  @Handle(value = "直接根据不同日期查询不同的数据，并组装成一个对象返回。")
  @Parameter(value = "无")
  public HttpResult<RealTimeJamLengthPo> getRealTimeJamLength() {
    return HttpResult.ok(service.getRealTimeJamLength());
  }

  @GetMapping(value = "getDistrictStatus")
  @ApiOperation(value = "行政区实时指数路况")
  @Requirement(value = "获取全市行政区实时指数信息、路况信息。")
  @Input(value = "无。")
  @Output(value = "行政区id、指数、交通运行状态")
  @Handle
  @Parameter(value = "")
  public HttpResult<List<DistrictStatusPo>> getDistrictStatus() {
    return HttpResult.ok(service.getDistrictStatus());
  }
}
