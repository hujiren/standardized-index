package com.sutpc.its.service;

import com.sutpc.its.dao.IVideoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VideoService {
    @Autowired
    private IVideoDao iVideoDao;
    public List<Map<String,Object>> getVideoLocation(Map<String,Object> map){
        return iVideoDao.getVideoLocation(map);
    }
}
