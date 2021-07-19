package com.sutpc.its.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ICommonQueryDao {

  List<Map<String, Object>> getDownloadInfo(Map<String, Object> map);

  void setDownloadInfo(Map<String, Object> map);

  List<Map<String, Object>> getRoadBig(Map<String, Object> map);

  List<Map<String, Object>> getRoadBigName(Map<String, Object> map);

  List<Map<String, Object>> getRoadBigDir(Map<String, Object> map);

  List<Map<String, Object>> getRoadMid(Map<String, Object> map);

  List<Map<String, Object>> getDistrict(Map<String, Object> map);

  List<Map<String, Object>> getDistrictForBus(Map<String, Object> map);

  List<Map<String, Object>> getDistrictForCity(Map<String, Object> map);

  List<Map<String, Object>> getBlock(Map<String, Object> map);

  List<Map<String, Object>> getSubsect(Map<String, Object> map);

  List<Map<String, Object>> getPoiCategory(Map<String, Object> map);

  List<Map<String, Object>> getPoi(Map<String, Object> map);

  List<Map<String, Object>> getInterestPoi();

  List<Map<String, Object>> getDetector(Map<String, Object> map);

  List<Map<String, Object>> getBusLane();

  List<Map<String, Object>> getGdRoadBig();

  List<Map<String, Object>> getGdRoadMid(Map<String, Object> map);

  List<Map<String, Object>> getCheckLine();

  List<Map<String, Object>> getCross();

  List<Map<String, Object>> getIntersectionDelay(Map<String, Object> map);

  List<Map<String, Object>> getVehicle(Map<String, Object> map);

  List<Map<String, Object>> getBusRouteFname(Map<String, Object> map);

  List<Map<String, Object>> getBusRouteDirection(Map<String, Object> map);

  List<Map<String, Object>> getTrafficEventType();

  List<Map<String, Object>> getParkRoadBaseInfo(Map<String, Object> map);

  List<Map<String, Object>> getFreightTransportInfo(Map<String, Object> map);

  Map<String, Object> getBaseDateListInfo(Map<String, Object> map);

  List<Map<String, Object>> getRoadType(Map<String, Object> map);
}
