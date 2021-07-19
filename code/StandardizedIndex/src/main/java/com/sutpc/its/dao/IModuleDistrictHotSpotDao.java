package com.sutpc.its.dao;

import com.sutpc.its.dto.HotpotInfoDto;
import com.sutpc.its.po.PeakHotPotPo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:10 2020/5/29.
 * @Description
 * @Modified By:
 */
@Mapper
@Repository
public interface IModuleDistrictHotSpotDao {

  /**
   * 高峰时段最大指数信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   * @return 结果
   */
  PeakHotPotPo getPeakMaxHotSpotTpi(@Param("startDate") int startDate,
      @Param("endDate") int endDate, @Param("districtFid") int districtFid);

  /**
   * 高峰时段最小指数热点信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   * @return 结果
   */
  PeakHotPotPo getPeakMinHotSpotTpi(@Param("startDate") int startDate,
      @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);

  /**
   * 获取热点-医院基础信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   * @return 结果
   */
  List<HotpotInfoDto> getHospitalInfo(@Param("startDate") int startDate,
      @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);

  /**
   * 获取热点-学校基础信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   * @return 结果
   */
  List<HotpotInfoDto> getSchoolInfo(@Param("startDate") int startDate,
      @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);

  /**
   * 获取热点-枢纽基础信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   * @return 结果
   */
  List<HotpotInfoDto> getHingeInfo(@Param("startDate") int startDate, @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);

  /**
   * 获取热点-商圈基础信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   * @return 结果
   */
  List<HotpotInfoDto> getBusinessInfo(@Param("startDate") int startDate,
      @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);

  /**
   * 获取热点-景区基础信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   * @return 结果
   */
  List<HotpotInfoDto> getScenicInfo(@Param("startDate") int startDate,
      @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);

  /**
   * 获取热点-口岸基础信息.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param districtFid 行政区id
   * @return 结果
   */
  List<HotpotInfoDto> getPortInfo(@Param("startDate") int startDate, @Param("endDate") int endDate,
      @Param("districtFid") int districtFid);
}
