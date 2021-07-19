package com.sutpc.its.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 交通改善或恶化路段列表实体类.
 *
 * @Author: zuotw
 * @Date: created on 17:00 2020/6/2.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "交通改善或恶化路段列表实体类")
public class RoadSectionChangePo {

  @ApiModelProperty(value = "序号")
  private int index;
  @ApiModelProperty(value = "道路名")
  private String name;
  @ApiModelProperty(value = "路段起点")
  private String from;
  @ApiModelProperty(value = "路段终点")
  private String to;
  @ApiModelProperty(value = "方向名称")
  private String dirName;
  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "指数")
  private double tpi;
  @ApiModelProperty(value = "指数环比")
  private double ratio;
}
