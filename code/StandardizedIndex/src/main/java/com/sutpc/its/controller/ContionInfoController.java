package com.sutpc.its.controller;

import com.sutpc.its.annotation.Handle;
import com.sutpc.its.annotation.Input;
import com.sutpc.its.annotation.Output;
import com.sutpc.its.annotation.Parameter;
import com.sutpc.its.annotation.Requirement;
import com.sutpc.its.annotation.Scanning;
import com.sutpc.its.dto.DelConditionInfoDto;
import com.sutpc.its.dto.QueryConditionInfoDto;
import com.sutpc.its.dto.SaveConditionInfoDto;
import com.sutpc.its.model.ConditionInfoEntity;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.service.ConditionInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 福田中心区条件保存。就是把系统查询的那些所有条件用json保存到数据库。
 */
@RestController
@RequestMapping(value = "/condition")
@Scanning
@Api(tags = "条件查询")
public class ContionInfoController {

  @Autowired
  private ConditionInfoService service;

  @ApiOperation("保存條件")
  @PostMapping(value = "setConditionInfo")
  @Requirement(value = "保存查询条件到系统。")
  @Input(value = "所在模块，条件json，自定义名称，创建者/当前用户。")
  @Output(value = "成功则返回成功码。")
  @Handle(value = "直接把需要保存的数据装配到实体类，再通过mybatis进行持久化操作。")
  @Parameter(value = "module，param，name，creator")
  public HttpResult<List<ConditionInfoEntity>> setConditionInfo(
      SaveConditionInfoDto conditionInfoDto)
      throws Exception {
    return HttpResult.ok(service.setConditionInfo(conditionInfoDto));
  }

  @ApiOperation("查询条件列表")
  @PostMapping(value = "getConditionInfo")
  @Requirement(value = "展示出已经保存的查询条件列表。单击列表，所选条件会自动装配到条件查询框内，然后即可点击查询，应用条件了。")
  @Input(value = "查询需要输入当前模块，当前用户/创建者。")
  @Output(value = "输出当前用户所选模块已经保存的查询条件近次的条件集合。")
  @Handle(value = "通过条件装配直接查询数据库。")
  @Parameter(value = "module，creator")
  public HttpResult<List<ConditionInfoEntity>> getConditionInfo(
      QueryConditionInfoDto queryConditionInfoDto) {
    return HttpResult.ok(service.getConditionInfo(queryConditionInfoDto));
  }

  @ApiOperation("删除条件信息")
  @PostMapping(value = "delConditionInfo")
  @Requirement(value = "根据唯一id删除掉不需要的已经保存的条件信息。")
  @Input(value = "操作需要传入需要删除的条件的唯一id。")
  @Output(value = "")
  @Handle(value = "直接调用接口操作数据库，删掉数据。")
  @Parameter(value = "id")
  public HttpResult<Object> delConditionInfo(DelConditionInfoDto dto) throws Exception {
    return service.delConditionInfo(dto);
  }
}
