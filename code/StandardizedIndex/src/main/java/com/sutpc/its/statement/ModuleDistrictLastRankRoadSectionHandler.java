package com.sutpc.its.statement;

import com.sutpc.its.dao.IModuleDistrictRankRoadSectionDao;
import com.sutpc.its.dto.RoadSectionInfoDto;
import com.sutpc.its.po.RoadSectionChangeInfoPo;
import com.sutpc.its.statement.bean.ModuleDistrictLastRankRoadSectionValue;
import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.tools.DocxUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:50 2020/6/2.
 * @Description
 * @Modified By:
 */
@Component
@Service
public class ModuleDistrictLastRankRoadSectionHandler implements ModuleHandler {

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
    return ModuleEnum.MODULE_DISTRICT_LAST_ROAD_SECTION.name().equals(type);
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
    ModuleDistrictLastRankRoadSectionValue value = new ModuleDistrictLastRankRoadSectionValue();
    calRoadSectionInfo(param, value);
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
    ModuleDistrictLastRankRoadSectionValue data = value
        .getValue(ModuleDistrictLastRankRoadSectionValue.class);
    data.setTitle(value.getTitle());
    return DocxUtil.getDocxPathByConditions(template, data);
  }

  /**
   * 具体计算程序.
   */
  public void calRoadSectionInfo(StatementParam param,
      ModuleDistrictLastRankRoadSectionValue value) {
    //快速路信息
    List<RoadSectionInfoDto> lastFastList = dao
        .getFastInfo(param.getCycleStartDate(), param.getCycleEndDate(), param.getDistrictId());
    //主干路信息
    List<RoadSectionInfoDto> lastMainList = dao
        .getMainInfo(param.getCycleStartDate(), param.getCycleEndDate(), param.getDistrictId());
    //次干路信息
    List<RoadSectionInfoDto> lastSubList = dao
        .getSubInfo(param.getCycleStartDate(), param.getCycleEndDate(), param.getDistrictId());
    lastFastList = CalIndexUtils.calPickList(lastFastList, param);
    lastMainList = CalIndexUtils.calPickList(lastMainList, param);
    lastSubList = CalIndexUtils.calPickList(lastSubList, param);

    //快速路信息
    List<RoadSectionInfoDto> thisFastList = dao
        .getFastInfo(param.getCurrentStartDate(), param.getCurrentEndDate(), param.getDistrictId());
    List<RoadSectionChangeInfoPo> fastList = CalIndexUtils
        .getCalResult(lastFastList, thisFastList, param);
    //添加序号index
    for (int i = 0; i < fastList.size(); i++) {
      RoadSectionChangeInfoPo po = fastList.get(i);
      po.setIndex(i + 1);
    }
    value.setFastList(fastList);
    value.setFastImprove(CalIndexUtils.calTotalImprove(fastList));
    value.setFastWorsen(CalIndexUtils.calTotalWorse(fastList));
    //主干路信息
    List<RoadSectionInfoDto> thisMainList = dao
        .getMainInfo(param.getCurrentStartDate(), param.getCurrentEndDate(), param.getDistrictId());
    List<RoadSectionChangeInfoPo> mainList = CalIndexUtils
        .getCalResult(lastMainList, thisMainList, param);
    //添加序号index
    for (int i = 0; i < mainList.size(); i++) {
      RoadSectionChangeInfoPo po = mainList.get(i);
      po.setIndex(i + 1);
    }
    value.setMainList(mainList);
    value.setMainImprove(CalIndexUtils.calTotalImprove(mainList));
    value.setMainWorsen(CalIndexUtils.calTotalWorse(mainList));

    //次干路信息
    List<RoadSectionInfoDto> thisSubList = dao
        .getSubInfo(param.getCurrentStartDate(), param.getCurrentEndDate(), param.getDistrictId());
    List<RoadSectionChangeInfoPo> subList = CalIndexUtils
        .getCalResult(lastSubList, thisSubList, param);
    //添加序号index
    for (int i = 0; i < subList.size(); i++) {
      RoadSectionChangeInfoPo po = subList.get(i);
      po.setIndex(i + 1);
    }
    value.setSubList(subList);
    value.setSubImprove(CalIndexUtils.calTotalImprove(subList));
    value.setSubWorsen(CalIndexUtils.calTotalWorse(subList));
  }
}
