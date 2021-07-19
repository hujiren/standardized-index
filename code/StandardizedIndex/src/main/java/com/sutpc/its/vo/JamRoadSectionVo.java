package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 19:35 2020/11/10.
 * @Description
 * @Modified By:
 */
@Data
public class JamRoadSectionVo {

  @ApiModelProperty(value = "中路段id")
  private int id;
  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "指数")
  private double tpi;
}
