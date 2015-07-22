package main.java.com.yvalera.scheduler.service;

import java.util.ArrayList;
import java.util.List;

import main.java.com.yvalera.scheduler.dao.Dao;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Model;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;
import main.java.com.yvalera.scheduler.model.persistentObjects.User;
import main.java.com.yvalera.scheduler.model.persistentObjects.Task.Task;
import main.java.com.yvalera.scheduler.service.Interfaces.TaskRepresentation;
import main.java.com.yvalera.scheduler.service.auxiliary_objects.TaskRepresentationImpl;
import main.java.com.yvalera.scheduler.service.auxiliary_objects.UserFactory;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This is an implementation of Service interface.
 * Service layer implementation. No to forget
 * close Session after all
 * 
 * @author Yakubovich Valeriy
 */
@Component
public class ServiceImpl implements Service{

	@Autowired
	private Dao dao;
	
	@Autowired
	private Model model;

	@Override
	public Schedule getSchedule(Interval interval, String userName){
		
		Session session = dao.getSession();
		Transaction tx = session.beginTransaction();
		
		User user = dao.getUserByUserName(userName, session);
		
		Schedule schedule = model.calculateSchedule(user, interval);
				
		//closes retrieved sesssion after all work with persisted
		//User object done
		tx.commit();
		session.close();
		
		return schedule;
	}
	
	/**
     * Saves new user to database. Returns true if new user was
     * saved and false if new user wasn't saved 
     * 
     * TODO think to request opportunity to save new user
     * and after that create and fills new user. 
     */
	public boolean addNewUser(String username, 
			String password){
		
		Session session = dao.getSession();//DB session
		Transaction tx = session.beginTransaction();
		
		boolean saved = false;//is username and password saved
		
		//creates new user
    	User user = UserFactory.createStandartNewUser(username);
		
		//tries to save user
    	saved = dao.saveNewUser(user, password, session);
    	
    	//closes retrieved sesssion after all work with persisted
    	//User object done
    	tx.commit();
    	session.close();
    	
		return saved;
	}
	
	/**
	 * @return TaskRepresentation object
	 * @param String username - name of requested user
	 * @param long taskId - id number of user's task
	 */
	@Override
	public TaskRepresentation getTaskReprByUsernameAndTaskId(
			String username, long taskId) {

		Session session = dao.getSession();//DB session
		Transaction tx = session.beginTransaction();
		
		User user = dao.getUserByUserName(username, session);
		
		TaskRepresentation taskRepr = null;
		
		//Searches requested task
		for(Task t: user.getTasks()){
			if(t.getId() == taskId){
				taskRepr = new TaskRepresentationImpl(t);
				break;
			}
		}
	
		//closes retrieved sesssion after all work with persisted
    	//User object done
		tx.commit();
    	session.close();
    	
    	return taskRepr;
	}

	/**
	 * Adds or updates task for specified user
	 * @param username name of user which task will be added or updated
	 * @param task parameter for task to add
	 */
	@Override
	public void updateUserTasks(String username, TaskRepresentation taskRepr) {
		
		Session session = dao.getSession();//DB session
		Transaction tx = session.beginTransaction();
		
		boolean isUserHasThisTask = false;
		
		Task task = new Task(taskRepr);
		
		User user = dao.getUserByUserName(username, session);
		
		//if user has this task
		for(Task t: user.getTasks()){
			if(t.getId() == taskRepr.getId()){
				isUserHasThisTask = true;
				break;
			}
		}
		
		//removes old copy of this task wit the same id
		if(isUserHasThisTask){
			user.getTasks().remove(task);
		}
		
		//adds new or updated task
		user.getTasks().add(task);
		
		/*for(Task t: user.getTasks()){
			System.out.println("before closing session user has task: " 
					+ t.getTitle());
		}*/
	
		//closes retrieved sesssion after all work with persisted
    	//User object done
		tx.commit();
    	session.close();
	}

	/**
	 * Deletes task from specified
	 * @param username name of user which task will be added
	 * @param task parameter for task to add
	 */
	@Override
	public void deleteTask(String username, long taskId) {
		Session session = dao.getSession();//DB session
		Transaction tx = session.beginTransaction();
		
		//creates new empty task
		Task task = new Task();
		
		//makes this task equal to task in User object
		task.setId(taskId);
		
		User user = dao.getUserByUserName(username, session);
	
		//removes task
		user.getTasks().remove(task);
	
		//closes retrieved sesssion after all work with persisted
    	//User object done
		tx.commit();
    	session.close();
	}

	/**
	 * @return List<TaskRepresentation> of all tasks which has specified
	 * user in TaskRepresentation format
	 * @param username - username for necessary user
	 */
	@Override
	public List<TaskRepresentation> getTaskRepresentations(String username) {

		Session session = dao.getSession();//DB session
		Transaction tx = session.beginTransaction();
		
		List<TaskRepresentation> reprList = new 
				ArrayList<TaskRepresentation>();
		
		/*
		 * converts all task from user to taskRepresentation and adds
		 * them to list
		 */
		for(Task t: dao.getUserByUserName(username, session).getTasks()){
			reprList.add(new TaskRepresentationImpl(t));
		}
		
		//closes retrieved sesssion after all work with persisted
    	//User object done
		tx.commit();
    	session.close();
    	
    	return reprList;
	}
}
