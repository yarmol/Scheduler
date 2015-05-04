package main.java.com.yvalera.scheduler.model.OutInterfaces;

import main.java.com.yvalera.scheduler.model.ScheduleProcessors.persistentObjects.User;

import org.joda.time.Interval;


/*
 * This interface for classes which will be 
 * calculation schedules.
 */

public interface Model{
	public Schedule calculateSchedule(User user,Interval interval);
}
