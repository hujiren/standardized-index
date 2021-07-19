package com.sutpc.its.statement.bean;

import com.deepoove.poi.data.PictureRenderData;
import com.sutpc.its.po.WorkDayPeakBlockChartPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:30 2020/5/26.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "街道交通运行")
public class ModuleDistrictBlockValue {

  @ApiModelProperty(value = "标题名称")
  private String title;
  @ApiModelProperty(value = "缓行及以上街道数量")
  private int slowCount;
  @ApiModelProperty(value = "畅通及基本畅通街道数量")
  private int unimpededCount;
  @ApiModelProperty(value = "工作日高峰时段交通压力最大街道名称")
  private String peakMaxBlock;
  @ApiModelProperty(value = "工作日高峰时段交通压力最大街道的指数")
  private double peakMaxBlockTpi;
  @ApiModelProperty(value = "工作日高峰时段交通压力最大街道的平均速度")
  private double peakMaxBlockSpeed;
  @ApiModelProperty(value = "工作日高峰时段交通压力最大街道的交通状态")
  private String peakMaxBlockStatus;
  @ApiModelProperty(value = "工作日高峰时段最畅通街道名称")
  private String peakMinBlock;
  @ApiModelProperty(value = "工作日高峰时段最畅通街道的指数")
  private double peakMinBlockTpi;
  @ApiModelProperty(value = "工作日高峰时段最畅通街道的平均速度")
  private double peakMinBlockSpeed;
  @ApiModelProperty(value = "工作日高峰时段最畅通街道的交通状态")
  private String peakMinBlockStatus;
  @ApiModelProperty(value = "各街道高峰时段平均速度图数据")
  private List<WorkDayPeakBlockChartPo> list;

  @ApiModelProperty(value = "各街道高峰时段平均速度图chart图的base64")
  private String chart;
  @ApiModelProperty(value = "插件指定的图片呈现数据")
  private PictureRenderData picChart;
  @ApiModelProperty(value = "各街道高峰时段交通运行指数图map图的base64")
  private String map;
  @ApiModelProperty(value = "插件指定的图片呈现数据")
  private PictureRenderData picMap;
}
