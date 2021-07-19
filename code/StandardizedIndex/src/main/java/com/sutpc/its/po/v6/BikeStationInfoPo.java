package com.sutpc.its.po.v6;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:01 2020/10/18.
 * @Description
 * @Modified By:
 */
@Data
public class BikeStationInfoPo {

  @ApiModelProperty(value = "单车经度")
  private double lng;
  @ApiModelProperty(value = "单车纬度")
  private double lat;
}
