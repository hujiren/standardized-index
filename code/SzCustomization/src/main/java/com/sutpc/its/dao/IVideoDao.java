package com.sutpc.its.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IVideoDao {
    public List<Map<String, Object>> getVideoLocation(Map<String,Object> map);
}
