package com.sutpc.its.statement;

import com.sutpc.its.dao.IModuleCityTotalityDao;
import com.sutpc.its.po.CityTotalitySpeedPo;
import com.sutpc.its.po.CityTotalityTpiPo;
import com.sutpc.its.statement.bean.ModuleCityTotalityValue;
import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.tools.TpiUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 报告模块处理程序-总体交通运行.
 *
 * @author admin
 * @date 2020/5/19 14:06
 */
@Component
public class ModuleCityTotalityHandler implements ModuleHandler {

  @Autowired
  private IModuleCityTotalityDao dao;

  private static final String FID_CITY_MORNING = "111_MORNING";
  private static final String FID_CITY_EVENING = "111_EVENING";
  private static final String FID_CENTRAL_MORNING = "222_MORNING";
  private static final String FID_CENTRAL_EVENING = "222_EVENING";

  /**
   * 是否支持该模块处理器.
   *
   * @param type 类型
   * @return true or false
   */
  @Override
  public boolean support(String type) {
    return ModuleEnum.MODULE_CITY_TOTALITY.name().equals(type);
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
    ModuleCityTotalityValue value = new ModuleCityTotalityValue();
    putForAll(param, value);
    putForTpi(param, value);
    putForSpeed(param, value);
    return new ModuleValue(value, type);
  }

  @Override
  public String buildWord(ModuleValue value, ModuleTemplate template) {
    return null;
  }

  /**
   * 全市路网高峰时段.
   *
   * @param param 参数
   * @param value 值
   */
  private void putForAll(StatementParam param, ModuleCityTotalityValue value) {
    // 全市路网高峰时段平均速度
    double current = dao.getAllAverageSpeed(param.getCurrentStartDate(), param.getCurrentEndDate());
    value.setPeakSpeed(TpiUtils.getByDigit(current, param.getSpeedDigit()));
    // 环比
    double cycle = dao.getAllAverageSpeed(param.getCycleStartDate(), param.getCycleEndDate());
    value.setPeakCycleRatio(TpiUtils.calculateGrowth(cycle, current, param.getRatioDigit()));
    // 同比
    double year = dao.getAllAverageSpeed(param.getYearStartDate(), param.getYearEndDate());
    value.setPeakYearRatio(TpiUtils.calculateGrowth(year, current, param.getRatioDigit()));
  }

  /**
   * 全市及中心城区道路交通运行指数变化.
   *
   * @param param 参数
   * @param value 值
   */
  private void putForTpi(StatementParam param, ModuleCityTotalityValue value) {
    List<CityTotalityTpiPo> speeds = dao
        .getAllTpi(param.getCurrentStartDate(), param.getCurrentEndDate());
    Map<String, List<CityTotalityTpiPo>> mapSpeed = speeds.stream().collect(
        Collectors.groupingBy(p -> String.format("%d_%s", p.getFid(), p.getPeriod())));
    double cityPeakTpi = 0;
    int cityPeakCount = 0;
    double centralPeakTpi = 0;
    int centralPeakCount = 0;
    double cityMorningTpi = 0;
    double cityEveningTpi = 0;
    double centralMorningTpi = 0;
    double centralEveningTpi = 0;
    for (String key : mapSpeed.keySet()) {
      List<CityTotalityTpiPo> values = mapSpeed.get(key);
      double tpi = 0;
      int count = values.size();
      for (CityTotalityTpiPo p : values) {
        tpi += p.getTpi();
      }
      if (FID_CITY_MORNING.equals(key)) {
        cityPeakTpi += tpi;
        cityPeakCount += count;
        cityMorningTpi = count == 0 ? 0 : tpi / count;
      } else if (FID_CITY_EVENING.equals(key)) {
        cityPeakTpi += tpi;
        cityPeakCount += count;
        cityEveningTpi = count == 0 ? 0 : tpi / count;
      } else if (FID_CENTRAL_MORNING.equals(key)) {
        centralPeakTpi += tpi;
        centralPeakCount += count;
        centralMorningTpi = count == 0 ? 0 : tpi / count;
      } else if (FID_CENTRAL_EVENING.equals(key)) {
        centralPeakTpi += tpi;
        centralPeakCount += count;
        centralEveningTpi = count == 0 ? 0 : tpi / count;
      }
    }
    cityPeakTpi = cityPeakCount == 0 ? 0 : cityPeakTpi / cityPeakCount;
    value.setCityTpis(Arrays.asList(TpiUtils.getByDigit(cityMorningTpi, param.getTpiDigit()),
        TpiUtils.getByDigit(cityEveningTpi, param.getTpiDigit()),
        TpiUtils.getByDigit(cityPeakTpi, param.getTpiDigit())));
    centralPeakTpi = centralPeakCount == 0 ? 0 : centralPeakTpi / centralPeakCount;
    value.setCentralTpis(Arrays.asList(TpiUtils.getByDigit(centralMorningTpi, param.getTpiDigit()),
        TpiUtils.getByDigit(centralEveningTpi, param.getTpiDigit()),
        TpiUtils.getByDigit(centralPeakTpi, param.getTpiDigit())));
    value.setMorningTpi(TpiUtils.getByDigit(cityMorningTpi, param.getTpiDigit()));
    value.setMorningStatus(TpiUtils.getStatusByTpi(cityMorningTpi));
    value.setEveningTpi(TpiUtils.getByDigit(cityEveningTpi, param.getTpiDigit()));
    value.setEveningStatus(TpiUtils.getStatusByTpi(cityEveningTpi));
  }

