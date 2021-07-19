package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 福田中心区公交实时路况.
 *
 * @Author: zuotw
 * @Date: created on 10:01 2020/10/10.
 * @Description
 * @Modified By:
 */
@Data
public class BusRoadsectStatus {

  @ApiModelProperty(value = "中路段id")
  private int id;
  @ApiModelProperty(value = "路名")
  private String name;
  @ApiModelProperty(value = "起点")
  private String from;
  @ApiModelProperty(value = "终点")
  private String to;
  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "指数")
  private double tpi;
}
