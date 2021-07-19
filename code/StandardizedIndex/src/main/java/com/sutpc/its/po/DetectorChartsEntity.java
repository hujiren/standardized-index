package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:08 2020/7/22.
 * @Description
 * @Modified By:
 */
@Data
public class DetectorChartsEntity {

  @ApiModelProperty(value = "小时")
  private int hour;
  @ApiModelProperty(value = "流量")
  private int volume;
}
