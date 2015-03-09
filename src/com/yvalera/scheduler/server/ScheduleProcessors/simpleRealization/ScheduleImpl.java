package com.yvalera.scheduler.server.ScheduleProcessors.simpleRealization;

import java.util.HashMap;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.yvalera.scheduler.server.OutInterfaces.Point;
import com.yvalera.scheduler.server.OutInterfaces.Schedule;

/*
 * This class represents schedule for passing
 * to View
 */

//TODO make interface for schedule
class ScheduleImpl implements Schedule{
	Interval interval;
	//TODO replace with hashMap for quick returning queried day
	//TODO remove days list getter
	private HashMap<LocalDate, CountedDay> days;
	//for test only
	//private int times = 0;
	
	public ScheduleImpl(Interval interval, HashMap<LocalDate, CountedDay> days) {
		this.interval = interval;
		this.days = days;
	}

	@Override
	//TODO add verification to before / after days
	public Point getPointAt(LocalDate date, int timeInterval){
		//for test only
		//System.out.println("times: " + times);
		/*if(times == 0){
			
			System.out.println("storage point: " + date);
			System.out.println("stored day date: " + days.get(date).getDate());
		}else if(times == 23){
			times = -1;
		}
		
		times ++;*/
		return days.get(date).getPointAt(timeInterval);
		
	}
	
	@Override
	public Interval getInterval() {
		return interval;
	}

}
