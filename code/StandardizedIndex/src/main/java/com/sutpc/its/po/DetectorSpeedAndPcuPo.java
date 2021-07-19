package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:45 2020/9/25.
 * @Description
 * @Modified By:
 */
@Data
public class DetectorSpeedAndPcuPo {

  @ApiModelProperty(value = "检测器id")
  private String id;
  @ApiModelProperty(value = "检测器位置")
  private String position;
  @ApiModelProperty(value = "方向。1-进深，2-出深")
  private String dir;
  @ApiModelProperty(value = "所属高速公路")
  private String description;
  @ApiModelProperty(value = "日期")
  private String time;
  @ApiModelProperty(value = "时间片15分钟")
  private int period;
  @ApiModelProperty(value = "大车流量")
  private int largeFlow;
  @ApiModelProperty(value = "中型车流量")
  private int middleFlow;
  @ApiModelProperty(value = "小车流量")
  private int smallFlow;
}
