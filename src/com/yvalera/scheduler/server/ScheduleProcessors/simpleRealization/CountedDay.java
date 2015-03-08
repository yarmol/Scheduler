package com.yvalera.scheduler.server.ScheduleProcessors.simpleRealization;

import org.joda.time.LocalDate;

import com.yvalera.scheduler.server.BaseUnits.Point.RigidTimePoint;
import com.yvalera.scheduler.server.OutInterfaces.Point;

/*
 * It represents universal day for schedule;
 */

interface CountedDay {
	public LocalDate getDate();
	public Point getPointAt(int timeInterval);
	public int getFreeTime();
	//TODO Remove this method to another interface
	public void addPoint(PointImpl point);
	public void setPointAt(Point point, int timeInterval);
}
