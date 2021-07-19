package com.sutpc.its.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:11 2020/9/23.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "热点信息")
public class PoiInfoNowPo {

  @ApiModelProperty(value = "时间")
  private int time;
  @ApiModelProperty(value = "时间片")
  private int period;
  @ApiModelProperty(value = "热点id")
  private int id;
  @ApiModelProperty(value = "热点名称")
  private String name;
  @ApiModelProperty(value = "热点类型")
  private String category;
  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "指数")
  private double tpi;
}
