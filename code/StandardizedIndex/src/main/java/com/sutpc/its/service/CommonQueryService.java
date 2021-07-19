package com.sutpc.its.service;

import com.sutpc.its.dao.ICommonQueryDao;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonQueryService {

  @Autowired
  private ICommonQueryDao commonQueryDao;

  /**
   * .
   */
  public List<Map<String, Object>> getRoadBig(Map<String, Object> map) {
    return commonQueryDao.getRoadBig(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadBigName(Map<String, Object> map) {
    return commonQueryDao.getRoadBigName(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadBigDir(Map<String, Object> map) {
    return commonQueryDao.getRoadBigDir(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadMid(Map<String, Object> paramap) {
    return commonQueryDao.getRoadMid(paramap);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDistrict(Map<String, Object> map) {
    if (!map.containsKey("range")) {
      map.put("range", "1");
    }
    return commonQueryDao.getDistrict(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDistrictForBus(Map<String, Object> map) {
    return commonQueryDao.getDistrictForBus(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDistrictForCity(Map<String, Object> map) {
    return commonQueryDao.getDistrictForCity(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getBlock(Map<String, Object> map) {
    return commonQueryDao.getBlock(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getSubsect(Map<String, Object> map) {
    return commonQueryDao.getSubsect(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getPoi(Map<String, Object> map) {
    return commonQueryDao.getPoi(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getInterestPoi() {
    return commonQueryDao.getInterestPoi();
  }

  /**
   * .
   */
  public List<Map<String, Object>> getPoiCategory(Map<String, Object> map) {
    return commonQueryDao.getPoiCategory(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getDetector(Map<String, Object> map) {
    return commonQueryDao.getDetector(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getBusLane() {
    return commonQueryDao.getBusLane();
  }

  /**
   * .
   */
  public List<Map<String, Object>> getGdRoadBig() {
    return commonQueryDao.getGdRoadBig();
  }

  /**
   * .
   */
  public List<Map<String, Object>> getGdRoadMid(Map<String, Object> map) {
    return commonQueryDao.getGdRoadMid(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getCheckLine() {
    return commonQueryDao.getCheckLine();
  }

  /**
   * .
   */
  public List<Map<String, Object>> getCross() {
    return commonQueryDao.getCross();
  }

  /**
   * .
   */
  public List<Map<String, Object>> getIntersectionDelay(Map<String, Object> map) {
    return commonQueryDao.getIntersectionDelay(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getVehicle(Map<String, Object> map) {
    return commonQueryDao.getVehicle(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getBusRouteFname(Map<String, Object> map) {
    return commonQueryDao.getBusRouteFname(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getBusRouteDirection(Map<String, Object> map) {
    return commonQueryDao.getBusRouteDirection(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getTrafficEventType() {
    return commonQueryDao.getTrafficEventType();
  }

  /**
   * .
   */
  public List<Map<String, Object>> getParkRoadBaseInfo(Map<String, Object> map) {
    return commonQueryDao.getParkRoadBaseInfo(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getFreightTransportInfo(Map<String, Object> map) {
    return commonQueryDao.getFreightTransportInfo(map);
  }

  /**
   * .
   *
   * @Author chensy
   * @Description 获取日期信息，第几周，第几天，是否工作日
   * @Modified by :
   * @params
   */
  public Map<String, Object> getBaseDateListInfo(Map<String, Object> map) {
    return commonQueryDao.getBaseDateListInfo(map);
  }

  /**
   * .
   */
  public List<Map<String, Object>> getRoadType(Map<String, Object> map) {
    return commonQueryDao.getRoadType(map);
  }

}
