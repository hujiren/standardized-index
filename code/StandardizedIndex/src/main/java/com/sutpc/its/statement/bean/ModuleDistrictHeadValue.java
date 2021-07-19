package com.sutpc.its.statement.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:18 2020/6/17.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "辖区报告红头")
public class ModuleDistrictHeadValue {

  @ApiModelProperty(value = "行政区名称")
  private String districtName;
  @ApiModelProperty(value = "行政区sub（福田区->福田)")
  private String nameSub;
  @ApiModelProperty(value = "出报告日期")
  private String resultDate;
}
