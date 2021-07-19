package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:59 2020/9/7.
 * @Description
 * @Modified By:
 */
@Data
public class JamLengthListPo {

  @ApiModelProperty(value = "状态")
  private String status;
  @ApiModelProperty(value = "拥堵里程")
  private double jamLength;
}
