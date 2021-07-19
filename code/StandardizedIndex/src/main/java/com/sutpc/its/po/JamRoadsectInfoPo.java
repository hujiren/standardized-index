package com.sutpc.its.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:52 2020/8/27.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "十大拥堵路段")
public class JamRoadsectInfoPo {

  @ApiModelProperty(value = "路段id")
  private int id;
  @ApiModelProperty(value = "路段名")
  private String name;
  @ApiModelProperty(value = "路段起点")
  private String from;
  @ApiModelProperty(value = "路段终点")
  private String to;
  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "环比(已经是百分比，直接使用)")
  private double ratio;
}
