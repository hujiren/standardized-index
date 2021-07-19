package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:10 2020/11/14.
 * @Description
 * @Modified By:
 */
@Data
public class PeriodValueVo {

  @ApiModelProperty(value = "时间片")
  private int period;
  @ApiModelProperty(value = "值")
  private String value;
}
