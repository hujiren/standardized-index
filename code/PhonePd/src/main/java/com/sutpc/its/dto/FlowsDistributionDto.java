package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:23 2020/9/7.
 * @Description
 * @Modified By:
 */
@Data
public class FlowsDistributionDto {

  @ApiModelProperty(value = "状态")
  private String status;
  @ApiModelProperty(value = "总流量")
  private int flows;

}
