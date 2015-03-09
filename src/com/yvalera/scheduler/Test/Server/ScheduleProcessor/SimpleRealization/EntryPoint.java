package com.yvalera.scheduler.Test.Server.ScheduleProcessor.SimpleRealization;

import java.util.ArrayList;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.yvalera.scheduler.Views.TextView.View_1;
import com.yvalera.scheduler.server.BaseUnits.Day.SpecialDay;
import com.yvalera.scheduler.server.BaseUnits.Day.TemplateDay;
import com.yvalera.scheduler.server.BaseUnits.Point.FlexibleTermPoint;
import com.yvalera.scheduler.server.BaseUnits.Point.LimitedTermPoint;
import com.yvalera.scheduler.server.BaseUnits.Point.RigidTimePoint;
import com.yvalera.scheduler.server.BaseUnits.User.User;
import com.yvalera.scheduler.server.OutInterfaces.Schedule;
import com.yvalera.scheduler.server.ScheduleProcessors.ScheduleProcessor;
import com.yvalera.scheduler.server.ScheduleProcessors.simpleRealization.ScheduleProcessorSimpleImpl;

public class EntryPoint {
	private User user = new User();
	private LocalDate startDate = new LocalDate(2000, 1, 1);
	private LocalDate endDate = new LocalDate(2000, 1, 7);
	
	private LocalDate specialDay = new LocalDate(2000, 1, 3);
	
	Interval interval =  new Interval(startDate.toDate().getTime(),
			endDate.toDate().getTime());
	
	private void go(){
		fillTemplateDays();
		fillSpecialDays();
		addLimitedTimePoint();
		addFlexiblePoint();
		
		ScheduleProcessor pr = new ScheduleProcessorSimpleImpl();
		Schedule sch = pr.calculateSchedule(user, interval);
		
		View_1 view = new View_1();
		view.printView(sch);
	}
	
	private void addFlexiblePoint(){
		LocalDate startPoint = new LocalDate(2000, 1, 3);
		FlexibleTermPoint point = new FlexibleTermPoint();
		point.setTitle("Flexible point");
		point.setNecessaryTime(18);
		point.setStartDate(startPoint);
		user.getFlexPoints().add(point);
	}
	
	private void addLimitedTimePoint(){
		LocalDate startPoint = new LocalDate(2000, 1, 2);
		LocalDate endPoint = new LocalDate(2000, 1, 5);
		
		
		LimitedTermPoint point = new LimitedTermPoint();
		point.setInterval(new Interval(startPoint.toDate().getTime(),
				endPoint.toDate().getTime()));
		
		point.setTitle("Limited terms point_1");
		point.setNecessaryTime(7);
		user.getLimitedPoints().add(point);
		
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
	
	private void fillSpecialDays(){
		
		int pointCounter = 1;
						
		SpecialDay sDay = new SpecialDay();
		sDay.setDate(specialDay);
			
		for(int j=12; j<24; j++){
			//creates unique point
			RigidTimePoint rPoint = new RigidTimePoint();
			rPoint.setTitle("SpecialPoint_" + pointCounter);
			//System.out.println(rPoint.getTitle());
			rPoint.setDescription("");
			sDay.getRigidPoints()[j] = rPoint;
			pointCounter++;
		}
			
		user.getSpecialDays().put(specialDay, sDay);
	}
	
	public static void main(String[] args) {
		new EntryPoint().go();
	}
}
