package com.yvalera.scheduler.server.BaseUnits.Day;

import com.yvalera.scheduler.server.BaseUnits.Point.RigidTimePoint;

/*
 * This class is abstract superclass for day with
 * regid schedule
 */
abstract public class Day {
	private String nameOfTemplate;
	private RigidTimePoint[] rigidPoints = new RigidTimePoint[24];
	private int dayOfWeek;
	
	//getters and setters
	public String getNameOfTemplate() {
		return nameOfTemplate;
	}
	
	public void setNameOfTemplate(String nameOfTemplate) {
		this.nameOfTemplate = nameOfTemplate;
	}
	
	public RigidTimePoint[] getRigidPoints() {
		return rigidPoints;
	}
	
	public void setRigidPoints(RigidTimePoint[] rigidPoints) {
		this.rigidPoints = rigidPoints;
	}
	
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
}
