package com.sutpc.its.statement.bean;

import com.sutpc.its.po.RoadSectionChangeInfoPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:31 2020/6/2.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "上期不同等级拥堵路段变化情况")
public class ModuleDistrictLastRankRoadSectionValue {

  @ApiModelProperty(value = "标题名称")
  private String title;
  @ApiModelProperty(value = "快速路-改善路段数")
  private int fastImprove;
  @ApiModelProperty(value = "快速路-恶化路段数")
  private int fastWorsen;
  @ApiModelProperty(value = "快速路-上期五大拥堵快速路路段变化情况")
  private List<RoadSectionChangeInfoPo> fastList;
  @ApiModelProperty(value = "主干道-改善路段数")
  private int mainImprove;
  @ApiModelProperty(value = "主干道-恶化路段数")
  private int mainWorsen;
  @ApiModelProperty(value = "主干道-上期五大拥堵主干道路段变化情况")
  private List<RoadSectionChangeInfoPo> mainList;
  @ApiModelProperty(value = "次干道-改善路段数")
  private int subImprove;
  @ApiModelProperty(value = "次干道-恶化路段数")
  private int subWorsen;
  @ApiModelProperty(value = "次干道-上期五大拥堵次干道路段变化情况")
  private List<RoadSectionChangeInfoPo> subList;
}
