package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:56 2020/11/3.
 * @Description
 * @Modified By:
 */
@Data
public class RoadsectMapVolumeVo {

  @ApiModelProperty(value = "中路段id")
  private int id;
  @ApiModelProperty(value = "流量")
  private int volume;
}
