package com.sutpc.its.statement;

import com.sutpc.its.dao.IModuleDistrictHeadFootDao;
import com.sutpc.its.statement.bean.ModuleDistrictFootValue;
import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.tools.DocxUtil;
import com.sutpc.its.tools.TpiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 9:58 2020/6/17.
 * @Description
 * @Modified By:
 */
@Component
@Service
public class ModuleDistrictFootHandler implements ModuleHandler {

  @Autowired
  private IModuleDistrictHeadFootDao dao;

  /**
   * 是否支持该模块处理器.
   *
   * @param type 类型
   * @return true or false
   */
  @Override
  public boolean support(String type) {
    return ModuleEnum.MODULE_DISTRICT_FOOT.name().equals(type);
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
    ModuleDistrictFootValue value = new ModuleDistrictFootValue();
    String districtName = dao.getDistrictNameById(param.getDistrictId());
    value.setDistrictName(districtName);
    String dateCalculated = TpiUtils
        .getDateCalculated(param.getCurrentStartDate(), param.getCurrentEndDate());
    value.setDateCalculated(dateCalculated);
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
    ModuleDistrictFootValue data = value.getValue(ModuleDistrictFootValue.class);
    return DocxUtil.getDocxPathByConditions(template, data);
  }
}
