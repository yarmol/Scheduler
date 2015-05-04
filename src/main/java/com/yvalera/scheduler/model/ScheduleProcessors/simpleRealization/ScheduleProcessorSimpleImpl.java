package main.java.com.yvalera.scheduler.model.ScheduleProcessors.simpleRealization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.com.yvalera.scheduler.model.OutInterfaces.Model;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Point;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;
import main.java.com.yvalera.scheduler.model.ScheduleProcessors.persistentObjects.Day;
import main.java.com.yvalera.scheduler.model.ScheduleProcessors.persistentObjects.User;
import main.java.com.yvalera.scheduler.model.ScheduleProcessors.persistentObjects.Task.Task;
import main.java.com.yvalera.scheduler.model.ScheduleProcessors.persistentObjects.Task.TypeOfTask;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

/*
 * Object of this class calculates and returns schedule. 
 */

public class ScheduleProcessorSimpleImpl implements Model{

	//injects by constructor
	private Interval requestedInterval;
	private User user;
	
	//calculating in class
	private ArrayList<String> absentDayErrors = new ArrayList<String>();
	private ArrayList<String> tasksErrors = new ArrayList<String>();
	private int totalFreeTime;//total free time in requested interval
	
	//result of calculations
	private HashMap<LocalDate, CountedDay> countedDays;
	
	//days from user, which will be used for calculations
	//resets by methods
	private HashMap<LocalDate, Day> necessaryRealDays;
	
	//considering limited terms tasks
	private Interval intervalToCount;//resets by method
	
	//synchronized for model, maybe it is a reason to makes 
	//it prototype
	@Override
	public synchronized Schedule calculateSchedule(User user,
			Interval interval){
	
		//sets variables
		this.user = user;
		requestedInterval = interval;
		
		//clears object variables of prevision calculations
		//clearVariables();
		
		//Calculate necessary interval fo further calculations
		calculateIntervalToCount();
		//System.out.println("interval calculated");
		
		//finds all necessary days from user
		findNecessaryDays();
		//System.out.println("necessary days found");
		
		//if there is not all days, it's no reso to continue calculating
		if(absentDayErrors.size() != 0){
			return new ScheduleImpl(null, absentDayErrors, null, 0);
		}
		
		//makes empty days for calculating schedule
		makeEmptyCountedDays();
		//System.out.println("empty counted days made");
		
		//fills counted days with tasks
		fillsCountedDaysWithRoutine();
		//System.out.println("days filled with routine");
		
		//fills with limited terms tasks
		fillWithLimitedTasks();
		//System.out.println("days filled with limited tasks");

		//fills with flexible term points
		fillWithFlexibleTasks();
		//System.out.println("days filled with limited tasks");
		
		//reduces counted interval to requested
		reduceToRequestedInterval();
		//System.out.println("interval reduced");
		
		//counts free time for requested period
		fillFreeTime();
		System.out.println("Model: OK" + "\n");
		
		return new ScheduleImpl(countedDays, absentDayErrors,
				tasksErrors, totalFreeTime);
	}
	
	/*
	 * Finds all necessary days from user days templates.
	 * If there isn't at least one day for calculations
	 * it writes it in to errors.
	 */
	//TODO Remake it effective
	private void findNecessaryDays(){
		necessaryRealDays = new HashMap<LocalDate, Day>();
		
		LocalDate pointer = intervalToCount.getStart().toLocalDate();
		
		//firstly finds special days
		//while in interval
		while(intervalToCount.contains(pointer.toInterval())){
			for(Day day: user.getDays()){//for all days in array
				//System.out.println("special day loop: " 
				//		+ day.getSpecialDate());
				if(day.isActive() && day.isSpecial()){
					if(day.getSpecialDate().equals(pointer)){
						necessaryRealDays.put(pointer, day);
						break;
					}
				}
			}
			
			pointer = pointer.plusDays(1);
		}
		
		//finds suitable template days
		
		pointer = intervalToCount.getStart().toLocalDate();
		
		//while in interval
		while(intervalToCount.contains(pointer.toInterval())){
			//if this day already filled by special day
			if(necessaryRealDays.get(pointer) == null){
				for(Day day: user.getDays()){//for all days in array
					//System.out.println("template dayOfWeek: " 
					//		+ day.getDayOfWeek());
					if(day.isActive() && !day.isSpecial() && 
							day.getInterval().contains(pointer.toInterval())){
						//if it is necessary day of week
						if(day.getDayOfWeek() == pointer.getDayOfWeek()){
							necessaryRealDays.put(pointer, day);
							break;
						}
					}
				}
			}
					
			pointer = pointer.plusDays(1);

		}
				
		//finds absent day templates
		absentDayErrors.clear();//clears errors from prevision equations
		pointer = intervalToCount.getStart().toLocalDate();
		
		//while in interval
		while(intervalToCount.contains(pointer.toInterval())){
			if(necessaryRealDays.get(pointer) == null){
				String error = "Day on date " + pointer.toString() + 
						" is absent. Create this day for calculating "
						+ "schedule.";
				
				absentDayErrors.add(error);
			}
			
			pointer = pointer.plusDays(1);
		}
	}
	
