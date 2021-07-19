package com.sutpc.its.statement;

import com.sutpc.its.dao.IModuleCityOverviewDao;
import com.sutpc.its.po.CityDistrictTpiRankPo;
import com.sutpc.its.statement.bean.ModuleCityOverviewValue;
import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.tools.TpiUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 报告模块处理程序-全市综述.
 *
 * @author admin
 * @date 2020/5/19 14:06
 */
@Component
public class ModuleCityOverviewHandler implements ModuleHandler {

  @Autowired
  private IModuleCityOverviewDao dao;

  /**
   * 是否支持该模块处理器.
   *
   * @param type 类型
   * @return true or false
   */
  @Override
  public boolean support(String type) {
    return ModuleEnum.MODULE_CITY_OVERVIEW.name().equals(type);
  }

  /**
   * 处理器.
   *
   * @param type 模块类型
   * @param param 参数
   * @return 模块值
   */
  @Override
  public ModuleValue handle(String type, StatementParam param) {
    ModuleCityOverviewValue value = new ModuleCityOverviewValue();
    putForCity(param, value);
    putForDistrict(param, value);
    putForPrimary(param, value);
    putForHub(param, value);
    putForPort(param, value);
    putForGateway(param, value);
    return new ModuleValue(value, type);
  }

  @Override
  public String buildWord(ModuleValue value, ModuleTemplate template) {
    return null;
  }

  /**
   * 市路网高峰时段.
   *
   * @param param 参数
   * @param value 值
   */
  private void putForCity(StatementParam param, ModuleCityOverviewValue value) {
    // 全市路网高峰时段平均速度
    double current = dao.getAllAverageSpeed(param.getCurrentStartDate(), param.getCurrentEndDate());
    value.setCitySpeed(TpiUtils.getByDigit(current, param.getSpeedDigit()));
    // 环比
    double cycle = dao.getAllAverageSpeed(param.getCycleStartDate(), param.getCycleEndDate());
    value.setCityCycleRatio(TpiUtils.calculateGrowth(cycle, current, param.getRatioDigit()));
    // 同比
    double year = dao.getAllAverageSpeed(param.getYearStartDate(), param.getYearEndDate());
    value.setCityYearRatio(TpiUtils.calculateGrowth(year, current, param.getRatioDigit()));
    // 指数
    double tpi = dao.getAllAverageTpi(param.getCycleStartDate(), param.getCurrentEndDate());
    value.setCityTpi(TpiUtils.getByDigit(tpi, param.getTpiDigit()));
    String status = TpiUtils.getStatusByTpi(tpi);
    value.setCityStatus(status);
  }

  /**
   * 行政区数据.
   *
   * @param param 参数
   * @param value 值
   */
  private void putForDistrict(StatementParam param, ModuleCityOverviewValue value) {
    List<CityDistrictTpiRankPo> ranks = dao
        .getDistrictTpiRank(param.getCurrentStartDate(), param.getCurrentEndDate());
    if (CollectionUtils.isEmpty(ranks)) {
      return;
    }
    // 最拥堵
    CityDistrictTpiRankPo first = ranks.get(0);
    value.setDistrictHighestName(first.getName());
    value.setDistrictHighestSpeed(TpiUtils.getByDigit(first.getSpeed(), param.getSpeedDigit()));
    value.setDistrictHighestTpi(TpiUtils.getByDigit(first.getTpi(), param.getTpiDigit()));
    value.setDistrictHighestStatus(TpiUtils.getStatusByTpi(first.getTpi()));
    // 最畅通
    CityDistrictTpiRankPo last = ranks.get(ranks.size() - 1);
    value.setDistrictLowestName(last.getName());
    value.setDistrictLowestSpeed(TpiUtils.getByDigit(last.getSpeed(), param.getSpeedDigit()));
    value.setDistrictLowestTpi(TpiUtils.getByDigit(last.getTpi(), param.getTpiDigit()));
    value.setDistrictLowestStatus(TpiUtils.getStatusByTpi(last.getTpi()));
  }

