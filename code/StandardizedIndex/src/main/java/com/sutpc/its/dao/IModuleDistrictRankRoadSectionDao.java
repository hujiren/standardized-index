package com.sutpc.its.dao;

import com.sutpc.its.dto.RoadSectionInfoDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 路段交通运行-数据接口.
 *
 * @Author: zuotw
 * @Date: created on 16:22 2020/6/1.
 * @Description
 * @Modified By:
 */
@Repository
@Mapper
public interface IModuleDistrictRankRoadSectionDao {

  /**
   * 获取快速路信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  List<RoadSectionInfoDto> getFastInfo(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);

  /**
   * 获主干速路信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  List<RoadSectionInfoDto> getMainInfo(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);

  /**
   * 获次干速路信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  List<RoadSectionInfoDto> getSubInfo(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);
}
