package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:27 2020/10/29.
 * @Description
 * @Modified By:
 */
@Data
public class OldScreenAllRoadMidSectVo {

  @ApiModelProperty(value = "中路段id")
  private int id;
  @ApiModelProperty(value = "中路段名称")
  private String name;
  @ApiModelProperty(value = "中路段起点位置名称")
  private String from;
  @ApiModelProperty(value = "中路段终点位置名称")
  private String to;
  @ApiModelProperty(value = "指数")
  private double tpi;
}
