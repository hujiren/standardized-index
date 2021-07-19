package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:14 2020/11/13.
 * @Description
 * @Modified By:
 */
@Data
public class PoiNearRoadSectionPo {

  @ApiModelProperty(value = "时间片")
  private int period;
  @ApiModelProperty(value = "中路段id")
  private int id;
  @ApiModelProperty(value = "指数")
  private double tpi;
  @ApiModelProperty(value = "速度")
  private double speed;

}
