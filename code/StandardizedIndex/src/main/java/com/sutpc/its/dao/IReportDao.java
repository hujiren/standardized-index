package com.sutpc.its.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IReportDao {

  int setSpecialReportInfo(Map<String, Object> map);

  List<Map<String, Object>> getSpecialReportInfo(Map<String, Object> map);

  int updateSpecialReportInfo(Map<String, Object> map);

  int deleteSpecialReportInfo(Map<String, Object> map);

  List<Map<String, Object>> getSubmitReportInfo(Map<String, Object> map);

  List<Map<String, Object>> getSpecialReportInfoByPage(Map<String, Object> map);

  List<Integer> getDistrictIds();

  int setMonthReport(@Param("id") String id, @Param("fdate") String fdate,
      @Param("districtFid") int districtFid, @Param("json") String json, @Param("key") String key);

  Map<String, Object> getMonthReport(@Param("key") String key);
}
