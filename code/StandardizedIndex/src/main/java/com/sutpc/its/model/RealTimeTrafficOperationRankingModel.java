package com.sutpc.its.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel("实时交通运行排名-实体类")
@Data
public class RealTimeTrafficOperationRankingModel<T> {

  @ApiModelProperty(value = "行政区ID", notes = "行政区ID")
  private String fid;
  @ApiModelProperty(value = "速度", notes = "速度")
  private String speed;
  @ApiModelProperty(value = "时间片（默认15分钟）", notes = "时间片（默认15分钟）")
  private int period;
  @ApiModelProperty(value = "指数", notes = "指数")
  private String tpi;
  @ApiModelProperty(value = "行政区名称", notes = "行政区名称")
  private String fname;

}
