package com.sutpc.its.statement.bean;

import com.sutpc.its.vo.CityDistrictSpeedVo;
import com.sutpc.its.vo.CityDistrictTpiVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * 全市行政区交通运行模块值.
 *
 * @author admin
 * @date 2020/5/20 10:53
 */
@Data
@ApiModel(value = "全市行政区交通运行模块值", parent = ModuleValue.class)
public class ModuleCityDistrictValue extends ModuleValue {

  @ApiModelProperty(value = "高峰时段压力最大区域")
  private String highestName;
  @ApiModelProperty(value = "高峰时段压力最大区域速度")
  private double highestSpeed;
  @ApiModelProperty(value = "高峰时段压力最大区域指数")
  private double highestTpi;
  @ApiModelProperty(value = "高峰时段压力最大区域运行等级")
  private String highestStatus;

  @ApiModelProperty(value = "高峰时最畅通区域")
  private String lowestName;
  @ApiModelProperty(value = "高峰时段最畅通区域速度")
  private double lowestSpeed;
  @ApiModelProperty(value = "高峰时段最畅通区域指数")
  private double lowestTpi;
  @ApiModelProperty(value = "高峰时段最畅通区域运行等级")
  private String lowestStatus;

  @ApiModelProperty(value = "各行政区高峰时段交通指数变化")
  private List<CityDistrictTpiVo> tpis;
  @ApiModelProperty(value = "各行政区交通运行速度及变化")
  private List<CityDistrictSpeedVo> speeds;

}
