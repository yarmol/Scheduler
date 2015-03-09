package com.yvalera.scheduler.server.ScheduleProcessors.simpleRealization;

import org.joda.time.LocalDate;

import com.yvalera.scheduler.server.BaseUnits.Point.RigidTimePoint;
import com.yvalera.scheduler.server.OutInterfaces.Point;

/*
 * Objects of this class keeps schedule on 
 * concrete day
 */
class CountedDayImpl implements CountedDay{
	private Point[] points = new PointImpl[24];
	private LocalDate date;
	
	public CountedDayImpl(LocalDate date) {
		this.date = date;
	}
	
	@Override
	public LocalDate getDate() {
		return date;
	}

	@Override
	public Point getPointAt(int timeInterval) {
		return  points[timeInterval];
	}

	@Override
	public int getFreeTime() {
		int freeTime = 0;
		
		for(Point p: points){
			if(p == null){
				freeTime++;
			}
		}
		
		return freeTime;
	}

	/*
	 * adds Point to counted day on free place
	 */
	@Override
	public void addPoint(PointImpl point) {
		for(int i=0; i < points.length; i++){
			if(points[i] == null){
				points[i] = point;
				break;
			}
		}
	}

	@Override
	public void setPointAt(Point point, int timeInterval) {
		points[timeInterval] = point;
	}
}
