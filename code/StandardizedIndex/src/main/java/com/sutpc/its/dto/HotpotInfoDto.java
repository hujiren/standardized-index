package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 所有热点（医院、商圈、口岸、学校、枢纽、景区）的实体信息.
 *
 * @Author: zuotw
 * @Date: created on 10:32 2020/6/1.
 * @Description
 * @Modified By:
 */
@Data
public class HotpotInfoDto {

  @ApiModelProperty(value = "编号")
  private int id;
  @ApiModelProperty(value = "热点名称")
  private String name;
  @ApiModelProperty(value = "指数")
  private double tpi;
}
