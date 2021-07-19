package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Description
 * @Modified By:
 */
@Data
public class RoadSixDDto {

  @ApiModelProperty(value = "道路类型编号")
  private int roadTypeId;
  @ApiModelProperty(value = "道路类型名称")
  private String roadType;
  @ApiModelProperty(value = "拥堵里程比例")
  private double jamRatio;
  @ApiModelProperty(value = "高峰速度抖缓程度")
  private double peakSpeedKt;
  @ApiModelProperty(value = "高峰速度偏离系数")
  private double peakSpeedSk;
  @ApiModelProperty(value = "高峰速度稳定系数")
  private double peakSpeedStd;
  @ApiModelProperty(value = "高峰交通运行指数")
  private double avgTpi;
  @ApiModelProperty(value = "拥堵持续时长")
  private double avgJamTime;
}
