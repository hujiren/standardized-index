package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:52 2020/8/11.
 * @Description
 * @Modified By:
 */
@Data
public class GreenWaveVo {

  @ApiModelProperty(value = "绿波干线平均速度")
  private double speed;
  @ApiModelProperty(value = "实时绿波带条数")
  private int count;
  @ApiModelProperty(value = "绿波明细")
  private List<RoadSectionVo> list;
}
