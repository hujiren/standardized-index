package com.sutpc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface Sh1Dao {
	List<Map<String, Object>> selectGrid2HotpointData(Map<String, Object> map);
	List<Map<String, Object>> selectHotpoint2GridData(Map<String, Object> map);
}
