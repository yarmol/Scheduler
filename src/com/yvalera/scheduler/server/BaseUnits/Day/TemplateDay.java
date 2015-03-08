package com.yvalera.scheduler.server.BaseUnits.Day;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

/*
 * Object of this class represents Template days with rigid
 * structure
 */
public class TemplateDay extends Day{
	private Interval interval;
	
	//getters and setters
	public Interval getInterval() {
		return interval;
	}
	
	public void setInterval(Interval interval){
		this.interval = interval;
	}
	
	public void setStartDate(LocalDate startDate) {
		long endDate = interval.getEndMillis();
		interval = new Interval(startDate.toDate().getTime(), endDate);
	}
	
	public void setEndDate(LocalDate endDate) {
		long startDate = interval.getStartMillis();
		interval = new Interval(startDate, endDate.toDate().getTime());
	}

	
}
