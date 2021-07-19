package com.sutpc.its.service.v6.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sutpc.its.dao.v6.IUserDefinedDao;
import com.sutpc.its.model.HttpResult;
import com.sutpc.its.po.PeriodValuePo;
import com.sutpc.its.po.RegionHistoryTrendPo;
import com.sutpc.its.po.v6.AreaInfoPo;
import com.sutpc.its.po.v6.RoadTypeLengthsPo;
import com.sutpc.its.po.v6.UserDefinedInfoPo;
import com.sutpc.its.service.v6.IUserDefinedService;
import com.sutpc.its.tools.DateUtils;
import com.sutpc.its.tools.Utils;
import com.sutpc.its.vo.HistoryInfoVo;
import com.sutpc.its.vo.HistorySituationVo;
import com.sutpc.its.vo.JamRoadSectionVo;
import com.sutpc.its.vo.RealTimeTotalIndexVo;
import com.sutpc.its.vo.RegionOperationCharacteristicsVo;
import com.sutpc.its.vo.RegionSpeedAndTpiVo;
import com.sutpc.its.vo.RelatedSectionVo;
import com.sutpc.its.vo.TotalMonitor;
import com.sutpc.its.vo.TotalRealTimeMonitorVo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 14:30 2020/10/18.
 * @Description
 * @Modified By:
 */
@Slf4j
@Service
public class UserDefinedServiceImpl implements IUserDefinedService {

  @Autowired
  private IUserDefinedDao definedDao;

