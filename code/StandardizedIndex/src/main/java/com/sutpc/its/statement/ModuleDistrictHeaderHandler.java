package com.sutpc.its.statement;

import com.sutpc.its.dao.IModuleDistrictHeadFootDao;
import com.sutpc.its.statement.bean.ModuleDistrictHeadValue;
import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import com.sutpc.its.tools.DateUtils;
import com.sutpc.its.tools.DocxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 10:25 2020/6/17.
 * @Description
 * @Modified By:
 */
@Component
@Service
public class ModuleDistrictHeaderHandler implements ModuleHandler {

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
    return ModuleEnum.MODULE_DISTRICT_HEAD.name().equals(type);
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
    ModuleDistrictHeadValue value = new ModuleDistrictHeadValue();
    String districtName = dao.getDistrictNameById(param.getDistrictId());
    String nameSub = districtName.substring(0, districtName.length() - 1);
    String dateStr = DateUtils.getFirstDayOfNextMonth(String.valueOf(param.getCurrentStartDate()),
        "yyyyMMdd");//DateUtils.monthStartStr(0);
    String resultDate =
        dateStr.substring(0, 4) + "年" + dateStr.substring(4, 6) + "月" + dateStr.substring(6, 8)
            + "日";
    value.setDistrictName(districtName);
    value.setNameSub(nameSub);
    value.setResultDate(resultDate);
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
    ModuleDistrictHeadValue data = value.getValue(ModuleDistrictHeadValue.class);
    return DocxUtil.getDocxPathByConditions(template, data);
  }
}
