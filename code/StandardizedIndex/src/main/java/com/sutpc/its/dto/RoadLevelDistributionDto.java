package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 20:23 2020/6/18.
 * @Description
 * @Modified By:
 */
@Data
public class RoadLevelDistributionDto {

  @ApiModelProperty(value = "道路类型编号")
  private int roadTypeId;
  @ApiModelProperty(value = "道路类型")
  private String roadType;
  @ApiModelProperty(value = "高峰道路状态")
  private String peakStatus;
  @ApiModelProperty(value = "数量/条数")
  private int count;
}
