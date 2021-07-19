package com.sutpc.its.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:30 2020/9/7.
 * @Description
 * @Modified By:
 */
@Data
public class FlowsDistributionEntity {

  @ApiModelProperty(value = "热点人流量等级分布")
  private List<FlowsDistributionDto> data;
  @ApiModelProperty(value = "当前流量")
  private int flow;

  public void setData(List<FlowsDistributionDto> data) {
    this.data = data;
    for (FlowsDistributionDto dto:data){
      this.flow += dto.getFlows();
    }
  }
}
