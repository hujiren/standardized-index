package com.sutpc.its.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:22 2020/8/17.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "绿波列表信息")
public class SubAreaInfo {

  @ApiModelProperty(value = "id")
  private String id;
  @ApiModelProperty(value = "绿波带名称")
  private String name;
  @ApiModelProperty(value = "速度")
  private double speed;
}
