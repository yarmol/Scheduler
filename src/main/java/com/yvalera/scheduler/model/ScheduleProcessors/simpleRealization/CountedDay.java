package main.java.com.yvalera.scheduler.model.ScheduleProcessors.simpleRealization;

import main.java.com.yvalera.scheduler.model.OutInterfaces.Point;

import org.joda.time.LocalDate;

/*
 * Objects of this class keeps schedule on 
 * concrete day
 */
class CountedDay{
	private Point[] points = new PointImpl[24];
	private LocalDate date;
	
	public CountedDay(LocalDate date) {
		this.date = date;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public Point getPointAt(int timeInterval) {
		return  points[timeInterval];
	}

	public int getFreeTime() {
		int freeTime = 0;
		
		for(Point p: points){
			if(p == null){
				freeTime++;
			}
		}
		
		return freeTime;
	}

	//adds Task on specific hour
	public void addPoint(PointImpl point) {
		for(int i=0; i < points.length; i++){
			if(points[i] == null){
				points[i] = point;
				break;
			}
		}
	}

	public void setPointAt(Point point, int timeInterval) {
		points[timeInterval] = point;
	}
}
