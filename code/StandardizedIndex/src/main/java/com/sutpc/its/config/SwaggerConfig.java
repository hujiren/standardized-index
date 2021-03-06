package com.sutpc.its.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  /*private static final Contact DEFAULT_CONTACT = new Contact(
      "zhengyl", "http://jira.sutpc.org/secure/ViewProfile.jspa", "zhengyl@sutpc.com");

  private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
      "Accident API", "福田中心区API", "1.0",
      "urn:tos", DEFAULT_CONTACT,
      "[JIRA] SZACCDT", "http://jira.sutpc.org/projects/", Collections.emptyList());*/

  private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
      new HashSet<>(Collections.singletonList("application/json"));

  /**
   * Config swagger api.
   *
   * @return docket
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any())
        .build()
        //.apiInfo(DEFAULT_API_INFO)
        .produces(DEFAULT_PRODUCES_AND_CONSUMES)
        .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
        //.forCodeGeneration(true)
        ;
  }
}
