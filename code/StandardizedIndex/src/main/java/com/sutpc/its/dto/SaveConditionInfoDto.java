package com.sutpc.its.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:41 2020/5/26.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel("条件保存-请求参数")
public class SaveConditionInfoDto {

  @ApiModelProperty(value = "模块", required = true)
  private String module;
  @ApiModelProperty(value = "条件json", required = true)
  private String param;
  @ApiModelProperty(value = "名称", required = true)
  private String name;
  @ApiModelProperty(value = "创建者/当前用户名", required = true)
  private String creator;
}
