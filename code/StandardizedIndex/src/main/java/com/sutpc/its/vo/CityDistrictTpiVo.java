package com.sutpc.its.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 各行政区高峰时段交通指数.
 *
 * @author admin
 * @date 2020/6/11 15:37
 */
@ApiModel("各行政区高峰时段交通指数")
@Data
public class CityDistrictTpiVo {

  @ApiModelProperty(value = "行政区id")
  private Long id;
  @ApiModelProperty(value = "行政区名称")
  private String name;
  @ApiModelProperty(value = "早高峰指数")
  private double morningTpi;
  @ApiModelProperty(value = "晚高峰指数")
  private double eveningTpi;
}
