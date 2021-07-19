package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:36 2020/9/7.
 * @Description
 * @Modified By:
 */
@Data
public class JamLengthInfoPo {

  @ApiModelProperty(value = "拥堵里程")
  private double jamLength;
  @ApiModelProperty(value = "比例.已经乘以100，直接使用")
  private double ratio;
}
