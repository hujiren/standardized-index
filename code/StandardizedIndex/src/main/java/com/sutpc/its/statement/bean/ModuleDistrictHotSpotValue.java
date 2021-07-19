package com.sutpc.its.statement.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 热点区域周边交通运行指数.
 *
 * @Author: zuotw
 * @Date: created on 16:41 2020/5/29.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "热点区域周边交通运行指数")
public class ModuleDistrictHotSpotValue {

  @ApiModelProperty(value = "标题名称")
  private String title;
  @ApiModelProperty(value = "本期热点区域周边道路高峰时段平均交通运行指数最高的热点")
  private String maxTpiHot;
  @ApiModelProperty(value = "本期热点区域周边道路高峰时段平均交通运行指数最低的热点")
  private String minTpiHot;
  @ApiModelProperty(value = "本期热点区域周边道路高峰时段平均交通运行指数最高值")
  private double maxTpi;
  @ApiModelProperty(value = "本期热点区域周边道路高峰时段平均交通运行指数最低值")
  private double minTpi;
  @ApiModelProperty(value = "本期热点区域周边道路高峰时段平均交通运行指数最高的热点的环比")
  private double maxTpiHotRatio;
  @ApiModelProperty(value = "本期热点区域周边道路高峰时段平均交通运行指数最低的热点的环比")
  private double minTpiHotRatio;
}
