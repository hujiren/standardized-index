package com.sutpc.its;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = {"com.sutpc"})
@EnableScheduling
@EnableAsync
@EnableEurekaClient
public class PhonePdApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(PhonePdApplication.class, args);

    //ClassName cn = context.getBean(ClassName.class)
  }

}
