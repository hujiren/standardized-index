package com.sutpc.its.controller.v6;

import com.sutpc.its.dto.v6.UserDefinedInfoDto;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.po.v6.AreaInfoPo;
import com.sutpc.its.po.v6.UserDefinedInfoPo;
import com.sutpc.its.service.v6.IUserDefinedService;
import com.sutpc.its.vo.HistorySituationVo;
import com.sutpc.its.vo.JamRoadSectionVo;
import com.sutpc.its.vo.RealTimeTotalIndexVo;
import com.sutpc.its.vo.RegionOperationCharacteristicsVo;
import com.sutpc.its.vo.RelatedSectionVo;
import com.sutpc.its.vo.TotalMonitor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:22 2020/10/18.
 * @Description
 * @Modified By:
 */
@RestController
@RequestMapping(value = "user/defined")
@Api(tags = "自定义")
public class UserDefinedController {

  @Autowired
  private IUserDefinedService service;

  @PostMapping(value = "setDefinedInfo")
  @ApiImplicitParams(
      {
          @ApiImplicitParam(value = "名称", name = "name", required = true),
          @ApiImplicitParam(value = "组成区域", name = "region"),
          @ApiImplicitParam(value = "框选区域面积", name = "area"),
          @ApiImplicitParam(value = "框选坐标集", name = "points", required = false),
          @ApiImplicitParam(value = "点选路段坐标集，用逗号割开", name = "links", required = true, example = "11301,11302"),
          @ApiImplicitParam(value = "选择类型。1-框选，2-点选", name = "type", required = true),
          @ApiImplicitParam(value = "创建人", name = "creator")
      }
  )
  @ApiOperation(value = "存储选择区域信息")
  @ResponseBody
  public HttpResult setDefinedInfo(UserDefinedInfoDto dto) {
    return service.setDefinedInfo(dto.getName(), dto.getRegion(), dto.getArea(), dto.getPoints(),
        dto.getLinks(), dto.getType(), dto.getCreator());
  }

  @GetMapping(value = "getDefinedBaseInfo")
  @ApiOperation(value = "获取存储选择区域基础信息")
  public HttpResult<List<UserDefinedInfoPo>> getDefinedBaseInfo() {
    return HttpResult.ok(service.getDefinedBaseInfo());
  }

  @PutMapping(value = "updateDefinedInfo")
  @ApiOperation(value = "修改选择区域名称信息")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "名称", name = "name", required = true),
      @ApiImplicitParam(value = "信息id", name = "id", required = true)
  })
  public HttpResult updateDefinedInfo(@RequestParam String name, @RequestParam String id) {
    return service.updateDefinedInfo(name, id);
  }

  @DeleteMapping(value = "deleteDefinedInfoById/{id}")
  @ApiOperation(value = "删除选择区域信息")
  @ApiImplicitParam(value = "区域id", name = "id", required = true)
  public HttpResult deleteDefinedInfoById(@PathVariable("id") String id) {
    return service.deleteDefinedInfoById(id);
  }

  @GetMapping(value = "getAreaInfoPo")
  @ApiOperation(value = "获取指定区域详情")
  @ApiImplicitParam(value = "区域id", name = "id", required = true)
  public HttpResult<AreaInfoPo> getAreaInfoPo(@RequestParam String id) {
    return HttpResult.ok(service.getAreaInfoPo(id));
  }

  @GetMapping(value = "getRealTimeTotalIndex")
  @ApiOperation(value = "区域-实时总体指标")
  @ApiImplicitParam(value = "区域id", name = "id", required = true)
  public HttpResult<RealTimeTotalIndexVo> getRealTimeTotalIndex(String id) {
    return HttpResult.ok(service.getRealTimeTotalIndex(id));
  }

  @GetMapping(value = "getTotalRealTimeMonitor")
  @ApiOperation(value = "区域-总体实时监测")
  @ApiImplicitParam(value = "区域id", name = "id", required = true)
  public HttpResult<TotalMonitor> getTotalRealTimeMonitor(String id) {
    return HttpResult.ok(service.getTotalRealTimeMonitor(id));
  }

  @GetMapping(value = "getRelatedSectionMonitor")
  @ApiOperation(value = "区域-关联路段监测")
  @ApiImplicitParam(value = "区域id", name = "id", required = true)
  public HttpResult<List<RelatedSectionVo>> getRelatedSectionMonitor(String id) {
    return HttpResult.ok(service.getRelatedSectionMonitor(id));
  }

  @GetMapping(value = "getHistorySituationInfo")
  @ApiOperation(value = "区域-历史态势分析")
  @ApiImplicitParam(value = "区域id", name = "id", required = true)
  public HttpResult<HistorySituationVo> getHistorySituationInfo(String id) {
    return HttpResult.ok(service.getHistorySituationInfo(id));
  }

  @GetMapping(value = "getJamRoadSection")
  @ApiOperation(value = "区域-拥堵识别点")
  @ApiImplicitParam(value = "区域id", name = "id", required = true)
  public HttpResult<List<JamRoadSectionVo>> getJamRoadSection(String id) {
    return HttpResult.ok(service.getJamRoadSection(id));
  }

  @GetMapping(value = "getRegionOperationCharacteristics")
  @ApiOperation(value = "区域-区域运行特征")
  @ApiImplicitParam(value = "区域id", name = "id", required = true)
  public HttpResult<RegionOperationCharacteristicsVo> getRegionOperationCharacteristics(String id) {
    return HttpResult.ok(service.getRegionOperationCharacteristics(id));
  }
}
