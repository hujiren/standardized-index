package com.sutpc.its.statement.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:22 2020/6/17.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "组合报告尾部")
public class ModuleDistrictFootValue {

  @ApiModelProperty(value = "统计日期")
  private String dateCalculated;
  @ApiModelProperty(value = "行政区")
  private String districtName;
}
