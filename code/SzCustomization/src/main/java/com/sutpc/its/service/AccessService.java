package com.sutpc.its.service;
import java.text.DecimalFormat;
import	java.util.HashMap;

import com.sutpc.its.dao.IAccessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccessService {
    @Autowired
    private IAccessDao accessDao;
    public List<Map<String, Object>> selectGrid2HotpointData(Map<String, Object> map) {
        return accessDao.selectGrid2HotpointData(map);
    }
    public List<Map<String, Object>> selectHotpoint2GridData(Map<String, Object> map) {
        return accessDao.selectHotpoint2GridData(map);
    }
    public Map<String,Object> getTripTimeDistribution(Map<String,Object> map){
        Map<String,Object> result = new HashMap<String, Object> ();
        double sumarea =0.00;
        DecimalFormat df = new DecimalFormat("#0.00");
        if(true){
            map.put("start_travel_time",0);
            map.put("end_travel_time",1800);
            Map<String,Object> m = accessDao.getTripTimeDistribution(map);
            if(m!=null){
                sumarea =sumarea+Double.parseDouble(m.get("NUM").toString());
                result.put("time0_30",m.get("NUM"));
            }else{
                result.put("time0_30",null);
            }
        }
        if(true){
            map.put("start_travel_time",1800);
            map.put("end_travel_time",3600);
            Map<String,Object> m = accessDao.getTripTimeDistribution(map);
            if(m!=null){
                sumarea =sumarea+Double.parseDouble(m.get("NUM").toString());
                result.put("time30_60",m.get("NUM"));
            }else{
                result.put("time30_60",null);
            }
        }
        if(true){
            map.put("start_travel_time",3600);
            map.put("end_travel_time",5400);
            Map<String,Object> m = accessDao.getTripTimeDistribution(map);
            if(m!=null){
                sumarea =sumarea+Double.parseDouble(m.get("NUM").toString());
                result.put("time60_90",m.get("NUM"));
            }else{
                result.put("time60_90",null);
            }
        }
        if(true){
            map.put("start_travel_time",5400);
            map.put("end_travel_time",0);
            Map<String,Object> m = accessDao.getTripTimeDistribution(map);
            if(m!=null){
                sumarea =sumarea+Double.parseDouble(m.get("NUM").toString());
                result.put("time90",m.get("NUM"));
            }else{
                result.put("time90",null);
            }
        }
        for (String key:result.keySet()){
            result.put(key, df.format((Double.parseDouble(result.get(key).toString())>0?Double.parseDouble(result.get(key).toString()):0)*100/sumarea));
        }
        return result;
    }
    public Map<String,Object> getTripDistanceDistribution(Map<String,Object> map){
        Map<String,Object> result = new HashMap<String, Object> ();
        double sumarea =0.00;
        DecimalFormat df = new DecimalFormat("#0.00");
        if(true){
            map.put("start_distance",0);
            map.put("end_distance",20000);
            Map<String,Object> m = accessDao.getTripDistanceDistribution(map);
            if (m != null) {
                sumarea =sumarea+Double.parseDouble(m.get("NUM").toString());
                result.put("dis0_20",m.get("NUM"));
            }else {
                result.put("dis0_20", null);
            }
        }
        if(true){
            map.put("start_distance",20000);
            map.put("end_distance",40000);
            Map<String,Object> m = accessDao.getTripDistanceDistribution(map);
            if (m != null) {
                sumarea =sumarea+Double.parseDouble(m.get("NUM").toString());
                result.put("dis20_40",m.get("NUM"));
            }else {
                result.put("dis20_40", null);
            }
        }
        if(true){
            map.put("start_distance",40000);
            map.put("end_distance",60000);
            Map<String,Object> m = accessDao.getTripDistanceDistribution(map);
            if (m != null) {
                sumarea =sumarea+Double.parseDouble(m.get("NUM").toString());
                result.put("dis40_60",m.get("NUM"));
            }else {
                result.put("dis40_60", null);
            }
        }
        if(true){
            map.put("start_distance",60000);
            map.put("end_distance",80000);
            Map<String,Object> m = accessDao.getTripDistanceDistribution(map);
            if (m != null) {
                sumarea =sumarea+Double.parseDouble(m.get("NUM").toString());
                result.put("dis60_80",m.get("NUM"));
            }else {
                result.put("dis60_80", null);
            }
        }
        if(true){
            map.put("start_distance",80000);
            map.put("end_distance",100000);
            Map<String,Object> m = accessDao.getTripDistanceDistribution(map);
            if (m != null) {
                sumarea =sumarea+Double.parseDouble(m.get("NUM").toString());
                result.put("dis80_100",m.get("NUM"));
            }else {
                result.put("dis80_100", null);
            }
        }
        if(true){
            map.put("start_distance",100000);
            map.put("end_distance",0);
            Map<String,Object> m = accessDao.getTripDistanceDistribution(map);
            if (m != null) {
                sumarea =sumarea+Double.parseDouble(m.get("NUM").toString());
                result.put("dis100",m.get("NUM"));
            }else {
                result.put("dis100", null);
            }
        }
        for (String key:result.keySet()){
            result.put(key, df.format((Double.parseDouble(result.get(key).toString())>0?Double.parseDouble(result.get(key).toString()):0)*100/sumarea));
        }
        return result;
    }
}
