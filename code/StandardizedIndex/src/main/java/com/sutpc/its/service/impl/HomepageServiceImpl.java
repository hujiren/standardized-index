package com.sutpc.its.service.impl;

import com.sutpc.its.dao.IHomepageDao;
import com.sutpc.its.po.BlockInfoAllInfoPo;
import com.sutpc.its.po.BlockInfoPo;
import com.sutpc.its.po.CrossInfoPo;
import com.sutpc.its.po.DistrictStatusPo;
import com.sutpc.its.po.DistrictTpiChartsPo;
import com.sutpc.its.po.JamLengthInfoPo;
import com.sutpc.its.po.JamLengthListPo;
import com.sutpc.its.po.JamRoadsectInfoPo;
import com.sutpc.its.po.PoiAvgTpiPo;
import com.sutpc.its.po.RealTimeJamLengthPo;
import com.sutpc.its.service.IHomepageService;
import com.sutpc.its.tools.BeanCopyUtil;
import com.sutpc.its.tools.DateUtils;
import com.sutpc.its.tools.PeriodUtils;
import com.sutpc.its.tools.TpiUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 首页处理 .
 *
 * @Author: zuotw
 * @Date: created on 14:44 2020/8/26.
 * @Description
 * @Modified By:
 */
@Slf4j
@Service
public class HomepageServiceImpl implements IHomepageService {

  @Autowired
  private IHomepageDao dao;

