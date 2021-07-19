package com.sutpc.its.dao;

import com.sutpc.its.po.WorkDayPeakExtremaBlockInfoPo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 11:34 2020/5/27.
 * @Description
 * @Modified By:
 */
@Mapper
@Repository
public interface IModuleDistrictBlockDao {

  /**
   * 获取缓行及以上街道数量.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  int getSlowCount(@Param("startDate") int startDate, @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);

  /**
   * 获取畅通及基本畅通街道数量.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  int getUnimpededCount(@Param("startDate") int startDate, @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);

  /**
   * 获取交通压力最大街道相关信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   * @return WorkDayPeakExtremaBlockInfoPo
   */
  WorkDayPeakExtremaBlockInfoPo getMaxBlockInfo(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);

  /**
   * 获取交通压力最小（畅通）街道相关信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   * @return WorkDayPeakExtremaBlockInfoPo
   */
  WorkDayPeakExtremaBlockInfoPo getMinBlockInfo(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);

  /**
   * 获取各街道高峰时段平均速度.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   * @return WorkDayPeakExtremaBlockInfoPo
   */
  List<WorkDayPeakExtremaBlockInfoPo> getPeakBlockChartData(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);
}
