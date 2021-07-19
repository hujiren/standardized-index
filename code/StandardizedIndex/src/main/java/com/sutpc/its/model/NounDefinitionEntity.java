package com.sutpc.its.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:21 2020/8/5.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "名词解释")
public class NounDefinitionEntity {

  @ApiModelProperty(value = "id")
  private String id;
  @ApiModelProperty(value = "名词")
  private String fname;
  @ApiModelProperty(value = "上传人")
  private String uploadPerson;
  @ApiModelProperty(value = "描述")
  private String description;
}
