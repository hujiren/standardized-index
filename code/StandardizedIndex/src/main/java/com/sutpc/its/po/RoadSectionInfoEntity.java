package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取中路段信息.
 *
 * @Author: zuotw
 * @Date: created on 14:57 2020/9/21.
 * @Description
 * @Modified By:
 */
@Data
public class RoadSectionInfoEntity {

  @ApiModelProperty(value = "中路段id")
  private int id;
  @ApiModelProperty(value = "中路段名称")
  private String name;
  @ApiModelProperty(value = "方向")
  private String dir;
  @ApiModelProperty(value = "路段起点")
  private String from;
  @ApiModelProperty(value = "路段终点")
  private String to;
}