  /**
   * 存入选择信息.
   *
   * @param name 名称
   * @param points 经纬度范围
   * @param links 小路段id
   * @param type 类型
   */
  @Override
  public HttpResult setDefinedInfo(String name, String region, double area, String points,
      String links, int type,
      String creator) {
    int lengths = 0;
    //根据小路段查找大路段id
    List<Integer> roadList = definedDao.getRoadIdByLInks(links.split(","));
    String roads = "";
    for (int i = 0; i < roadList.size(); i++) {
      if (i != roadList.size() - 1) {
        roads = roads + roadList.get(i) + ",";
      }
      if (i == roadList.size() - 1) {
        roads = roads + roadList.get(i);
      }
    }
    //根据小路段查找中路段id
    List<Integer> roadSectionList = definedDao.getRoadSectioinIdByLInks(links.split(","));
    String roadSections = "";
    for (int i = 0; i < roadSectionList.size(); i++) {
      if (i != roadSectionList.size() - 1) {
        roadSections = roadSections + roadSectionList.get(i) + ",";
      }
      if (i == roadSectionList.size() - 1) {
        roadSections = roadSections + roadSectionList.get(i);
      }
    }
    List<RoadTypeLengthsPo> list = definedDao.getRoadTypeLengths(links.split(","));
    for (RoadTypeLengthsPo lengthsPo : list) {
      lengths += lengthsPo.getLength();
    }
    Iterator<RoadTypeLengthsPo> it = list.iterator();
    int other = 0;
    //除了类型为：1,2,3,4,5的道路类型，其余的都归为其它
    while (it.hasNext()) {
      RoadTypeLengthsPo po = it.next();
      int typeId = po.getType();
      if (typeId != 1 && typeId != 2 && typeId != 3 && typeId != 4 && typeId != 5) {
        other += po.getLength();
        it.remove();
      }
    }
    RoadTypeLengthsPo roadTypeLengthsPo = new RoadTypeLengthsPo();
    roadTypeLengthsPo.setLength(other);
    roadTypeLengthsPo.setType(-1);
    roadTypeLengthsPo.setName("其它");
    list.add(roadTypeLengthsPo);
    JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSON(list).toString());
    String listStr = jsonArray.toJSONString();
    String uuid = Utils.getUUID();
    int num = definedDao
        .setDefinedInfo(uuid, DateUtils.currentDate(), name, region, area, listStr, points, links,
            roads, roadSections,
            lengths, type,
            creator);
    new MyThread(uuid, roadSections).start();
    if (num == 1 || num == -1) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", uuid);
      return HttpResult.ok(map);
    } else {
      return HttpResult.error("操作失败！");
    }
  }

  class MyThread extends Thread {

    private String sections;
    private String id;

    MyThread(String id, String sections) {
      this.id = id;
      this.sections = sections;
    }

    @Override
    public void run() {
      calculateHistoryInfo(id, sections);
    }
  }

  public void calculateHistoryInfo(String id, String sections) {
    String[] section = sections.split(",");
    final int DAY = 30;
    for (int i = 1; i <= DAY; i++) {
      String time = DateUtils.dayBefore(0 - i);
      calculateHistoryInfo(time, id, section);
    }
  }

  /**
   * 计算指定日期、指定区域范围信息的各指标指数.
   *
   * @param time 日期 例：20201114
   * @param id 区域id
   * @param section 区域中路段点集
   */
  public void calculateHistoryInfo(String time, String id, String[] section) {

    RegionHistoryTrendPo po = new RegionHistoryTrendPo();
    //全天
    HistoryInfoVo allDay = definedDao.getHistoryAllDayInfo(time, section);
    if (allDay != null) {
      po.setAvgSpeed(allDay.getSpeed());
      po.setAvgTpi(allDay.getTpi());
    }
    //早高峰
    HistoryInfoVo morningPeak = definedDao.getHistoryMorningPeakInfo(time, section);
    if (morningPeak != null) {
      po.setMorningSpeed(morningPeak.getSpeed());
      po.setMorningTpi(morningPeak.getTpi());
    }
    //晚高峰
    HistoryInfoVo eveningPeak = definedDao.getHistoryEveningPeakInfo(time, section);
    if (eveningPeak != null) {
      po.setEveningSpeed(eveningPeak.getSpeed());
      po.setEveningTpi(eveningPeak.getTpi());
    }
    //平峰
    HistoryInfoVo flatHump = definedDao.getHistoryFlatHumpPeakInfo(time, section);
    if (flatHump != null) {
      po.setFlatSpeed(flatHump.getSpeed());
      po.setFlatTpi(flatHump.getTpi());
    }
    po.setTime(time);
    po.setId(id);
    int index = definedDao.setHistoryInfo(po);
    if (index != 1) {
      log.error("存储数据出错！存储日期：{}", time);
    }
  }

  /**
   * 每天早上零点10分，定时任务触发，计算前一天区域指数.
   */
  @Scheduled(cron = "0 10 0 * * ?")
  //@Scheduled(fixedRate = 50000000)
  public void calHistoryInfoByDay() {
    List<UserDefinedInfoPo> list = getDefinedBaseInfo();
    for (UserDefinedInfoPo po : list) {
      String id = po.getId();
      String[] section = po.getSections().split(",");
      String time = DateUtils.dayBefore(-1);
      calculateHistoryInfo(time, id, section);
    }
    log.info("定时计算前一天区域指数完成！");
  }

  /**
   * 修改名称.
   */
  @Override
  public HttpResult updateDefinedInfo(String name, String id) {
    int num = definedDao.updateDefinedInfo(name, id);
    if (num == 1) {
      return HttpResult.ok();
    } else {
      return HttpResult.error("操作失败！");
    }
  }

  /**
   * 删除信息.
   */
  @Override
  public HttpResult deleteDefinedInfoById(String id) {
    int num = definedDao.deleteDefinedInfoById(id);
    if (num == 1) {
      //未知多条，不作判断了。
      definedDao.deleteHistoryInfoById(id);
      return HttpResult.ok();
    } else {
      return HttpResult.error("操作失败！");
    }
  }

  /**
   * 获取点选信息.
   */
  @Override
  public List<UserDefinedInfoPo> getDefinedBaseInfo() {
    List<UserDefinedInfoPo> list = definedDao.getDefinedBaseInfo();
    return list;
  }

  /**
   * 所选区域信息.
   */
  @Override
  public AreaInfoPo getAreaInfoPo(String id) {
    UserDefinedInfoPo po = definedDao.getDefinedBaseInfo(id);
    AreaInfoPo areaInfoPo = new AreaInfoPo();
    BeanUtils.copyProperties(po, areaInfoPo);
    java.sql.Timestamp d = new java.sql.Timestamp(po.getCreate_time().getTime());
    areaInfoPo.setCreateTime(d.toString());
    String listStr = po.getList();
    List<RoadTypeLengthsPo> list = JSONObject.parseArray(listStr, RoadTypeLengthsPo.class);
    areaInfoPo.setList(list);
    return areaInfoPo;
  }

  /**
   * 区域实时总体指标.
   *
   * @param id 自定义区域id
   */
  @Override
  public RealTimeTotalIndexVo getRealTimeTotalIndex(String id) {
    AreaInfoPo areaInfoPo = getAreaInfoPo(id);
    String[] sections = areaInfoPo.getSections().split(",");
    String[] roads = areaInfoPo.getRoads().split(",");
    String time = DateUtils.currentDate();
    int period = DateUtils.currentPeriod();
    RegionSpeedAndTpiVo regionSpeedAndTpiVo = definedDao
        .getRegionSpeedAndTpi(time, period, sections);
    RealTimeTotalIndexVo result = new RealTimeTotalIndexVo();
    BeanUtils.copyProperties(regionSpeedAndTpiVo, result);
    RealTimeTotalIndexVo vo = definedDao.getRegionJamLengthAndRatio(time, period, roads);
    if (vo != null) {
      result.setJamLength(vo.getJamLength());
      result.setJamRatio(vo.getJamRatio());
    }
    return result;
  }

  /**
   * 总体实时监测.
   */
  @Override
  public TotalMonitor getTotalRealTimeMonitor(String id) {
    AreaInfoPo areaInfoPo = getAreaInfoPo(id);
    String[] links = areaInfoPo.getLinks().split(",");
    String[] sections = areaInfoPo.getSections().split(",");
    String time = DateUtils.currentDate();
    int period = DateUtils.currentHour();
    TotalMonitor totalMonitor = new TotalMonitor();
    //当天实时
    List<TotalRealTimeMonitorVo> list = definedDao
        .getTotalRealTimeMonitor(time, period, sections);
    totalMonitor.setList(list);
    //上周同期
    List<TotalRealTimeMonitorVo> lately = definedDao
        .getTotalRealTimeMonitor(DateUtils.dayBefore(-7), period, sections);
    totalMonitor.setLately(lately);
    return totalMonitor;
  }

  /**
   * 关联路段监测.
   */
  @Override
  public List<RelatedSectionVo> getRelatedSectionMonitor(String id) {
    AreaInfoPo areaInfoPo = getAreaInfoPo(id);
    String[] sections = areaInfoPo.getSections().split(",");
    List<RelatedSectionVo> list = definedDao
        .getRelatedSectionMonitor(DateUtils.currentDate(), DateUtils.currentPeriod(), sections);
    return list;
  }

  /**
   * 历史态势分析.
   */
  @Override
  public HistorySituationVo getHistorySituationInfo(String id) {
    String startTime = DateUtils.dayBefore(-30);
    String endTime = DateUtils.dayBefore(-1);
    List<RegionHistoryTrendPo> list = definedDao.getHistroyInfo(id, startTime, endTime);
    List<HistoryInfoVo> allDay = new ArrayList<>();
    List<HistoryInfoVo> morningPeak = new ArrayList<>();
    List<HistoryInfoVo> eveningPeak = new ArrayList<>();
    List<HistoryInfoVo> flatHumpPeak = new ArrayList<>();
    for (RegionHistoryTrendPo po : list) {
      HistoryInfoVo allVo = new HistoryInfoVo();
      HistoryInfoVo morningVo = new HistoryInfoVo();
      HistoryInfoVo eveningVo = new HistoryInfoVo();
      HistoryInfoVo flatHumpVo = new HistoryInfoVo();
      allVo.setSpeed(po.getAvgSpeed());
      allVo.setTpi(po.getAvgTpi());
      allVo.setTime(Integer.parseInt(po.getTime()));
      allDay.add(allVo);

      morningVo.setSpeed(po.getMorningSpeed());
      morningVo.setTpi(po.getMorningTpi());
      morningVo.setTime(Integer.parseInt(po.getTime()));
      morningPeak.add(morningVo);

      eveningVo.setSpeed(po.getEveningSpeed());
      eveningVo.setTpi(po.getEveningTpi());
      eveningVo.setTime(Integer.parseInt(po.getTime()));
      eveningPeak.add(eveningVo);

      flatHumpVo.setSpeed(po.getFlatSpeed());
      flatHumpVo.setTpi(po.getFlatTpi());
      flatHumpVo.setTime(Integer.parseInt(po.getTime()));
      flatHumpPeak.add(flatHumpVo);
    }
    HistorySituationVo vo = new HistorySituationVo();
    vo.setAllDay(allDay);
    vo.setMorningPeak(morningPeak);
    vo.setFlatHumpPeak(flatHumpPeak);
    vo.setEveningPeak(eveningPeak);
    return vo;
  }

  /**
   * 拥堵识别点.
   */
  @Override
  public List<JamRoadSectionVo> getJamRoadSection(String id) {
    AreaInfoPo areaInfoPo = getAreaInfoPo(id);
    String[] sections = areaInfoPo.getSections().split(",");
    List<JamRoadSectionVo> list = definedDao
        .getJamRoadSection(DateUtils.currentDate(), DateUtils.currentPeriod(), sections);
    return list;
  }

  /**
   * 区域运行特征.
   */
  @Override
  public RegionOperationCharacteristicsVo getRegionOperationCharacteristics(String id) {
    AreaInfoPo areaInfoPo = getAreaInfoPo(id);
    String[] sections = areaInfoPo.getSections().split(",");
    String time = DateUtils.currentDate();
    int period = DateUtils.currentPeriod();

    //拥堵里程比例
    double jamRatio = definedDao.getRoadJamLengthRatio(time, period, sections);
    RegionOperationCharacteristicsVo vo = new RegionOperationCharacteristicsVo();
    vo.setJamRatio(Utils.setTpiValue(String.valueOf(jamRatio), 3));

    //拥堵持续时长
    vo.setJamTime("7.8");

    //高峰交通运行指数
    double tpi = definedDao.getRoadPeakTpi(time, sections);
    vo.setTpi(Utils.setTpiValue(String.valueOf(tpi), 2));

    //高峰速度稳定性
    List<PeriodValuePo> listValue = definedDao.getSpeedList(time, sections);
    double avgSpeed = definedDao.getAvgSpeed(time, sections);
    double count = 0.0;
    for (PeriodValuePo po : listValue) {
      double x = po.getValue();
      count = Math.pow(x - avgSpeed, 2);
    }
    //获取到速度稳定性值
    double speedStability = Math.sqrt(count / (listValue.size() - 1));
    vo.setSpeedStability(Utils.setTpiValue(String.valueOf(speedStability), 1));

    //高峰速度偏离系数
    for (PeriodValuePo po : listValue) {
      double x = po.getValue();
      count = Math.pow(x - avgSpeed, 3);
    }
    //得到偏离系数
    double speedDeviationCoefficient = count / Math.pow(speedStability, 3) / (listValue.size() - 1);
    vo.setSpeedDeviationCoefficient(
        Utils.setTpiValue(String.valueOf(speedDeviationCoefficient), 1));

    //高峰速度抖缓程度
    for (PeriodValuePo po : listValue) {
      double x = po.getValue();
      count = Math.pow(x - avgSpeed, 4);
    }
    double speedShake = count / Math.pow(speedStability, 4) / (listValue.size() - 1) - 3;
    vo.setSpeedShake(Utils.setTpiValue(String.valueOf(speedShake), 1));

    return vo;
  }


}
