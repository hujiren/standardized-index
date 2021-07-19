package com.sutpc.its.controller;

import com.sutpc.its.dto.CapacityDto;
import com.sutpc.its.dto.CheckDto;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.po.BusRoadsectStatus;
import com.sutpc.its.po.SubAreaInfo;
import com.sutpc.its.service.FtCenterService;
import com.sutpc.its.vo.PeriodValueVo;
import com.sutpc.its.vo.GreenWaveVo;
import com.sutpc.its.vo.LinkVo;
import com.sutpc.its.vo.PoiNearRoadSectionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:53 2020/3/19.
 * @Description
 * @Modified By:
 */
@RequestMapping("/ftcenter")
@RestController
@Api(tags = "福田中心区道路监测等页面与指数相关的定制开发控制入口")
public class FtCenterController {

  @Autowired
  private FtCenterService ftCenterService;

  @ResponseBody
  @PostMapping(value = "getOverAllIndex")
  public HttpResult<Object> getOverAllIndex(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(ftCenterService.getOverAllIndex(map));
  }

  @ResponseBody
  @PostMapping(value = "getTotalIndexDayStatus")
  public HttpResult<Object> getTotalIndexDayStatus(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(ftCenterService.getTotalIndexDayStatus(map));
  }

  @ResponseBody
  @PostMapping("getRoadTrafficInfo")
  public HttpResult<Object> getRoadTrafficInfo() {
    return HttpResult.ok(ftCenterService.getRoadTrafficInfo());
  }

  @ResponseBody
  @GetMapping("getFtcCapacity")
  public HttpResult<CapacityDto> getFtcCapacity() {
    return HttpResult.ok(ftCenterService.getFtcCapacity());
  }

  @GetMapping(value = "getGreenWaveInfo")
  @ApiOperation(value = "获取绿波速度等信息")
  @ApiImplicitParam(value = "子区id（多个用英文状态的逗号','隔开）", name = "ids", required = true)
  public HttpResult<GreenWaveVo> getGreenWaveInfo(@RequestParam("ids") String ids) {
    return HttpResult.ok(ftCenterService.getGreenWaveInfo(ids));
  }

  @GetMapping(value = "getRealTimeLinkInfo")
  @ApiOperation(value = "小路段实时信息")
  public HttpResult<List<LinkVo>> getRealTimeLinkInfo() {
    return HttpResult.ok(ftCenterService.getRealTimeLinkInfo());
  }

  @GetMapping(value = "getGreenWaveAreaInfo")
  @ApiOperation(value = "新-获取绿波信息")
  @ApiImplicitParam(value = "子区id（多个用英文状态的逗号','隔开）", name = "ids", required = true)
  public HttpResult<List<SubAreaInfo>> getGreenWaveAreaInfo(@RequestParam("ids") String ids) {
    return HttpResult.ok(ftCenterService.getGreenWaveAreaInfo(ids));
  }

  @GetMapping(value = "getBusRoadsectStatusNow")
  @ApiOperation(value = "公交速度实时状态")
  public HttpResult<List<BusRoadsectStatus>> getBusRoadsectStatusNow() {
    return HttpResult.ok(ftCenterService.getBusRoadsectStatusNow());
  }

  @GetMapping(value = "poi/near/road")
  @ApiOperation(value = "热点区域-周边路段")
  public HttpResult<List<PoiNearRoadSectionVo>> getPoiNearRoadsect(CheckDto dto) {
    return HttpResult.ok(ftCenterService.getPoiNearRoadsect(dto));
  }

  @GetMapping(value = "bus/{type}")
  @ApiOperation(value = "中心区-公交速度/速度比")
  @ApiImplicitParam(value = "指标：公交速度：speed,速度比：ratio", name = "type", required = true)
  public HttpResult<List<PeriodValueVo>> getFtcBusSpeedAndRatio(CheckDto dto,
      @PathVariable String type) {
    return HttpResult.ok(ftCenterService.getFtcBusSpeedAndRatio(dto, type));
  }

  @GetMapping(value = "getSubsectQueryData")
  @ApiOperation(value = "道路分析-中心区-平均速度/指数")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "指标：平均速度：speed,指数：tpi", name = "index", required = true),
      @ApiImplicitParam(value = "道路等级（不限：0，主干道：3，次干道：4）", name = "roadLevel")
  })
  public HttpResult<List<PeriodValueVo>> getSubsectQueryData(CheckDto dto, int roadLevel,
      String index) {
    return HttpResult.ok(ftCenterService.getSubsectQueryData(dto, roadLevel, index));
  }
}
