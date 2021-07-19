package com.sutpc.its.timertask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DemoTimerTask {
	
	//每分钟执行一次
	@Scheduled(cron="0 */1 * * * ?")
	public void demoExec() {
		//System.out.print(1);
	}
}
