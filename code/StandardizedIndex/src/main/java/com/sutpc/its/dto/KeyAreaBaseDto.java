package com.sutpc.its.dto;

import com.sutpc.its.po.DetectorInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:35 2020/6/23.
 * @Description
 * @Modified By:
 */
@Data
public class KeyAreaBaseDto {

  @ApiModelProperty(value = "速度")
  private double speed;
  @ApiModelProperty(value = "指数")
  private double tpi;
  @ApiModelProperty(value = "交通运力-公交车（辆）")
  private int bus;
  @ApiModelProperty(value = "交通运力-出租车（辆）")
  private int taxi;
  @ApiModelProperty(value = "交通运力-网约车（辆）")
  private int net;
  @ApiModelProperty(value = "公交车坐标点位分布")
  private List<Map<String, Double>> busList;
  @ApiModelProperty(value = "出租车坐标点位分布")
  private List<Map<String, Double>> taxiList;
  @ApiModelProperty(value = "网约车坐标点位分布")
  private List<Map<String, Double>> netList;
  @ApiModelProperty(value = "地磁点位信息分布")
  private List<DetectorInfoEntity> detectorList;

  public void setBusList(List<Map<String, Double>> busList) {
    this.busList = busList;
    this.bus = busList.size();
  }

  public void setTaxiList(List<Map<String, Double>> taxiList) {
    this.taxiList = taxiList;
    this.taxi = taxiList.size();
  }

  public void setNetList(List<Map<String, Double>> netList) {
    this.netList = netList;
    this.net = netList.size();
  }
}
