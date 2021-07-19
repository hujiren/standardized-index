package com.sutpc.its.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:11 2020/11/3.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "路段点击结果")
public class RoadsectChartInfoVo {

  @ApiModelProperty(value = "当日速度曲线")
  private List<SpeedChartVo> thisSpeedChart;

  @ApiModelProperty(value = "上周同期速度曲线")
  private List<SpeedChartVo> lastSpeedChart;

  @ApiModelProperty(value = "当日指数曲线")
  private List<TpiChartVo> thisTpiChart;

  @ApiModelProperty(value = "上周同期速度曲线")
  private List<TpiChartVo> lastTpiChart;

  @ApiModelProperty(value = "当日流量曲线")
  private List<VolumeChartVo> thisVolumeChart;

  @ApiModelProperty(value = "上周同期流量曲线")
  private List<VolumeChartVo> lastVolumeChart;
}