  /**
   * 主要干道.
   *
   * @param param 参数
   * @param value 值
   */
  private void putForPrimary(StatementParam param, ModuleCityOverviewValue value) {
    // 早高峰时段
    double current = dao
        .getPrimaryForMorning(param.getCurrentStartDate(), param.getCurrentEndDate());
    value.setPrimaryMorningSpeed(TpiUtils.getByDigit(current, param.getSpeedDigit()));
    // 环比
    double cycle = dao.getPrimaryForMorning(param.getCycleStartDate(), param.getCycleEndDate());
    value.setPrimaryMorningCycleRatio(
        TpiUtils.calculateGrowth(cycle, current, param.getRatioDigit()));
    // 同比
    double year = dao.getPrimaryForMorning(param.getYearStartDate(), param.getYearEndDate());
    value.setPrimaryMorningYearRatio(
        TpiUtils.calculateGrowth(year, current, param.getRatioDigit()));
    // 晚高峰时段
    current = dao
        .getPrimaryForEvening(param.getCurrentStartDate(), param.getCurrentEndDate());
    value.setPrimaryEveningSpeed(TpiUtils.getByDigit(current, param.getSpeedDigit()));
    // 环比
    cycle = dao.getPrimaryForEvening(param.getCycleStartDate(), param.getCycleEndDate());
    value.setPrimaryEveningCycleRatio(
        TpiUtils.calculateGrowth(cycle, current, param.getRatioDigit()));
    // 同比
    year = dao.getPrimaryForEvening(param.getYearStartDate(), param.getYearEndDate());
    value.setPrimaryEveningYearRatio(
        TpiUtils.calculateGrowth(year, current, param.getRatioDigit()));
  }

  /**
   * 枢纽片区.
   *
   * @param param 参数
   * @param value 值
   */
  private void putForHub(StatementParam param, ModuleCityOverviewValue value) {
    // 早高峰时段
    double current = dao.getHubForMorning(param.getCurrentStartDate(), param.getCurrentEndDate());
    value.setHubMorningSpeed(TpiUtils.getByDigit(current, param.getSpeedDigit()));
    // 环比
    double cycle = dao.getHubForMorning(param.getCycleStartDate(), param.getCycleEndDate());
    value.setHubMorningCycleRatio(TpiUtils.calculateGrowth(cycle, current, param.getRatioDigit()));
    // 晚高峰时段
    current = dao.getHubForEvening(param.getCurrentStartDate(), param.getCurrentEndDate());
    value.setHubEveningSpeed(TpiUtils.getByDigit(current, param.getSpeedDigit()));
    // 环比
    cycle = dao.getHubForEvening(param.getCycleStartDate(), param.getCycleEndDate());
    value.setHubEveningCycleRatio(TpiUtils.calculateGrowth(cycle, current, param.getRatioDigit()));
  }

  /**
   * 口岸片区.
   *
   * @param param 参数
   * @param value 值
   */
  private void putForPort(StatementParam param, ModuleCityOverviewValue value) {
    // 早高峰时段
    double current = dao.getPortForMorning(param.getCurrentStartDate(), param.getCurrentEndDate());
    value.setPortMorningSpeed(TpiUtils.getByDigit(current, param.getSpeedDigit()));
    // 环比
    double cycle = dao.getPortForMorning(param.getCycleStartDate(), param.getCycleEndDate());
    value.setPortMorningCycleRatio(TpiUtils.calculateGrowth(cycle, current, param.getRatioDigit()));
    // 晚高峰时段
    current = dao.getPortForEvening(param.getCurrentStartDate(), param.getCurrentEndDate());
    value.setPortEveningSpeed(TpiUtils.getByDigit(current, param.getSpeedDigit()));
    // 环比
    cycle = dao.getPortForEvening(param.getCycleStartDate(), param.getCycleEndDate());
    value.setPortEveningCycleRatio(TpiUtils.calculateGrowth(cycle, current, param.getRatioDigit()));
  }

  /**
   * 主要二线关.
   *
   * @param param 参数
   * @param value 值
   */
  private void putForGateway(StatementParam param, ModuleCityOverviewValue value) {
    // 早高峰时段
    double current = dao
        .getGatewayForMorning(param.getCurrentStartDate(), param.getCurrentEndDate());
    value.setGatewayMorningSpeed(TpiUtils.getByDigit(current, param.getSpeedDigit()));
    // 环比
    double cycle = dao.getGatewayForMorning(param.getCycleStartDate(), param.getCycleEndDate());
    value.setGatewayMorningCycleRatio(
        TpiUtils.calculateGrowth(cycle, current, param.getRatioDigit()));
    // 同比
    double year = dao.getGatewayForMorning(param.getYearStartDate(), param.getYearEndDate());
    value.setGatewayMorningYearRatio(
        TpiUtils.calculateGrowth(year, current, param.getRatioDigit()));
    // 晚高峰时段
    current = dao
        .getGatewayForEvening(param.getCurrentStartDate(), param.getCurrentEndDate());
    value.setGatewayEveningSpeed(TpiUtils.getByDigit(current, param.getSpeedDigit()));
    // 环比
    cycle = dao.getGatewayForEvening(param.getCycleStartDate(), param.getCycleEndDate());
    value.setGatewayEveningCycleRatio(
        TpiUtils.calculateGrowth(cycle, current, param.getRatioDigit()));
    // 同比
    year = dao.getGatewayForEvening(param.getYearStartDate(), param.getYearEndDate());
    value.setGatewayEveningYearRatio(
        TpiUtils.calculateGrowth(year, current, param.getRatioDigit()));
  }
}
