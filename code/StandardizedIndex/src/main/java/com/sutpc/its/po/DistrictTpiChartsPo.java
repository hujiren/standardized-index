package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:54 2020/8/26.
 * @Description
 * @Modified By:
 */
@Data
public class DistrictTpiChartsPo {

  @ApiModelProperty(value = "行政区名称")
  private String name;
  @ApiModelProperty(value = "行政区id")
  private int districtId;
  @ApiModelProperty(value = "时间片：15分钟")
  private int period;
  @ApiModelProperty(value = "指数")
  private double tpi;
}
