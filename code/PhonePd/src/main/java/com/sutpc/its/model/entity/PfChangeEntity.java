package com.sutpc.its.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实时人流曲线参数.
 *
 * @Author: zuotw
 * @Date: created on 9:23 2020/6/28.
 * @Description
 * @Modified By:
 */
@Data
public class PfChangeEntity {

  @ApiModelProperty(value = "热点区域id")
  private int tazid;
}
