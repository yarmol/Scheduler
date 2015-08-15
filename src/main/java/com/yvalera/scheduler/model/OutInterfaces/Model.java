package main.java.com.yvalera.scheduler.model.OutInterfaces;


import main.java.com.yvalera.scheduler.model.persistentObjects.User;

import org.joda.time.Interval;


/**
 * This interface for classes which will be 
 * calculate schedules.
 */

public interface Model{
	
	/**
	 * Returns a calculated Schedule
	 */
	public Schedule calculateSchedule(User user,Interval interval);
}
