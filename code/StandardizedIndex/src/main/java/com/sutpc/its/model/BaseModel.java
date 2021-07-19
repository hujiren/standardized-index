package com.sutpc.its.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;

@ApiModel("Demo实体类")
public class BaseModel {

  //@ApiModelProperty(hidden = true)
  private String id;

  @ApiModelProperty(hidden = true)
  @JSONField(serialize = false)
  private String sysUuid;

  private String time;
  private String period15;


  public String getSysUuid() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
