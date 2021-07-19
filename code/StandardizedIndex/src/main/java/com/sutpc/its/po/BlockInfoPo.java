package com.sutpc.its.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:19 2020/8/26.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "街道（片区）信息")
public class BlockInfoPo {

  @ApiModelProperty(value = "街道id")
  private int blockId;
  @ApiModelProperty(value = "街道名")
  private String name;
  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "指数")
  private double tpi;
  @ApiModelProperty(value = "交通状态")
  private String status;
  @ApiModelProperty(value = "环比")
  private double ratio;

}
