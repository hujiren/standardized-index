package com.sutpc.its.statement;

import com.sutpc.its.statement.bean.ModuleTemplate;
import com.sutpc.its.statement.bean.ModuleValue;
import com.sutpc.its.statement.bean.StatementParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 报告模块工厂服务.
 *
 * @author admin
 * @date 2020/5/19 14:06
 */
@Component
public class ModuleFactory {

  @Autowired
  private List<ModuleHandler> handlers;

  /**
   * 获取模块处理器.
   *
   * @param type 类型
   * @return 模块处理器
   */
  public ModuleHandler getHandler(String type) {
    for (ModuleHandler handler : handlers) {
      if (handler.support(type)) {
        return handler;
      }
    }
    return new ModuleHandler() {
      @Override
      public boolean support(String type) {
        return false;
      }

      @Override
      public ModuleValue handle(String type, StatementParam param) {
        ModuleValue value = new ModuleValue();
        value.setType(type);
        value.setFound(false);
        return value;
      }

      @Override
      public String buildWord(ModuleValue value, ModuleTemplate template) {
        return null;
      }
    };

  }
}
