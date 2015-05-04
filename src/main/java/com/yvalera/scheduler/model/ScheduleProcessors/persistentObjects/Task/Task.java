package main.java.com.yvalera.scheduler.model.ScheduleProcessors.persistentObjects.Task;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

/*
 * This point doesn't have stricted finish date, but 
 * possibly it have the date? earlier that it can't start 
 * or / and previous points
 */
//TODO Make predecessors
public class Task{
	
	//general for all task
	private String title;
	private String description;
	private TypeOfTask type;
	private boolean active;
	
	//general for flexible and Limited terms task
	private int necessaryTime;
	private LocalDate	startDate;
	//private List<Point> predecessor;
	
	//for Limited term task
	private Interval interval;

	//getters end setters
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setInterval(Interval interval){
		this.interval = interval;
	}

	public Interval getInterval(){
		return interval;
	}

	public TypeOfTask getType() {
		return type;
	}

	public void setType(TypeOfTask type) {
		this.type = type;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public int getNecessaryTime() {
		return necessaryTime;
	}

	public void setNecessaryTime(int necessaryTime) {
		this.necessaryTime = necessaryTime;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}


