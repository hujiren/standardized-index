package com.sutpc.its.service;

import com.sutpc.its.po.BlockInfoAllInfoPo;
import com.sutpc.its.po.CrossInfoPo;
import com.sutpc.its.po.DistrictStatusPo;
import com.sutpc.its.po.DistrictTpiChartsPo;
import com.sutpc.its.po.JamLengthInfoPo;
import com.sutpc.its.po.JamRoadsectInfoPo;
import com.sutpc.its.po.PoiAvgTpiPo;
import com.sutpc.its.po.RealTimeJamLengthPo;
import java.util.List;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:43 2020/8/26.
 * @Description
 * @Modified By:
 */
public interface IHomepageService {

  /**
   * 获取街道片区相关信息.
   *
   * @return 结果
   */
  BlockInfoAllInfoPo getBlockInfo();

  /**
   * 获取行政区指数曲线.
   *
   * @return 结果
   */
  List<DistrictTpiChartsPo> getDistrictTpiCharts();

  /**
   * 获取关口信息.
   *
   * @return 结果
   */
  List<CrossInfoPo> getCrossInfoPo();

  /**
   * 获取拥堵路段.
   *
   * @return 结果
   */
  List<JamRoadsectInfoPo> getJamRoadsectInfo();

  /**
   * 获取热点区域平均指数和环比.
   *
   * @return 结果
   */
  PoiAvgTpiPo getPoiAvgTpiAndRatio();

  /**
   * 获取拥堵里程.
   *
   * @return 结果
   */
  JamLengthInfoPo getJamLength();

  /**
   * 实时拥堵里程.
   *
   * @return 结果
   */
  RealTimeJamLengthPo getRealTimeJamLength();

  /**
   * 获取行政区平均路况.
   *
   * @return 结果
   */
  List<DistrictStatusPo> getDistrictStatus();
}
