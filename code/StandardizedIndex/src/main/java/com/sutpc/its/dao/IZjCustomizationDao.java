package com.sutpc.its.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IZjCustomizationDao {

  List<Map<String, Object>> getSubsectHisTpi(Map<String, Object> map);

  List<Map<String, Object>> getRoadsectHisTpi(Map<String, Object> map);
}
