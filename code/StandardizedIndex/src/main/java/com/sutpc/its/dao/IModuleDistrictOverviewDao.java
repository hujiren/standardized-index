package com.sutpc.its.dao;

import com.sutpc.its.dto.RoadSectionInfoDto;
import com.sutpc.its.po.PeakHotPotPo;
import com.sutpc.its.po.WorkDayPeakExtremaBlockInfoPo;
import com.sutpc.its.po.WorkDayPeakInfoPo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:14 2020/6/3.
 * @Description
 * @Modified By:
 */
@Repository
@Mapper
public interface IModuleDistrictOverviewDao {

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
   * 全路网高峰指数.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  double getAllPeakTpi(@Param("startDate") int startDate, @Param("endDate") int endDate,
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
   * 高峰时段最大指数信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  PeakHotPotPo getPeakMaxHotSpotTpi(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);

  /**
   * 高峰时段最小指数热点信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  PeakHotPotPo getPeakMinHotSpotTpi(@Param("startDate") int startDate,
      @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);

  /**
   * 主干道工作日高峰时段平均速度.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  double getMainRoadSectionSpeed(@Param("startDate") int startDate, @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);

  /**
   * 获取主干道工作日高峰列表信息，用于计算最大最小环比值等信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   */
  List<RoadSectionInfoDto> getMainRoadSectionInfo(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);

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
