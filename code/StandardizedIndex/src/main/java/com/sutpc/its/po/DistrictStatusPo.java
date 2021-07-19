package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:38 2020/9/9.
 * @Description
 * @Modified By:
 */
@Data
public class DistrictStatusPo {

  @ApiModelProperty(value = "行政区id")
  private int id;
  @ApiModelProperty(value = "指数")
  private double tpi;
  @ApiModelProperty(value = "状态")
  private String status;

  /**
   * .
   */
  public void setTpi(double tpi) {
    this.tpi = tpi;
    if (tpi >= 8) {
      this.status = "拥堵";
    } else if (tpi >= 6) {
      this.status = "较拥堵";
    } else if (tpi >= 4) {
      this.status = "缓行";
    } else if (tpi >= 2) {
      this.status = "基本畅通";
    } else {
      this.status = "畅通";
    }
  }
}
