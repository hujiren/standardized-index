package com.sutpc.its.statement;

import com.sutpc.its.dao.IModuleDistrictWorseDao;
import com.sutpc.its.dto.RoadSectionInfoDto;
import com.sutpc.its.po.RoadSectionChangePo;
import com.sutpc.its.statement.bean.ModuleDistrictWorseValue;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:32 2020/6/2.
 * @Description
 * @Modified By:
 */
@Component
@Service
public class ModuleDistrictWorseHandler implements ModuleHandler {

  @Autowired
  private IModuleDistrictWorseDao dao;

  /**
   * 是否支持该模块处理器.
   *
   * @param type 类型
   * @return true or false
   */
  @Override
  public boolean support(String type) {
    return ModuleEnum.MODULE_DISTRICT_WORSE.name().equals(type);
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
    ModuleDistrictWorseValue value = new ModuleDistrictWorseValue();
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
    ModuleDistrictWorseValue data = value.getValue(ModuleDistrictWorseValue.class);
    data.setTitle(value.getTitle());
    return DocxUtil.getDocxPathByConditions(template, data);
  }

  /**
   * 计算程序.
   *
   * @param param 参数
   * @param value 模板值
   */
  public void calResult(StatementParam param, ModuleDistrictWorseValue value) {
    //本期列表
    List<RoadSectionInfoDto> thisList = dao
        .getRoadSectionList(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    for (RoadSectionInfoDto thisDto : thisList) {
      int typeId = thisDto.getTypeId();
      double speed = thisDto.getSpeed();
      double tpi = TpiUtils.speedToTpi(speed, typeId);
      thisDto.setTpi(TpiUtils.getByDigit(tpi, param.getTpiDigit()));
      thisDto.setSpeed(TpiUtils.getByDigit(speed, param.getSpeedDigit()));
    }
    //先筛选出大于6的部分数据，减轻后面循环计算次数
    thisList.removeIf(roadSectionInfoDto -> roadSectionInfoDto.getTpi() <= 6);
    List<RoadSectionInfoDto> lastList = dao
        .getRoadSectionList(param.getCycleStartDate(), param.getCycleEndDate(),
            param.getDistrictId());
    List<RoadSectionChangePo> data = new ArrayList<>();
    for (RoadSectionInfoDto lastDto : lastList) {
      int lastId = lastDto.getId();
      int typeId = lastDto.getTypeId();
      double speed = lastDto.getSpeed();
      double tpi = TpiUtils.speedToTpi(speed, typeId);
      lastDto.setTpi(TpiUtils.getByDigit(tpi, param.getTpiDigit()));
      for (RoadSectionInfoDto thisDto : thisList) {
        int thisId = thisDto.getId();
        if (thisId == lastId) {
          RoadSectionChangePo po = new RoadSectionChangePo();
          BeanUtils.copyProperties(thisDto, po);
          data.add(po);
          double thisTpi = thisDto.getTpi();
          double ratio = TpiUtils.calculateGrowth(tpi, thisTpi, param.getRatioDigit());
          po.setRatio(ratio);
        }
      }
    }
    //筛选并删掉环比小于5%的数据
    data.removeIf(roadSectionChangePo -> roadSectionChangePo.getRatio() < 5);
    TpiUtils.collectionSortDesc(data);
    for (int i = 0; i < data.size(); i++) {
      RoadSectionChangePo po = data.get(i);
      po.setIndex(i + 1);
    }
    value.setCount(data.size());
    value.setList(data);
  }
}
