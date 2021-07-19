package com.sutpc.its.dao;

import com.sutpc.its.po.WorkDayPeakInfoPo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 辖区整体模块.
 *
 * @author zuotw
 * @date 2020/5/25 15:35
 */
@Mapper
@Repository
public interface IModuleDistrictTotalNetDao {

  /**
   * 全路网高峰平均速度.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  double getAllPeakAverageSpeed(@Param("startDate") int startDate, @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);

  /**
   * 全路网早高峰平均速度.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  double getAllMorningPeakAverageSpeed(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);

  /**
   * 全路网早高峰指数.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  double getAllMorningPeakTpi(@Param("startDate") int startDate, @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);

  /**
   * 全路网晚高峰平均速度.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  double getAllEveningPeakAverageSpeed(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);

  /**
   * 全路网晚高峰指数.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  double getAllEveningPeakTpi(@Param("startDate") int startDate, @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);

  /**
   * 全路网高峰最大速度值信息（日期、速度值）.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  WorkDayPeakInfoPo getWorkDayPeakMaxInfo(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);

  /**
   * 全路网高峰最小速度值信息（日期、速度值）.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  WorkDayPeakInfoPo getWorkDayPeakMinInfo(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);

  /**
   * 获取全区路网高峰时段日均速度变化图-全市.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   */
  List<WorkDayPeakInfoPo> getCityChartData(@Param("startDate") int startDate,
      @Param("endDate") int endDate);

  /**
   * 获取全区路网高峰时段日均速度变化图-行政区.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  List<WorkDayPeakInfoPo> getDistrictChartData(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);
}
