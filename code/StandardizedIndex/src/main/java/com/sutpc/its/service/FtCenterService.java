package com.sutpc.its.service;

import com.sutpc.its.constant.DataMendingConstants;
import com.sutpc.its.dao.IFtCenterDao;
import com.sutpc.its.dao.IMonitorEarlyWarningDao;
import com.sutpc.its.dto.CapacityDto;
import com.sutpc.its.dto.CheckDto;
import com.sutpc.its.dto.CheckParams;
import com.sutpc.its.po.BusRoadsectStatus;
import com.sutpc.its.po.PeriodValuePo;
import com.sutpc.its.po.PoiNearRoadSectionPo;
import com.sutpc.its.po.SubAreaInfo;
import com.sutpc.its.timertask.ScheduledClass;
import com.sutpc.its.tools.DateUtils;
import com.sutpc.its.tools.PeriodUtils;
import com.sutpc.its.tools.Utils;
import com.sutpc.its.vo.GreenWaveVo;
import com.sutpc.its.vo.LinkVo;
import com.sutpc.its.vo.PeriodValueVo;
import com.sutpc.its.vo.PoiNearRoadSectionVo;
import com.sutpc.its.vo.RoadSectionVo;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:53 2020/3/19.
 * @Description
 * @Modified By:
 */
@Service
public class FtCenterService {

  @Autowired
  private IFtCenterDao ftCenterDao;
  @Autowired
  private IMonitorEarlyWarningDao monitorEarlyWarningDao;
  @Autowired
  private DataMendingService dataMendingService;

  @Autowired
  private ScheduledClass scheduled;

  @Value("${tpi.netCount}")
  private String netUrl;
  @Value("${tpi.busCount}")
  private String busUrl;
  @Value("${tpi.taxiCount}")
  private String taxiUrl;

  /**
   * .
   */
  public Map<String, Object> getOverAllIndex(Map<String, Object> map) {
    Map<String, Object> mapCenter = ftCenterDao.getCenterDistrictStatus(map);
    map.put("district_fid", 1);
    Map<String, Object> mapDistrictFt = ftCenterDao.getCityDistrictStatus(map);
    map.put("district_fid", 111);
    Map<String, Object> mapDistrictCity = ftCenterDao.getCityDistrictStatus(map);
    int anyDay = 7;
    map.put("time",
        PeriodUtils.getAppointDateAnyDayBeforeDate(anyDay, PeriodUtils.getCurrentDate()));
    Map<String, Object> lastWeekMapcenter = ftCenterDao.getCenterDistrictStatus(map);//上周同期数据，算环比需要
    while (lastWeekMapcenter == null || lastWeekMapcenter.isEmpty()) {
      anyDay++;
      map.put("time",
          PeriodUtils.getAppointDateAnyDayBeforeDate(anyDay, PeriodUtils.getCurrentDate()));
      lastWeekMapcenter = ftCenterDao.getCenterDistrictStatus(map);//上周同期数据，算环比需要
    }
    map.put("district_fid", 1);
    Map<String, Object> lastWeekMapDistrictFt = ftCenterDao.getCityDistrictStatus(map);
    while (mapDistrictFt == null && mapDistrictFt.isEmpty()) {
      anyDay++;
      map.put("time",
          PeriodUtils.getAppointDateAnyDayBeforeDate(anyDay, PeriodUtils.getCurrentDate()));
      lastWeekMapDistrictFt = ftCenterDao.getCityDistrictStatus(map);
    }
    map.put("district_fid", 111);
    Map<String, Object> lastWeekMapDistrictCity = ftCenterDao.getCityDistrictStatus(map);
    while (lastWeekMapDistrictCity == null || lastWeekMapDistrictCity.isEmpty()) {
      anyDay++;
      map.put("time",
          PeriodUtils.getAppointDateAnyDayBeforeDate(anyDay, PeriodUtils.getCurrentDate()));
      lastWeekMapDistrictFt = ftCenterDao.getCityDistrictStatus(map);
    }
    String thisCenterTpi = mapCenter.get("tpi") + "";
    String thisDistrictFtTpi = mapDistrictFt.get("tpi") + "";
    String thisDistrictCityTpi = mapDistrictCity.get("tpi") + "";
    String lastCenterTpi = lastWeekMapcenter.get("tpi") + "";
    String lastDistrictFtTpi = lastWeekMapDistrictFt.get("tpi") + "";
    String lastDistrictCityTpi = lastWeekMapDistrictCity.get("tpi") + "";
    if (!"null".equals(thisCenterTpi) & !"null".equals(lastCenterTpi)) {
      mapCenter.put("ratio", Utils.formatDouble(
          (Double.parseDouble(thisCenterTpi) - Double.parseDouble(lastCenterTpi)) / Double
              .parseDouble(lastCenterTpi) * 100));
    } else {
      mapCenter.put("ratio", 0.00);
    }
    if (!"null".equals(thisDistrictFtTpi) & !"null".equals(lastDistrictFtTpi)) {
      mapDistrictFt.put("ratio", Utils.formatDouble(
          (Double.parseDouble(thisDistrictFtTpi) - Double.parseDouble(lastDistrictFtTpi))
              / Double.parseDouble(lastDistrictFtTpi) * 100));
    } else {
      mapDistrictFt.put("ratio", 0.00);
    }

    if (!"null".equals(thisDistrictCityTpi) & !"null".equals(lastDistrictCityTpi)) {
      mapDistrictCity.put("ratio", Utils.formatDouble(
          (Double.parseDouble(thisDistrictCityTpi) - Double.parseDouble(lastDistrictCityTpi))
              / Double.parseDouble(lastDistrictCityTpi) * 100));
    } else {
      mapDistrictCity.put("ratio", 0.00);
    }
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("center", mapCenter);
    resultMap.put("futian", mapDistrictFt);
    resultMap.put("city", mapDistrictCity);

    return resultMap;
  }

