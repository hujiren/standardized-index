package com.sutpc.its.statement.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * 报表返回值.
 *
 * @author admin
 * @date 2020/5/19 14:27
 */
@Data
@ApiModel("报表返回值")
public class StatementValue {

  @ApiModelProperty(value = "计算开始时间")
  private Long startTime;
  @ApiModelProperty(value = "计算结束时间")
  private Long endTime;
  @ApiModelProperty(value = "模块列表")
  private List<ModuleValue> modules;

  /**
   * 添加模块值.
   *
   * @param module 模块
   */
  public void putModule(ModuleValue module) {
    if (this.modules == null) {
      this.modules = new ArrayList<>();
    }
    this.modules.add(module);
  }
}
