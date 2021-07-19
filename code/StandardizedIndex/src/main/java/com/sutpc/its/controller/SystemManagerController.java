package com.sutpc.its.controller;

import com.sutpc.its.model.HttpResult;
import com.sutpc.its.model.NounDefinitionEntity;
import com.sutpc.its.service.SystemManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/systemManager")
@Api(tags = "系统管理功能模块")
public class SystemManagerController {

  @Autowired
  private SystemManagerService systemManagerService;

  @ResponseBody
  @PostMapping(value = "getCityRoadNetworkBaseInfo")
  @ApiOperation(value = "全市路网基础信息", notes = "系统指引-数据概览")
  public HttpResult<Object> getCityRoadNetworkBaseInfo() {
    return HttpResult.ok(systemManagerService.getCityRoadNetworkBaseInfo());
  }

  @ResponseBody
  @PostMapping(value = "getEveryVehicleNums")
  @ApiOperation(value = "各类车辆接入数", notes = "系统指引-数据概览")
  public HttpResult<Object> getEveryVehicleNums() {
    return HttpResult.ok(systemManagerService.getEveryVehicleNums());
  }

  @ResponseBody
  @PostMapping(value = "getDevicePointNums")
  @ApiOperation(value = "设备点位数", notes = "系统指引-数据概览")
  public HttpResult<Object> getDevicePointNums() {
    return HttpResult.ok(systemManagerService.getDevicePointNums());
  }

  @ResponseBody
  @PostMapping(value = "getNounDefinitionList")
  @ApiOperation(value = "名词解释列表", notes = "系统指引-名词解释")
  public HttpResult<Object> getNounDefinitionList() {
    return HttpResult.ok(systemManagerService.getNounDefinitionList());
  }

  @ResponseBody
  @PostMapping(value = "updateNounDefinition")
  @ApiOperation(value = "名称解释-修改", notes = "系统指引-名称解释")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "id", name = "id", required = true),
      @ApiImplicitParam(value = "名词", name = "fname", required = true)
  })
  public HttpResult<Object> updateNounDefinition(NounDefinitionEntity entity) {
    return HttpResult.ok(systemManagerService.updateNounDefinition(entity));
  }

  @ResponseBody
  @PostMapping(value = "setNounDefinition")
  @ApiOperation(value = "名词解释-插入", notes = "系统指引-名词解释")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "名词", name = "fname", required = true),
      @ApiImplicitParam(value = "上传人", name = "uploadPerson", required = true),
      @ApiImplicitParam(value = "描述", name = "description", required = true)
  })
  public HttpResult<Object> setNounDefinition(NounDefinitionEntity entity) {
    return HttpResult.ok(systemManagerService.setNounDefinition(entity));
  }

  @ResponseBody
  @PostMapping(value = "deleteNounDefinition")
  @ApiOperation(value = "名词解释-删除", notes = "系统指引-名词解释")
  @ApiImplicitParam(value = "id", name = "id", required = true)
  public HttpResult<Object> deleteNounDefinition(@RequestParam("id") String id) {
    return HttpResult.ok(systemManagerService.deleteNounDefinition(id));
  }

  @ResponseBody
  @PostMapping(value = "setSysSuggestion")
  @ApiOperation(value = "建议")
  public HttpResult<Object> setSysSuggestion(@RequestParam Map<String, Object> map) {
    return HttpResult.ok(systemManagerService.setSysSuggestion(map));
  }
}
