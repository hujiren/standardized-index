package com.sutpc.its.statement.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * 全市总体交通运行模块值.
 *
 * @author admin
 * @date 2020/5/20 10:53
 */
@Data
@ApiModel(value = "全市总体交通运行模块值")
public class ModuleCityTotalityValue {

  @ApiModelProperty(value = "全市路网高峰时段平均速度")
  private double peakSpeed;
  @ApiModelProperty(value = "全市路网高峰时段平均速度环比")
  private double peakCycleRatio;
  @ApiModelProperty(value = "全市路网高峰时段平均速度同比")
  private double peakYearRatio;
  @ApiModelProperty(value = "早高峰时段平均速度")
  private double morningSpeed;
  @ApiModelProperty(value = "早高峰时段交通指数")
  private double morningTpi;
  @ApiModelProperty(value = "早高峰时段交通运行等级")
  private String morningStatus;
  @ApiModelProperty(value = "晚高峰时段平均速度")
  private double eveningSpeed;
  @ApiModelProperty(value = "晚高峰时段交通指数")
  private double eveningTpi;
  @ApiModelProperty(value = "晚高峰时段交通运行等级")
  private String eveningStatus;

  @ApiModelProperty(value = "全市平均速度：早高峰、晚高峰、高峰时段")
  private List<Double> citySpeeds;
  @ApiModelProperty(value = "全市指数：早高峰、晚高峰、高峰时段")
  private List<Double> cityTpis;
  @ApiModelProperty(value = "中心城区平均速度：早高峰、晚高峰、高峰时段")
  private List<Double> centralSpeeds;
  @ApiModelProperty(value = "中心城区指数：早高峰、晚高峰、高峰时段")
  private List<Double> centralTpis;


}
