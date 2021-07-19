package com.sutpc.its.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:51 2020/5/26.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "查询列表-请求参数")
public class QueryConditionInfoDto {

  @ApiModelProperty(value = "模块", required = true)
  private String module;
  @ApiModelProperty(value = "创建者/当前用户名", required = true)
  private String creator;
}
