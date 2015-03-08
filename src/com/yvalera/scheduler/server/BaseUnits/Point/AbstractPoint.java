package com.yvalera.scheduler.server.BaseUnits.Point;

/*
 * This abstract class encludes general methods
 * of all type of points
 */
abstract public class AbstractPoint {
	private String title;
	private String description;
	
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
