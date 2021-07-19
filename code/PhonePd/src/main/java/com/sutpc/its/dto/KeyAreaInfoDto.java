package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:46 2020/6/24.
 * @Description
 * @Modified By:
 */
@Data
public class KeyAreaInfoDto {

  @ApiModelProperty(value = "重点区域id")
  private int areaId;
  @ApiModelProperty(value = "对应热点id")
  private int hotSpotId;
  @ApiModelProperty(value = "重点区域（热点）名称")
  private String name;
  @ApiModelProperty(value = "人流数量")
  private int pf;
  @ApiModelProperty(value = "人流环比")
  private double ratio;
  @ApiModelProperty(value = "饱和度")
  private double saturation;
  @ApiModelProperty(value = "人流（舒适）等级")
  private String level;

}

