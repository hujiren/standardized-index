package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:44 2020/11/10.
 * @Description
 * @Modified By:
 */
@Data
public class RelatedSectionVo {

  @ApiModelProperty(value = "中路段名称")
  private String name;
  @ApiModelProperty(value = "中路段起点")
  private String from;
  @ApiModelProperty(value = "中路段终点")
  private String to;
  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "指数")
  private double tpi;
}