  /**
   * .
   */
  public Map<String, Object> getTotalIndexDayStatus(Map<String, Object> map) {
    List<Map<String, Object>> centerlist = ftCenterDao.getCenterDayStatus(map);
    if (centerlist.size() < (PeriodUtils.getCurrentPeriod() / 3)) {
      dataMendingService
          .dataMendingByPeriod15(centerlist, map, DataMendingConstants.getCenterDayStatus);
    }
    map.put("config_district_fid", "111".split(","));
    final List<Map<String, Object>> citylist = monitorEarlyWarningDao
        .getCityAndEveryDistrictTpi(map);
    map.put("config_district_fid", "1".split(","));
    List<Map<String, Object>> ftlist = monitorEarlyWarningDao.getCityAndEveryDistrictTpi(map);
    Map<String, Object> resultmap = new HashMap<>();
    resultmap.put("center", centerlist);
    resultmap.put("ft", ftlist);
    resultmap.put("citywide", citylist);
    return resultmap;
  }

  /**
   * .
   */
  public Map<String, Object> getRoadTrafficInfo() {
    Map<String, Object> mapTpi = ftCenterDao.getFtcRealTimeTpiAndSpeed();
    Map<String, Object> jamLength = ftCenterDao.getFtcJamLength();
    mapTpi.putAll(jamLength);
    return mapTpi;
  }

  /**
   * 获取福田中心区公交运力.
   */
  public CapacityDto getFtcCapacity() {
    CapacityDto dto = scheduled.capacityDto;
    if (dto == null) {
      dto = scheduled.getFtcCapacity();
    }
    return dto;
  }


  /**
   * 获取绿波信息.
   */
  public GreenWaveVo getGreenWaveInfo(String ids) {
    String[] subSegmentId = ids.split(",");
    String time = PeriodUtils.getCurrentDate();
    int period = (PeriodUtils.getCurrentPeriod() - 1) < 1 ? 1 : PeriodUtils.getCurrentPeriod() - 1;
    List<RoadSectionVo> list = ftCenterDao.getGreenWaveList(time, period, subSegmentId);
    double speed = 0.0;
    GreenWaveVo vo = new GreenWaveVo();
    double sum = 0.0;
    if (list != null) {
      for (int i = 0; i < list.size(); i++) {
        sum += list.get(i).getSpeed();
      }
      DecimalFormat df = new DecimalFormat("0.0");
      speed = Double.parseDouble(df.format(sum / list.size()));
    }
    vo.setCount(list.size());
    vo.setList(list);
    vo.setSpeed(speed);
    return vo;
  }

  /**
   * 获取实时小路段信息.
   */
  public List<LinkVo> getRealTimeLinkInfo() {
    return ftCenterDao.getRealTimeLinkInfo();
  }

