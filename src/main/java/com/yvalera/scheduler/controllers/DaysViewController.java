package main.java.com.yvalera.scheduler.controllers;

import java.util.LinkedHashMap;

import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;
import main.java.com.yvalera.scheduler.service.Service;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//Controller of the login page
@Controller
//@RequestMapping(value = "/user**", method = RequestMethod.POST)
@RequestMapping("/app/days")
public class DaysViewController {
	
	@Autowired
	private Service service;
	
	/*
	 * Calls with request without parameters
	 * Returns returns curent and next months view.
	 */
    @RequestMapping(method = RequestMethod.GET)
    public String tempRedirect(ModelMap model) {
    	
    	
    	LocalDate today = new LocalDate();
    	Interval interval = null;
    	LocalDate startInterval = null;
    	LocalDate endInterval = null;
    	
    	int curMonths = today.getMonthOfYear();
    	int curYear = today.getYear();
    	
    	//set up 2 months interval
    	startInterval = new LocalDate(curYear, curMonths, 1);
    	endInterval = startInterval.plusMonths(2);
    	
    	interval = new Interval(startInterval.toDate().getTime(),
    			endInterval.toDate().getTime());
    	
    	//TODO make it work
    	//retrieve userId from Spring Security
        /*Authentication auth = SecurityContextHolder.getContext().
        		getAuthentication();
        User user = (User)auth.getPrincipal();*/
    	
    	//stub interval to test the page, the same interval in the
    	//DaoStubImpl
    	LocalDate startDate = new LocalDate(2000, 1, 10);
    	LocalDate endDate = new LocalDate(2000, 3, 22);
    	interval = new Interval(startDate.toDate().getTime(),
    			endDate.toDate().getTime());
    	
    	
    	//TODO make it work
    	Schedule schedule = service.getSchedule(interval, 0);
    	
    	model.put("schedule", schedule);
    	
    	//type of the review
    	//model.put("reviewType", "moths");
    	
    	//puts interval for jsp rendering
    	model.put("interval", interval);
    	
        return "days_view_page";
    }
    
    private void cssGenerator(Schedule schedule, Interval interval){
    	
    	int sqSize = 15;//size of every square element
    	int space = 2;//space between
    	//days in certain months in interval
    	//keeps insertion order
    	LinkedHashMap<String, Integer> daysInMonthes = null;
    	
    	//how months in period
    	Period p = new Period(interval.getStartMillis(),
    			interval.getEndMillis(), PeriodType.months().
    				withDaysRemoved());
    	int totalMonths = p.getMonths() + 1;
    	
    	/*
    	 * parsed start position for pointer (pointer increase 
    	 * on 1 month in loop)
    	 */
    	daysInMonthes = new LinkedHashMap<String, Integer>();
    	int startYear = new LocalDate(interval.getStartMillis()).
    			getYear();
    	int startMonths = new LocalDate(interval.getStartMillis()).
    			getMonthOfYear();
    	
    	//days in first months
    	int firstMonthStartDay = new LocalDate(interval.getStartMillis()).
    			getDayOfMonth();
    	int daysInFirstMonth = new LocalDate(interval.getStartMillis()).
    			dayOfMonth().getMaximumValue();
    	String firstMonthName = new LocalDate(interval.getStartMillis()).
    			monthOfYear().getAsText();
    	daysInMonthes.put(firstMonthName, daysInFirstMonth);
    	
    	//days in other months except last
    	for(int i = 0; i < totalMonths - 2; i++){
    		//begining of the start months
    		LocalDate date = new LocalDate(startYear, startMonths, 1);
    		//retrieving necessary months
    		date = date.plusMonths(i+1);
    		//retrieving last day in months
    		int daysQuantity = date.dayOfMonth().getMaximumValue();
    		//retrieving months name
    		String monthName = date.monthOfYear().getAsText();
    		//saves data in the Map
    		daysInMonthes.put(monthName, daysQuantity);
    	}
    	
    	//necessary days in last months
    	int lastDayOfLastMonth = new LocalDate(interval.getEndMillis()).
    			getDayOfMonth();
    	String lastMonthName = new LocalDate(interval.getEndMillis()).
    			monthOfYear().getAsText();
    	daysInMonthes.put(lastMonthName, lastDayOfLastMonth);  		
    }
}
