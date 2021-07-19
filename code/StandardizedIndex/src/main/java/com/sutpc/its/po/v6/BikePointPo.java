package com.sutpc.its.po.v6;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单车信息.
 *
 * @Author: zuotw
 * @Date: created on 9:56 2020/10/18.
 * @Description
 * @Modified By:
 */
@Data
public class BikePointPo {

  @ApiModelProperty(value = "经度")
  private double lng;
  @ApiModelProperty(value = "纬度")
  private double lat;
}
