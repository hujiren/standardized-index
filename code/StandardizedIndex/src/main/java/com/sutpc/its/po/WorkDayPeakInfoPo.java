package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 20:24 2020/5/25.
 * @Description
 * @Modified By:
 */
@Data
public class WorkDayPeakInfoPo {

  @ApiModelProperty(value = "工作日高峰速度机值（最大或最小）的日期")
  private String date;
  @ApiModelProperty(value = "工作日高峰速度极值（最大或最小）")
  private double speed;
}
