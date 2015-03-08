package com.yvalera.scheduler.server.BaseUnits.Point;

import java.util.GregorianCalendar;

import org.joda.time.LocalDate;

/*
 * This point doesn't have stricted finish date, but 
 * possibly it have the date? earlier that it can't start 
 * or / and previous points
 */
//TODO Make predecessors
public class FlexibleTermPoint extends AbstractPoint{
	//TODO move it to superclass
	private int necessaryTime;
	private LocalDate	startDate;
	//private List<Point> predecessor;

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public int getNecessaryTime() {
		return necessaryTime;
	}

	public void setNecessaryTime(int necessaryTime) {
		this.necessaryTime = necessaryTime;
	}
}


