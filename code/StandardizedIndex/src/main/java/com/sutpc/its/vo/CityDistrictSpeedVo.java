package com.sutpc.its.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 各行政区交通运行速度及变化.
 *
 * @author admin
 * @date 2020/6/11 15:37
 */
@ApiModel("各行政区交通运行速度及变化")
@Data
public class CityDistrictSpeedVo {

  @ApiModelProperty(value = "行政区id")
  private Long id;
  @ApiModelProperty(value = "行政区名称")
  private String name;
  @ApiModelProperty(value = "早高峰速度")
  private double morningSpeed;
  @ApiModelProperty(value = "晚高峰速度")
  private double eveningSpeed;
  @ApiModelProperty(value = "高峰时段速度")
  private double peakSpeed;
  @ApiModelProperty(value = "高峰时段速度环比")
  private double peakCycleRatio;
}
