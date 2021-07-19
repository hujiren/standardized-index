package com.sutpc.its.controller.v6;

import com.sutpc.its.model.HttpResult;
import com.sutpc.its.po.v6.BikePointPo;
import com.sutpc.its.service.v6.IBikeService;
import com.sutpc.its.vo.SlowBikeOperationVo;
import com.sutpc.its.vo.StationSurroundRankingVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:47 2020/10/18.
 * @Description
 * @Modified By:
 */
@RestController
@RequestMapping(value = "bike")
@Api(tags = "单车")
public class BikeController {

  @Autowired
  private IBikeService service;

  @GetMapping(value = "getBikeStationInfo")
  @ApiOperation(value = "出行热力图点位")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "日期", name = "time", defaultValue = "20181012", required = true),
      @ApiImplicitParam(value = "小时", name = "hour", defaultValue = "8", required = true),
      @ApiImplicitParam(value = "开关锁。1-开锁(公共交通-共享单车)，2-关锁（共享单车-公共交通）", name = "lockStatus", defaultValue = "1", required = true)
  })
  public HttpResult<List<BikePointPo>> getBikeStationInfo(@RequestParam int time,
      @RequestParam int hour, @RequestParam int lockStatus) {
    return HttpResult.ok(service.getBikeStationInfo(time, hour, lockStatus));
  }

  @GetMapping(value = "getSlowBikeOperationInfo")
  @ApiOperation(value = "慢行单车运行情况")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "日期", name = "time", defaultValue = "20181012", required = true),
      @ApiImplicitParam(value = "小时", name = "hour", defaultValue = "8", required = true),
      @ApiImplicitParam(value = "开关锁。1-开锁(公共交通-共享单车)，2-关锁（共享单车-公共交通）", name = "lockStatus", defaultValue = "1", required = true)
  })
  public HttpResult<SlowBikeOperationVo> getSlowBikeOperationInfo(@RequestParam int time,
      @RequestParam int hour, @RequestParam int lockStatus) {
    return HttpResult.ok(service.getSlowBikeOperationInfo(time, hour, lockStatus));
  }

  @GetMapping(value = "getSubwaySurroundAmount")
  @ApiOperation(value = "地铁周边接驳量排名")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "日期", name = "time", defaultValue = "20181012", required = true),
      @ApiImplicitParam(value = "小时", name = "hour", defaultValue = "8", required = true),
      @ApiImplicitParam(value = "开关锁。1-开锁(公共交通-共享单车)，2-关锁（共享单车-公共交通）", name = "lockStatus", defaultValue = "1", required = true)
  })
  public HttpResult<List<StationSurroundRankingVo>> getSubwaySurroundAmount(@RequestParam int time,
      @RequestParam int hour, @RequestParam int lockStatus) {
    return HttpResult.ok(service.getSubwaySurroundAmount(time, hour, lockStatus));
  }

  @GetMapping(value = "getBusSurroundAmount")
  @ApiOperation(value = "公交周边接驳量排名")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "日期", name = "time", defaultValue = "20181012", required = true),
      @ApiImplicitParam(value = "小时", name = "hour", defaultValue = "8", required = true),
      @ApiImplicitParam(value = "开关锁。1-开锁(公共交通-共享单车)，2-关锁（共享单车-公共交通）", name = "lockStatus", defaultValue = "1", required = true)
  })
  public HttpResult<List<StationSurroundRankingVo>> getBusSurroundAmount(@RequestParam int time,
      @RequestParam int hour, @RequestParam int lockStatus) {
    return HttpResult.ok(service.getBusSurroundAmount(time, hour, lockStatus));
  }
}
