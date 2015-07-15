package main.java.com.yvalera.scheduler.model.persistentObjects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import main.java.com.yvalera.scheduler.model.persistentObjects.Task.Task;

/*
 * It represents users with their schedulled days and
 * tasks
 */
//@Entity
public class User {
	
	private String name;//username
	
	/*
	 * Tasks which user scheduled to do
	 */
	//TODO to divide on active and unactive 
	private List<Task> tasks = new ArrayList<Task>();
	
	/*
	 * Typical and special days for concrete user
	 */
	//TODO to remake with []ArrayList<Day>
	private List<Day> days = new ArrayList<Day>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public List<Day> getDays() {
		return days;
	}
	
}
