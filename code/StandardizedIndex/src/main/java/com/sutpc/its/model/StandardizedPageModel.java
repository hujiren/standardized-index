package com.sutpc.its.model;

import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @Author:chensy
 * @Date:
 * @Description
 * @Modified By:
 */
public class StandardizedPageModel {

  //结果集
  private List<Map<String, Object>> list;
  //查询记录数
  private int totalRecords;
  //每页多少条数据
  private int pageSize;
  //第几页
  private int pageNum;

  /**
   * 总页数.
   */
  public int getTotalPages() {
    return (totalRecords + pageSize - 1) / pageSize;
  }

  /**
   * 取得首页.
   */
  public int getTopPageNum() {
    return 1;
  }

  public List<Map<String, Object>> getList() {
    return list;
  }

  /**
   * 上一页.
   */
  public int getPreviousPageNum() {
    if (pageNum <= 1) {
      return 1;
    }
    return pageNum - 1;
  }

  /**
   * 下一页.
   */
  public int getNextPageNum() {
    if (pageNum >= getBottomPageNum()) {
      return getBottomPageNum();
    }
    return pageNum + 1;
  }

  /**
   * 取得尾页.
   */
  public int getBottomPageNum() {
    return getTotalPages();
  }

  public void setList(List<Map<String, Object>> list) {
    this.list = list;
  }

  public int getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords(int totalRecords) {
    this.totalRecords = totalRecords;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getPageNum() {
    return pageNum;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

}
