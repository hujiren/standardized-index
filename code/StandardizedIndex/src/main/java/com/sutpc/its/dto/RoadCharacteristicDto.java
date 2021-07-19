package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:31 2020/6/22.
 * @Description
 * @Modified By:
 */
@Data
public class RoadCharacteristicDto {

  @ApiModelProperty(value = "道路id编号")
  private int id;
  @ApiModelProperty(value = "道路名称")
  private String name;
  @ApiModelProperty(value = "道路起点")
  private String from;
  @ApiModelProperty(value = "道路终点")
  private String to;
  @ApiModelProperty(value = "道路设置-0、无，1、公交专用道，2、潮汐车道，3、HOV车道")
  private int roadSet;
  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "速度环比")
  private double speedRatio;
  @ApiModelProperty(value = "指数")
  private double tpi;
  @ApiModelProperty(value = "指数环比")
  private double tpiRatio;
}
