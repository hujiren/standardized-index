package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:57 2020/6/22.
 * @Description
 * @Modified By:
 */
@Data
public class RoadsectDistributionDto {

  @ApiModelProperty(value = "中路段id")
  private int id;
  @ApiModelProperty(value = "是否常发拥堵路段-是，非")
  private String proneCongestion;
}
