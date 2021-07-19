package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 路段交通运行-实体.
 *
 * @Author: zuotw
 * @Date: created on 16:19 2020/6/1.
 * @Description
 * @Modified By:
 */
@Data
public class RoadSectionInfoPo {

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
  @ApiModelProperty(value = "指数")
  private double tpi;
}
