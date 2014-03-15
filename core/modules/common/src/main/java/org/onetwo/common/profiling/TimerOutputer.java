package org.onetwo.common.profiling;

import java.util.Date;

import org.onetwo.common.utils.DateUtil;


public class TimerOutputer implements TimeLogger {
	
	@Override
	public void log(String msg){
		System.out.println("["+DateUtil.formatDateTime(new Date())+"]: "+msg);
	}

}
