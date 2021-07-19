package com.sutpc.its.statement.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 辖区报告-综述实体类.
 *
 * @Author: zuotw
 * @Date: created on 11:10 2020/6/3.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "辖区报告-综述实体类")
public class ModuleDistrictOverviewValue {

  @ApiModelProperty(value = "标题名称")
  private String title;
  @ApiModelProperty(value = "行政区名称")
  private String districtName;
  @ApiModelProperty(value = "统计日期")
  private String dateCalculated;
  @ApiModelProperty(value = "全区路网工作日高峰时段平均速度")
  private double peakAverageSpeed;
  @ApiModelProperty(value = "全区路网工作日高峰时段平均速度-环比")
  private double cycleAverageSpeedRatio;
  @ApiModelProperty(value = "全区路网工作日高峰时段指数")
  private double peakTpi;
  @ApiModelProperty(value = "全区路网工作日高峰时段运行状态")
  private String peakStatus;
  @ApiModelProperty(value = "工作日高峰时段速度最高的日期")
  private String peakMaxDate;
  @ApiModelProperty(value = "工作日高峰时段速度最高的速度")
  private double peakMaxSpeed;
  @ApiModelProperty(value = "工作日高峰时段速度最低的日期")
  private String peakMinDate;
  @ApiModelProperty(value = "工作日高峰时段速度最低的速度")
  private double peakMinSpeed;

  @ApiModelProperty(value = "缓行及以上街道数量")
  private int slowCount;
  @ApiModelProperty(value = "畅通及基本畅通街道数量")
  private int unimpededCount;
  @ApiModelProperty(value = "工作日高峰时段交通压力最大街道名称")
  private String peakMaxBlock;
  @ApiModelProperty(value = "工作日高峰时段交通压力最大街道的指数")
  private double peakMaxBlockTpi;
  @ApiModelProperty(value = "工作日高峰时段最畅通街道名称")
  private String peakMinBlock;
  @ApiModelProperty(value = "工作日高峰时段最畅通街道的指数")
  private double peakMinBlockTpi;

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

  @ApiModelProperty(value = "工作日高峰时段主要干道平均速度")
  private double mainRoadSpeed;
  @ApiModelProperty(value = "工作日高峰时段主要干道平均速度环比")
  private double mainRoadRatio;
  @ApiModelProperty(value = "高峰时平均速度上升幅度较大路段名")
  private String mainRoadMaxRatioName;
  @ApiModelProperty(value = "高峰时平均速度上升幅度较大路段-环比")
  private double mainRoadMaxRatio;
  @ApiModelProperty(value = "高峰时平均速度下降幅度较大路段名")
  private String mainRoadMinRatioName;
  @ApiModelProperty(value = "高峰时平均速度下降幅度较大路段-环比")
  private double mainRoadMinRatio;

  @ApiModelProperty(value = "快速路最拥堵路段")
  private String fastRoad;
  @ApiModelProperty(value = "快速路最拥堵路段指数")
  private double fastTpi;
  @ApiModelProperty(value = "快速路最拥堵路段平均速度")
  private double fastSpeed;
  @ApiModelProperty(value = "主干道最拥堵路段")
  private String mainRoad;
  @ApiModelProperty(value = "主干道最拥堵路段指数")
  private double mainTpi;
  @ApiModelProperty(value = "主干道最拥堵路段平均速度")
  private double mainSpeed;
  @ApiModelProperty(value = "次干道最拥堵路段")
  private String subRoad;
  @ApiModelProperty(value = "次干道最拥堵路段指数")
  private double subTpi;
  @ApiModelProperty(value = "次干道最拥堵路段平均速度")
  private double subSpeed;

}
