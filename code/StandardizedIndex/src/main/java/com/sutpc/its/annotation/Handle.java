package com.sutpc.its.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 处理过程描述.
 *
 * @Author: zuotw
 * @Date: created on 16:06 2020/11/23.
 * @Description
 * @Modified By:
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Handle {

  String value() default "直接查询数据库。";
}
