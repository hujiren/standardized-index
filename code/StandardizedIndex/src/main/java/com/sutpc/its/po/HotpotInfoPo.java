package com.sutpc.its.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:26 2020/6/1.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "医院热点")
public class HotpotInfoPo {

  @ApiModelProperty(value = "热点名称")
  private String name;
  @ApiModelProperty(value = "指数")
  private double tpi;
  @ApiModelProperty(value = "环比")
  private double ratio;
}
