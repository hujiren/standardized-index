package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:28 2020/11/14.
 * @Description
 * @Modified By:
 */
@Data
public class PeriodValuePo {

  @ApiModelProperty(value = "时间片")
  private int period;
  @ApiModelProperty(value = "速度")
  private double value;

}
