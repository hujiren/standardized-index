package com.sutpc.its.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:27 2020/6/17.
 * @Description
 * @Modified By:
 */
@Repository
@Mapper
public interface IModuleDistrictHeadFootDao {

  /**
   * 获取行政名称.
   *
   * @param districtId 行政区id
   * @return 行政区名称
   */
  String getDistrictNameById(@Param("districtId") Integer districtId);
}
