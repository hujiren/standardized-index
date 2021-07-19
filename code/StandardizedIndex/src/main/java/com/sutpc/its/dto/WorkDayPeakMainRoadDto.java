package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 18:00 2020/5/27.
 * @Description
 * @Modified By:
 */
@Data
public class WorkDayPeakMainRoadDto {

  @ApiModelProperty(value = "道路id")
  private int id;
  @ApiModelProperty(value = "道路名称")
  private String roadName;
  @ApiModelProperty(value = "方向名称")
  private String dirName;
  @ApiModelProperty(value = "平均速度")
  private double speed;
  @ApiModelProperty(value = "环比")
  private double ratio;
}
