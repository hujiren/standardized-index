package com.sutpc.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.sutpc.datasource.DataSourceContextHolder;


@Aspect
public class ServiceAop {
	 //切换数据源
	 @Before(value = "execution(* com.sutpc.service.Sh1Service.*(..))")
	 public void beforeSh1Service(JoinPoint point) throws Exception {
	     DataSourceContextHolder.setDbType("SH1DATA");
	 }
}
