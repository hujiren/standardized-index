package com.sutpc.its.statement;

import com.sutpc.its.dao.IModuleDistrictHeadFootDao;
import com.sutpc.its.dao.IModuleDistrictOverviewDao;
import com.sutpc.its.dto.RoadSectionInfoDto;
import com.sutpc.its.po.PeakHotPotPo;
import com.sutpc.its.po.RoadSectionChangeInfoPo;
import com.sutpc.its.po.WorkDayPeakExtremaBlockInfoPo;
import com.sutpc.its.po.WorkDayPeakInfoPo;
import com.sutpc.its.statement.bean.ModuleDistrictOverviewValue;
import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.tools.DocxUtil;
import com.sutpc.its.tools.TpiUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:42 2020/6/3.
 * @Description
 * @Modified By:
 */
@Component
public class ModuleDistrictOverviewHandler implements ModuleHandler {

  @Autowired
  private IModuleDistrictOverviewDao dao;

  @Autowired
  private IModuleDistrictHeadFootDao headFootDao;

  @Value("${tpi.combined.report}")
  private String rootPath;

  /**
   * 是否支持该模块处理器.
   *
   * @param type 类型
   * @return true or false
   */
  @Override
  public boolean support(String type) {
    return ModuleEnum.MODULE_DISTRICT_OVERVIEW.name().equals(type);
  }

  /**
   * 模块数据程序.
   *
   * @param type 模块类型
   * @param param 参数
   * @return 结果
   */
  @Override
  public ModuleValue handle(String type, StatementParam param) {
    ModuleDistrictOverviewValue value = new ModuleDistrictOverviewValue();
    value.setDateCalculated(
        TpiUtils.getDateCalculated(param.getCurrentStartDate(), param.getCurrentEndDate()));
    calTotalNet(param, value);
    calBlock(param, value);
    calHotpot(param, value);
    calMainRoad(param, value);
    calRoadSection(param, value);
    String districtName = headFootDao.getDistrictNameById(param.getDistrictId());
    value.setDistrictName(districtName);
    return new ModuleValue(value, type);
  }

  /**
   * 根据模板和值生成模块word.
   *
   * @param value 值
   * @param template 模板路径
   * @return 生成的word路径
   */
  @Override
  public String buildWord(ModuleValue value, ModuleTemplate template) {
    ModuleDistrictOverviewValue data = value.getValue(ModuleDistrictOverviewValue.class);
    data.setTitle(value.getTitle());
    return DocxUtil.getDocxPathByConditions(template, data);
  }

