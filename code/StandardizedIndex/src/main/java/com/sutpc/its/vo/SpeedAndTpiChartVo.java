package com.sutpc.its.vo;

import com.sutpc.its.tools.TpiUtils;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:47 2020/11/3.
 * @Description
 * @Modified By:
 */
@Data
public class SpeedAndTpiChartVo {

  private int period;
  private double speed;
  private double tpi;

  public void setSpeed(double speed) {
    this.speed = TpiUtils.getByDigit(speed, 1);
  }

  public void setTpi(double tpi) {
    this.tpi = TpiUtils.getByDigit(tpi, 2);
  }
}
