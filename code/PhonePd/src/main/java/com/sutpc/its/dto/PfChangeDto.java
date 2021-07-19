package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实时人流曲线实体类.
 *
 * @Author: zuotw
 * @Date: created on 9:25 2020/6/28.
 * @Description
 * @Modified By:
 */
@Data
public class PfChangeDto {

  @ApiModelProperty(value = "时间片-15分钟")
  private int period;
  @ApiModelProperty(value = "人流数量")
  private int pf;
}
