package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:39 2020/11/10.
 * @Description
 * @Modified By:
 */
@Data
public class RealTimeTotalIndexVo {

  @ApiModelProperty(value = "平均速度")
  private double speed;
  @ApiModelProperty(value = "指数")
  private double tpi;
  @ApiModelProperty(value = "拥堵里程")
  private double jamLength;
  @ApiModelProperty(value = "拥堵里程比例")
  private double jamRatio;
}