	/*
	 * Calculates interval to count considers limited
	 * terms tasks. The interval with them must be
	 * calculated to understand is it real to do them
	 * in limited period
	 */
	private void calculateIntervalToCount(){
		intervalToCount = requestedInterval;
		
		for(Task task: user.getTasks()){
			//if task active
			if(task.isActive() && task.getType() == TypeOfTask.LimitedTerm){
				if(task.getInterval().overlaps(requestedInterval)){
					//merge intervals
					
					// Take the earliest of both starting date-times.
				    DateTime start =  intervalToCount.getStart().isBefore(
				    		task.getInterval().getStart() )  ? intervalToCount.
				    				getStart() : task.getInterval().getStart();
				    				
				    // Take the latest of both ending date-times.
				    DateTime end =  intervalToCount.getEnd().isAfter( 
				    		task.getInterval().getEnd() )  ? intervalToCount
				    				.getEnd() : task.getInterval().getEnd();
				    
				    // Instantiate a new Interval from start and end
				    intervalToCount = new Interval( start, end );
				}
			}
		}
	}
	
	/*
	 * Fills Counted days with routine according to
	 * found days
	 */
	private void fillsCountedDaysWithRoutine(){
		LocalDate pointer = intervalToCount.getStart().toLocalDate();
		
		//while in interval
		while(intervalToCount.contains(pointer.toInterval())){
			CountedDay toFill = countedDays.get(pointer);
			Day template = necessaryRealDays.get(pointer);
			
			//!!!all unactive tusk must be deleted from here before!!!
			for(int i=0; i<24; i++){
				if(template.getTasks()[i] != null){
					Point point = new PointImpl(template.getTasks()[i].
							getTitle(), template.getTasks()[i].
							getDescription(), "routine", "");
					
					toFill.setPointAt(point, i);
				}
			}
			
			pointer = pointer.plusDays(1);
		}
	}
	
	
	/*
	 * Reduces counted interval to requested
	 */
	private void reduceToRequestedInterval(){
		LocalDate pointer = intervalToCount.getStart().toLocalDate();
		
		//while in interval
		while(intervalToCount.contains(pointer.toInterval())){
			if(!requestedInterval.contains(pointer.toInterval())){
				countedDays.remove(pointer);
			}
			
			pointer = pointer.plusDays(1);
		}
	}
		
	/*
	 * Fills CountedDays with points with flexible terms
	 */
	//TODO add counts copleted hours/necessary hours in description
	private void fillWithFlexibleTasks(){
		//int totalUnalocatedTime = 0;
		
		//for every Flexible point
		ArrayList<Task> flexibleTasks = new ArrayList<Task>();
				
		for(Task task: user.getTasks()){
			if(task.isActive() && task.getType() == TypeOfTask.FlexibleTerm){
				flexibleTasks.add(task);
			}
		}
		
		//for every task
		for(Task task: flexibleTasks){
			//if there is no task
			if(task == null){
				continue;
			}
			
			//TODO add setter to Point impl and add stop date after
			//time filling if the end of task reached in requested period
			PointImpl p = new PointImpl(task.getTitle(), task.getDescription(),
					task.getStartDate().toString(), "");
			
			Interval desiredInterval = 
					new Interval(task.getStartDate().toDate().
							getTime(), requestedInterval.getEndMillis());
					//IntervalToCount.overlap(
					//point.getStartDate().toInterval());
			
			LocalDate pointer = desiredInterval.getStart().toLocalDate();
			
			int unallocatedTime = task.getNecessaryTime();
			
			//while in interval
			while(desiredInterval.contains(pointer.toInterval())){
				if(unallocatedTime == 0){//point located
					break;
				}
				
				CountedDay cDay = countedDays.get(pointer);
				int freeTime = cDay.getFreeTime();
								
				
				if(freeTime == 0){
					//just go to pointer.plusDays(1);
				}else if(unallocatedTime > freeTime){
					//System.out.println("1: " + freeTime);
					for(int i=0; i<freeTime; i++){
						cDay.addPoint(p);
						unallocatedTime--;
					}
				}else{
					while(unallocatedTime > 0){
						//System.out.println("2: " + freeTime);
						cDay.addPoint(p);
						unallocatedTime--;
					}
				}
				
				pointer = pointer.plusDays(1);
			}
			
			//totalUnalocatedTime += unallocatedTime;
		}
	}
	
