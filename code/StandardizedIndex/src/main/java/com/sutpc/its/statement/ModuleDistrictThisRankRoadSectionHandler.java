package com.sutpc.its.statement;

import com.sutpc.its.dao.IModuleDistrictRankRoadSectionDao;
import com.sutpc.its.dto.RoadSectionInfoDto;
import com.sutpc.its.statement.bean.ModuleDistrictThisRankRoadSectionValue;
import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.tools.DocxUtil;
import com.sutpc.its.tools.TpiUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 本期不同等级拥堵路段排名.
 *
 * @Author: zuotw
 * @Date: created on 16:54 2020/6/1.
 * @Description
 * @Modified By:
 */
@Component
@Service
public class ModuleDistrictThisRankRoadSectionHandler implements ModuleHandler {

  @Autowired
  private IModuleDistrictRankRoadSectionDao dao;

  /**
   * 是否支持该模块处理器.
   *
   * @param type 类型
   * @return true or false
   */
  @Override
  public boolean support(String type) {
    return ModuleEnum.MODULE_DISTRICT_THIS_ROAD_SECTION.name().equals(type);
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
    ModuleDistrictThisRankRoadSectionValue value = new ModuleDistrictThisRankRoadSectionValue();
    calRoadSection(param, value);
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
    ModuleDistrictThisRankRoadSectionValue data = value
        .getValue(ModuleDistrictThisRankRoadSectionValue.class);
    data.setTitle(value.getTitle());
    return DocxUtil.getDocxPathByConditions(template, data);
  }

  /**
   * .
   */
  public void calRoadSection(StatementParam param, ModuleDistrictThisRankRoadSectionValue value) {
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

    //快速路信息
    if (fastList != null && fastList.size() > 0) {
      value.setFastPeakMaxRoad(String
          .format("%s(%s至%s)", fastList.get(0).getName(), fastList.get(0).getFrom(),
              fastList.get(0).getTo()));
      value.setFastPeakSpeed(CalIndexUtils.calPicSpeed(fastList, param));
      value.setFastPeakTpi(CalIndexUtils.calPicTpi(fastList, param));
      value.setFastPeakMaxSpeed(
          TpiUtils.getByDigit(fastList.get(0).getSpeed(), param.getSpeedDigit()));
      value.setFastPeakMaxTpi(fastList.get(0).getTpi());
      value.setFastList(CalIndexUtils.getRoadSectionChangePoList(fastList));
    }

    //主干路信息
    if (mainList != null && mainList.size() > 0) {
      value.setMainPeakMaxRoad(String
          .format("%s(%s至%s)", mainList.get(0).getName(), mainList.get(0).getFrom(),
              mainList.get(0).getTo()));
      value.setMainPeakSpeed(CalIndexUtils.calPicSpeed(mainList, param));
      value.setMainPeakTpi(CalIndexUtils.calPicTpi(mainList, param));
      value.setMainPeakMaxSpeed(
          TpiUtils.getByDigit(mainList.get(0).getSpeed(), param.getSpeedDigit()));
      value.setMainPeakMaxTpi(mainList.get(0).getTpi());
      value.setMainList(CalIndexUtils.getRoadSectionChangePoList(mainList));
    }

    //次干路信息
    if (subList != null && subList.size() > 0) {
      value.setSubPeakMaxRoad(String
          .format("%s(%s至%s)", subList.get(0).getName(), subList.get(0).getFrom(),
              subList.get(0).getTo()));
      value.setSubPeakSpeed(CalIndexUtils.calPicSpeed(subList, param));
      value.setSubPeakTpi(CalIndexUtils.calPicTpi(subList, param));
      value.setSubPeakMaxSpeed(
          TpiUtils.getByDigit(subList.get(0).getSpeed(), param.getSpeedDigit()));
      value.setSubPeakMaxTpi(subList.get(0).getTpi());
      value.setSubList(CalIndexUtils.getRoadSectionChangePoList(subList));
    }
  }
}
