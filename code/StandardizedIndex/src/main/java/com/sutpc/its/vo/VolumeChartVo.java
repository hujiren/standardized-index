package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:05 2020/11/3.
 * @Description
 * @Modified By:
 */
@Data
public class VolumeChartVo {

  @ApiModelProperty(value = "时间片")
  private int period;
  @ApiModelProperty(value = "路段流量")
  private int volume;
}
