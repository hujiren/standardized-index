package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:48 2020/11/13.
 * @Description
 * @Modified By:
 */
@Data
public class CheckDto {

  @ApiModelProperty(value = "开始日期", example = "20201113", required = true)
  private int startDate;
  @ApiModelProperty(value = "结束日期", example = "20201113", required = true)
  private int endDate;
  @ApiModelProperty(value = "热点区域id。多个用英文状态的逗号隔开。", required = true)
  private String id;
  @ApiModelProperty(value = "开始时间", example = "00:00")
  private String startTime;
  @ApiModelProperty(value = "结束日期", example = "23:59")
  private String endTime;
  @ApiModelProperty(value = "时间颗粒。5分钟:five_min,15分钟：fifteen_min,小时：hour，日：day，月：month，周：week，年：year", example = "hour", required = true)
  private String timePrecision;
  @ApiModelProperty(value = "是否工作日。0-否，1-是，2-不限", example = "2", required = true)
  private String dateProperty;
  @ApiModelProperty(value = "时段范围。早高峰：morning_peak；晚高峰：evening_peak；早晚高峰：all_peak；自定义时段：user_defined；", example = "user_defined", required = true)
  private String timeProperty;
}
