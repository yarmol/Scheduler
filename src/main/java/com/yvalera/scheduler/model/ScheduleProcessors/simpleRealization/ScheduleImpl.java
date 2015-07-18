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
	private ArrayList<String> tasksErrors;
	private ArrayList<String> tasksNames;
	private int totalFreeTime;
	
	ScheduleImpl(HashMap<LocalDate,	CountedDay> days,  
				ArrayList<String> tasksErrors,
				int totalFreeTime, 
				ArrayList<String> tasksNames){
		//this.interval = interval;
		this.days = days;
		this.tasksErrors = tasksErrors;
		this.totalFreeTime = totalFreeTime;
		this.tasksNames = tasksNames;
	}

	@Override
	public Point getPointAt(LocalDate date, int timeInterval){

		return days.get(date).getPointAt(timeInterval);
	}

	@Override
	public List<String> getTasksErrors() {
		return tasksErrors;
	}

	@Override
	public int getTotalFreeTime() {
		return totalFreeTime;
	}

	@Override
	public ArrayList<String> getTasksNames() {
		// TODO Auto-generated method stub
		return tasksNames;
	}
}
