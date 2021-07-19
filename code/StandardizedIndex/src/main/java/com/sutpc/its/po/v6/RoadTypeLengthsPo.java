package com.sutpc.its.po.v6;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:36 2020/10/18.
 * @Description
 * @Modified By:
 */
@Data
public class RoadTypeLengthsPo {

  @ApiModelProperty(value = "道路类型id")
  private int type;
  @ApiModelProperty(value = "道路类型")
  private String name;
  @ApiModelProperty(value = "长度.单位：米")
  private int length;
}
