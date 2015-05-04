package main.java.com.yvalera.scheduler.model.ScheduleProcessors.persistentObjects;

import java.util.ArrayList;
import java.util.List;

import main.java.com.yvalera.scheduler.model.ScheduleProcessors.persistentObjects.Task.Task;

public class User {
	
	private String name;
	//TODO to divede on active and unactive 
	private List<Task> tasks = new ArrayList<Task>();
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
