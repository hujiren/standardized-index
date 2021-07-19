package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:30 2020/7/21.
 * @Description
 * @Modified By:
 */
@Data
public class KeyAreaLikeDto {

  @ApiModelProperty(value = "重点区域id")
  private int areaId;
  @ApiModelProperty(value = "热点区域名称")
  private String name;
}
