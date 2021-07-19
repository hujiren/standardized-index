package com.sutpc.its.statement;

import com.deepoove.poi.data.PictureRenderData;
import com.sutpc.its.dao.IModuleDistrictTotalNetDao;
import com.sutpc.its.po.WorkDayPeakInfoPo;
import com.sutpc.its.statement.bean.ModuleDistrictTotalNetValue;
import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.tools.DocxUtil;
import com.sutpc.its.tools.TpiUtils;
import java.awt.image.BufferedImage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 报告模块处理程序-全路网交通运行.
 *
 * @Author: zuotw
 * @Date: created on 15:21 2020/5/25.
 * @Description
 * @Modified By:
 */
@Service
@Component
public class ModuleDistrictTotalNetHandler implements ModuleHandler {

  @Autowired
  private IModuleDistrictTotalNetDao dao;

  @Override
  public boolean support(String type) {
    return ModuleEnum.MODULE_DISTRICT_TOTAL_NET.name().equals(type);
  }

  @Override
  public ModuleValue handle(String type, StatementParam param) {
    ModuleDistrictTotalNetValue value = new ModuleDistrictTotalNetValue();
    calPeakAvgSpeedAndRatio(param, value);
    calMorningPeakAvgStatus(param, value);
    calEveningPeakAvgStatus(param, value);
    calPeakMaxSpeedInfo(param, value);
    calPeakMinSpeedInfo(param, value);
    calWordDayPeakCityChartData(param, value);
    calWordDayPeakDistrictChartData(param, value);
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
    ModuleDistrictTotalNetValue data = value.getValue(ModuleDistrictTotalNetValue.class);
    data.setTitle(value.getTitle());
    String chart = data.getChart();
    //chart图塞入容器
    if (chart != null && chart.length() > 0) {
      //width:552,height:329 模板图片宽高
      BufferedImage bufferedImage = TpiUtils.base64ToBufferedImage(chart);
      data.setPicChart(new PictureRenderData(552, 329, ".png", bufferedImage));
    } else {
      //预留功能：待定给出示例图片，以免生成报告出错
    }
    return DocxUtil.getDocxPathByConditions(template, data);
  }

