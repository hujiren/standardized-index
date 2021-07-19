package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:15 2020/5/29.
 * @Description
 * @Modified By:
 */
@Data
public class PeakHotPotPo {

  @ApiModelProperty(value = "热点名称")
  private String poiName;
  @ApiModelProperty(value = "指数")
  private double tpi;
}
