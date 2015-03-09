package com.yvalera.scheduler.server.BaseUnits.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.LocalDate;

import com.yvalera.scheduler.server.BaseUnits.Day.SpecialDay;
import com.yvalera.scheduler.server.BaseUnits.Day.TemplateDay;
import com.yvalera.scheduler.server.BaseUnits.Point.FlexibleTermPoint;
import com.yvalera.scheduler.server.BaseUnits.Point.LimitedTermPoint;

public class User {
	
	//TODO id
	//private int id;
	private String name;
	private List<LimitedTermPoint> limitedPoints = 
			new ArrayList<LimitedTermPoint>();
	private List<FlexibleTermPoint> flexPoints = 
			new ArrayList<FlexibleTermPoint>();
	//TODO make lazy initialization
	private HashMap<Integer, List<TemplateDay>> templateDays = 
			new HashMap<Integer, List<TemplateDay>>();
	//TODO make lazy initialization
	private HashMap<LocalDate, SpecialDay> specialDays = 
			new HashMap<LocalDate, SpecialDay>();
	
	public List<LimitedTermPoint> getLimitedPoints() {
		return limitedPoints;
	}
	
	public List<FlexibleTermPoint> getFlexPoints() {
		return flexPoints;
	}

	public HashMap<Integer, List<TemplateDay>> getTemplateDays() {
		return templateDays;
	}

	public HashMap<LocalDate, SpecialDay> getSpecialDays() {
		return specialDays;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
