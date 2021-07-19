package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:57 2020/5/26.
 * @Description
 * @Modified By:
 */
@Data
public class DelConditionInfoPo {

  @ApiModelProperty(value = "数据库唯一id", required = true)
  private String id;
}
