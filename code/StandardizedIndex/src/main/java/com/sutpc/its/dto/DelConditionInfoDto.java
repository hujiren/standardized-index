package com.sutpc.its.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:57 2020/5/26.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel("删除条件-参数")
public class DelConditionInfoDto {

  @ApiModelProperty(value = "数据库唯一id", required = true)
  private String id;
}