  /**
   * 获取街道片区相关信息.
   *
   * @return 结果
   */
  @Override
  public BlockInfoAllInfoPo getBlockInfo() {
    String time = PeriodUtils.getCurrentDate();
    int period15 = PeriodUtils.getCurrentPeriod15();
    List<BlockInfoPo> thisList = dao.getBlockInfo(time, period15);
    List<BlockInfoPo> lastList = dao
        .getBlockInfo(PeriodUtils.getAppointDateAnyDayBeforeDate(7, time), period15);
    //计算环比
    int count = 0;
    for (BlockInfoPo thisPo : thisList) {
      double thisTpi = thisPo.getTpi();
      int thisId = thisPo.getBlockId();
      for (BlockInfoPo lastPo : lastList) {
        double lastTpi = lastPo.getTpi();
        int lastId = lastPo.getBlockId();
        if (thisId == lastId) {
          double ratio = TpiUtils.calculateGrowth(lastTpi, thisTpi, 1);
          thisPo.setRatio(ratio);
          //恶化片区：指数大于6并且环比>5%
          if (thisTpi > 6 && ratio > 5) {
            count++;
          }
          break;
        }
      }
    }
    BlockInfoAllInfoPo info = new BlockInfoAllInfoPo();
    info.setCount(count);
    info.setList(thisList);
    List<BlockInfoPo> list = BeanCopyUtil.copyListProperties(thisList, BlockInfoPo::new);
    //按照指数倒序排序
    Collections.sort(list, new Comparator<BlockInfoPo>() {
      @Override
      public int compare(BlockInfoPo o1, BlockInfoPo o2) {
        double t1 = o1.getTpi();
        double t2 = o2.getTpi();
        if (t1 > t2) {
          return -1;
        } else if (t1 < t2) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    list = list.subList(0, 10);
    info.setJamList(list);
    return info;
  }

  /**
   * 获取行政区指数曲线.
   *
   * @return 结果
   */
  @Override
  public List<DistrictTpiChartsPo> getDistrictTpiCharts() {
    List<DistrictTpiChartsPo> list = dao
        .getDistrictTpiCharts(PeriodUtils.getCurrentDate(), PeriodUtils.getCurrentPeriod15());
    return list;
  }

  /**
   * 获取关口信息.
   *
   * @return 结果
   */
  @Override
  public List<CrossInfoPo> getCrossInfoPo() {
    List<CrossInfoPo> list = dao
        .getCrossInfo(PeriodUtils.getCurrentDate(), PeriodUtils.getCurrentPeriod15());
    return list;
  }

  /**
   * 获取拥堵路段.
   *
   * @return 结果
   */
  @Override
  public List<JamRoadsectInfoPo> getJamRoadsectInfo() {
    String thisTime = PeriodUtils.getCurrentDate();
    int period15 = PeriodUtils.getCurrentPeriod15();
    List<JamRoadsectInfoPo> thisList = dao.getJamRoadsectInfo(thisTime, period15);
    String lastTime = PeriodUtils.getAppointDateAnyDayBeforeDate(7, thisTime);
    List<JamRoadsectInfoPo> lastList = dao.getJamRoadsectInfo(lastTime, period15);

    //计算环比
    for (JamRoadsectInfoPo thisPo : thisList) {
      int thisId = thisPo.getId();
      double thisSpeed = thisPo.getSpeed();
      for (JamRoadsectInfoPo lastPo : lastList) {
        int lastId = lastPo.getId();
        double lastSpeed = lastPo.getSpeed();
        if (thisId == lastId) {
          double ratio = TpiUtils.calculateGrowth(lastSpeed, thisSpeed, 1);
          thisPo.setRatio(ratio);
          break;
        }
      }
    }

    //排序
    Collections.sort(thisList, new Comparator<JamRoadsectInfoPo>() {
      @Override
      public int compare(JamRoadsectInfoPo o1, JamRoadsectInfoPo o2) {
        double t1 = o1.getSpeed();
        double t2 = o2.getSpeed();
        if (t1 > t2) {
          return 1;
        } else if (t1 < t2) {
          return -1;
        } else {
          return 0;
        }
      }
    });

    //筛选前十
    if (thisList != null && thisList.size() > 10) {
      thisList.subList(0, 10);
    }
    return thisList;
  }

  /**
   * 获取热点区域平均指数和环比.
   */
  @Override
  public PoiAvgTpiPo getPoiAvgTpiAndRatio() {
    String time = PeriodUtils.getCurrentDate();
    int period15 = PeriodUtils.getCurrentPeriod15();
    PoiAvgTpiPo thisPo = dao.getPoiAvgTpi(time, period15);
    String lastTime = PeriodUtils.getAppointDateAnyDayBeforeDate(7, time);
    //    PoiAvgTpiPo lastPo = dao.getPoiAvgTpi(lastTime, period15);
    //    double ratio = TpiUtils.calculateGrowth(lastPo.getTpi(), thisPo.getTpi(), 1);
    //    thisPo.setRatio(ratio);
    return thisPo;
  }

  /**
   * 获取拥堵里程.
   *
   * @return 结果
   */
  @Override
  public JamLengthInfoPo getJamLength() {
    String time = DateUtils.currentDate();
    int period30 = DateUtils.currentPeriod30();
    JamLengthInfoPo thisPo = dao.getJamLength(time, period30);
    if (thisPo != null) {
      String lastTime = DateUtils.dayBefore(-7);
      JamLengthInfoPo lastPo = dao.getJamLength(lastTime, period30);
      if (lastPo != null) {
        double ratio = TpiUtils.calculateGrowth(lastPo.getJamLength(), thisPo.getJamLength(), 1);
        thisPo.setRatio(ratio);
      }
    }
    return thisPo;
  }

  /**
   * 实时拥堵里程.
   *
   * @return 结果
   */
  @Override
  public RealTimeJamLengthPo getRealTimeJamLength() {
    String time = DateUtils.currentDate();
    int period30 = DateUtils.currentPeriod30();
    List<JamLengthListPo> currentData = dao.getJamList(time, period30);
    String startDate = DateUtils.weekStart(-1).toString().replace("-", "");
    String endDate = DateUtils.weekFriday(-1).toString().replace("-", "");
    List<JamLengthListPo> lastData = dao.getJamLastList(startDate, endDate, period30);
    RealTimeJamLengthPo po = new RealTimeJamLengthPo();
    po.setCurrentData(currentData);
    po.setLastData(lastData);
    return po;
  }

  /**
   * 获取行政区平均路况.
   *
   * @return 结果
   */
  @Override
  public List<DistrictStatusPo> getDistrictStatus() {
    List<DistrictStatusPo> po = dao
        .getDistrictStatus(DateUtils.currentDate(), DateUtils.currentPeriod());
    return po;
  }
}
