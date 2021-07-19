package com.sutpc.its.statement;

import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;

/**
 * 报告模块处理程序.
 *
 * @author admin
 * @date 2020/5/19 14:06
 */
public interface ModuleHandler {

  /**
   * 是否支持该模块处理器.
   *
   * @param type 类型
   * @return true or false
   */
  boolean support(String type);

  /**
   * 模块数据程序.
   *
   * @param type 模块类型
   * @param param 参数
   * @return 结果
   */
  ModuleValue handle(String type, StatementParam param);

  /**
   * 根据模板和值生成模块word.
   *
   * @param value 值
   * @param template 模板路径
   * @return 生成的word路径
   */
  String buildWord(ModuleValue value, ModuleTemplate template);

}
