package main.java.com.yvalera.scheduler.dao;

import java.util.ArrayList;

import main.java.com.yvalera.scheduler.model.OutInterfaces.Model;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;
import main.java.com.yvalera.scheduler.model.ScheduleProcessors.simpleRealization.ScheduleProcessorSimpleImpl;
import main.java.com.yvalera.scheduler.model.persistentObjects.Day;
import main.java.com.yvalera.scheduler.model.persistentObjects.User;
import main.java.com.yvalera.scheduler.model.persistentObjects.Task.Task;
import main.java.com.yvalera.scheduler.model.persistentObjects.Task.TypeOfTask;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Test.Server.ScheduleProcessor.SimpleRealization.View_1;

/*
 * This is the stub which always returns the same object
 */
@Repository
public class DaoStubImpl implements Dao{

	@Autowired
	private SessionFactory sessionFactory; 
	
	//temporary part for create user without DB request
	private User user = new User();
	private LocalDate startDate = new LocalDate(2000, 1, 1);
	private LocalDate endDate = new LocalDate(2000, 1, 7);
	
	private LocalDate specialDay = new LocalDate(2000, 1, 3);
	
	Interval interval = new Interval(startDate.toDate().getTime(),
			endDate.toDate().getTime());
	
	@Override
	public User getUserById(long id, Session session) {
		
		go();
		
		return user;
	}
	
	private void go(){
		
		//for(int i=0; i<10; i++){
			fillTemplateDays();
			fillSpecialDays();
			addLimitedTimePoint(/*i+*/1);//uncoment for "for loop"
			addFlexiblePoint();
			makeDaysAndTasksActive();
			
			Model pr = new ScheduleProcessorSimpleImpl();
			Schedule sch = pr.calculateSchedule(user, interval);
			
			//View_1 view = new View_1();
			//view.printView(sch, interval);
		//}
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
	 * Fills User with templates day for one week
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

	@Override
	public Session getSession() {
		// TODO Auto-generated method stub
		return sessionFactory.openSession();
	}
	
}
