package com.sutpc.its.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:07 2020/11/23.
 * @Description
 * @Modified By:
 */
@Data
public class DocxSonVo {

  /**
   * 小模块名称
   */
  private String name = "";
  @ApiModelProperty(value = "需求")
  private String requirement = "";
  @ApiModelProperty(value = "输入")
  private String input = "";
  @ApiModelProperty(value = "输出")
  private String output = "";
  @ApiModelProperty(value = "处理")
  private String handle = "";
  @ApiModelProperty(value = "请求方式")
  private String method = "";
  @ApiModelProperty(value = "请求url")
  private String url = "";
  @ApiModelProperty(value = "参数")
  private String param = "";


}
