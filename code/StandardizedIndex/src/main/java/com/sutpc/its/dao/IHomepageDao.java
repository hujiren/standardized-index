package com.sutpc.its.dao;

import com.sutpc.its.po.BlockInfoPo;
import com.sutpc.its.po.CrossInfoPo;
import com.sutpc.its.po.DistrictStatusPo;
import com.sutpc.its.po.DistrictTpiChartsPo;
import com.sutpc.its.po.JamLengthInfoPo;
import com.sutpc.its.po.JamLengthListPo;
import com.sutpc.its.po.JamRoadsectInfoPo;
import com.sutpc.its.po.PoiAvgTpiPo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 首页dao.
 *
 * @Author: zuotw
 * @Date: created on 14:39 2020/8/26.
 * @Description
 * @Modified By:
 */
@Repository
@Mapper
public interface IHomepageDao {

  /**
   * 获取街道信息.
   *
   * @param time 日期
   * @param period15 15分钟时间片
   * @return 信息列表
   */
  List<BlockInfoPo> getBlockInfo(@Param("time") String time, @Param("period15") int period15);

  /**
   * 获取行政区指数曲线图.
   *
   * @param time 日期
   * @param period15 15分钟时间片
   * @return 信息列表
   */
  List<DistrictTpiChartsPo> getDistrictTpiCharts(@Param("time") String time,
      @Param("period15") int period15);

  /**
   * 获取拥堵里程.
   *
   * @param time 日期
   * @param period30 30分钟时间片
   * @return 值
   */
  double getJamLengths(@Param("time") String time, @Param("period30") int period30);

  /**
   * 获取关口信息.
   *
   * @param time 日期
   * @param period15 15分钟时间片
   * @return 结果
   */
  List<CrossInfoPo> getCrossInfo(@Param("time") String time, @Param("period15") int period15);

  /**
   * 获取拥堵路段.
   *
   * @param time 日期
   * @param period15 15分钟时间片
   * @return 结果
   */
  List<JamRoadsectInfoPo> getJamRoadsectInfo(@Param("time") String time,
      @Param("period15") int period15);

  /**
   * 热点平均指数.
   *
   * @param time 日期
   * @param period15 15分钟时间片
   * @return 结果
   */
  PoiAvgTpiPo getPoiAvgTpi(@Param("time") String time, @Param("period15") int period15);

  /**
   * 获取拥堵里程.
   *
   * @param time 日期
   * @param period30 半小时时间片
   * @return 结果
   */
  JamLengthInfoPo getJamLength(@Param("time") String time, @Param("period30") int period30);

  /**
   * 获取当前拥堵里程状态.
   *
   * @param time 日期
   * @param period30 半小时时间片
   * @return 结果
   */
  List<JamLengthListPo> getJamList(@Param("time") String time, @Param("period30") int period30);

  /**
   * 获取上周工作日拥堵里程.
   *
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param period30 半小时时间片
   * @return 结果
   */
  List<JamLengthListPo> getJamLastList(@Param("startDate") String startDate,
      @Param("endDate") String endDate, @Param("period30") int period30);

  /**
   * 获取行政区平均路况.
   *
   * @param time 当前日期
   * @param period 时间片
   * @return 结果
   */
  List<DistrictStatusPo> getDistrictStatus(@Param("time") String time, @Param("period") int period);
}
