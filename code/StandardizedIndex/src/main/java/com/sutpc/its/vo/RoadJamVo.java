package com.sutpc.its.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:33 2020/8/7.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "指数系统大屏-实时拥堵路段预警")
public class RoadJamVo {

  @ApiModelProperty(value = "道路名称")
  private String roadName;
  @ApiModelProperty(value = "方西")
  private String dirName;
  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "指数")
  private double tpi;
  @ApiModelProperty(value = "状态")
  private String status;
}
