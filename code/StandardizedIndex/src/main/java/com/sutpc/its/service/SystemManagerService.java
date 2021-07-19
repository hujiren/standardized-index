package com.sutpc.its.service;

import com.sutpc.its.dao.ISystemManagerDao;
import com.sutpc.its.model.NounDefinitionEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SystemManagerService {

  private List<Map<String, Object>> thisData;

  @Autowired
  private ISystemManagerDao systemManagerDao;

  /**
   * .
   */
  public Map<String, Object> getCityRoadNetworkBaseInfo() {
    return systemManagerDao.getCityRoadNetworkBaseInfo();
  }

  /**
   * .
   */
  public Map<String, Object> getEveryVehicleNums() {
    return systemManagerDao.getEveryVehicleNums();
  }

  /**
   * .
   */
  public Map<String, Object> getDevicePointNums() {
    return systemManagerDao.getDevicePointNums();
  }

  /**
   * .
   */
  public List<Map<String, Object>> getNounDefinitionList() {
    if (thisData == null) {
      thisData = loadNounDefinitionList();
    }
    return thisData;
  }

  /**
   * .
   */
  //@Scheduled(fixedRate = 60000)
  public List<Map<String, Object>> loadNounDefinitionList() {
    List<Map<String, Object>> list = systemManagerDao.getNounDefinitionList();
    log.info("[名词解释]->:{}", list);
    thisData = list;
    return list;
  }

  /**
   * .
   */
  public int updateNounDefinition(NounDefinitionEntity entity) {
    return systemManagerDao.updateNounDefinition(entity.getId(), entity.getFname(),
        entity.getDescription(), String.valueOf(System.currentTimeMillis() / 1000),
        entity.getUploadPerson());
  }

  /**
   * .
   */
  public int setNounDefinition(NounDefinitionEntity entity) {
    return systemManagerDao
        .setNounDefinition(UUID.randomUUID().toString().replace("-", ""), entity.getFname(),
            entity.getDescription(), String.valueOf(System.currentTimeMillis() / 1000),
            entity.getUploadPerson());
  }

  /**
   * .
   */
  public int deleteNounDefinition(String id) {
    return systemManagerDao
        .deleteNounDefinition(id, String.valueOf(System.currentTimeMillis() / 1000));
  }

  /**
   * .
   */
  public int setSysSuggestion(Map<String, Object> map) {
    map.put("id", UUID.randomUUID().toString().replace("-", ""));
    map.put("timestamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
    return systemManagerDao.setSysSuggestion(map);
  }
}
