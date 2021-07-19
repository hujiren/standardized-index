package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:11 2020/11/3.
 * @Description
 * @Modified By:
 */
@Data
public class SpeedChartVo {

  @ApiModelProperty(value = "时间片")
  private int period;
  @ApiModelProperty(value = "速度")
  private double speed;
}
