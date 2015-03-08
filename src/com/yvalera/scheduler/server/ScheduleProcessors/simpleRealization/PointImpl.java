package com.yvalera.scheduler.server.ScheduleProcessors.simpleRealization;

import com.yvalera.scheduler.server.OutInterfaces.Point;


/*
 * it necessary for View, this objects will be pass
 * to View from CountedDay
 */
class PointImpl implements Point{
	private String title;
	private String description;
	private String startDay;
	private String endDay;
	
	public PointImpl(String title, String description, String startDay,
			String endDay) {
		this.title = title;
		this.description = description;
		this.startDay = startDay;
		this.endDay = endDay;
	}
	
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public String getStartDay() {
		return "startDay";
	}
	
	@Override
	public String getEndDay() {
		return "endDay";
	}
}
