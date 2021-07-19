package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 8:47 2020/11/10.
 * @Description
 * @Modified By:
 */
@Data
public class StationSurroundRankingVo {

  @ApiModelProperty(value = "站点名称")
  private String name;
  @ApiModelProperty(value = "接驳量")
  private int count;
}
