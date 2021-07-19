package com.sutpc.its.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * .
 *
 * @Author: zuotw
 * @Date: created on 15:55 2020/10/20.
 * @Description
 * @Modified By:
 */
@Configuration
public class TomcatConfig {

  @Bean
  public TomcatServletWebServerFactory webServerFactory() {
    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
    factory.addConnectorCustomizers((Connector connector) -> {
      connector.setProperty("relaxedPathChars", "\"<>[\\]^`{|},");
      connector.setProperty("relaxedQueryChars", "\"<>[\\]^`{|},");
    });
    return factory;
  }
}
