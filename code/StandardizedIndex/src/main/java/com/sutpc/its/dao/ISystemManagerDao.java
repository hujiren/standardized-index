package com.sutpc.its.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ISystemManagerDao {

  Map<String, Object> getCityRoadNetworkBaseInfo();

  Map<String, Object> getEveryVehicleNums();

  Map<String, Object> getDevicePointNums();

  List<Map<String, Object>> getNounDefinitionList();

  int updateNounDefinition(@Param("id") String id, @Param("fname") String fname,
      @Param("description") String description, @Param("update_time") String updateTime,
      @Param("upload_person") String uploadPerson);

  int setNounDefinition(@Param("id") String id, @Param("fname") String fname,
      @Param("description") String description, @Param("create_time") String createTime,
      @Param("upload_person") String uploadPerson);

  int deleteNounDefinition(@Param("id") String id, @Param("update_time") String updateTime);

  int setSysSuggestion(Map<String, Object> map);
}
