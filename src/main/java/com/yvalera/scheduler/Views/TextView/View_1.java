package main.java.com.yvalera.scheduler.Views.TextView;

import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;

import org.joda.time.Interval;
import org.joda.time.LocalDate;


public class View_1 {
	
	public void printView(Schedule sch, Interval interval){

		LocalDate pointer = interval.getStart().toLocalDate();
		
		if(sch.getAbsentDayErrors().size() != 0){
			System.out.println("absentDayErrors:");
			
			for(String s: sch.getAbsentDayErrors()){
				System.out.println("ads: " + s);
			}
			
			System.out.println("Exit");
			return;
		}
		
		if(sch.getTasksErrors().size() != 0){
			System.out.println("TasksErrors:");
			
			for(String s: sch.getTasksErrors()){
				System.out.println("tsk: " + s);
			}
		}
		
		//through all day in interval
		while(interval.contains(pointer.toInterval())){
			//System.out.println("view pointer: " + pointer);
			
			for(int i=0; i<24; i++){
				System.out.println(pointer + ":  for " + i + " to " + (i+1) + 
						 ": " + sch.getPointAt(pointer, i).getTitle());
			}
			
			System.out.println();
			
			pointer = pointer.plusDays(1);
			
			/*try{//to see memory consumption
				Thread.sleep(20000);
			}catch(Exception e){
				
			}*/
		}
		
		System.out.println("total free time: " + sch.getTotalFreeTime());
	}
}
