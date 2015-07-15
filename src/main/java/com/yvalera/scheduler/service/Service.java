package main.java.com.yvalera.scheduler.service;

import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;

import org.joda.time.Interval;

/**
 * Service layer of application. Keeps all logic of application
 * except "buisness logic" which are in the Model implementation.
 */
public interface Service {
	
	/**
	 * Retrieves calculated schedule uses requested interval
	 * of dates and certain user
	 */
	public Schedule getSchedule(Interval interval, long userId);
}