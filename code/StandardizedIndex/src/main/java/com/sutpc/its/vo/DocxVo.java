package com.sutpc.its.vo;

import freemarker.template.utility.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:04 2020/11/23.
 * @Description
 * @Modified By:
 */
@Data
public class DocxVo {

  @ApiModelProperty(value = "模块名称（controller作用）")
  private String moduleName = "";
  List<DocxSonVo> listSon;
}
