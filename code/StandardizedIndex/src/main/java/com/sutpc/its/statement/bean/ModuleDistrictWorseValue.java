package com.sutpc.its.statement.bean;

import com.sutpc.its.po.RoadSectionChangePo;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * 交通运行恶化路段信息.
 *
 * @Author: zuotw
 * @Date: created on 15:29 2020/6/2.
 * @Description
 * @Modified By:
 */
@Data
public class ModuleDistrictWorseValue {

  @ApiModelProperty(value = "标题名称")
  private String title;
  @ApiModelProperty(value = "交通运行恶化路段（当周指数>6且环比大于5%）总数")
  private int count;
  @ApiModelProperty(value = "交通运行恶化路段列表")
  private List<RoadSectionChangePo> list;
}
