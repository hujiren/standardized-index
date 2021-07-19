package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:26 2020/6/22.
 * @Description
 * @Modified By:
 */
@Data
public class RoadPortrayalTagDto {

  @ApiModelProperty(value = "高峰运行状态")
  private String peakStatus;

  @ApiModelProperty(value = "是否常发路段。是-常发拥堵路段，非-偶发拥堵路段")
  private String proneCongestion;

  @ApiModelProperty(value = "拥堵程度")
  private String congestionDegree = "";
}
