package com.sutpc.its.statement;

import java.util.UUID;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 生成报告路径.
 *
 * @Author: zuotw
 * @Date: created on 16:13 2020/6/12.
 * @Description
 * @Modified By:
 */
@Component
public class ModuleFileConfig implements EnvironmentAware {

  private static volatile ModuleFileConfig instance = null;

  private String rootPath;

  private Environment environment;

  private ModuleFileConfig() {

  }

  /**
   * 获取实例.
   */
  public static synchronized ModuleFileConfig getInstance() {
    if (instance == null) {
      instance = new ModuleFileConfig();
    }
    return instance;
  }

  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  public String getRootPath() {
    return this.rootPath;
  }

  /**
   * 生成docx地址.
   */
  public String getFilepath() {
    return String.format("%s%s.docx", getRootPath(),
        UUID.randomUUID().toString().replaceAll("-", ""));
  }

  /**
   * 加载实例来赋值.
   */
  @Bean
  public void setInstance() {
    ModuleFileConfig moduleFileConfig = getInstance();
    moduleFileConfig.setRootPath(this.environment.getProperty("tpi.combined.report"));

  }

  @Override
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }
}
