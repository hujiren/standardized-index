package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 20:24 2020/5/25.
 * @Description
 * @Modified By:
 */
@Data
public class CityDistrictTpiRankPo {

  @ApiModelProperty(value = "行政区id")
  private Long id;
  @ApiModelProperty(value = "行政区名称")
  private String name;
  @ApiModelProperty(value = "速度")
  private Double speed;
  @ApiModelProperty(value = "指数")
  private Double tpi;
}
