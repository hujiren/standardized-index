package com.sutpc.its.po;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:49 2020/9/7.
 * @Description
 * @Modified By:
 */
@Data
public class RealTimeJamLengthPo {

  @ApiModelProperty(value = "当前拥堵里程")
  private List<JamLengthListPo> currentData;
  @ApiModelProperty(value = "上周平均拥堵里程")
  private List<JamLengthListPo> lastData;
}
