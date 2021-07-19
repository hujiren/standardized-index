package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流量推算 .
 *
 * @Author: zuotw
 * @Date: created on 14:44 2020/11/3.
 * @Description
 * @Modified By:
 */
@Data
public class CongestionWarnRealVo {

  @ApiModelProperty(value = "中路段id")
  private int roadsectId;
  @ApiModelProperty(value = "时间片-15分钟")
  private int period;
  @ApiModelProperty(value = "路段流量")
  private int volume;
  @ApiModelProperty(value = "中路段名称")
  private String roadsectName;
  @ApiModelProperty(value = "中路段起点")
  private String roadsectFrom;
  @ApiModelProperty(value = "中路段终点")
  private String roadsectTo;
  @ApiModelProperty(value = "拥堵路段类型。0-常发拥堵，1-偶发拥堵")
  private int congestionType;
  @ApiModelProperty(value = "指数")
  private double tpi;
  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "环比")
  private double ratio;
}
