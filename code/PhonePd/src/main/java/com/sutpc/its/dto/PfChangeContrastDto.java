package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:31 2020/6/28.
 * @Description
 * @Modified By:
 */
@Data
public class PfChangeContrastDto {

  @ApiModelProperty(value = "实时人数")
  private int count;
  @ApiModelProperty(value = "环比上周")
  private double ratio;
  @ApiModelProperty(value = "人数变化-正数为增加，负数为减少")
  private int change;
}
