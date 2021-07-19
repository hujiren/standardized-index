package com.sutpc.its.statement.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * 报表参数.
 *
 * @author admin
 * @date 2020/5/19 14:27
 */
@Data
public class StatementParam {

  @ApiModelProperty(value = "同比开始日期，格式为yyyyMMdd", example = "20190517")
  private Integer yearStartDate;
  @ApiModelProperty(value = "同比结束日期，格式为yyyyMMdd", example = "20190524")
  private Integer yearEndDate;
  @ApiModelProperty(value = "环比开始日期，格式为yyyyMMdd", example = "20200510")
  private Integer cycleStartDate;
  @ApiModelProperty(value = "环比结束日期，格式为yyyyMMdd", example = "20200517")
  private Integer cycleEndDate;
  @ApiModelProperty(value = "当前开始日期，格式为yyyyMMdd", example = "20200517")
  private Integer currentStartDate;
  @ApiModelProperty(value = "当前结束日期，格式为yyyyMMdd", example = "20200524")
  private Integer currentEndDate;
  @ApiModelProperty(value = "行政区id", example = "1")
  private Integer districtId;
  @ApiModelProperty(value = "模块类型列表")
  private List<String> types;
  @ApiModelProperty(value = "指数保留小数位数，默认为1，可选", required = false)
  private int tpiDigit = 1;
  @ApiModelProperty(value = "速度保留小数位数，默认为1，可选", required = false)
  private int speedDigit = 1;
  @ApiModelProperty(value = "环比、同比保留小数位数，默认为1，可选", required = false)
  private int ratioDigit = 1;
}
