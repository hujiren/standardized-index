package com.sutpc.its;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * . 必须添加这个扫包范围,否则 @SysUserLog(description="") 该注解会失效
 */
@ComponentScan("com.sutpc")
@SpringBootApplication(scanBasePackages = {"com.sutpc"})
@EnableScheduling
@EnableAsync
@ServletComponentScan
public class Application {

  /**
   * main.
   */
  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    //ClassName cn = context.getBean(ClassName.class)
  }

}
