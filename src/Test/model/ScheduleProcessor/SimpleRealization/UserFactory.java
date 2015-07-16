package Test.model.ScheduleProcessor.SimpleRealization;

import java.util.ArrayList;

import main.java.com.yvalera.scheduler.model.persistentObjects.Day;
import main.java.com.yvalera.scheduler.model.persistentObjects.User;
import main.java.com.yvalera.scheduler.model.persistentObjects.Task.Task;
import main.java.com.yvalera.scheduler.model.persistentObjects.Task.TypeOfTask;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

public class UserFactory {
	
	private static UserFactory instance;
	
	private User user;
	private Interval fillRoutineDaysInterval;
	private Interval limitedTaskInterval;
	private LocalDate specialDayDate;
	private LocalDate flexibleTermPointStart;
	
	//returns Users from one instance of UserFactory
	public static synchronized User getUser(Interval fillRoutineDaysInterval, 
			Interval limitedTaskInterval, LocalDate specialDayDate,
			LocalDate limitTermPointStart){
		
		if(instance == null){
			instance = new UserFactory();
		}
		
		return instance.createUser(fillRoutineDaysInterval, 
				limitedTaskInterval, specialDayDate,
				limitTermPointStart);
	}
	
	//hidden constructor by default
	private UserFactory(){
	}
	
	private User createUser(Interval fillRoutineDaysInterval, 
			Interval limitedTaskInterval, LocalDate specialDayDate,
			LocalDate flexibleTermPointStart){
		
		user = new User();
		
		this.fillRoutineDaysInterval = fillRoutineDaysInterval;
		this.limitedTaskInterval = limitedTaskInterval;
		this.specialDayDate = specialDayDate;
		this.flexibleTermPointStart = flexibleTermPointStart;
		
		
		fillTemplateDays();
		fillSpecialDays();
		addLimitedTimePoint(1);//number of taks
		addFlexiblePoint();
		makeDaysAndTasksActive();
		
		return user;
	}
	
	private void addFlexiblePoint(){

		Task task = new Task();
		task.setTitle("Flexible point");
		task.setNecessaryTime(18);
		task.setStartDate(flexibleTermPointStart);
		task.setType(TypeOfTask.FlexibleTerm);
		user.getTasks().add(task);
	}
	
	private void addLimitedTimePoint(int i){
		
		Task task = new Task();
		task.setInterval(limitedTaskInterval);
		
		task.setTitle("Limited terms point_" + i);
		task.setNecessaryTime(7);
		task.setType(TypeOfTask.LimitedTerm);
		user.getTasks().add(task);
	}
	
	/*
	 * Fills User with templates day
	 */
	private void fillTemplateDays(){
		
		int pointCounter = 1;
		
		//for each from monday to sunday a week
		for(int i=1; i<8; i++){
						
			Day tDay = new Day();
			tDay.setDayOfWeek(i);
			tDay.setInterval(fillRoutineDaysInterval);
			
			for(int j=0; j<20; j++){
				//creates unique point
				Task rTask = new Task();
				rTask.setTitle("RoutinePoint_" + pointCounter);
				//System.out.println(rPoint.getTitle());
				rTask.setDescription("");
				rTask.setType(TypeOfTask.Routine);
				tDay.getTasks()[j] = rTask;
				pointCounter++;
			}
			
			ArrayList<Day> days = new ArrayList<Day>();
			days.add(tDay);
			
			//adds generated days to User
			user.getDays().addAll(days);
		}
	}
	
	private void fillSpecialDays(){
		
		int pointCounter = 1;
						
		Day sDay = new Day();
		sDay.setSpecialDate(specialDayDate);
		sDay.setSpecial(true);
			
		for(int j=12; j<24; j++){
			//creates unique point
			Task rTask = new Task();
			rTask.setTitle("SpecialDayPoint_" + pointCounter);
			//System.out.println(rPoint.getTitle());
			rTask.setDescription("");
			rTask.setType(TypeOfTask.Routine);
			sDay.getTasks()[j] = rTask;
			pointCounter++;
		}
			
		user.getDays().add(sDay);
	}
	
	//because they are turned off by default
	private void makeDaysAndTasksActive(){
		for(Day d: user.getDays()){
			d.setActive(true);
		}
		
		for(Task t: user.getTasks()){
			t.setActive(true);
		}
	}
}
