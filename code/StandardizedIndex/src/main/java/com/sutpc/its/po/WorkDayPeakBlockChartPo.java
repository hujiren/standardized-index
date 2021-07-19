package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:57 2020/5/27.
 * @Description
 * @Modified By:
 */
@Data
public class WorkDayPeakBlockChartPo {

  @ApiModelProperty(value = "街道名称")
  private String name;
  @ApiModelProperty(value = "速度")
  private double speed;
}