  /**
   * 计算全网总体运行数据.
   *
   * @param param 参数值
   * @param value 模板对象
   */
  public void calTotalNet(StatementParam param, ModuleDistrictOverviewValue value) {
    double peakAverageSpeed = dao
        .getAllPeakAverageSpeed(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    peakAverageSpeed = TpiUtils.getByDigit(peakAverageSpeed, param.getSpeedDigit());
    value.setPeakAverageSpeed(peakAverageSpeed);

    double peakTpi = dao.getAllPeakTpi(param.getCurrentStartDate(), param.getCurrentEndDate(),
        param.getDistrictId());
    value.setPeakTpi(TpiUtils.getByDigit(peakTpi, param.getTpiDigit()));
    value.setPeakStatus(TpiUtils.getStatusByTpi(peakTpi));

    double cyclePeakAverageSpeed = dao
        .getAllPeakAverageSpeed(param.getCycleStartDate(), param.getCycleEndDate(),
            param.getDistrictId());

    double ratio = TpiUtils.calculateGrowth(cyclePeakAverageSpeed, peakAverageSpeed);
    ratio = TpiUtils.getByDigit(ratio, param.getRatioDigit());
    value.setCycleAverageSpeedRatio(ratio);

    WorkDayPeakInfoPo workDayPeakInfoPo = dao
        .getWorkDayPeakMaxInfo(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    double peakMaxSpeed = workDayPeakInfoPo.getSpeed();
    value.setPeakMaxSpeed(TpiUtils.getByDigit(peakMaxSpeed, param.getSpeedDigit()));
    String peakMaxDate = workDayPeakInfoPo.getDate();
    String peakMaxDateStr = TpiUtils.dateToWeek(peakMaxDate);
    value.setPeakMaxDate(peakMaxDateStr);

    WorkDayPeakInfoPo workMinDayPeakInfoPo = dao
        .getWorkDayPeakMinInfo(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    double peakMinSpeed = workMinDayPeakInfoPo.getSpeed();
    value.setPeakMinSpeed(TpiUtils.getByDigit(peakMinSpeed, param.getSpeedDigit()));
    String peakMinDate = workMinDayPeakInfoPo.getDate();
    String peakMinDateStr = TpiUtils.dateToWeek(peakMinDate);
    value.setPeakMinDate(peakMinDateStr);
  }

  /**
   * 计算街道交通运行数据.
   *
   * @param param 参数值
   * @param value 模板对象
   */
  public void calBlock(StatementParam param, ModuleDistrictOverviewValue value) {
    int slowCount = dao.getSlowCount(param.getCurrentStartDate(), param.getCurrentEndDate(),
        param.getDistrictId());
    int unimpededCount = dao
        .getUnimpededCount(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    value.setSlowCount(slowCount);
    value.setUnimpededCount(unimpededCount);

    WorkDayPeakExtremaBlockInfoPo poMax = dao
        .getMaxBlockInfo(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    value.setPeakMaxBlock(poMax.getName());
    value.setPeakMaxBlockTpi(TpiUtils.getByDigit(poMax.getTpi(), param.getTpiDigit()));

    WorkDayPeakExtremaBlockInfoPo poMin = dao
        .getMinBlockInfo(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    value.setPeakMinBlock(poMin.getName());
    value.setPeakMinBlockTpi(TpiUtils.getByDigit(poMin.getTpi(), param.getTpiDigit()));
  }

  /**
   * 计算热点交通运行数据.
   *
   * @param param 参数值
   * @param value 模板对象
   */
  public void calHotpot(StatementParam param, ModuleDistrictOverviewValue value) {
    //高峰最高指数相关信息
    PeakHotPotPo thisMaxPo = dao
        .getPeakMaxHotSpotTpi(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    double thisMaxTpi = thisMaxPo.getTpi();
    value.setMaxTpi(TpiUtils.getByDigit(thisMaxTpi, param.getTpiDigit()));
    value.setMaxTpiHot(thisMaxPo.getPoiName());

    //高峰最低指数相关信息
    PeakHotPotPo thisMinPo = dao
        .getPeakMinHotSpotTpi(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    double thisMinTpi = thisMinPo.getTpi();
    value.setMinTpi(TpiUtils.getByDigit(thisMinTpi, param.getTpiDigit()));
    value.setMinTpiHot(thisMinPo.getPoiName());

    PeakHotPotPo lastMaxPo = dao
        .getPeakMaxHotSpotTpi(param.getCycleStartDate(), param.getCycleEndDate(),
            param.getDistrictId());
    double lastMaxTpi = lastMaxPo.getTpi();

    PeakHotPotPo lastMinPo = dao
        .getPeakMinHotSpotTpi(param.getCycleStartDate(), param.getCycleEndDate(),
            param.getDistrictId());
    double lastMinTpi = lastMinPo.getTpi();

    double maxRatio = TpiUtils.calculateGrowth(lastMaxTpi, thisMaxTpi, param.getRatioDigit());
    value.setMaxTpiHotRatio(maxRatio);

    double minRatio = TpiUtils.calculateGrowth(lastMinTpi, thisMinTpi, param.getRatioDigit());
    value.setMinTpiHotRatio(minRatio);
  }

  /**
   * 计算主要干道交通运行数据.
   *
   * @param param 参数值
   * @param value 模板对象
   */
  public void calMainRoad(StatementParam param, ModuleDistrictOverviewValue value) {
    double thisSpeed = dao
        .getMainRoadSectionSpeed(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    double lastSpeed = dao
        .getMainRoadSectionSpeed(param.getCycleStartDate(), param.getCycleEndDate(),
            param.getDistrictId());
    double peakRatio = TpiUtils.calculateGrowth(lastSpeed, thisSpeed, param.getRatioDigit());
    value.setMainRoadSpeed(TpiUtils.getByDigit(thisSpeed, param.getSpeedDigit()));
    value.setMainRoadRatio(peakRatio);

    List<RoadSectionInfoDto> thisList = dao
        .getMainRoadSectionInfo(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    List<RoadSectionInfoDto> lastList = dao
        .getMainRoadSectionInfo(param.getCycleStartDate(), param.getCycleEndDate(),
            param.getDistrictId());
    List<RoadSectionChangeInfoPo> data = new ArrayList<>();
    for (RoadSectionInfoDto thisDto : thisList) {
      double theSpeed = thisDto.getSpeed();
      int theId = thisDto.getId();
      for (RoadSectionInfoDto lastDto : lastList) {
        double thatSpeed = lastDto.getSpeed();
        int thatId = lastDto.getId();
        if (theId == thatId) {
          double ratio = TpiUtils.calculateGrowth(thatSpeed, theSpeed, param.getRatioDigit());
          RoadSectionChangeInfoPo po = new RoadSectionChangeInfoPo();
          BeanUtils.copyProperties(thisDto, po);
          po.setRatio(ratio);
          data.add(po);
        }
      }
    }
    //按环比排序
    Collections.sort(data, new Comparator<RoadSectionChangeInfoPo>() {
      @Override
      public int compare(RoadSectionChangeInfoPo o1, RoadSectionChangeInfoPo o2) {
        double r1 = o1.getRatio();
        double r2 = o2.getRatio();
        if (r2 >= r1) {
          return 1;
        } else {
          return -1;
        }
      }
    });
    value.setMainRoadMaxRatioName(
        String.format("%s(%s)", data.get(0).getName(), data.get(0).getDirName()));
    value.setMainRoadMaxRatio(data.get(0).getRatio());
    value.setMainRoadMinRatioName(String.format("%s(%s)", data.get(data.size() - 1).getName(),
        data.get(data.size() - 1).getDirName()));
    value.setMainRoadMinRatio(data.get(data.size() - 1).getRatio());
  }

  /**
   * 计算不同等级录道路运行数据.
   *
   * @param param 参数值
   * @param value 模板对象
   */
  public void calRoadSection(StatementParam param, ModuleDistrictOverviewValue value) {
    //快速路信息
    List<RoadSectionInfoDto> fastList = dao
        .getFastInfo(param.getCurrentStartDate(), param.getCurrentEndDate(), param.getDistrictId());
    //主干路信息
    List<RoadSectionInfoDto> mainList = dao
        .getMainInfo(param.getCurrentStartDate(), param.getCurrentEndDate(), param.getDistrictId());
    //次干路信息
    List<RoadSectionInfoDto> subList = dao
        .getSubInfo(param.getCurrentStartDate(), param.getCurrentEndDate(), param.getDistrictId());
    fastList = CalIndexUtils.calPickList(fastList, param);
    mainList = CalIndexUtils.calPickList(mainList, param);
    subList = CalIndexUtils.calPickList(subList, param);

    if (fastList != null && fastList.size() > 0) {
      value.setFastRoad(String
          .format("%s(%s至%s)", fastList.get(0).getName(), fastList.get(0).getFrom(),
              fastList.get(0).getTo()));
      value.setFastSpeed(fastList.get(0).getSpeed());
      value.setFastTpi(fastList.get(0).getTpi());
    }
    if (mainList != null && mainList.size() > 0) {
      value.setMainRoad(String
          .format("%s(%s至%s)", mainList.get(0).getName(), mainList.get(0).getFrom(),
              mainList.get(0).getTo()));
      value.setMainSpeed(mainList.get(0).getSpeed());
      value.setMainTpi(mainList.get(0).getTpi());
    }

    if (subList != null && subList.size() > 0) {
      value
          .setSubRoad(String.format("%s(%s至%s)", subList.get(0).getName(), subList.get(0).getFrom(),
              subList.get(0).getTo()));
      value.setSubSpeed(subList.get(0).getSpeed());
      value.setSubTpi(subList.get(0).getTpi());
    }
  }
}