  /**
   * 绿波信息.
   */
  public List<SubAreaInfo> getGreenWaveAreaInfo(String ids) {
    String[] subSegmentId = ids.split(",");
    return ftCenterDao.getGreenWaveAreaInfo(subSegmentId);
  }

  /**
   * 公交速度.
   */
  public List<BusRoadsectStatus> getBusRoadsectStatusNow() {
    String time = DateUtils.currentDate();
    List<BusRoadsectStatus> list = ftCenterDao
        .getBusRoadsectStatusNow(time, DateUtils.currentPeriod());
    int i = 0;
    while (list == null || list.size() == 0) {
      list = ftCenterDao
          .getBusRoadsectStatusNow(DateUtils.dayBefore(--i), DateUtils.currentPeriod());
    }
    return list;
  }

  public List<PoiNearRoadSectionVo> getPoiNearRoadsect(CheckDto dto) {
    CheckParams checkParams = new CheckParams();
    BeanUtils.copyProperties(dto, checkParams);
    checkParams.setStartPeriod(PeriodUtils.time2period(dto.getStartTime()));
    checkParams.setEndPeriod(PeriodUtils.time2period(dto.getEndTime()));
    checkParams.setId(dto.getId().split(","));
    List<PoiNearRoadSectionPo> list = ftCenterDao.getPoiNearRoadsect(checkParams);
    List<PoiNearRoadSectionVo> data = new ArrayList<>();
    for (PoiNearRoadSectionPo po : list) {
      PoiNearRoadSectionVo vo = new PoiNearRoadSectionVo();
      BeanUtils.copyProperties(po, vo);
      vo.setTpi(Utils.setTpiValue(String.valueOf(po.getTpi()), 2));
      vo.setSpeed(Utils.setTpiValue(String.valueOf(po.getSpeed()), 1));
      data.add(vo);
    }
    return data;
  }

  public List<PeriodValueVo> getFtcBusSpeedAndRatio(CheckDto dto, String type) {
    CheckParams checkParams = new CheckParams();
    BeanUtils.copyProperties(dto, checkParams);
    checkParams.setStartPeriod(PeriodUtils.time2period(dto.getStartTime()));
    checkParams.setEndPeriod(PeriodUtils.time2period(dto.getEndTime()));
    if ("speed".equals(type)) {
      List<PeriodValuePo> list = ftCenterDao.getFtcBusSpeed(checkParams);
      List<PeriodValueVo> data = new ArrayList<>();
      for (PeriodValuePo po : list) {
        PeriodValueVo vo = new PeriodValueVo();
        BeanUtils.copyProperties(po, vo);
        vo.setValue(Utils.setTpiValue(String.valueOf(po.getValue()), 1));
        data.add(vo);
      }
      return data;
    } else if ("ratio".equals(type)) {
      List<PeriodValuePo> list = ftCenterDao.getFtcBusSpeedRatio(checkParams);
      List<PeriodValueVo> data = new ArrayList<>();
      for (PeriodValuePo po : list) {
        PeriodValueVo vo = new PeriodValueVo();
        BeanUtils.copyProperties(po, vo);
        vo.setValue(Utils.setTpiValue(String.valueOf(po.getValue()), 2));
        data.add(vo);
      }
      return data;
    }
    return new ArrayList<>();
  }

  public List<PeriodValueVo> getSubsectQueryData(CheckDto dto, int roadLevel, String index) {
    CheckParams checkParams = new CheckParams();
    BeanUtils.copyProperties(dto, checkParams);
    checkParams.setStartPeriod(PeriodUtils.time2period(dto.getStartTime()));
    checkParams.setEndPeriod(PeriodUtils.time2period(dto.getEndTime()));
    if (StringUtils.isEmpty(index)) {
      return null;
    }
    checkParams.setIndex(index);
    checkParams.setRoadLevel(roadLevel);
    List<PeriodValuePo> list = ftCenterDao.getFtcSpeedOrTpi(checkParams);
    List<PeriodValueVo> data = new ArrayList<>();
    for (PeriodValuePo po : list) {
      PeriodValueVo vo = new PeriodValueVo();
      BeanUtils.copyProperties(po, vo);
      if ("tpi".equals(index)) {
        vo.setValue(Utils.setTpiValue(String.valueOf(po.getValue()), 2));
        data.add(vo);
      } else {
        vo.setValue(Utils.setTpiValue(String.valueOf(po.getValue()), 1));
        data.add(vo);
      }
    }
    return data;
  }
}
