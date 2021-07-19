package com.sutpc.its.dao;

import com.sutpc.its.dto.RoadCharacteristicDto;
import com.sutpc.its.dto.RoadLevelDistributionDto;
import com.sutpc.its.dto.RoadPortrayalTagDto;
import com.sutpc.its.dto.RoadSectionJamCountDto;
import com.sutpc.its.dto.RoadSixDByIdDto;
import com.sutpc.its.dto.RoadSixDDto;
import com.sutpc.its.dto.RoadsectDistributionDto;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ISpecialtyAnalysisDao {

  List<Map<String, Object>> getJamSpaceTimeData(Map<String, Object> map);

  List<Map<String, Object>> getJamSpaceTimeFiveMinData(Map<String, Object> map);

  Map<String, Object> getWeekDays(Map<String, Object> map);

  List<Map<String, Object>> getRoadWeekChangeData(Map<String, Object> map);

  String getRoadMidType(int id);

  List<Map<String, Object>> getRoadWeekCompare(Map<String, Object> map);

  List<Map<String, Object>> getJamRanking_JamTimeRate(Map<String, Object> map);

  List<Map<String, Object>> getJamRanking_Tpi(Map<String, Object> map);

  List<Map<String, Object>> getParkingTollRoadMapData(Map<String, Object> map);

  List<Map<String, Object>> getParkingTollRoad(Map<String, Object> map);

  List<Map<String, Object>> getAllHoiday(Map<String, Object> map);

  List<Map<String, Object>> getAllSpotindexCategory(Map<String, Object> map);

  List<Map<String, Object>> getAllSpotindex(Map<String, Object> map);

  List<Map<String, Object>> getSpotAroundJamRoadsectStatus(Map<String, Object> map);

  List<Map<String, Object>> getHolidayQueryResult(Map<String, Object> map);

  List<Map<String, Object>> getTaxiIndexData(Map<String, Object> map);

  Map<String, Object> getTripDistanceData(Map<String, Object> map);

  Map<String, Object> getTripTimeData(Map<String, Object> map);

  List<Map<String, Object>> getNavigationCarIndexData(Map<String, Object> map);

  Map<String, Object> getTripSpeedData(Map<String, Object> map);

  List<Map<String, Object>> getOdDistributionData(Map<String, Object> map);

  List<Map<String, Object>> getTripsData(Map<String, Object> map);

  List<Map<String, Object>> getRoadsectFlow(Map<String, Object> map);

  List<Map<String, Object>> getDistrictLeaveAndArrivalRanking(Map<String, Object> map);

  List<Map<String, Object>> getContrastiveSpeed(Map<String, Object> map);

  List<Map<String, Object>> getContrastiveTpi(Map<String, Object> map);

  Map<String, Object> getTotalSpeed(Map<String, Object> map);

  Map<String, Object> getTotalTpi(Map<String, Object> map);

  List<Map<String, Object>> getRoadRunState(Map<String, Object> map);

  List<Map<String, Object>> getRoadFlevelNum(Map<String, Object> map);

  List<Map<String, Object>> getCongestionRoadInfo(Map<String, Object> map);

  List<Map<String, Object>> getCongestionRoadInfoByPage(Map<String, Object> map);

  List<Map<String, Object>> getArrivalLeaveFreightTransportNumByDistrict(Map<String, Object> map);

  List<Map<String, Object>> getArrivalLeaveFreightTransportNumByBlock(Map<String, Object> map);

  List<Map<String, Object>> getArrivalLeaveFreightTransportNumByDistrictByHour(
      Map<String, Object> map);

  List<Map<String, Object>> getArrivalLeaveFreightTransportNumByBlockByHour(
      Map<String, Object> map);

  List<Map<String, Object>> getFreightTransportHpArrivalLeave(Map<String, Object> map);

  List<Map<String, Object>> getFreightTransportPass(Map<String, Object> map);

  List<Map<String, Object>> getHotpointInfo(Map<String, Object> map);

  List<RoadSixDDto> getRoadSixDData(@Param("date") int date,
      @Param("timeproperty") String timeproperty);

  List<RoadLevelDistributionDto> getRoadLevelDistribution(@Param("date") int date);

  RoadCharacteristicDto getRoadCharacteristicData(@Param("date") int date,
      @Param("timeproperty") String timeproperty, @Param("roadsectFid") int roadsectFid);

  int getRoadSetId(@Param("id") int id);

  RoadPortrayalTagDto getRoadPortrayalTag(@Param("id") int id, @Param("date") int date);

  List<RoadsectDistributionDto> getRoadsectDistribution(@Param("date") int date);

  RoadSixDByIdDto getRoadSixDDataById(@Param("id") int id, @Param("date") int date,
      @Param("timeproperty") String timeproperty);

  RoadSixDByIdDto getRoadSixDDataAll(@Param("date") int date,
      @Param("timeproperty") String timeproperty);

  RoadSectionJamCountDto getRoadSectionJamCounts(@Param("date") int date);

}
