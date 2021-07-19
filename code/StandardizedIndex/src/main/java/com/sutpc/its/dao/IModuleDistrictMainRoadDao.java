package com.sutpc.its.dao;

import com.sutpc.its.dto.WorkDayPeakMainRoadDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 16:55 2020/5/27.
 * @Description
 * @Modified By:
 */
@Mapper
@Repository
public interface IModuleDistrictMainRoadDao {

  /**
   * 工作日高峰时段主要干道高峰时段平均速度.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  double getMainRoadSpeed(@Param("startDate") int startDate, @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);

  /**
   * 主要干道交通运行速度表.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  List<WorkDayPeakMainRoadDto> getMainRoadInfo(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);
}
