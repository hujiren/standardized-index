package com.sutpc.its.statement;

import com.sutpc.its.dao.IModuleDistrictImproveDao;
import com.sutpc.its.dto.RoadSectionInfoDto;
import com.sutpc.its.po.RoadSectionChangePo;
import com.sutpc.its.statement.bean.ModuleDistrictImproveValue;
import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.tools.DocxUtil;
import com.sutpc.its.tools.TpiUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 17:25 2020/6/2.
 * @Description
 * @Modified By:
 */
@Component
@Service
public class ModuleDistrictImproveHandler implements ModuleHandler {

  @Autowired
  private IModuleDistrictImproveDao dao;

  /**
   * 是否支持该模块处理器.
   *
   * @param type 类型
   * @return true or false
   */
  @Override
  public boolean support(String type) {
    return ModuleEnum.MODULE_DISTRICT_IMPROVE.name().equals(type);
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
    ModuleDistrictImproveValue value = new ModuleDistrictImproveValue();
    calResult(param, value);
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
    ModuleDistrictImproveValue data = value.getValue(ModuleDistrictImproveValue.class);
    data.setTitle(value.getTitle());
    return DocxUtil.getDocxPathByConditions(template, data);
  }

  /**
   * 计算程序.
   *
   * @param param 参数
   * @param value 模板值
   */
  public void calResult(StatementParam param, ModuleDistrictImproveValue value) {
    List<RoadSectionInfoDto> lastList = dao
        .getRoadSectionList(param.getCycleStartDate(), param.getCycleEndDate(),
            param.getDistrictId());

    for (RoadSectionInfoDto lastDto : lastList) {
      int typeId = lastDto.getTypeId();
      double speed = lastDto.getSpeed();
      double tpi = TpiUtils.speedToTpi(speed, typeId);
      lastDto.setTpi(TpiUtils.getByDigit(tpi, param.getTpiDigit()));
      lastDto.setSpeed(TpiUtils.getByDigit(speed, param.getSpeedDigit()));
    }
    //先筛选出大于6的部分数据，减轻后面循环计算次数和内存消耗
    lastList.removeIf(dto -> dto.getTpi() <= 6);
    List<RoadSectionInfoDto> thisList = dao
        .getRoadSectionList(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    List<RoadSectionChangePo> data = new ArrayList<>();
    for (RoadSectionInfoDto thisDto : thisList) {
      int lastId = thisDto.getId();
      int typeId = thisDto.getTypeId();
      double speed = thisDto.getSpeed();
      double tpi = TpiUtils.speedToTpi(speed, typeId);
      thisDto.setTpi(TpiUtils.getByDigit(tpi, param.getTpiDigit()));
      for (RoadSectionInfoDto lastDto : lastList) {
        int thisId = lastDto.getId();
        if (thisId == lastId) {
          RoadSectionChangePo po = new RoadSectionChangePo();
          po.setDirName(thisDto.getDirName());
          po.setFrom(thisDto.getFrom());
          po.setName(thisDto.getName());
          po.setSpeed(TpiUtils.getByDigit(thisDto.getSpeed(), param.getSpeedDigit()));
          po.setTo(thisDto.getTo());
          po.setTpi(thisDto.getTpi());
          data.add(po);
          double lastTpi = lastDto.getTpi();
          double ratio = TpiUtils.calculateGrowth(lastTpi, tpi, param.getRatioDigit());
          po.setRatio(ratio);
        }
      }
    }
    //筛选并删掉环比小于5%的数据
    data.removeIf(roadSectionChangePo -> roadSectionChangePo.getRatio() >= -5);
    TpiUtils.collectionSortAsc(data);
    for (int i = 0; i < data.size(); i++) {
      RoadSectionChangePo po = data.get(i);
      po.setIndex(i + 1);
    }
    value.setCount(data.size());
    value.setList(data);
  }
}
