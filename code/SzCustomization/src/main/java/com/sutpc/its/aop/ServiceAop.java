package com.sutpc.its.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sutpc.its.tools.PeriodUtils;


@Aspect
@Repository("SzTiBizServiceAop")
public class ServiceAop {

	/*
	 * @AfterReturning(returning="rvt", value =
	 * "execution(* com.sutpc.its.sztrafficindex.business.service.EarlyWarningService.*(..))"
	 * ) public void beforeTiService(JoinPoint point,Map<String,Object> rvt)
	 * throws Exception {
	 * 
	 * 
	 * rvt.put("sects", ShiroUtils.getUserEntity()); System.out.println("");
	 * 
	 * }
	 */

	@Before(value = "execution(* com.sutpc.its..service.*.*(..))")
	public void beforeTiBizSysService(JoinPoint point) throws Exception {

		Object[] args = point.getArgs();
		if (args.length == 1) {

			if (args[0] instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) args[0];
				// 统一注入当前日期当前时间片systemCurrentTime
				if (!map.containsKey("time"))
					map.put("time", PeriodUtils.getCurrentDate());
				if (!map.containsKey("period"))
					map.put("period", PeriodUtils.getCurrentPeriod());
				if (!map.containsKey("period15"))
					map.put("period15", (PeriodUtils.getCurrentPeriod() - 1) / 3 + 1);
			}

		}

	}

}
