package main.java.com.yvalera.scheduler.service;

import java.util.List;

import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;
import main.java.com.yvalera.scheduler.service.Interfaces.TaskRepresentation;

import org.joda.time.Interval;

/**
 * Service layer of application. Keeps all logic of application
 * except "buisness logic" which are in the Model implementation.
 */
public interface Service {
	
	/**
	 * @return calculated schedule uses 
	 * @param requested interval  of dates and 
	 * @param certain user
	 */
	public Schedule getSchedule(Interval interval, String userName);
	
	/**
     * Adds new user to application
     * @param String username - new user's username
     * @param String passwor - password of new user
     */
	public boolean addNewUser(String username, 
			String password);
	
	/**
	 * @return TaskRepresentation object
	 * @param String username - name of requested user
	 * @param long taskId - id number of user's task
	 */
	public TaskRepresentation getTaskReprByUsernameAndTaskId(
			String username, long taskId);
	
	/**
	 * @return List<TaskRepresentation> of all tasks which has specified
	 * user in TaskRepresentation format
	 * @param username - username for necessary user
	 */
	public List<TaskRepresentation> getTaskRepresentations(String username);
	
	/**
	 * Adds or updates task for specified user
	 * @param username name of user which task will be added or updated
	 * @param taskRepe parameter for task to add or update
	 */
	public void updateUserTasks(String username, TaskRepresentation taskId);
	
	/**
	 * Deletes task from specified
	 * @param username name of user which task will be added
	 * @param id of task to delete
	 */
	public void deleteTask(String username, long taskId);
}