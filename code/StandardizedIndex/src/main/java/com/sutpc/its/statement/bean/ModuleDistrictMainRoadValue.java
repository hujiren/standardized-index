package com.sutpc.its.statement.bean;

import com.deepoove.poi.data.PictureRenderData;
import com.sutpc.its.po.WorkDayPeakMainRoadPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:39 2020/5/27.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "主要干道交通运行")
public class ModuleDistrictMainRoadValue {

  @ApiModelProperty(value = "标题名称")
  private String title;
  @ApiModelProperty(value = "工作日高峰时段主要干道高峰时段平均速度")
  private double mainRoadSpeed;
  @ApiModelProperty(value = "工作日高峰时段主要干道高峰时段平均速度环比")
  private double cycleMainRoadRatio;
  @ApiModelProperty(value = "平均速度上升幅度较大的道路")
  private String upMaxRoad;
  @ApiModelProperty(value = "环比最大上升值")
  private double cycleUpMaxRatio;
  @ApiModelProperty(value = "平均速度下降幅度较大的道路")
  private String downMaxRoad;
  @ApiModelProperty(value = "环比最大下降值")
  private double cycleDownMaxRatio;
  @ApiModelProperty(value = "主要干道交通运行速度表")
  private List<WorkDayPeakMainRoadPo> list;

  @ApiModelProperty(value = "主干道速度图base64")
  private String mainRoadImage;
  @ApiModelProperty(value = "主干道速度图-插件指定的图片呈现数据")
  private PictureRenderData picMainRoadImage;
}
