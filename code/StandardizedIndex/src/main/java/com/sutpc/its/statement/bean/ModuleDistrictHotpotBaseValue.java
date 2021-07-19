package com.sutpc.its.statement.bean;

import com.sutpc.its.po.HotpotInfoPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * 热点-周边详情.
 *
 * @Author: zuotw
 * @Date: created on 9:28 2020/6/1.
 * @Description
 * @Modified By:
 */
@Data
@ApiModel(value = "热点-(医院、商圈、口岸、学校、景区)周边详情")
public class ModuleDistrictHotpotBaseValue {

  @ApiModelProperty(value = "标题名称")
  private String title;
  @ApiModelProperty(value = "指数")
  private double tpi;
  @ApiModelProperty(value = "环比")
  private double ratio;
  @ApiModelProperty(value = "指数状态")
  private String status;
  @ApiModelProperty(value = "列表")
  private List<HotpotInfoPo> list;
}
