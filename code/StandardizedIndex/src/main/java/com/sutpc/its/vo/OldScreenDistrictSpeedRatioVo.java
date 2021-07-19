package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:43 2020/10/29.
 * @Description
 * @Modified By:
 */
@Data
public class OldScreenDistrictSpeedRatioVo {

  @ApiModelProperty(value = "小汽车速度")
  private double carSpeed;
  @ApiModelProperty(value = "公交车速度")
  private double busSpeed;
  @ApiModelProperty(value = "速度比")
  private double ratio;
}
