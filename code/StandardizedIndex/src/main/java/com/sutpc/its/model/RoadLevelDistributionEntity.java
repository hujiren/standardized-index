package com.sutpc.its.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 20:39 2020/6/18.
 * @Description
 * @Modified By:
 */
@Data
public class RoadLevelDistributionEntity {

  @ApiModelProperty(value = "日期", example = "202006")
  private Integer date;
}
