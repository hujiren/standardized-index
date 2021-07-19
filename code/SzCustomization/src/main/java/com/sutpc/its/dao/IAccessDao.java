package com.sutpc.its.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IAccessDao {
    List<Map<String, Object>> selectGrid2HotpointData(Map<String, Object> map);
    List<Map<String, Object>> selectHotpoint2GridData(Map<String, Object> map);

    Map<String, Object> getTripTimeDistribution(Map<String,Object> map);
    Map<String, Object> getTripDistanceDistribution(Map<String,Object> map);
}
