package com.sutpc.its.statement.bean;

import com.deepoove.poi.data.PictureRenderData;
import com.sutpc.its.po.WorkDayPeakInfoPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * 辖区报告-全路网交通运行模块值.
 *
 * @Author: zuotw
 * @Date: created on 14:34 2020/5/25.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "全路网交通运行")
public class ModuleDistrictTotalNetValue {

  @ApiModelProperty(value = "标题名称")
  private String title;
  @ApiModelProperty(value = "全区路网工作日高峰时段平均速度")
  private double peakAverageSpeed;
  @ApiModelProperty(value = "全区路网工作日高峰时段平均速度-环比")
  private double cycleAverageSpeedRatio;
  @ApiModelProperty(value = "全区路网工作日早高峰时段平均速度")
  private double morningPeakAverageSpeed;
  @ApiModelProperty(value = "全区路网工作日早高峰时段指数")
  private double morningPeakTpi;
  @ApiModelProperty(value = "全区路网工作日早高峰交通状态")
  private String morningPeakStatus;
  @ApiModelProperty(value = "全区路网工作日晚高峰时段平均速度")
  private double eveningPeakAverageSpeed;
  @ApiModelProperty(value = "全区路网工作日晚高峰时段指数")
  private double eveningPeakTpi;
  @ApiModelProperty(value = "全区路网工作日晚高峰交通状态")
  private String eveningPeakStatus;
  @ApiModelProperty(value = "工作日高峰时段速度最高的日期")
  private String peakMaxDate;
  @ApiModelProperty(value = "工作日高峰时段速度最高的速度")
  private double peakMaxSpeed;
  @ApiModelProperty(value = "工作日高峰时段速度最低的日期")
  private String peakMinDate;
  @ApiModelProperty(value = "工作日高峰时段速度最低的速度")
  private double peakMinSpeed;
  @ApiModelProperty(value = "全市-高峰时段日均速度变化图曲线数据")
  private List<WorkDayPeakInfoPo> cityChart;
  @ApiModelProperty(value = "行政区-高峰时段日均速度变化图曲线数据")
  private List<WorkDayPeakInfoPo> districtChart;

  @ApiModelProperty(value = "chart图的base64", example = "")
  private String chart;
  @ApiModelProperty(value = "插件指定的图片呈现数据")
  private PictureRenderData picChart;
}
