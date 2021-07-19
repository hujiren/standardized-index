package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:22 2020/11/10.
 * @Description
 * @Modified By:
 */
@Data
public class TotalMonitor {

  @ApiModelProperty(value = "当天实时")
  private List<TotalRealTimeMonitorVo> list;
  @ApiModelProperty(value = "近20天")
  private List<TotalRealTimeMonitorVo> lately;
}
