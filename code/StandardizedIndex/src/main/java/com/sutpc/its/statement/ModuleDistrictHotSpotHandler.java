package com.sutpc.its.statement;

import com.sutpc.its.dao.IModuleDistrictHotSpotDao;
import com.sutpc.its.po.PeakHotPotPo;
import com.sutpc.its.statement.bean.ModuleDistrictHotSpotValue;
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
 * @Date: created on 17:28 2020/5/29.
 * @Description
 * @Modified By:
 */
@Component
@Service
public class ModuleDistrictHotSpotHandler implements ModuleHandler {

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
    return ModuleEnum.MODULE_DISTRICT_HOT_SPOT.name().equals(type);
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
    ModuleDistrictHotSpotValue value = new ModuleDistrictHotSpotValue();
    calHotSpotRatio(param, value);
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
    ModuleDistrictHotSpotValue data = value.getValue(ModuleDistrictHotSpotValue.class);
    data.setTitle(value.getTitle());
    return DocxUtil.getDocxPathByConditions(template, data);
  }

  /**
   * 计算环比等.
   */
  public void calHotSpotRatio(StatementParam param, ModuleDistrictHotSpotValue value) {
    //高峰最高指数相关信息
    PeakHotPotPo thisMaxPo = dao
        .getPeakMaxHotSpotTpi(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    double thisMaxTpi = thisMaxPo.getTpi();
    value.setMaxTpi(TpiUtils.getByDigit(thisMaxTpi, param.getTpiDigit()));
    value.setMaxTpiHot(thisMaxPo.getPoiName());

    //高峰最低指数相关信息
    PeakHotPotPo thisMinPo = dao
        .getPeakMinHotSpotTpi(param.getCurrentStartDate(), param.getCurrentEndDate(),
            param.getDistrictId());
    double thisMinTpi = thisMinPo.getTpi();
    value.setMinTpi(TpiUtils.getByDigit(thisMinTpi, param.getTpiDigit()));
    value.setMinTpiHot(thisMinPo.getPoiName());

    PeakHotPotPo lastMaxPo = dao
        .getPeakMaxHotSpotTpi(param.getCycleStartDate(), param.getCycleEndDate(),
            param.getDistrictId());
    double lastMaxTpi = lastMaxPo.getTpi();

    PeakHotPotPo lastMinPo = dao
        .getPeakMinHotSpotTpi(param.getCycleStartDate(), param.getCycleEndDate(),
            param.getDistrictId());
    double lastMinTpi = lastMinPo.getTpi();

    double maxRatio = TpiUtils.calculateGrowth(lastMaxTpi, thisMaxTpi, param.getRatioDigit());
    value.setMaxTpiHotRatio(maxRatio);

    double minRatio = TpiUtils.calculateGrowth(lastMinTpi, thisMinTpi, param.getRatioDigit());
    value.setMinTpiHotRatio(minRatio);
  }
}
