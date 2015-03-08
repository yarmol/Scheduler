package com.yvalera.scheduler.server.OutInterfaces;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

public interface Schedule {
	public Point getPointAt(LocalDate date, int timeInterval);
	public Interval getInterval(); 
}
