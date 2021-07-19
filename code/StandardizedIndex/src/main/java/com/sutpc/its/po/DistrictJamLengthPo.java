package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:20 2020/9/17.
 * @Description
 * @Modified By:
 */
@Data
public class DistrictJamLengthPo {

  @ApiModelProperty(value = "行政区名称")
  private String name;
  @ApiModelProperty(value = "id")
  private int id;
  @ApiModelProperty(value = "拥堵里程")
  private double jamLength;
}
