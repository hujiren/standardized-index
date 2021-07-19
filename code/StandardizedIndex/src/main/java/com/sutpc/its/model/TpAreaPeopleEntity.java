package com.sutpc.its.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 19:33 2020/5/11.
 * @Description
 * @Modified By:
 */
@Data
public class TpAreaPeopleEntity extends Object {

  @ApiModelProperty("时间片-十五分钟")
  private int period;

  @ApiModelProperty("客流值")
  private int value;


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TpAreaPeopleEntity bean = (TpAreaPeopleEntity) o;

    if (period != bean.getPeriod()) {
      return false;
    }
    if (value != bean.getValue()) {
      return false;
    }
    return true;

  }

}
