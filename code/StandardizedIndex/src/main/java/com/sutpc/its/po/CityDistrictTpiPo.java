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
public class CityDistrictTpiPo {

  @ApiModelProperty(value = "行政区id")
  private Long id;
  @ApiModelProperty(value = "指数")
  private Double tpi;
  @ApiModelProperty(value = "时段")
  private String period;
}
