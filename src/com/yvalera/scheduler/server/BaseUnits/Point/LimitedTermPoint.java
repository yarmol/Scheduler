package com.yvalera.scheduler.server.BaseUnits.Point;

import java.util.GregorianCalendar;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

/*
 * This point must be start and end on defined term
 */
public class LimitedTermPoint extends AbstractPoint {
	private Interval interval;
	private int necessaryTime;

	public void setStartDate(LocalDate startDate) {
		long endDate = interval.getEndMillis();
		interval = new Interval(startDate.toDate().getTime(), endDate);
	}
	
	public void setEndDate(LocalDate endDate) {
		long startDate = interval.getStartMillis();
		interval = new Interval(startDate, endDate.toDate().getTime());
	}

	public Interval getInterval(){
		return interval;
	}

	public int getNecessaryTime() {
		return necessaryTime;
	}

	public void setNecessaryTime(int necessaryTime) {
		this.necessaryTime = necessaryTime;
	}
}
