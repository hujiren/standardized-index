package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:42 2020/7/22.
 * @Description
 * @Modified By:
 */
@Data
public class DetectorInfoEntity {

  @ApiModelProperty(value = "断面监测点id")
  private int id;
  @ApiModelProperty(value = "名称")
  private String name;
  @ApiModelProperty(value = "位置")
  private String position;
  @ApiModelProperty(value = "经度")
  private double lng;
  @ApiModelProperty(value = "纬度")
  private double lat;
}