  /**
   * 获取高峰时段全区平均速度及环比.
   *
   * @param param 查询参数
   * @param value 模板值
   */
  private void calPeakAvgSpeedAndRatio(StatementParam param, ModuleDistrictTotalNetValue value) {
    double peakAverageSpeed = dao
        .getAllPeakAverageSpeed(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    peakAverageSpeed = TpiUtils.getByDigit(peakAverageSpeed, param.getSpeedDigit());
    value.setPeakAverageSpeed(peakAverageSpeed);

    double cyclePeakAverageSpeed = dao
        .getAllPeakAverageSpeed(param.getCycleStartDate(), param.getCycleEndDate(),
            param.getDistrictId());

    double ratio = TpiUtils.calculateGrowth(cyclePeakAverageSpeed, peakAverageSpeed);
    ratio = TpiUtils.getByDigit(ratio, param.getRatioDigit());
    value.setCycleAverageSpeedRatio(ratio);
  }

  /**
   * 获取早高峰时段全区平均速度及指数及状态.
   *
   * @param param 查询参数
   * @param value 模板值
   */
  private void calMorningPeakAvgStatus(StatementParam param, ModuleDistrictTotalNetValue value) {
    double morningPeakAverageSpeed = dao
        .getAllMorningPeakAverageSpeed(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    value.setMorningPeakAverageSpeed(
        TpiUtils.getByDigit(morningPeakAverageSpeed, param.getSpeedDigit()));

    double morningPeakTpi = dao
        .getAllMorningPeakTpi(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    value.setMorningPeakTpi(TpiUtils.getByDigit(morningPeakTpi, param.getTpiDigit()));

    String morningPeakStatus = TpiUtils.getStatusByTpi(morningPeakTpi);
    value.setMorningPeakStatus(morningPeakStatus);
  }

  /**
   * 获取晚高峰时段全区平均速度及指数及状态.
   *
   * @param param 查询参数
   * @param value 模板值
   */
  private void calEveningPeakAvgStatus(StatementParam param, ModuleDistrictTotalNetValue value) {
    double eveningPeakAverageSpeed = dao
        .getAllEveningPeakAverageSpeed(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    value.setEveningPeakAverageSpeed(
        TpiUtils.getByDigit(eveningPeakAverageSpeed, param.getSpeedDigit()));

    double eveningPeakTpi = dao
        .getAllEveningPeakTpi(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    value.setEveningPeakTpi(TpiUtils.getByDigit(eveningPeakTpi, param.getTpiDigit()));

    String eveningPeakStatus = TpiUtils.getStatusByTpi(eveningPeakTpi);
    value.setEveningPeakStatus(eveningPeakStatus);
  }

  /**
   * 获取高峰时期最大速度值的日期等相关信息.
   *
   * @param param 查询参数
   * @param value 模板值
   */
  private void calPeakMaxSpeedInfo(StatementParam param, ModuleDistrictTotalNetValue value) {
    WorkDayPeakInfoPo workDayPeakInfoPo = dao
        .getWorkDayPeakMaxInfo(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    double peakMaxSpeed = workDayPeakInfoPo.getSpeed();
    value.setPeakMaxSpeed(TpiUtils.getByDigit(peakMaxSpeed, param.getSpeedDigit()));
    String peakMaxDate = workDayPeakInfoPo.getDate();
    String peakMaxDateStr = TpiUtils.dateToWeek(peakMaxDate);
    value.setPeakMaxDate(peakMaxDateStr);
  }

  /**
   * 获取高峰时期最小速度值的日期等相关信息.
   *
   * @param param 查询参数
   * @param value 模板值
   */
  private void calPeakMinSpeedInfo(StatementParam param, ModuleDistrictTotalNetValue value) {
    WorkDayPeakInfoPo workDayPeakInfoPo = dao
        .getWorkDayPeakMinInfo(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    double peakMinSpeed = workDayPeakInfoPo.getSpeed();
    value.setPeakMinSpeed(TpiUtils.getByDigit(peakMinSpeed, param.getSpeedDigit()));
    String peakMinDate = workDayPeakInfoPo.getDate();
    String peakMinDateStr = TpiUtils.dateToWeek(peakMinDate);
    value.setPeakMinDate(peakMinDateStr);
  }

  /**
   * 获取全区路网高峰时段日均速度变化图-全市.
   *
   * @param param 查询参数
   * @param value 模板值
   */
  private void calWordDayPeakCityChartData(StatementParam param,
      ModuleDistrictTotalNetValue value) {
    List<WorkDayPeakInfoPo> list = dao
        .getCityChartData(param.getCurrentStartDate(), param.getCurrentEndDate());
    for (WorkDayPeakInfoPo workDayPeakInfoPo : list) {
      workDayPeakInfoPo
          .setSpeed(TpiUtils.getByDigit(workDayPeakInfoPo.getSpeed(), param.getSpeedDigit()));
    }
    value.setCityChart(list);
  }

  /**
   * 获取全区路网高峰时段日均速度变化图-行政区.
   *
   * @param param 查询参数
   * @param value 模板值
   */
  private void calWordDayPeakDistrictChartData(StatementParam param,
      ModuleDistrictTotalNetValue value) {
    List<WorkDayPeakInfoPo> list = dao
        .getDistrictChartData(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    for (WorkDayPeakInfoPo workDayPeakInfoPo : list) {
      workDayPeakInfoPo
          .setSpeed(TpiUtils.getByDigit(workDayPeakInfoPo.getSpeed(), param.getSpeedDigit()));
    }
    value.setDistrictChart(list);
  }
}
