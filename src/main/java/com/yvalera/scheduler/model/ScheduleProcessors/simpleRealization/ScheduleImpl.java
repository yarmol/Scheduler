package main.java.com.yvalera.scheduler.model.ScheduleProcessors.simpleRealization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.com.yvalera.scheduler.model.OutInterfaces.Point;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;

import org.joda.time.LocalDate;

/*
 * This class represents schedule for passing
 * to View
 */
class ScheduleImpl implements Schedule{

	private HashMap<LocalDate, CountedDay> days;
	private ArrayList<String> absentDayErrors;
	private ArrayList<String> tasksErrors;
	private int totalFreeTime;
	//private Interval interval;
	//for test only
	//private int times = 0;
	
	ScheduleImpl(/*Interval interval, */HashMap<LocalDate, CountedDay> days, 
			ArrayList<String> absentDayErrors, 
			ArrayList<String> tasksErrors, int totalFreeTime){
		//this.interval = interval;
		this.days = days;
		this.absentDayErrors = absentDayErrors;
		this.tasksErrors = tasksErrors;
		this.totalFreeTime = totalFreeTime;
	}

	@Override
	//???add verification to before / after days???
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
	public List<String> getAbsentDayErrors() {
		return absentDayErrors;
	}

	@Override
	public List<String> getTasksErrors() {
		return tasksErrors;
	}

	@Override
	public int getTotalFreeTime() {
		return totalFreeTime;
	}
	
	/*@Override
	public Interval getInterval() {
		return interval;
	}*/
}
