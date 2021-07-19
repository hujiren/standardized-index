package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:56 2020/8/11.
 * @Description
 * @Modified By:
 */
@Data
public class RoadSectionVo {

  @ApiModelProperty(value = "绿波干线")
  private String name;
  @ApiModelProperty(value = "方向")
  private String dir;
  @ApiModelProperty(value = "速度")
  private double speed;
}
