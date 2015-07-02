package main.java.com.yvalera.scheduler.model.OutInterfaces;

import java.util.List;

import org.joda.time.LocalDate;

/**
 * This interface represents schedule for passing
 * to View. It consist of the hourly schedule
 * on requested time period.
 * 
 * If in the calculation were not all days
 * any invoke getPointAt() returns null and
 * getTasksErrors() returns null too;
 */
public interface Schedule {
	
	/**
	 * @param date for this date Point will be returned
	 * @param timeInterval for this time point will be returned
	 * @return Returns Point on pointed date and pointed interval
	 * (for now from 0 to 23 without minutes)
	 */
	public Point getPointAt(LocalDate date, int timeInterval);
	//public Interval getInterval();
	
	/*
	 * TODO Think how to should be Schedule if it
	 * will be having errors. Displayed or no?
	 */

	public List<String> getAbsentDayErrors();
	public List<String> getTasksErrors();
	public int getTotalFreeTime();
}
