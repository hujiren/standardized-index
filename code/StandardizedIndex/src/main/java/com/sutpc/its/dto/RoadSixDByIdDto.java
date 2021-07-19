package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:22 2020/6/22.
 * @Description
 * @Modified By:
 */
@Data
public class RoadSixDByIdDto {

  @ApiModelProperty(value = "道路名称")
  private String name;
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
