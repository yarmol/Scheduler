package main.java.com.yvalera.scheduler.model.persistentObjects;

import main.java.com.yvalera.scheduler.model.persistentObjects.Task.Task;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

/*
 * Object of this class represents Template for days.
 * Days can be templated with certain schedule for
 * all period of days and Special, with special
 * schedule for certain date.
 */
public class Day{

	//for Template day
	private Interval interval;
	private int dayOfWeek;
	
	//for special date
	private LocalDate specialDate;
	
	//general for both days
	private String nameOfTemplate;
	private boolean isSpecial;
	private boolean active;
	private Task[] tasks = new Task[24];
	
	//getters and setters
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isSpecial() {
		return isSpecial;
	}

	public void setSpecial(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	
	public String getNameOfTemplate() {
		return nameOfTemplate;
	}
	
	public void setNameOfTemplate(String nameOfTemplate) {
		this.nameOfTemplate = nameOfTemplate;
	}
	
	public Task[] getTasks() {
		return tasks;
	}

	public void setTasks(Task[] tasks) {
		this.tasks = tasks;
	}

	public LocalDate getSpecialDate() {
		return specialDate;
	}

	public void setSpecialDate(LocalDate date) {
		this.specialDate = date;
	}
	
	public Interval getInterval() {
		return interval;
	}
	
	public void setInterval(Interval interval){
		this.interval = interval;
	}
	
	public void setStartDate(LocalDate startDate) {
		long endDate = interval.getEndMillis();
		interval = new Interval(startDate.toDate().getTime(), endDate);
	}
	
	public void setEndDate(LocalDate endDate) {
		long startDate = interval.getStartMillis();
		interval = new Interval(startDate, endDate.toDate().getTime());
	}
	
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dayOfWeek;
		result = prime * result
				+ ((nameOfTemplate == null) ? 0 : nameOfTemplate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Day other = (Day) obj;
		if (dayOfWeek != other.dayOfWeek)
			return false;
		if (nameOfTemplate == null) {
			if (other.nameOfTemplate != null)
				return false;
		} else if (!nameOfTemplate.equals(other.nameOfTemplate))
			return false;
		return true;
	}
}
