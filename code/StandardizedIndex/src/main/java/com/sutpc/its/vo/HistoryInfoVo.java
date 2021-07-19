package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:24 2020/11/10.
 * @Description
 * @Modified By:
 */
@Data
public class HistoryInfoVo {

  @ApiModelProperty(value = "时间")
  private int time;
  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "指数")
  private double tpi;
}
