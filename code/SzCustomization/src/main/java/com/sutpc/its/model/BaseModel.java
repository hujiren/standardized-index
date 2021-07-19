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
	private String sys_uuid;

	private String time;
	private String period15;
	
	public String getSys_uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
