package com.yvalera.scheduler.server.ScheduleProcessors.simpleRealization;

import java.util.HashMap;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.yvalera.scheduler.server.BaseUnits.Day.Day;
import com.yvalera.scheduler.server.BaseUnits.Day.SpecialDay;
import com.yvalera.scheduler.server.BaseUnits.Day.TemplateDay;
import com.yvalera.scheduler.server.BaseUnits.Point.FlexibleTermPoint;
import com.yvalera.scheduler.server.BaseUnits.Point.LimitedTermPoint;
import com.yvalera.scheduler.server.BaseUnits.Point.RigidTimePoint;
import com.yvalera.scheduler.server.BaseUnits.User.User;
import com.yvalera.scheduler.server.OutInterfaces.Schedule;
import com.yvalera.scheduler.server.ScheduleProcessors.ScheduleProcessor;
/*
 * Object of this class returns schedule
 */

public class ScheduleProcessorSimpleImpl implements ScheduleProcessor{

	private User user;
	private HashMap<LocalDate, CountedDay> countedDays;
	private Interval requestedInterval;
	private Interval IntervalToCount;
	private Interval existInterval;//exist interval in user template Days
	
	@Override
	public Schedule calculateSchedule(User user, Interval interval){
	
		this.user = user;
		requestedInterval = interval;
		
		countExistInterval();
		//System.out.println("here 1");
		//System.out.println(existInterval);
		makeEmptyCountedDays();
		//System.out.println("here 2");
		fillWithTemplateDays();
		//System.out.println("here 3");
		fillWithLimitedPoints();
		//System.out.println("here 4");
		fillWithFlexiblePoints();
		//System.out.println("here 5");
		reduceToRequestedInterval();
		//System.out.println("here 6");
		fillFreeTime();
		//System.out.println("here 7");
		
		return new ScheduleImpl(requestedInterval, countedDays);
	}
	
	/*
	 * Feels all free time with free time points
	 */
	private void fillFreeTime(){
		PointImpl p = new PointImpl("Free time", "you can plan something for this time",
				"", "");
		
		for(LocalDate date: countedDays.keySet()){
			CountedDay day = countedDays.get(date);
			int freeTime = day.getFreeTime();
			
			if(freeTime > 0){
				for(int i=0; i< freeTime; i++){
					day.addPoint(p);
				}
			}
		}
	}
	
	/*
	 * Reduces counted interval to requested
	 */
	private void reduceToRequestedInterval(){
		//TODO make interval To count
		//LocalDate pointer = IntervalToCount.getStart().toLocalDate();
		LocalDate pointer = existInterval.getStart().toLocalDate();
		
		//while in interval
		while(existInterval.contains(pointer.toInterval())){
			if(!requestedInterval.contains(pointer.toInterval())){
				countedDays.remove(pointer);
			}
			
			pointer = pointer.plusDays(1);
		}
	}
		
	/*
	 * Fills CountedDays with points with flexible terms
	 */
	private void fillWithFlexiblePoints(){
		
		//for every Flexible point
		for(FlexibleTermPoint point: user.getFlexPoints()){
			
			//if there is no point
			if(point == null){
				continue;
			}
			
			PointImpl p = new PointImpl(point.getTitle(), point.getDescription(),
					"TODO", "TODO");
			
			Interval desiredInterval = IntervalToCount.overlap(
					point.getStartDate().toInterval());
			
			LocalDate pointer = desiredInterval.getStart().toLocalDate();
			
			int unallocatedTime = point.getNecessaryTime();
			
			//while in interval
			while(desiredInterval.contains(pointer.toInterval())){
				if(unallocatedTime == 0){//point located
					break;
				}
				
				CountedDay cDay = countedDays.get(pointer);
				int freeTime = cDay.getFreeTime();
				
				if(freeTime == 0){//busy day
					continue;
				}
				
				//adds point to day
				if(unallocatedTime >= freeTime){
					for(int i=0; i<freeTime; i++){
						cDay.addPoint(p);
						unallocatedTime--;
					}
				}else{
					for(int i=0; i<unallocatedTime; i++){
						cDay.addPoint(p);
						unallocatedTime--;
					}
				}
			}
		}
	}
	