	/*
	 * Fills CountedDays with points with limited terms
	 */
	//TODO make quicker algorithm
	private void fillWithLimitedTasks(){
		//for every limited point
		ArrayList<Task> limitedTasks = new ArrayList<Task>();
		
		for(Task task: user.getTasks()){
			if(task.isActive() && task.getType() == TypeOfTask.LimitedTerm){
				limitedTasks.add(task);
			}
		}
		
		for(Task task: limitedTasks){//for every task
			//if there is no point
			if(task == null){
				continue;
			}
			
			PointImpl p = new PointImpl(task.getTitle(), task.getDescription(),
					task.getInterval().getStart().toString(),
					task.getInterval().getEnd().toString());
			
			//counts counted days with free time
			Interval pointInterval = task.getInterval();
			//System.out.println(pointInterval);
			LocalDate pointer = null;

			int unallocatedTime = task.getNecessaryTime();
			
			//fills free time
			//System.out.println(unallocatedTime);
			while(unallocatedTime != 0){//while all the point time isn't located
				//System.out.println("unt: " + unallocatedTime + " " +
				//	(unallocatedTime != 0));
				//System.out.println("here");
				boolean change = false;
				pointer = pointInterval.getStart().toLocalDate();
				
				//trying to fill time evenly for all period of task
				while(pointInterval.contains(pointer.toInterval())){
					//System.out.println("pointer: " + pointer);
					//System.out.println("contains: " + pointInterval.contains(
					//	pointer.toInterval()));
					//System.out.println("counted: " + countedDays.get(
					//	pointer).getDate());
					//System.out.println();
					//break the loop if point is located
					if(unallocatedTime == 0){
						break;
					}
					
					
					//System.out.println(pointer);
					int freeTimeOnDay = countedDays.get(pointer).getFreeTime();
					
					if(freeTimeOnDay == 0){
						pointer = pointer.plusDays(1);//
						continue;//skips busy day 
					}
					
					countedDays.get(pointer).addPoint(p);
					unallocatedTime--;
					//System.out.println("unt: " + unallocatedTime);
					
					//task was pasted at least in one day
					change = true;
					pointer = pointer.plusDays(1);
				}
				
				//that means that it's impossible locate point to exist time
				if(!change){
					String error = "No enought time for " + task.getTitle() 
							+ ". It's necessary " + unallocatedTime  + 
							" hour(s) to" + "complete it.";
					
					tasksErrors.add(error);
				}
			}
		}
	}
	
	/*
	 * prepares new empty counted days
	 */
	private void makeEmptyCountedDays(){
		countedDays = new HashMap<LocalDate, CountedDay>();
		
		LocalDate pointer = intervalToCount.getStart().toLocalDate();
		//System.out.println(pointer);
		//while in interval
		while(intervalToCount.contains(pointer.toInterval())){
			countedDays.put(pointer, new CountedDay(new LocalDate(pointer)));
			pointer = pointer.plusDays(1);
		}
	}
	
	/*
	 * Feels all free time with free time points
	 */
	private void fillFreeTime(){
		totalFreeTime = 0;//total free time in requested interval
		
		PointImpl p = new PointImpl("Free time ", 
				"you can plan something for this time", "", "");
		
		for(LocalDate date: countedDays.keySet()){
			CountedDay day = countedDays.get(date);
			int freeTime = day.getFreeTime();
			
			if(freeTime > 0){
				for(int i=0; i< freeTime; i++){
					day.addPoint(p);
					totalFreeTime++;
				}
			}
		}
	}
}
