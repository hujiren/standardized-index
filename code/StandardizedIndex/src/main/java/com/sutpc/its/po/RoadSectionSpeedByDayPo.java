package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:32 2020/9/21.
 * @Description
 * @Modified By:
 */
@Data
public class RoadSectionSpeedByDayPo {

  @ApiModelProperty(value = "时间")
  private String time;
  @ApiModelProperty(value = "中路段id")
  private int id;
  @ApiModelProperty(value = "时间片-15分钟")
  private int period;
  @ApiModelProperty(value = "速度")
  private double speed;

}
