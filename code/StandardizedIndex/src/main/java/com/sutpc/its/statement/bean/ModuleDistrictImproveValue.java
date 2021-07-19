package com.sutpc.its.statement.bean;

import com.sutpc.its.po.RoadSectionChangePo;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * 交通运行改善路段.
 *
 * @Author: zuotw
 * @Date: created on 17:23 2020/6/2.
 * @Description
 * @Modified By:
 */
@Data
public class ModuleDistrictImproveValue {

  @ApiModelProperty(value = "标题名称")
  private String title;
  @ApiModelProperty(value = "交通运行改善路段（上周指数>6且环比小于-5%）总数")
  private int count;
  @ApiModelProperty(value = "交通运行改善路段列表")
  private List<RoadSectionChangePo> list;
}
