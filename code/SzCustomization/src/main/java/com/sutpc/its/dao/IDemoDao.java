package com.sutpc.its.dao;

import java.util.List;
import java.util.Map;

import com.sutpc.its.model.DemoModel;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface IDemoDao {

	
	List<DemoModel> findObjects(DemoModel map);
	
	
	int saveObject(DemoModel dm);
	
	int updateObject(DemoModel dm);

	List<Map<String,Object>> getBaseDistrictInfo(Map<String,Object> map);
	
}
