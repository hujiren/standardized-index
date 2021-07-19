package com.sutpc.its.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:17 2020/8/12.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "实时小路段信息")
public class LinkVo {

  @ApiModelProperty(value = "小路段id")
  private Integer linkFid;
  @ApiModelProperty(value = "小路段起点")
  private Integer fromNode;
  @ApiModelProperty(value = "小路段终点")
  private Integer toNode;
  @ApiModelProperty(value = "实时速度")
  private Double speed;
  @ApiModelProperty(value = "指数")
  private Double tpi;
  @ApiModelProperty(value = "运行状态")
  private String status;
}
