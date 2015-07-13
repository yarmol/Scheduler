package Test.Server.ScheduleProcessor.SimpleRealization;

import java.util.ArrayList;








import main.java.com.yvalera.scheduler.model.OutInterfaces.Model;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;
import main.java.com.yvalera.scheduler.model.ScheduleProcessors.simpleRealization.ScheduleProcessorSimpleImpl;
import main.java.com.yvalera.scheduler.model.persistentObjects.Day;
import main.java.com.yvalera.scheduler.model.persistentObjects.User;
import main.java.com.yvalera.scheduler.model.persistentObjects.Task.Task;
import main.java.com.yvalera.scheduler.model.persistentObjects.Task.TypeOfTask;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

//@Component
public class EntryPoint {
	
	//to avoid making more than one limited task with web testing
	//private boolean first = true;
	private int times = 0;
	
	private User user = new User();
	private LocalDate startDate = new LocalDate(2000, 1, 1);
	private LocalDate endDate = new LocalDate(2000, 1, 7);
	
	private LocalDate specialDay = new LocalDate(2000, 1, 3);
	
	Interval interval = new Interval(startDate.toDate().getTime(),
			endDate.toDate().getTime());
	
	public synchronized Schedule getSchedule(){
		fillTemplateDays();
		fillSpecialDays();
		
		//if(first){
			addLimitedTimePoint(++times);
			//first = false;
		//}
		
		addFlexiblePoint();
		makeDaysAndTasksActive();
		
		Model pr = new ScheduleProcessorSimpleImpl();
		
		return pr.calculateSchedule(user, interval);
	}
	
	private void go(){
		
		for(int i=0; i<10; i++){
			fillTemplateDays();
			fillSpecialDays();
			addLimitedTimePoint(i+1);
			addFlexiblePoint();
			makeDaysAndTasksActive();
			
			Model pr = new ScheduleProcessorSimpleImpl();
			Schedule sch = pr.calculateSchedule(user, interval);
			
			View_1 view = new View_1();
			view.printView(sch, interval);
		}
	}
	
	private void addFlexiblePoint(){
		LocalDate startPoint = new LocalDate(2000, 1, 3);
		Task task = new Task();
		task.setTitle("Flexible point");
		task.setNecessaryTime(18);
		task.setStartDate(startPoint);
		task.setType(TypeOfTask.FlexibleTerm);
		user.getTasks().add(task);
	}
	
	private void addLimitedTimePoint(int i){
		LocalDate startPoint = new LocalDate(2000, 1, 2);
		LocalDate endPoint = new LocalDate(2000, 1, 5);
		
		Task task = new Task();
		task.setInterval(new Interval(startPoint.toDate().getTime(),
				endPoint.toDate().getTime()));
		
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
		
		//for each day
		for(int i=1; i<8; i++){
						
			Day tDay = new Day();
			tDay.setDayOfWeek(i);
			tDay.setInterval(interval);
			
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
			
			user.getDays().addAll(days);
		}
	}
	
	private void fillSpecialDays(){
		
		int pointCounter = 1;
						
		Day sDay = new Day();
		sDay.setSpecialDate(specialDay);
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
	
	/*public static void main(String[] args) {
		new EntryPoint().go();
	}*/
	
	//for web testing
	public Interval getInterval(){
		return interval;
	}
}
