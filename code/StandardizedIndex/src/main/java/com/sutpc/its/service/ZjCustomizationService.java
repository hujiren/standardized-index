package com.sutpc.its.service;

import com.sutpc.its.dao.IZjCustomizationDao;
import com.sutpc.its.tools.PeriodUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZjCustomizationService {

  @Autowired
  private IZjCustomizationDao zjCustomizationDao;

  /**
   * getHisTpi.
   */
  public List<Map<String, Object>> getHisTpi(Map<String, Object> map) {
    String type = map.get("type").toString();
    map = PeriodUtils.time2period(map);
    if ("subsect".equals(type)) {
      return zjCustomizationDao.getSubsectHisTpi(map);
    } else if ("roadsect".equals(type)) {
      return zjCustomizationDao.getRoadsectHisTpi(map);
    }
    return null;
  }
}
