package com.sutpc.its.controller;

import com.sutpc.its.dto.FlowsDistributionEntity;
import com.sutpc.its.dto.KeyAreaInfoDto;
import com.sutpc.its.dto.KeyAreaLikeDto;
import com.sutpc.its.dto.PfChangeContrastDto;
import com.sutpc.its.dto.PfChangeDto;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.model.entity.PfChangeEntity;
import com.sutpc.its.service.HotSpotFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hotspotflow")
@Api(tags = "重点区域/热点人流量")
public class HotSpotFlowController {

  @Autowired
  private HotSpotFlowService hotSpotFlowService;

  @ResponseBody
  @PostMapping(value = "getHotSpotFlow")
  @ApiModelProperty(value = "热点人流量", notes = "全境交通一张图")
  public HttpResult<Object> getHotSpotFlow(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(hotSpotFlowService.getHotSpotFlow(map));
  }

  @ResponseBody
  @PostMapping(value = "getHotspotFlowLineData")
  @ApiModelProperty(value = "重点区域热点人流量曲线", notes = "重点区域")
  public HttpResult<Object> getHotspotFlowLineData(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(hotSpotFlowService.getHotspotFlowLineData(map));
  }

  @ResponseBody
  @PostMapping(value = "getHotspotFlowStatus")
  public HttpResult<Object> getHotspotFlowStatus(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(hotSpotFlowService.getHotspotFlowStatus(map));
  }

  @ResponseBody
  @PostMapping(value = "getKeyAreaInfo")
  @ApiOperation(value = "区域人流预警排名")
  public ResponseEntity<List<KeyAreaInfoDto>> getKeyAreaInfo() {
    return new ResponseEntity<>(hotSpotFlowService.getKeyAreaInfo(), HttpStatus.OK);
  }

  @ResponseBody
  @PostMapping(value = "getPfChangeChartData")
  @ApiOperation(value = "区域人流变化情况-曲线图")
  public ResponseEntity<List<PfChangeDto>> getPfChangeChartData(PfChangeEntity entity) {
    return new ResponseEntity<>(hotSpotFlowService.getPfChangeChartData(entity), HttpStatus.OK);
  }

  @ResponseBody
  @PostMapping(value = "getPresentPf")
  @ApiOperation(value = "当前人流对比信息等")
  public ResponseEntity<PfChangeContrastDto> getPresentPf(PfChangeEntity entity) {
    return new ResponseEntity<>(hotSpotFlowService.getPresentPf(entity), HttpStatus.OK);
  }

  @ApiImplicitParam(value = "名称提示", name = "name", required = true)
  @ApiOperation(value = "模糊查询重点区域")
  @GetMapping(value = "getKeyAreaInfoLikeName")
  public ResponseEntity<List<KeyAreaLikeDto>> getKeyAreaInfoLikeName(@RequestParam String name) {
    return new ResponseEntity<>(hotSpotFlowService.getKeyAreaInfoLikeName(name), HttpStatus.OK);
  }

  @ApiOperation(value = "热点人流等级分布")
  @GetMapping(value = "getFlowsGroupByStatus")
  public ResponseEntity<FlowsDistributionEntity> getFlowsGroupByStatus() {
    return new ResponseEntity<>(hotSpotFlowService.getFlowsGroupByStatus(), HttpStatus.OK);
  }

  @ResponseBody
  @RequestMapping(value = "/selectHotspotPassFlow", method = RequestMethod.POST)
  @ApiOperation(value = "旧指数大屏-热点人流列表")
  public List<Map<String, Object>> selectHotspotPassFlow() {
    return hotSpotFlowService.selectHotspotPassFlow();
  }
}
