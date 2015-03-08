package com.yvalera.scheduler.Test.Server.ScheduleProcessor.SimpleRealization;

import java.util.ArrayList;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.yvalera.scheduler.Views.TextView.View_1;
import com.yvalera.scheduler.server.BaseUnits.Day.TemplateDay;
import com.yvalera.scheduler.server.BaseUnits.Point.RigidTimePoint;
import com.yvalera.scheduler.server.BaseUnits.User.User;
import com.yvalera.scheduler.server.OutInterfaces.Schedule;
import com.yvalera.scheduler.server.ScheduleProcessors.ScheduleProcessor;
import com.yvalera.scheduler.server.ScheduleProcessors.simpleRealization.ScheduleProcessorSimpleImpl;

public class EntryPoint {
	private User user = new User();
	private LocalDate startDate = new LocalDate(2000, 1, 1);
	private LocalDate endDate = new LocalDate(2000, 1, 5);
	
	Interval interval =  new Interval(startDate.toDate().getTime(),
			endDate.toDate().getTime());
	
	private void go(){
		fillTemplateDays();
		
		ScheduleProcessor pr = new ScheduleProcessorSimpleImpl();
		Schedule sch = pr.calculateSchedule(user, interval);
		
		View_1 view = new View_1();
		view.printView(sch);
	}
	
	/*
	 * Fills User with templates day
	 */
	private void fillTemplateDays(){
		
		int pointCounter = 1;
		
		//for each day
		for(int i=1; i<8; i++){
						
			TemplateDay tDay = new TemplateDay();
			tDay.setDayOfWeek(i);
			tDay.setInterval(interval);
			
			for(int j=0; j<20; j++){
				//creates unique point
				RigidTimePoint rPoint = new RigidTimePoint();
				rPoint.setTitle("TestPoint_" + pointCounter);
				//System.out.println(rPoint.getTitle());
				rPoint.setDescription("");
				tDay.getRigidPoints()[j] = rPoint;
				pointCounter++;
			}
			
			ArrayList<TemplateDay> days = new ArrayList<TemplateDay>();
			days.add(tDay);
			
			user.getTemplateDays().put(i, days);
		}
	}
	
	public static void main(String[] args) {
		new EntryPoint().go();
	}
}
