package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:15 2020/6/24.
 * @Description
 * @Modified By:
 */
@Data
public class KeyAreaAlterDto {

  @ApiModelProperty(value = "是否预警，0-否，1-是")
  private int isAlert;
  @ApiModelProperty(value = "出租车调度数")
  private int taxiDispatch;
  @ApiModelProperty(value = "网约车调度数")
  private int netDispatch;
  @ApiModelProperty(value = "公交车调度数")
  private int busDispatch;
  @ApiModelProperty(value = "地铁调度数")
  private int subDispatch;
}
