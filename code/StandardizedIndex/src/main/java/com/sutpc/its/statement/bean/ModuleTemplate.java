package com.sutpc.its.statement.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Result;

/**
 * 模块模板.
 *
 * @author admin
 * @date 2020/5/20 11:39
 */
@Data
@ApiModel(value = "模块模板实体类")
public class ModuleTemplate {

  @ApiModelProperty(value = "主键")
  private String id;
  @ApiModelProperty(value = "模板类型标识，唯一")
  private String type;
  @ApiModelProperty(value = "模板分类")
  private String category;
  @ApiModelProperty(value = "模板分类-1，全市；2，行政区", example = "1")
  private int categoryId;
  @ApiModelProperty(value = "模板名称")
  private String name;
  @ApiModelProperty(value = "模板优先级")
  private int priority = 0;
  @ApiModelProperty(value = "模板示例路径，暂停图片")
  private String demoPath;
  @ApiModelProperty(value = "模板docx路径")
  private String filepath;
  @ApiModelProperty(value = "描述")
  private String brief;

}
