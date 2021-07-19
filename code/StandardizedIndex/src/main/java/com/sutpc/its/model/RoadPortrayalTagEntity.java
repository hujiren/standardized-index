package com.sutpc.its.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:30 2020/6/22.
 * @Description
 * @Modified By:
 */
@Data
public class RoadPortrayalTagEntity {

  @ApiModelProperty(value = "中路id", example = "3502301")
  private int id;
  @ApiModelProperty(value = "日期", example = "202006")
  private int date;
}
