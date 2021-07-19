package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:28 2020/11/10.
 * @Description
 * @Modified By:
 */
@Data
public class HistorySituationVo {

  @ApiModelProperty(value = "整天")
  private List<HistoryInfoVo> allDay;
  @ApiModelProperty(value = "早高峰")
  private List<HistoryInfoVo> morningPeak;
  @ApiModelProperty(value = "晚高峰")
  private List<HistoryInfoVo> eveningPeak;
  @ApiModelProperty(value = "平峰")
  private List<HistoryInfoVo> flatHumpPeak;

}
