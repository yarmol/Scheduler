package main.java.com.yvalera.scheduler.service;

import java.util.ArrayList;

import main.java.com.yvalera.scheduler.Test.Server.ScheduleProcessor.SimpleRealization.EntryPoint;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleServiceImpl implements ScheduleService{

	@Autowired
	private EntryPoint entry;
	
	@Override
	public ArrayList<String> getDaysSchedule() {
		Schedule sch = entry.getSchedule();
		Interval interval = entry.getInterval();
		LocalDate pointer = interval.getStart().toLocalDate();
		ArrayList<String> list = new ArrayList<String>();
		
		if(sch.getAbsentDayErrors().size() != 0){
			list.add("absentDayErrors:");
			
			for(String s: sch.getAbsentDayErrors()){
				list.add("ads: " + s);
			}
			
			list.add("Exit");
			return list;
		}
		
		if(sch.getTasksErrors().size() != 0){
			list.add("TasksErrors:");
			
			for(String s: sch.getTasksErrors()){
				list.add("tsk: " + s);
			}
		}
		
		//through all day in interval
		while(interval.contains(pointer.toInterval())){
					
			for(int i=0; i<24; i++){
				list.add(pointer + ":  for " + i + " to " + (i+1) + 
						 ": " + sch.getPointAt(pointer, i).getTitle());
			}	
					
			pointer = pointer.plusDays(1);
			
			list.add("\n");
		}
				
		list.add("total free time: " + sch.getTotalFreeTime());
		
		return list;
	}

}
