package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 交通压力最大/最小（畅通）的街道的相关信息接收类.
 *
 * @Author: zuotw
 * @Date: created on 14:27 2020/5/27.
 * @Description
 * @Modified By:
 */
@Data
public class WorkDayPeakExtremaBlockInfoPo {

  @ApiModelProperty(value = "街道id")
  private int id;
  @ApiModelProperty(value = "街道名称")
  private String name;
  @ApiModelProperty(value = "指数")
  private double tpi;
  @ApiModelProperty(value = "速度")
  private double speed;
}
