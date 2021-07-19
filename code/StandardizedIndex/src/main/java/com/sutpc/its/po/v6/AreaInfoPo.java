package com.sutpc.its.po.v6;

import com.sutpc.its.tools.TpiUtils;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:41 2020/10/18.
 * @Description
 * @Modified By:
 */
@Data
public class AreaInfoPo {

  @ApiModelProperty(value = "创建时间")
  private String createTime;
  @ApiModelProperty(value = "创建用户")
  private String creator;
  @ApiModelProperty(value = "区域面积")
  private double area;
  @ApiModelProperty(value = "区域总里程.单位：千米")
  private double lengths;
  @ApiModelProperty(value = "道路等级分布")
  private List<RoadTypeLengthsPo> list;
  @ApiModelProperty(value = "区域小路段组成")
  private String links;
  @ApiModelProperty(value = "区域中路段")
  private String sections;
  @ApiModelProperty(value = "区域大路段")
  private String roads;

  public void setLengths(double lengths) {
    this.lengths = TpiUtils.getByDigit(Double.parseDouble(String.valueOf(lengths)) / 1000.00, 2);
  }
}
