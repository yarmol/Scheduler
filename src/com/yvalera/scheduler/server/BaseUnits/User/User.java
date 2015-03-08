package com.yvalera.scheduler.server.BaseUnits.User;

import java.util.HashMap;
import java.util.List;

import com.yvalera.scheduler.server.BaseUnits.Day.SpecialDay;
import com.yvalera.scheduler.server.BaseUnits.Day.TemplateDay;
import com.yvalera.scheduler.server.BaseUnits.Point.FlexibleTermPoint;
import com.yvalera.scheduler.server.BaseUnits.Point.LimitedTermPoint;

public class User {
	
	//TODO id
	//private int id;
	private String name;
	private LimitedTermPoint[] limitedPoints = new LimitedTermPoint[24];
	private FlexibleTermPoint[] flexPoints = new FlexibleTermPoint[24];
	//TODO make lazy initialization
	private HashMap<Integer, List<TemplateDay>> templateDays = 
			new HashMap<Integer, List<TemplateDay>>();
	//TODO make lazy initialization
	private HashMap<Integer, List<SpecialDay>> specialDays = 
			new HashMap<Integer, List<SpecialDay>>();
	
	public LimitedTermPoint[] getLimitedPoints() {
		return limitedPoints;
	}
	
	public FlexibleTermPoint[] getFlexPoints() {
		return flexPoints;
	}

	public HashMap<Integer, List<TemplateDay>> getTemplateDays() {
		return templateDays;
	}

	public HashMap<Integer, List<SpecialDay>> getSpecialDays() {
		return specialDays;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
