package com.sutpc.its.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sutpc.its.config.MagicConfig;
import com.sutpc.its.dao.IMonitorEarlyWarningDao;
import com.sutpc.its.dao.IScreenDao;
import com.sutpc.its.enums.TpEnum;
import com.sutpc.its.model.TpAreaPeopleEntity;
import com.sutpc.its.tools.DateUtils;
import com.sutpc.its.tools.GpsUrlUtils;
import com.sutpc.its.tools.HttpUtils;
import com.sutpc.its.tools.PeriodUtils;
import com.sutpc.its.tools.Utils;
import com.sutpc.its.vo.OldScreenAllRoadMidSectVo;
import com.sutpc.its.vo.OldScreenDistrictChartVo;
import com.sutpc.its.vo.OldScreenDistrictSpeedRatioVo;
import com.sutpc.its.vo.RoadJamVo;
import com.sutpc.its.vo.RoadStatusInfoVo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ScreenService {

  @Value("${transpaas.prefixUrl}")
  private String baseUrl;

  @Value("${tpi.busUrl}")
  private String busUrl;

  @Value("${tpi.taxiUrl}")
  private String taxiUrl;

  @Value("${tpi.netUrl}")
  private String netUrl;

  private RestTemplate restTemplate = new RestTemplate();

  @Autowired
  private IScreenDao screenDao;

  @Autowired
  private MonitorEarlyWarningService monitorEarlyWarningService;

  @Autowired
  private IMonitorEarlyWarningDao earlyWarningDao;

  /**
   * .
   */
  public Map<String, Object> getRealTimeDistrictSpeedAndTpi(Map<String, Object> map) {
    return screenDao.getRealTimeDistrictSpeedAndTpi(map);
  }

  /**
   * .
   */
  public Map<String, Object> getSpeedAndTpiInfoByRoadsectfid(Map<String, Object> map) {
    Map<String, Object> mapResult1 = monitorEarlyWarningService
        .getSpeedAndTpiInfoByRoadsectfid(map);

    int lastWeekDate = Utils.getLastDay(map.get(MagicConfig.TIME) + "", 7);
    map.put(MagicConfig.TIME, lastWeekDate);
    Map<String, Object> mapResult2 = monitorEarlyWarningService
        .getSpeedAndTpiInfoByRoadsectfid(map);

    List<Map<String, Object>> list1 = (List<Map<String, Object>>) mapResult1.get(MagicConfig.DATA);

    for (Map<String, Object> m : list1) {
      m.put(MagicConfig.NAME, MagicConfig.TODAYCH);
    }

    List<Map<String, Object>> list2 = (List<Map<String, Object>>) mapResult2.get(MagicConfig.DATA);

    for (Map<String, Object> m : list2) {
      m.put(MagicConfig.NAME, MagicConfig.LASTWEEKCH);
    }
    list2.removeIf(
        map1 -> Integer.parseInt(map1.get(MagicConfig.UPPERCASE_PERIOD) + "") > PeriodUtils
            .getCurrentPeriod15());
    Map<String, Object> result = new HashMap<>();
    result.put(MagicConfig.TODAY, list1);
    result.put(MagicConfig.LASTWEEK, list2);

    return result;
  }

  /**
   * .
   */
  public Map<String, Object> getRoadsectRealTimeJamWarning(Map<String, Object> map) {
    map.put(MagicConfig.ISJAM, MagicConfig.TWO);
    int lastDay = Utils.getLastDay(PeriodUtils.getCurrentDate(), MagicConfig.SEVEN);
    List<Map<String, Object>> data = earlyWarningDao.getRoadsectRealTimeJamWarning(map);
    map.put(MagicConfig.TIME, lastDay);
    List<Map<String, Object>> lastData = earlyWarningDao.getRoadsectRealTimeJamWarning(map);
    for (Map<String, Object> m1 : data) {
      double thisSpeed = Double.parseDouble(m1.get(MagicConfig.SPEED) + "");
      int thisRoadsectId = Integer.parseInt(m1.get(MagicConfig.ROADSECTFID) + "");
      for (Map<String, Object> m2 : lastData) {
        int lastRoadsectId = Integer.parseInt(m2.get(MagicConfig.ROADSECTFID) + "");
        if (thisRoadsectId == lastRoadsectId) {
          double lastSpeed = Double.parseDouble(m2.get(MagicConfig.SPEED) + "");
          String ratio = Utils
              .formatDouble((thisSpeed - lastSpeed) / lastSpeed * MagicConfig.ONEHUNDRED);
          m1.put(MagicConfig.RATIO, ratio);
          if (Double.parseDouble(ratio) > MagicConfig.ZERO) {
            m1.put(MagicConfig.RATIOSTATUS, MagicConfig.UP);
          } else {
            m1.put(MagicConfig.RATIOSTATUS, MagicConfig.DOWN);
          }
        }
      }
    }
    if (data.size() >= MagicConfig.TWENTY) {
      data.subList(MagicConfig.ZERO, MagicConfig.TWENTY);
    }
    List<Map<String, Object>> list = new ArrayList<>();
    int count = MagicConfig.ZERO;
    if (data.size() > MagicConfig.FIVE) {
      count = MagicConfig.FIVE;
    } else {
      count = data.size();
    }
    for (int i = MagicConfig.ZERO; i < count; i++) {
      Map<String, Object> m = new HashMap<>();
      int roadsectFid = Integer.parseInt(data.get(i).get(MagicConfig.ROADSECTFID) + "");
      double tpi = Double.parseDouble(data.get(i).get(MagicConfig.TPI) + "");
      if (tpi >= MagicConfig.SIX) {
        m.put(MagicConfig.ROADSECTFID, roadsectFid);
        list.add(m);
      }
    }
    Map<String, Object> result = new HashMap<>();
    result.put(MagicConfig.LIST, data);
    result.put(MagicConfig.ID, list);
    return result;
  }

  /**
   * .
   */
  public Map<String, String> getLateseTime() {
    Map<String, String> result = new HashMap<>();
    Map<String, Object> m = screenDao.getLateseTime();
    StringBuilder stringBuilder = new StringBuilder();
    if (m.containsKey(MagicConfig.FDATE)) {
      String fdate = m.get(MagicConfig.FDATE) + "";
      stringBuilder.append(fdate.substring(MagicConfig.ZERO, MagicConfig.FOUR) + "-" + fdate
          .substring(MagicConfig.FOUR, MagicConfig.SIX) + "-" + fdate
          .substring(MagicConfig.SIX, MagicConfig.EIGHT));
    }
    if (m.containsKey(MagicConfig.PERIOD)) {
      stringBuilder.append(MagicConfig.BLANK);
      stringBuilder
          .append(PeriodUtils.period2time(Integer.parseInt(m.get(MagicConfig.PERIOD) + "")));
    }
    result.put(MagicConfig.TIME, stringBuilder.toString());
    return result;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getKeyAreaData(Map<String, Object> map) {
    return screenDao.getKeyAreaData(map);
  }

  /**
   * 获取实时客流曲线.
   *
   * @params
   */
  public List<TpAreaPeopleEntity> getRealTimeAreaPeopleCharts(Map<String, Object> map) {
    Map<String, Object> paramap = new HashMap<>();
    TpEnum tpEnum = TpEnum.findRealTimeAreaPeopleChart;
    String url = baseUrl + tpEnum.getSuffixUrl();
    paramap.put(MagicConfig.CURRENTDATE, PeriodUtils.getCurrentDate());
    paramap.put(MagicConfig.AREAID, map.get(MagicConfig.AREAID));
    HttpEntity herder = HttpUtils.getHerder(JSON.toJSONString(paramap), tpEnum.getApiKey());
    ResponseEntity<JSONObject> responseEntity = restTemplate
        .postForEntity(url, herder, JSONObject.class);
    List<TpAreaPeopleEntity> data = JSONArray
        .parseArray(JSON.toJSONString(responseEntity.getBody().get(MagicConfig.DATA)),
            TpAreaPeopleEntity.class);
    if (data != null && data.size() < PeriodUtils.getCurrentPeriod15()) {
      paramap.put(MagicConfig.CURRENTDATE, 20200419);//补全数据，暂时找了一天全数据的日期
      HttpEntity herder2 = HttpUtils.getHerder(JSON.toJSONString(paramap), tpEnum.getApiKey());
      ResponseEntity<JSONObject> responseEntity2 = restTemplate
          .postForEntity(url, herder2, JSONObject.class);
      List<TpAreaPeopleEntity> list = JSONArray
          .parseArray(JSON.toJSONString(responseEntity2.getBody().get(MagicConfig.DATA)),
              TpAreaPeopleEntity.class);
      list.removeIf(tp -> tp.getPeriod() > PeriodUtils.getCurrentPeriod15());//大于当前时间片的数据删除
      //boolean flag = list.removeAll(data); //取差集，此操作后，list去掉了与data交集部分，剩下的是data集合没有的数据
      getDifferentByList(list, data);//取差集，此操作后，list去掉了与data交集部分，剩下的是data集合没有的数据
      data.addAll(list);//把list中剩余的数据并入到data集合，达到取交集部分。
    }

    return data;
  }

  /**
   * .
   */
  public void getDifferentByList(List<TpAreaPeopleEntity> list, List<TpAreaPeopleEntity> data) {
    for (TpAreaPeopleEntity tp : data) {
      if (list != null) {
        Iterator<TpAreaPeopleEntity> iterator = list.iterator();
        while (iterator.hasNext()) {
          TpAreaPeopleEntity t = iterator.next();
          if (t.getPeriod() == tp.getPeriod()) {
            iterator.remove();
          }
        }
      }
    }
  }

  /**
   * .
   */
  public Map<String, Object> getCollects(Map<String, Object> map) {
    Object obj = map.get(MagicConfig.CONFIGDISTRICTFID);
    String[] str = (String[]) obj;
    String districtFid = str[MagicConfig.ZERO];
    String carType = map.get(MagicConfig.CARTYPE) + "";
    String url = GpsUrlUtils.getCollectsUrl(districtFid, carType);
    String result = null;
    try {
      HttpResponse<String> hr = Unirest.post(url).asString();
      result = hr.getBody();
    } catch (UnirestException e) {
      e.printStackTrace();
    }
    if (result != null) {
      return (Map<String, Object>) JSON.parse(result);
    } else {
      return null;
    }
  }

  /**
   * 根据点位信息获取指定范围的gps.
   *
   * @params
   */
  public Map<String, Object> getCollectsByRange(Map<String, Object> map) {
    Object obj = map.get(MagicConfig.CONFIGDISTRICTFID);
    String[] str = (String[]) obj;
    String districtFid = str[MagicConfig.ZERO];
    String carType = map.get(MagicConfig.CARTYPE) + "";
    // String url = GpsUrlUtils.getCollectsByRangeUrl(districtFid, carType);
    String url = "";
    if ("transit".equals(carType)) { //公交车
      url = "http://10.10.180.19:8184/gps/bus/getPoiCarInfoByDistance?topic=tpBus";
    } else {
      url = GpsUrlUtils.getCollectsByRangeUrl(districtFid, carType);
    }
    String result = null;
    Map<String, Object> paramap = new HashMap<>();
    paramap.put(MagicConfig.LONGITUDE, map.get(MagicConfig.LONGITUDE));
    paramap.put(MagicConfig.LATITUDE, map.get(MagicConfig.LATITUDE));
    paramap.put(MagicConfig.DISTANCE, map.get(MagicConfig.DISTANCE));
    try {
      HttpResponse<String> hr = Unirest.post(url).fields(paramap).asString();
      result = hr.getBody();
    } catch (UnirestException e) {
      e.printStackTrace();
    }
    if (result != null) {
      return (Map<String, Object>) JSON.parse(result);
    } else {
      return null;
    }
  }

  /**
   * .
   */
  public List<Map<String, Object>> getBusCapacity(Map<String, Object> map) {
    Map<String, Object> mapNet = new HashMap<>();
    Map<String, Object> mapTaxi = new HashMap<>();
    Map<String, Object> mapBus = new HashMap<>();

    String netStr = null;
    try {
      netStr = Unirest.post(netUrl)
          .field("topic", "tpNet")
          .field("poiId", map.get("poiId"))
          .field("distance", 5000)
          .asString().getBody();
      Map<String, Object> resultNet = (Map<String, Object>) JSON.parse(netStr);
      Map<String, Object> mnet = (Map<String, Object>) resultNet.get("data");
      mapNet.put("count", mnet.get("busycount"));
      mapNet.put("type", "net");
      mapNet.put("name", "网约车");
    } catch (UnirestException e) {
      e.printStackTrace();
    }

    String taxiStr = null;
    try {
      taxiStr = Unirest.post(taxiUrl)
          .field("topic", "tpTaxi")
          .field("poiId", map.get("poiId"))
          .field("distance", 5000)
          .asString().getBody();
      Map<String, Object> resultTaxi = (Map<String, Object>) JSON.parse(taxiStr);
      Map<String, Object> mtaxi = (Map<String, Object>) resultTaxi.get("data");
      mapTaxi.put("count", mtaxi.get("busycount"));
      mapTaxi.put("type", "taxi");
      mapTaxi.put("name", "出租车");
    } catch (UnirestException e) {
      e.printStackTrace();
    }

    String busStr = null;
    try {
      busStr = Unirest.post(busUrl)
          .field("topic", "tpBus")
          .field("poiId", map.get("poiId"))
          .field("distance", 5000)
          .asString().getBody();
      Map<String, Object> resultBus = (Map<String, Object>) JSON.parse(busStr);
      Map<String, Object> mbus = (Map<String, Object>) resultBus.get("data");
      mapBus.put("count", mbus.get("count"));
      mapBus.put("type", "bus");
      mapBus.put("name", "公交车");
    } catch (UnirestException e) {
      e.printStackTrace();
    }
    List<Map<String, Object>> list = new ArrayList<>();
    list.add(mapBus);
    list.add(mapNet);
    list.add(mapTaxi);
    return list;
  }

  /**
   * .
   */
  public List<Map<String, Object>> getCityAndEveryDistrictTpi(Map<String, Object> map) {
    String[] arr = (String[]) map.get("config_district_fid");
    String district = arr[0] + ",111,222";
    map.put("config_district_fid", district);
    return monitorEarlyWarningService.getCityAndEveryDistrictTpi(map);
  }

  /**
   * .
   */
  public List<RoadJamVo> getRealTimeJamRoad() {
    return screenDao
        .getRealTimeJamRoad(PeriodUtils.getCurrentDate(), PeriodUtils.getCurrentPeriod15());
  }

  public List<OldScreenDistrictChartVo> getAllAndDistrictExponentComparisonData(
      String districtFid) {
    List<OldScreenDistrictChartVo> vo = screenDao.getAllAndDistrictExponentComparisonData(DateUtils.currentDate(),
            DateUtils.currentPeriod15(), districtFid.split(","));

    return vo;
  }

  public List<OldScreenAllRoadMidSectVo> getAllRoadMidSectExp() {
    String time = DateUtils.currentDate();
    int period = DateUtils.currentPeriod();
    List<OldScreenAllRoadMidSectVo> list = screenDao.getAllRoadMidSectExp(time, period);
    int i = -1;
    while (list == null || list.size() == 0) {
      time = DateUtils.dayBefore(i--);
      list = screenDao.getAllRoadMidSectExp(time, period);
    }
    return list;
  }


  public OldScreenDistrictSpeedRatioVo getDistrictCarAndBusRatio() {
    OldScreenDistrictSpeedRatioVo vo = screenDao
        .getDistrictCarAndBusRatio(DateUtils.currentDate(), DateUtils.currentPeriod());
    return vo;
  }

  public List<RoadStatusInfoVo> getRoadStatus() {
    String time = DateUtils.currentDate();
    int period = DateUtils.currentPeriod();
    List<RoadStatusInfoVo> list = screenDao.getRoadStatus(time, period);
    int i = -1;
    while (list == null || list.size() == 0) {
      time = DateUtils.dayBefore(i--);
      list = screenDao.getRoadStatus(time, period);
    }
    return list;
  }
}
