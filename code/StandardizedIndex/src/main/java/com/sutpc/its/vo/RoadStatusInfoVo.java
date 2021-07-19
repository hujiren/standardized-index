package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公众版指数大路段路况.
 *
 * @Author: zuotw
 * @Date: created on 15:23 2020/10/29.
 * @Description
 * @Modified By:
 */
@Data
public class RoadStatusInfoVo {

  @ApiModelProperty(value = "大路段id")
  private int id;
  @ApiModelProperty(value = "大路段名称")
  private String name;
  @ApiModelProperty(value = "方向")
  private String dir;
  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "指数")
  private double tpi;
  @ApiModelProperty(value = "运行状态")
  private String status;
}
