package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:27 2020/6/2.
 * @Description
 * @Modified By:
 */
@Data
public class RoadSectionInfoDto {

  @ApiModelProperty(value = "id")
  private int id;
  @ApiModelProperty(value = "道路名")
  private String name;
  @ApiModelProperty(value = "路段起点")
  private String from;
  @ApiModelProperty(value = "路段终点")
  private String to;
  @ApiModelProperty(value = "方向名称")
  private String dirName;
  @ApiModelProperty(value = "道路类型id")
  private int typeId;
  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "指数")
  private double tpi;
}
