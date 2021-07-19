package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:16 2020/7/21.
 * @Description
 * @Modified By:
 */
@Data
public class RoadSectionJamCountDto {

  @ApiModelProperty(value = "拥堵路段条数")
  private int count;
}
