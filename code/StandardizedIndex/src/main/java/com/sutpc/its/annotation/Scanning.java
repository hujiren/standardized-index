package com.sutpc.its.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * 处理过程描述.
 *
 * @Author: zuotw
 * @Date: created on 16:06 2020/11/23.
 * @Description
 * @Modified By:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Scanning {

  @AliasFor(
      annotation = Component.class
  )
  String value() default "";
}
