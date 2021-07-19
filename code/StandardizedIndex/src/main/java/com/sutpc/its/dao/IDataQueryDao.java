package com.sutpc.its.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IDataQueryDao {

  List<Map<String, Object>> getCalenderMonthData(Map<String, Object> map);

  List<Map<String, Object>> getCalendarDayData(Map<String, Object> map);

  List<Map<String, Object>> getCalendarNoWorkdayData(Map<String, Object> map);

  List<Map<String, Object>> getTpiQueryData(Map<String, Object> map);

  List<Map<String, Object>> getSpeedQueryData(Map<String, Object> map);

  List<Map<String, Object>> getBuslaneSpeed(Map<String, Object> map);

  List<Map<String, Object>> getBuslaneSpeedRate(Map<String, Object> map);

  List<Map<String, Object>> getBuslaneRealTimeStatus(Map<String, Object> map);

  List<Map<String, Object>> getAreaSpeed(Map<String, Object> map);

  List<Map<String, Object>> getAreaSpeedRate(Map<String, Object> map);

  List<Map<String, Object>> getDistrictJamLengthRatio(Map<String, Object> map);

  List<Map<String, Object>> getDistrictJamLengthRatio2(Map<String, Object> map);

  List<Map<String, Object>> getBlockJamLengthRatio(Map<String, Object> map);

  List<Map<String, Object>> getBlockJamLengthRatio2(Map<String, Object> map);

  List<Map<String, Object>> getSubsectJamLengthRatio(Map<String, Object> map);

  List<Map<String, Object>> getSubsectJamLengthRatio2(Map<String, Object> map);

  List<Map<String, Object>> getRoadJamLengthRatio(Map<String, Object> map);

  List<Map<String, Object>> getRoadJamLengthRatio2(Map<String, Object> map);

  List<Map<String, Object>> getJamSpaceTime(Map<String, Object> map);

  List<Map<String, Object>> getAvgJamLength(Map<String, Object> map);

  List<Map<String, Object>> getCheckLineQueryData(Map<String, Object> map);

  List<Map<String, Object>> getCrossQueryData(Map<String, Object> map);

  List<Map<String, Object>> getRoadSectionFlow(Map<String, Object> map);

  List<Map<String, Object>> getRoadSectionFlowHourCoefficient(Map<String, Object> map);

  List<Map<String, Object>> getIntersectionDelayData(Map<String, Object> map);

  List<Map<String, Object>> getBusTrajectoryData(Map<String, Object> map);

  Map<String, Object> getBusArrivalInterval(Map<String, Object> map);

  List<Map<String, Object>> getBusStopInfo(Map<String, Object> map);

  /**
   * 性能优化调整后方法.
   *
   * @param map 参数
   * @return 结果
   */
  List<Map<String, Object>> getSpeedQueryDataNew(Map<String, Object> map);
}
