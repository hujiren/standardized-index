package com.sutpc.its.statement.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模块返回值.
 *
 * @author admin
 * @date 2020/5/20 10:53
 */
@Data
@ApiModel(value = "模块返回值")
public class ModuleValue {

  @ApiModelProperty(value = "标题名称")
  private String title;
  @ApiModelProperty(value = "模块类型")
  private String type;
  @ApiModelProperty(value = "模块是否匹配，默认为true")
  private boolean found = true;
  @ApiModelProperty(value = "模块对应的值")
  private Object value;

  public ModuleValue() {
  }

  public ModuleValue(Object value, String type) {
    this.value = value;
    this.type = type;
  }

  public <T> T getValue(Class<T> cls) {
    return JSONObject.parseObject(JSON.toJSONString(value), cls);
  }
}
