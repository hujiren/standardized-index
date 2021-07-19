package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:17 2020/11/10.
 * @Description
 * @Modified By:
 */
@Data
public class TotalRealTimeMonitorVo {

  @ApiModelProperty(value = "时间片-小时")
  private int period;
  @ApiModelProperty(value = "指数")
  private double tpi;
}
