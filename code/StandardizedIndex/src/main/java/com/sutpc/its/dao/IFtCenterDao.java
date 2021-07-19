package com.sutpc.its.dao;

import com.sutpc.its.dto.CheckParams;
import com.sutpc.its.po.BusRoadsectStatus;
import com.sutpc.its.po.PeriodValuePo;
import com.sutpc.its.po.PoiNearRoadSectionPo;
import com.sutpc.its.po.SubAreaInfo;
import com.sutpc.its.vo.LinkVo;
import com.sutpc.its.vo.RoadSectionVo;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:52 2020/3/19.
 * @Description
 * @Modified By:
 */
@Repository
@Mapper
public interface IFtCenterDao {

  Map<String, Object> getCenterDistrictStatus(Map<String, Object> map);

  Map<String, Object> getCityDistrictStatus(Map<String, Object> map);

  List<Map<String, Object>> getCenterDayStatus(Map<String, Object> map);

  Map<String, Object> getFtcRealTimeTpiAndSpeed();

  Map<String, Object> getFtcJamLength();

  //  double getGreenWaveAvgSpeed(@Param("time") String time, @Param("period") int period,
  //      @Param("subSegmentId") String[] subSegmentId);

  List<RoadSectionVo> getGreenWaveList(@Param("time") String time, @Param("period") int period,
      @Param("subSegmentId") String[] subSegmentId);

  List<LinkVo> getRealTimeLinkInfo();

  List<SubAreaInfo> getGreenWaveAreaInfo(@Param("subSegmentId") String[] subSegmentId);

  List<BusRoadsectStatus> getBusRoadsectStatusNow(@Param("time") String time,
      @Param("period") int period);

  List<PoiNearRoadSectionPo> getPoiNearRoadsect(CheckParams checkParams);

  List<PeriodValuePo> getFtcBusSpeed(CheckParams checkParams);

  List<PeriodValuePo> getFtcBusSpeedRatio(CheckParams checkParams);

  List<PeriodValuePo> getFtcSpeedOrTpi(CheckParams checkParams);
}
