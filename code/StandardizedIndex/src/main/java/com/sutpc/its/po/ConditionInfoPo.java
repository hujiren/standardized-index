package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:25 2020/5/26.
 * @Description
 * @Modified By:
 */
@Data
public class ConditionInfoPo {

  @ApiModelProperty(value = "数据库唯一id")
  private String id;
  @ApiModelProperty(value = "模块")
  private String module;
  @ApiModelProperty(value = "条件json")
  private String param;
  @ApiModelProperty(value = "名称")
  private String name;
  @ApiModelProperty(value = "创建者/当前用户名")
  private String creator;
}
