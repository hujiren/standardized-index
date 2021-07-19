package com.sutpc.its.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 19:30 2020/6/22.
 * @Description
 * @Modified By:
 */
@Data
public class RoadPortrayalRoadsectEntity {

  @ApiModelProperty(value = "中路id", example = "3502301")
  private int id;
  @ApiModelProperty(value = "日期", example = "202006")
  private int date;
  @ApiModelProperty(
      value = "高峰时段参数，早高峰:morning_peak,晚高峰:evening_peak,早晚高峰:all_peak",
      example = "morning_peak")
  private String timeProperty;
}
