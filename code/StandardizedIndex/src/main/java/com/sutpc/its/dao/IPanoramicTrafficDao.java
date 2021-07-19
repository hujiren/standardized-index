package com.sutpc.its.dao;

import com.sutpc.its.dto.KeyAreaAlterDto;
import com.sutpc.its.dto.KeyAreaBaseDto;
import com.sutpc.its.po.DetectorChartsEntity;
import com.sutpc.its.po.DetectorInfoEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IPanoramicTrafficDao {

  List<Map<String, Object>> getTpi(Map<String, Object> map);

  List<Map<String, Object>> getRealTimeRoadsect(Map<String, Object> map);

  List<Map<String, Object>> getBlockPeakStatus(Map<String, Object> map);

  List<Map<String, Object>> getCitywideTrafficTpi(Map<String, Object> map);

  Map<String, Object> getCitywideMonthAvgTpi(Map<String, Object> map);

  List<Map<String, Object>> getCitywideTrafficSpeed(Map<String, Object> map);

  List<Map<String, Object>> getMonthRoadsectWorsen(Map<String, Object> map);

  List<Map<String, Object>> getMonthRoadsectInDistrictNum(Map<String, Object> map);

  int setTpiTrafficEvent(Map<String, Object> map);

  int updateTpiTrafficEvent(Map<String, Object> map);

  List<Map<String, Object>> getTpiTrafficEvent(Map<String, Object> map);

  int deleteTpiTrafficEvent(Map<String, Object> map);

  List<Map<String, Object>> getTpiTrafficByFdate(Map<String, Object> map);

  List<Map<String, Object>> getYearTpiAndTrafficEventData(Map<String, Object> map);

  List<Map<String, Object>> getTrafficTpiData(Map<String, Object> map);

  List<Map<String, Object>> getTrafficRealTimeJamRanking(Map<String, Object> map);

  List<Map<String, Object>> getMainRoadRealTimeJamRanking(Map<String, Object> map);

  List<Map<String, Object>> getMainBuslaneLineDataByBuslaneId(Map<String, Object> map);

  List<Map<String, Object>> getMainPoiRealTimeJamRanking(Map<String, Object> map);

  List<Map<String, Object>> getPoiRealTimeTrafficStatus(Map<String, Object> map);

  KeyAreaBaseDto getKeyAreaSpeedAndTpi(@Param("time") int time, @Param("period") int period,
      @Param("hotspotId") int hotspotId);

  KeyAreaAlterDto getKeyAreaAlertInfo(@Param("period") int period, @Param("id") int id);

  List<DetectorInfoEntity> getDetectorPointsInfo();

  List<DetectorChartsEntity> getDetectorCharts(@Param("time") String time, @Param("hour") int hour,
      @Param("id") int id);

  int getCapacityById(@Param("id") int id);
}

