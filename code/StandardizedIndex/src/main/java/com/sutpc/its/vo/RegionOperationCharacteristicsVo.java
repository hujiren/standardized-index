package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:32 2020/11/14.
 * @Description
 * @Modified By:
 */
@Data
public class RegionOperationCharacteristicsVo {

  @ApiModelProperty(value = "拥堵里程比例")
  private String jamRatio;
  @ApiModelProperty(value = "拥堵持续时长")
  private String jamTime;
  @ApiModelProperty(value = "高峰运行指数")
  private String tpi;
  @ApiModelProperty(value = "高峰速度稳定性")
  private String speedStability;
  @ApiModelProperty(value = "高峰速度偏离系数")
  private String speedDeviationCoefficient;
  @ApiModelProperty(value = "高峰速度抖缓程度")
  private String speedShake;
}
