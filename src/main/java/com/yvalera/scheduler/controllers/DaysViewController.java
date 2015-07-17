package main.java.com.yvalera.scheduler.controllers;

import javax.validation.Valid;

import main.java.com.yvalera.scheduler.controllers.objects_page_messages.DVPageMessage;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;
import main.java.com.yvalera.scheduler.service.Service;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	 * Calls with request with parameters
	 * Returns requested interval view.
	 */
    @RequestMapping(method = RequestMethod.POST)
    public String requestedDatePage(@ModelAttribute("message")
    		@Valid DVPageMessage message,
    		Errors errors, ModelMap model) {
    	
    	Schedule schedule = null;//schedule
    	Interval interval = null;//interval to count schedule
    	LocalDate startInterval = null;//start of schedule interval
    	LocalDate endInterval = null;//end of schedule interval
    	
    	
    	/*
    	 * if there are no errors (without deep verification) in
    	 * entered dates make new interval, otherwise use old
    	 * and @Validate will show message "invalid start or 
    	 * end date (see DVPMessage class)" 
    	 */
    	if(!errors.hasErrors()){
	    	//set up 2 months interval
	    	startInterval = new LocalDate(message.getStartDay());
	    	endInterval = new LocalDate(message.getEndDay());
	    	
	    	//because Interval excludes last day
	    	endInterval = endInterval.plusDays(1);
	    	
	    	interval = new Interval(startInterval.toDate().getTime(),
	    			endInterval.toDate().getTime());
	    	
	    	//puts interval for jsp rendering
	    	model.put("interval", interval);
	    	
	    	schedule = service.getSchedule(interval, 0);
	    	model.put("schedule", schedule);
    	}
    	
    	//TODO make it work
    	//retrieve userId from Spring Security
        /*Authentication auth = SecurityContextHolder.getContext().
        		getAuthentication();
        User user = (User)auth.getPrincipal();*/ 	
    	
	
        return "days_view_page";
    }
	
	
	/*
	 * Calls with request without parameters
	 * Returns current and next months view.
	 */
    @RequestMapping(method = RequestMethod.GET)
    public String defaultDatePage(ModelMap model) {
    	
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
    	    	
    	//TODO make it work
    	Schedule schedule = service.getSchedule(interval, 0);
    	
    	model.put("schedule", schedule);
    	
    	//puts interval for jsp rendering
    	model.put("interval", interval);
    	
        return "days_view_page";
    }
    
    /*
     * adds DVPageMessage objects to JSP page (model). Not 
     * required, DVPageMessage can be put to model
     * clearly.
     */
    @ModelAttribute("message")
    public DVPageMessage getLoginForm() {
	        return new DVPageMessage();
    }
}
