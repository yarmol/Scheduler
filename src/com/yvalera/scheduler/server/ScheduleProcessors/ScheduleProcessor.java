package com.yvalera.scheduler.server.ScheduleProcessors;

import org.joda.time.Interval;

import com.yvalera.scheduler.server.BaseUnits.User.User;
import com.yvalera.scheduler.server.OutInterfaces.Schedule;

/*
 * This interface for classes which will be 
 * calculation schedules
 */
public interface ScheduleProcessor {
	//TODO add DB where user are stored
	public Schedule calculateSchedule(User user,Interval interval);
}
