package com.sutpc.its.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("分页参数")
public class PageModel {
	
	
	
	@ApiModelProperty(value = "起始页码", example = "1")
	private Integer pageNum;
	@ApiModelProperty(value = "每页数量", example = "5")
	private Integer pageSize;
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
	
	
}
