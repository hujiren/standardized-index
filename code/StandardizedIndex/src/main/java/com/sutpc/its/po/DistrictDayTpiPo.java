package com.sutpc.its.po;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.lang.reflect.Field;
import lombok.Data;
import lombok.Value;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:34 2020/9/7.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "")
public class DistrictDayTpiPo {

  @ApiModelProperty(value = "id")
  @JSONField(name = "id")
  private int id;

  @ApiModelProperty(value = "行政区名称")
  @JSONField(name = "name")
  private String name;

  @ApiModelProperty(value = "时间片")
  @JSONField(name = "period")
  private int period;

  @ApiModelProperty(value = "指数值")
  @JSONField(name = "tpi")
  private double tpi;


  /**
   * 遍历-是否有相应字段.
   */
  public boolean containsKey(String key) {
    Class thisClass = this.getClass();
    Field[] fields = thisClass.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];
      String property = field.getAnnotation(JSONField.class).name();
      if (property.equals(key)) {
        return true;
      } else {
        continue;
      }
    }
    return false;
  }
}
