package com.sutpc.its.dto;

import com.sutpc.its.po.DetectorChartsEntity;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:19 2020/7/22.
 * @Description
 * @Modified By:
 */
@Data
public class DetectorChartDto {

  @ApiModelProperty(value = "曲线信息")
  private List<DetectorChartsEntity> list;
  @ApiModelProperty(value = "阈值")
  private int capacity;
  @ApiModelProperty(value = "实时流量")
  private int flow;

  /**
   * 塞入flow.
   */
  public void setList(List<DetectorChartsEntity> list) {
    this.list = list;
    if (list != null && list.size() > 0) {
      this.flow = list.get(list.size() - 1).getVolume();
    }
  }
}