	/*
	 * Fills CountedDays with points with limited terms
	 */
	//TODO make quicker algorithm
	private boolean fillWithLimitedPoints(){
		//for every limited point
		for(LimitedTermPoint point: user.getLimitedPoints()){
			//if there is no point
			if(point == null){
				continue;
			}
			
			PointImpl p = new PointImpl(point.getTitle(), point.getDescription(),
					"TODO", "TODO");
			//counts counted days with free time
			Interval pointInterval = point.getInterval();
			LocalDate pointer;// = pointInterval.getStart().toLocalDate();
			//it float to avoid cutting fraction part
			/*float numberOfDays = 0;//days with free time
			
			while(pointInterval.contains(pointer.toInterval()));{
				if(countedDays.get(pointer).getFreeTime() != 0){
					numberOfDays ++;
				}
			}
			
			int timePerDay = (int) Math.ceil(point.getNecessaryTime() / numberOfDays);
			
			int transfer = 0;*/
			int unallocatedTime = point.getNecessaryTime();
			
			//TODO make it work quicker
			//fills free time
			while(unallocatedTime != 0){//while all the point time isn't located
				boolean change = false;
				pointer = pointInterval.getStart().toLocalDate();
				//trying to fill time
				while(pointInterval.contains(pointer.toInterval())){
					int freeTimeOnDay = countedDays.get(pointer).getFreeTime();
					
					if(freeTimeOnDay == 0){
						continue;//skips busy day 
					}
					
					countedDays.get(pointer).addPoint(p);
					unallocatedTime--;
					pointer = pointer.plusDays(1);
					change = true;
				}
				
				if(!change){
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	private void makeEmptyCountedDays(){
		countedDays = new HashMap<LocalDate, CountedDay>();
		
		LocalDate pointer = existInterval.getStart().toLocalDate();
		//System.out.println(pointer);
		//while in interval
		while(existInterval.contains(pointer.toInterval())){
			countedDays.put(pointer, new CountedDayImpl());
			pointer = pointer.plusDays(1);
		}
	}
	
	/*
	 * Filling out counted days with points from templateDay and
	 * specialDay
	 */
	private void fillWithTemplateDays(){
		 
		LocalDate pointer = existInterval.getStart().toLocalDate();
		//while in interval
		while(existInterval.contains(pointer.toInterval())){
			CountedDay countedDay = countedDays.get(pointer);
			//System.out.println("here!!");
			//takes certain type of day on pointer
			int dayOfWeek = pointer.getDayOfWeek();
			
			//if there is special day for this date
			if(getSpecialDayForDate(pointer) != null){
				//System.out.println(getSpecialDayForDate(pointer));
				fillCountedDayWithRigidDay(getSpecialDayForDate(pointer), countedDay);
			}else{//if there is not special day for this date
				//System.out.println(getTemplateDayForDate(pointer));
				fillCountedDayWithRigidDay(getTemplateDayForDate(pointer), countedDay);
			}
			
			countedDays.put(pointer, countedDay);
			pointer = pointer.plusDays(1);
		}
	}
	
	/*
	 * Fills CountedDay with Day with refid schedule
	 */
	//TODO Filling out right all the fields
	private void fillCountedDayWithRigidDay(Day rDay, CountedDay cDay){
		for(int i=0; i<24; i++){
			//System.out.println(rDay.getDayOfWeek());
			RigidTimePoint p = rDay.getRigidPoints()[i];
			//System.out.println("here 1");
			//if there is no point
			if(p == null){
				continue;
			}
			
			//System.out.println("here 2");
			
			PointImpl point = new PointImpl(
					p.getTitle(), p.getDescription(),
					"TODO", "TODO");
			
			cDay.setPointAt(point, i);
		}
	}
	
	/*
	 * Returns SpecialDay for certain date
	 */
	private SpecialDay getSpecialDayForDate(LocalDate date){
		//SpecialDay dayToReturn = null;
		
		//takes certain type of day on pointer
		int dayOfWeek = date.getDayOfWeek();
		
		if(user.getSpecialDays().get(dayOfWeek) == null){
			return null;
		}
		
		for(SpecialDay spDay: user.getSpecialDays().get(dayOfWeek)){
			if(spDay.getDate().equals(date)){
				//System.out.println("here");
				return spDay;
			}
		}
		
		return null;
	}
	
	/*
	 * Returns templatedDay for certain date
	 */
	private TemplateDay getTemplateDayForDate(LocalDate date){
		//SpecialDay dayToReturn = null;
		
		//takes certain type of day on pointer
		int dayOfWeek = date.getDayOfWeek();
				
		for(TemplateDay tDay: user.getTemplateDays().get(dayOfWeek)){
			//System.out.println(tDay.getInterval());
			//System.out.println(date.toInterval());
			//System.out.println(tDay.getInterval().overlaps(date.toInterval()));
			if(tDay.getInterval().overlaps(date.toInterval())){
				//System.out.println("here");
				return tDay;
			}
		}
				
		return null;		
	}
	
	/*
	 * Counts exist interval from Template day
	 */
	//TODO add counting of special day which next to template days interval
	private void countExistInterval(){
		//for week day intervals
		HashMap<Integer, Interval> daysIntervals = new HashMap<Integer, Interval>();
		
		//System.out.println(user.getTemplateDays().keySet());
		
		//iteration over hashMap day of week
		for(Integer dayOfWeek: user.getTemplateDays().keySet()){
			//System.out.println(dayOfWeek);
			//iterate over certain template day 
			for(TemplateDay tDay: user.getTemplateDays().get(dayOfWeek)){
				//if daysInterval not keeps curent day
				if(daysIntervals.get(dayOfWeek) == null){
					daysIntervals.put(dayOfWeek, tDay.getInterval());
				}else{//else saves overlap of intervals
					daysIntervals.put(dayOfWeek, tDay.getInterval().
							overlap(daysIntervals.get(dayOfWeek)));
				}
			}
		}
		
		//finding common exist interval for all days
		
		//variable initialisation
		existInterval = daysIntervals.get(1);
		
		//System.out.println(existInterval);
		
		for(int i = 2; i<8; i++){
			existInterval = existInterval.overlap(daysIntervals.get(i));
		}
	}
}