  /**
   * 全市及中心城区路网平均速度变化.
   *
   * @param param 参数
   * @param value 值
   */
  private void putForSpeed(StatementParam param, ModuleCityTotalityValue value) {
    List<CityTotalitySpeedPo> speeds = dao
        .getAllSpeed(param.getCurrentStartDate(), param.getCurrentEndDate());
    Map<String, List<CityTotalitySpeedPo>> mapSpeed = speeds.stream().collect(
        Collectors.groupingBy(p -> String.format("%d_%s", p.getFid(), p.getPeriod())));
    double cityPeakLength = 0;
    double cityPeakTime = 0;
    double centralPeakLength = 0;
    double centralPeakTime = 0;
    double cityMorningSpeed = 0;
    double cityEveningSpeed = 0;
    double centralMorningSpeed = 0;
    double centralEveningSpeed = 0;
    for (String key : mapSpeed.keySet()) {
      List<CityTotalitySpeedPo> values = mapSpeed.get(key);
      double length = 0;
      double time = 0;
      for (CityTotalitySpeedPo p : values) {
        length += p.getLength();
        time += p.getTime();
      }
      if (FID_CITY_MORNING.equals(key)) {
        cityPeakLength += length;
        cityPeakTime += time;
        cityMorningSpeed = time == 0 ? 0 : (length * 3.6) / time;
      } else if (FID_CITY_EVENING.equals(key)) {
        cityPeakLength += length;
        cityPeakTime += time;
        cityEveningSpeed = time == 0 ? 0 : (length * 3.6) / time;
      } else if (FID_CENTRAL_MORNING.equals(key)) {
        centralPeakLength += length;
        centralPeakTime += time;
        centralMorningSpeed = time == 0 ? 0 : (length * 3.6) / time;
      } else if (FID_CENTRAL_EVENING.equals(key)) {
        centralPeakLength += length;
        centralPeakTime += time;
        centralEveningSpeed = time == 0 ? 0 : (length * 3.6) / time;
      }
    }
    double cityPeakSpeed = cityPeakTime == 0 ? 0 : (cityPeakLength * 3.6) / cityPeakTime;
    double centralPeakSpeed =
        centralPeakTime == 0 ? 0 : (centralPeakLength * 3.6) / centralPeakTime;
    value.setCitySpeeds(Arrays.asList(TpiUtils.getByDigit(cityMorningSpeed, param.getSpeedDigit()),
        TpiUtils.getByDigit(cityEveningSpeed, param.getSpeedDigit()),
        TpiUtils.getByDigit(cityPeakSpeed, param.getSpeedDigit())));
    value.setCentralSpeeds(
        Arrays.asList(TpiUtils.getByDigit(centralMorningSpeed, param.getSpeedDigit()),
            TpiUtils.getByDigit(centralEveningSpeed, param.getSpeedDigit()),
            TpiUtils.getByDigit(centralPeakSpeed, param.getSpeedDigit())));
    value.setMorningSpeed(TpiUtils.getByDigit(cityMorningSpeed, param.getSpeedDigit()));
    value.setEveningSpeed(TpiUtils.getByDigit(cityEveningSpeed, param.getSpeedDigit()));
  }
}
