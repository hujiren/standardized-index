package com.sutpc.its.statement;

import com.sutpc.its.dao.IModuleDistrictHotSpotDao;
import com.sutpc.its.dto.HotpotInfoDto;
import com.sutpc.its.statement.bean.ModuleDistrictHotpotBaseValue;
import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.tools.DocxUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 热点-医院-信息处理器.
 *
 * @Author: zuotw
 * @Date: created on 10:36 2020/6/1.
 * @Description
 * @Modified By:
 */
@Component
@Service
public class ModuleDistrictHospitalHandler implements ModuleHandler {

  @Autowired
  private IModuleDistrictHotSpotDao dao;

  /**
   * 是否支持该模块处理器.
   *
   * @param type 类型
   * @return true or false
   */
  @Override
  public boolean support(String type) {
    return ModuleEnum.MODULE_DISTRICT_HOSPITAL.name().equals(type);
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
    ModuleDistrictHotpotBaseValue value = new ModuleDistrictHotpotBaseValue();
    calHospitalRatio(param, value);
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
    ModuleDistrictHotpotBaseValue data = value.getValue(ModuleDistrictHotpotBaseValue.class);
    data.setTitle(value.getTitle());
    return DocxUtil.getDocxPathByConditions(template, data);
  }

  /**
   * 计算医院环比等.
   */
  public void calHospitalRatio(StatementParam param, ModuleDistrictHotpotBaseValue value) {
    List<HotpotInfoDto> thisList = dao
        .getHospitalInfo(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    List<HotpotInfoDto> lastList = dao
        .getHospitalInfo(param.getCycleStartDate(), param.getCycleEndDate(),
            param.getDistrictId());
    CalIndexUtils.calHotpotRatio(thisList, lastList, value, param);
  }
}
