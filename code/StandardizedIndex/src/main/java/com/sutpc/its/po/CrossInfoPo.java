package com.sutpc.its.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:30 2020/8/27.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "关口信息")
public class CrossInfoPo {

  @ApiModelProperty(value = "关口id")
  private int id;
  @ApiModelProperty(value = "关口名称")
  private String name;
  @ApiModelProperty(value = "关口方向")
  private String dir;
  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "状态")
  private String status;

  public double getSpeed() {
    return speed;
  }

  /**
   * 设置状态.
   */
  public void setSpeed(double speed) {
    this.speed = speed;
    if (speed > 50) {
      this.status = "畅通";
    } else if (speed > 35) {
      this.status = "基本畅通";
    } else if (speed > 20) {
      this.status = "缓行";
    } else if (speed > 15) {
      this.status = "较拥堵";
    } else {
      this.status = "拥堵";
    }
  }
}
