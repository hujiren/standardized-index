package com.sutpc.its.service;

import com.sutpc.its.dao.IHotSpotFlowDao;
import com.sutpc.its.dto.FlowsDistributionDto;
import com.sutpc.its.dto.FlowsDistributionEntity;
import com.sutpc.its.dto.KeyAreaInfoDto;
import com.sutpc.its.dto.KeyAreaLikeDto;
import com.sutpc.its.dto.PfChangeContrastDto;
import com.sutpc.its.dto.PfChangeDto;
import com.sutpc.its.model.entity.PfChangeEntity;
import com.sutpc.its.tools.PeriodUtils;
import com.sutpc.its.tools.TpiUtils;
import com.sutpc.its.tools.Utils;
import com.sutpc.its.tools.time.TimeUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotSpotFlowService {

  @Autowired
  private IHotSpotFlowDao iHotSpotFlowDao;

  /**
   * 选项里添加一个“热点人流量”图层，获取不同热点面层的客流量、 饱和度及当前服务水平（舒适、基本舒适、拘束、较拥挤、拥挤） 叠加热点图层、展示各热点的服务水平，点击单个热点展示热点 详细信息
   */
  public List<Map<String, Object>> getHotSpotFlow(Map<String, Object> map) {

    String curTime = TimeUtils.GetCurrentTimeString();
    int minute = Integer.valueOf(curTime.substring(14, 16));
    Calendar c = Calendar.getInstance();

    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MINUTE, minute / 15 * 15);
    //提前一小时
    c.add(Calendar.HOUR, -1);

    String countdate = new SimpleDateFormat("yyyyMMddHHmmss").format(c.getTime());

    map.put("time", countdate);

    return iHotSpotFlowDao.getHotSpotFlow(map);
  }

  public List<Map<String, Object>> getHotspotFlowLineData(Map<String, Object> map) {
    return iHotSpotFlowDao.getHotspotFlowLineData(map);
  }

  public Map<String, Object> getHotspotFlowStatus(Map<String, Object> map) {
    Map<String, Object> result = new HashMap<>();
    Map<String, Object> status = iHotSpotFlowDao.getHotspotFlowStatus(map);
    List<Map<String, Object>> list = iHotSpotFlowDao.getHotspotFlowLineData(map);
    result.put("status", status);
    result.put("lineData", list);
    return result;
  }

  public List<KeyAreaInfoDto> getKeyAreaInfo() {
    String currentDate = PeriodUtils.getCurrentDate();
    int currentPeriod15 = PeriodUtils.getCurrentPeriod15();
    List<KeyAreaInfoDto> data = iHotSpotFlowDao.getKeyAreaInfo(Integer.parseInt(currentDate),currentPeriod15);
    String time = "20200623";
    while (data == null || data.size() == 0) {
      time = PeriodUtils.getAppointDateAnyDayBeforeDate(1, time);
      data = iHotSpotFlowDao
          .getKeyAreaInfo(Integer.parseInt(time),
              PeriodUtils.getCurrentPeriod15());

    }
    String lastTime = "20200616";
    List<KeyAreaInfoDto> lastData = iHotSpotFlowDao
        .getKeyAreaInfo(Integer.parseInt(lastTime),
            PeriodUtils.getCurrentPeriod15());
    while(lastData == null || lastData.size() == 0){
      lastTime = PeriodUtils.getAppointDateAnyDayBeforeDate(1, lastTime);
      lastData = iHotSpotFlowDao
          .getKeyAreaInfo(Integer.parseInt(lastTime),
              PeriodUtils.getCurrentPeriod15());
    }
    for(KeyAreaInfoDto thisDto : data){
      int thisPf = thisDto.getPf();
      int thisId = thisDto.getAreaId();
      for(KeyAreaInfoDto lastDto:lastData){
        int lastPf = lastDto.getPf();
        int lastId = lastDto.getAreaId();
        if(thisId == lastId){
          double ratio = TpiUtils.calculateGrowth(lastPf,thisPf,1);
          thisDto.setRatio(ratio);
          break;
        }
      }
    }
    return data;
  }

  public List<PfChangeDto> getPfChangeChartData(PfChangeEntity entity) {
    List<PfChangeDto> data = iHotSpotFlowDao
        .getPfChangeChartData(Integer.parseInt(PeriodUtils.getCurrentDate()), entity.getTazid());
    String time = "20200623";
    while (data == null || data.size() == 0) {
      time = PeriodUtils.getAppointDateAnyDayBeforeDate(1, time);
      data = iHotSpotFlowDao
          .getPfChangeChartData(
              Integer.parseInt(time),
              entity.getTazid());
      data.removeIf(dto -> dto.getPeriod() > PeriodUtils.getCurrentPeriod15());
    }
    return data;
  }

  public PfChangeContrastDto getPresentPf(PfChangeEntity entity) {
    String thisTime = PeriodUtils.getCurrentDate();
    int period = PeriodUtils.getCurrentPeriod15();
    PfChangeDto thisDto = iHotSpotFlowDao
        .getPresentPf(Integer.parseInt(thisTime), period, entity.getTazid());
    PfChangeContrastDto dto = new PfChangeContrastDto();
    if (thisDto != null) {
      int count = thisDto.getPf();
      dto.setCount(count);
      //获取上周同期日期
      int lastTime = Utils.getLastDay(thisTime, 7);
      PfChangeDto lastDto = iHotSpotFlowDao.getPresentPf(lastTime, period, entity.getTazid());
      if (lastDto != null) {
        int lastPeople = lastDto.getPf();
        double ratio = TpiUtils.calculateGrowth(count, lastPeople, 1);
        int change = count - lastPeople;
        dto.setChange(change);
        dto.setRatio(ratio);
      }
    }
    return dto;
  }

  /**
   * 根据重点区域名称进行模糊查询.
   *
   * @param name 重点区域名称
   * @return 查询列表
   */
  public List<KeyAreaLikeDto> getKeyAreaInfoLikeName(String name) {
    return iHotSpotFlowDao.getKeyAreaInfoLikeName(name);
  }

  /**
   * 获取人流等级分布.
   */
  public FlowsDistributionEntity getFlowsGroupByStatus() {

    List<FlowsDistributionDto> data = iHotSpotFlowDao
        .getFlowsGroupByStatus(PeriodUtils.getCurrentDate(), PeriodUtils.getCurrentPeriod15());
    FlowsDistributionEntity entity = new FlowsDistributionEntity();
    entity.setData(data);
    return entity;
  }

  public List<Map<String, Object>> selectHotspotPassFlow() {
    List<Map<String,Object>> list =	 iHotSpotFlowDao.selectHotspotPassFlow();
    return list;
  }
}
