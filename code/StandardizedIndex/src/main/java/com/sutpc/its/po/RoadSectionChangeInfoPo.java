package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:55 2020/6/2.
 * @Description
 * @Modified By:
 */
@Data
public class RoadSectionChangeInfoPo {

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
  @ApiModelProperty(value = "本期指数变化（比例）")
  private double ratio;
  @ApiModelProperty(value = "变化情况")
  private String situation;
}
