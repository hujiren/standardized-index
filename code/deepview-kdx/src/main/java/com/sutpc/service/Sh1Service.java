package com.sutpc.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sutpc.dao.Sh1Dao;


@Service
public class Sh1Service {
	
	@Autowired
	private Sh1Dao sh1Dao;
	public List<Map<String, Object>> selectGrid2HotpointData(Map<String, Object> map) {
		return sh1Dao.selectGrid2HotpointData(map);
	}
	public List<Map<String, Object>> selectHotpoint2GridData(Map<String, Object> map) {
		return sh1Dao.selectHotpoint2GridData(map);
	}
}
