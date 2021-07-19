package com.sutpc.its.statement.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 全市综述模块值.
 *
 * @author admin
 * @date 2020/5/20 10:53
 */
@Data
@ApiModel(value = "全市综述模块值", description = "全市综述模块值")
public class ModuleCityOverviewValue {

  @ApiModelProperty(value = "全市路网高峰时段平均速度")
  private double citySpeed;
  @ApiModelProperty(value = "全市路网高峰时段平均速度环比")
  private double cityCycleRatio;
  @ApiModelProperty(value = "全市路网高峰时段平均速度同比")
  private double cityYearRatio;
  @ApiModelProperty(value = "全市路网高峰时段交通指数")
  private double cityTpi;
  @ApiModelProperty(value = "全市路网高峰时段交通运行等级")
  private String cityStatus;

  @ApiModelProperty(value = "行政区运行中-高峰时段压力最大区域")
  private String districtHighestName;
  @ApiModelProperty(value = "行政区运行中-高峰时段压力最大区域速度")
  private double districtHighestSpeed;
  @ApiModelProperty(value = "行政区运行中-高峰时段压力最大区域指数")
  private double districtHighestTpi;
  @ApiModelProperty(value = "行政区运行中-高峰时段压力最大区域运行等级")
  private String districtHighestStatus;

  @ApiModelProperty(value = "行政区运行中-高峰时最畅通区域")
  private String districtLowestName;
  @ApiModelProperty(value = "行政区运行中-高峰时段最畅通区域速度")
  private double districtLowestSpeed;
  @ApiModelProperty(value = "行政区运行中-高峰时段最畅通区域指数")
  private double districtLowestTpi;
  @ApiModelProperty(value = "行政区运行中-高峰时段最畅通区域运行等级")
  private String districtLowestStatus;

  @ApiModelProperty(value = "主要干道早高峰时段平均速度")
  private double primaryMorningSpeed;
  @ApiModelProperty(value = "主要干道早高峰时段平均速度环比")
  private double primaryMorningCycleRatio;
  @ApiModelProperty(value = "主要干道早高峰时段平均速度同比")
  private double primaryMorningYearRatio;
  @ApiModelProperty(value = "主要干道晚高峰时段平均速度")
  private double primaryEveningSpeed;
  @ApiModelProperty(value = "主要干道晚高峰时段平均速度环比")
  private double primaryEveningCycleRatio;
  @ApiModelProperty(value = "主要干道晚高峰时段平均速度同比")
  private double primaryEveningYearRatio;

  @ApiModelProperty(value = "枢纽片区早高峰时段平均速度")
  private double hubMorningSpeed;
  @ApiModelProperty(value = "枢纽片区早高峰时段平均速度环比")
  private double hubMorningCycleRatio;
  @ApiModelProperty(value = "枢纽片区晚高峰时段平均速度")
  private double hubEveningSpeed;
  @ApiModelProperty(value = "枢纽片区晚高峰时段平均速度环比")
  private double hubEveningCycleRatio;

  @ApiModelProperty(value = "口岸片区早高峰时段平均速度")
  private double portMorningSpeed;
  @ApiModelProperty(value = "口岸片区早高峰时段平均速度环比")
  private double portMorningCycleRatio;
  @ApiModelProperty(value = "口岸片区晚高峰时段平均速度")
  private double portEveningSpeed;
  @ApiModelProperty(value = "口岸片区晚高峰时段平均速度环比")
  private double portEveningCycleRatio;

  @ApiModelProperty(value = "主要二线关早高峰进关平均速度")
  private double gatewayMorningSpeed;
  @ApiModelProperty(value = "主要二线关早高峰进关平均速度环比")
  private double gatewayMorningCycleRatio;
  @ApiModelProperty(value = "主要二线关早高峰进关平均速度同比")
  private double gatewayMorningYearRatio;
  @ApiModelProperty(value = "主要二线关晚高峰进关平均速度")
  private double gatewayEveningSpeed;
  @ApiModelProperty(value = "主要二线关晚高峰进关平均速度环比")
  private double gatewayEveningCycleRatio;
  @ApiModelProperty(value = "主要二线关晚高峰进关平均速度同比")
  private double gatewayEveningYearRatio;

}
