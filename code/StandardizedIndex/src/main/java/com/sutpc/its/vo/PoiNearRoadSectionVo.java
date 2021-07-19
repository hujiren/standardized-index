package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:07 2020/11/14.
 * @Description
 * @Modified By:
 */
@Data
public class PoiNearRoadSectionVo {

  @ApiModelProperty(value = "时间片")
  private int period;
  @ApiModelProperty(value = "中路段id")
  private int id;
  @ApiModelProperty(value = "指数")
  private String tpi;
  @ApiModelProperty(value = "速度")
  private String speed;
}
