package main.java.com.yvalera.scheduler.model.persistentObjects.Task;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

/*
 * Tasks keep title, description, start and and dates
 * and other information about their selves.
 * Tasks can be three types: Routine, FlexibleTerm,
 * LimitedTerm. Routine task repeats all the time without
 * start and end period. This is a sleep, cooking food and etc.
 * Limited term point has start and and date and must be 
 * completed until end date, so for examle to study subject to 
 * exam date, complete project on work and etc. Flexible task
 * means task, which mustn't be finished to certain date.
 * The main idea of this program is to count free time for this
 * type of tasks. For example: when I'll complete to study
 * something for my self-education if I have certain day schedule
 * and accordingly some free time.
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


