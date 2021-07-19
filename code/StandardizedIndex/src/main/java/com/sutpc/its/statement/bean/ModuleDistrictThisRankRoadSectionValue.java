package com.sutpc.its.statement.bean;

import com.sutpc.its.po.RoadSectionChangePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * 路段交通运行-本期不同等级拥堵路段排名.
 *
 * @Author: zuotw
 * @Date: created on 15:55 2020/6/1.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "本期不同等级拥堵路段排名")
public class ModuleDistrictThisRankRoadSectionValue {

  @ApiModelProperty(value = "标题名称")
  private String title;
  @ApiModelProperty(value = "全区五大快速路拥堵路段高峰时段平均速度")
  private double fastPeakSpeed;
  @ApiModelProperty(value = "全区五大快速路拥堵路段高峰时段指数")
  private double fastPeakTpi;
  @ApiModelProperty(value = "快速路拥堵路段之最")
  private String fastPeakMaxRoad;
  @ApiModelProperty(value = "快速路拥堵路段之最-速度")
  private double fastPeakMaxSpeed;
  @ApiModelProperty(value = "快速路拥堵路段之最-指数")
  private double fastPeakMaxTpi;
  @ApiModelProperty(value = "五大拥堵快速路路段表")
  private List<RoadSectionChangePo> fastList;

  @ApiModelProperty(value = "全区五大主干路拥堵路段高峰时段平均速度")
  private double mainPeakSpeed;
  @ApiModelProperty(value = "全区五大主干路拥堵路段高峰时段指数")
  private double mainPeakTpi;
  @ApiModelProperty(value = "主干路拥堵路段之最")
  private String mainPeakMaxRoad;
  @ApiModelProperty(value = "主干路拥堵路段之最-速度")
  private double mainPeakMaxSpeed;
  @ApiModelProperty(value = "主干路拥堵路段之最-指数")
  private double mainPeakMaxTpi;
  @ApiModelProperty(value = "五大拥堵主干路路段表")
  private List<RoadSectionChangePo> mainList;

  @ApiModelProperty(value = "全区五大次干路拥堵路段高峰时段平均速度")
  private double subPeakSpeed;
  @ApiModelProperty(value = "全区五大次干路拥堵路段高峰时段指数")
  private double subPeakTpi;
  @ApiModelProperty(value = "次干路拥堵路段之最")
  private String subPeakMaxRoad;
  @ApiModelProperty(value = "次干路拥堵路段之最-速度")
  private double subPeakMaxSpeed;
  @ApiModelProperty(value = "次干路拥堵路段之最-指数")
  private double subPeakMaxTpi;
  @ApiModelProperty(value = "五大拥堵次干路路段表")
  private List<RoadSectionChangePo> subList;
}
