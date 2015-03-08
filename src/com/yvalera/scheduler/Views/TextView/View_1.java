package com.yvalera.scheduler.Views.TextView;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.yvalera.scheduler.server.OutInterfaces.Schedule;

public class View_1 {
	
	public void printView(Schedule sch){
		Interval interval = sch.getInterval();
		LocalDate pointer = interval.getStart().toLocalDate();
		
		//through all day in interval
		while(interval.contains(pointer.toInterval())){
			System.out.println(pointer);
			
			for(int i=0; i<24; i++){
				System.out.println("for " + i + " to " + (i+1) + 
						 ": " + sch.getPointAt(pointer, i).getTitle());
			}
			
			System.out.println();
			
			pointer = pointer.plusDays(1);
			
			/*try{//to see memory consumption
				Thread.sleep(20000);
			}catch(Exception e){
				
			}*/
		}
	}
}
