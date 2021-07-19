package com.sutpc.its.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:02 2020/6/18.
 * @Description
 * @Modified By:
 */
@Data
public class RoadPortraitEntity {

  @ApiModelProperty(value = "查询年月（日期）", example = "202006")
  private int date;
  @ApiModelProperty(
      value = "高峰时段参数，早高峰:morning_peak,晚高峰:evening_peak,早晚高峰:all_peak",
      example = "morning_peak")
  private String timeProperty;
  @ApiModelProperty(value = "中路段id", example = "351201")
  private int id;
}
