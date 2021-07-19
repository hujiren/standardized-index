package com.sutpc.its.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IAppletDao {

  /**
   * 获取全市实时信息.
   */
  List<Map<String, Object>> getCityRealTimeInfo(Map<String, Object> map);

  List<Map<String, Object>> getKeyAreaAvgSpeed(Map<String, Object> map);

  List<Map<String, Object>> getKeyCrossAvgSpeed(Map<String, Object> map);

  List<Map<String, Object>> getKeyPoiINfo(Map<String, Object> map);

  List<Map<String, Object>> getKeyPoiStatusByPoiFid(Map<String, Object> map);

  List<Map<String, Object>> getKeyPoiSpeedByPoiFid(Map<String, Object> map);

  List<Map<String, Object>> getRoadsectOperationDistribute(Map<String, Object> map);

  Map<String, Object> getPeakTpi(Map<String, Object> map);

  List<Map<String, Object>> getDayReportAvgSpeed(Map<String, Object> map);

  List<Map<String, Object>> getDayReportAvgTpi(Map<String, Object> map);

  List<Map<String, Object>> getEveryDistrictSpeed(Map<String, Object> map);

  List<Map<String, Object>> getMajorRoadSpeed(Map<String, Object> map);

  List<Map<String, Object>> getMajorCrossSpeed(Map<String, Object> map);

  List<Map<String, Object>> getWeekCityPeak(Map<String, Object> map);

  List<Map<String, Object>> getWeekDistrictTpiRanking(Map<String, Object> map);

  List<Map<String, Object>> getWeekBlockTpiRanking(Map<String, Object> map);

  List<Map<String, Object>> getWeekPoiSpeedRanking(Map<String, Object> map);

  List<Map<String, Object>> getWeekCrossPeak(Map<String, Object> map);

  List<Map<String, Object>> getWeekJamRoadsectRanking(Map<String, Object> map);

  List<Map<String, Object>> getUpdateDataTime(Map<String, Object> map);
}
