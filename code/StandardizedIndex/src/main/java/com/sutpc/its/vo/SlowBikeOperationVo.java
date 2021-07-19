package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:21 2020/11/4.
 * @Description
 * @Modified By:
 */
@Data
public class SlowBikeOperationVo {

  @ApiModelProperty(value = "全市单车服务量")
  private int count;
  @ApiModelProperty(value = "全市单车出行量")
  private int travel;
  @ApiModelProperty(value = "平均单车服务次数")
  private double average;
}
