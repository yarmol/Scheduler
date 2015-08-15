package main.java.com.yvalera.scheduler.service.auxiliary_objects;

import main.java.com.yvalera.scheduler.model.persistentObjects.User;
import main.java.com.yvalera.scheduler.model.persistentObjects.Task.Task;
import main.java.com.yvalera.scheduler.model.persistentObjects.Task.TypeOfTask;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

/**
 * It generates new user with given parameters.
 */
public class UserFactory {
	
	private static UserFactory instance;

	//hidden constructor by default
	private UserFactory(){
	}
	
	/**
	 * Its creates new User object fith filled -2 monts 
	 * backward and 1 year forward filled days, to make
	 * possible show new User in view. Thread safe.
	 */
	public static synchronized User createStandartNewUser(String userName){
		
		if(instance == null){
			instance = new UserFactory();
		}
		
		LocalDate startDate = new LocalDate().minusMonths(2);
		LocalDate endDate = new LocalDate().plusYears(1);
		
		Interval interval = new Interval(startDate.toDate().getTime()
				, endDate.toDate().getTime());
		
		User user =  instance.createUserWithRoutine(interval);
		
		user.setName(userName);
		
		return user;
	}

	/*
	 * Creates new user, then creates simple routine tasks
	 * and fills user with them 
	 */
	private User createUserWithRoutine(Interval interval) {
		
		User user = new User();
		
		Task sleep = new Task();
		
		user.getTasks().add(sleep);
		
		sleep.setTitle("sleeping");
		sleep.setInterval(interval);
		sleep.setType(TypeOfTask.Routine);
		sleep.setNecessaryTime(1);
		
		for(int i = 1; i < 8; i++){
			sleep.setActiveDayAt(i, true);
		}
		
		for(int j = 22; j < 24; j++){
			sleep.setActiveHourAt(j, true);
		}
		
		for(int j = 0; j < 6; j++){
			sleep.setActiveHourAt(j, true);
		}
		
		makeTasksActive(user);
		
		return user;
	}
	
	//makes all task active 
	private void makeTasksActive(User user){
		for(Task t: user.getTasks()){
			t.setActive(true);
		}
	}
}
