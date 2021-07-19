package com.sutpc.its.dao;

import com.sutpc.its.model.DemoModel;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IDemoDao {


  List<DemoModel> findObjects(DemoModel map);


  int saveObject(DemoModel dm);

  int updateObject(DemoModel dm);

  List<Map<String, Object>> getBaseDistrictInfo(Map<String, Object> map);

}
