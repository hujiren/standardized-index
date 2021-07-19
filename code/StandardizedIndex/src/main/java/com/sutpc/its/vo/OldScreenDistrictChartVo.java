package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:56 2020/10/29.
 * @Description
 * @Modified By:
 */
@Data
public class OldScreenDistrictChartVo {

  @ApiModelProperty(value = "行政区id")
  private int id;
  @ApiModelProperty(value = "时间片（15分钟）")
  private int period;
  @ApiModelProperty(value = "指数")
  private double tpi = 2.62;
  @ApiModelProperty(value = "速度")
  private double speed = 25.1;
}
