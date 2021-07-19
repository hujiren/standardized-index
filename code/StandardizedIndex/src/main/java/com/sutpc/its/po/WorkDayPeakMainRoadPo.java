package com.sutpc.its.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 19:33 2020/5/27.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "主要干道交通运行速度表")
public class WorkDayPeakMainRoadPo {


  @ApiModelProperty(value = "序号")
  private int index;
  @ApiModelProperty(value = "道路名称")
  private String roadName;
  @ApiModelProperty(value = "方向名称")
  private String dirName;
  @ApiModelProperty(value = "平均速度")
  private double speed;
  @ApiModelProperty(value = "环比")
  private double ratio;
}
