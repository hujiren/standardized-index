package com.sutpc.its.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:38 2020/5/26.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "查询条件实体类")
public class ConditionInfoEntity {

  @ApiModelProperty(value = "数据库唯一id", required = false)
  private String id;
  @ApiModelProperty(value = "模块")
  private String module;
  @ApiModelProperty("条件json")
  private String param;
  @ApiModelProperty("名称")
  private String name;
  @ApiModelProperty("创建者/当前用户名")
  private String creator;
}
