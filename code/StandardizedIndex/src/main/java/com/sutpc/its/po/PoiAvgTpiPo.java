package com.sutpc.its.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:48 2020/8/28.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "热点区域指数和环比")
public class PoiAvgTpiPo {

  @ApiModelProperty(value = "指数")
  private double tpi;
  @ApiModelProperty(value = "热点运行状态")
  private String status;
  @ApiModelProperty(value = "环比")
  private double ratio;

  public double getTpi() {
    return tpi;
  }

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
