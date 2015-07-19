package main.java.com.yvalera.scheduler.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import main.java.com.yvalera.scheduler.controllers.objects_page_messages.TSPageMessage;
import main.java.com.yvalera.scheduler.model.persistentObjects.Task.TypeOfTask;
import main.java.com.yvalera.scheduler.service.Service;
import main.java.com.yvalera.scheduler.service.Interfaces.TaskRepresentation;
import main.java.com.yvalera.scheduler.service.auxiliary_objects.TaskRepresentationImpl;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/app/task")
public class TaskManageController {
	
	@Autowired
	private Service service;
	
	/*
	 * Passes changes in TaskRepresantation object to Service layer
	 */
	@RequestMapping(method = RequestMethod.POST)
    public String getTaskParameters(@ModelAttribute("tSPageMessage")  
    		@Valid  TSPageMessage tSPageMessage,
    		Errors errors, ModelMap model) {
    	
		Authentication auth = null;
		User user = null;//SpringSecurity user
		
		
		if(errors.hasErrors()){
			
			//header of page
			model.put("TS_page_header", "edit task");
			
			//adds type of tasks radio buttons
			model.put("typeOftaskArary", getRadioButtons());
			
			//adds last message object
			model.put("tSPageMessage", tSPageMessage);
			return "task_edit_create_page";
		}else{
			
			//retrieve username from Spring Security
			auth = SecurityContextHolder.getContext().
	        		getAuthentication();
			
			user = (User)auth.getPrincipal();
			
			service.updateUserTasks(user.getUsername(), 
					convertToTaskRepresentation(tSPageMessage));
			
			return "redirect:days";
		}       
    }
	
	/*
	 * Processes a tasks requests
	 */
	@RequestMapping(method = RequestMethod.GET)
    public String showTask(@RequestParam(value="task_id",
    	defaultValue="0") long taskId, ModelMap model,
    	HttpServletRequest request) {
    	
		TaskRepresentation representation = null;
		Authentication auth = null;
		User user = null;
		TSPageMessage tSPageMessage = null;
	
		
		tSPageMessage = new TSPageMessage();
		
		if(taskId == 0){
			model.put("TS_page_header", "create new task");
		}else{
			
			//retrieve userId from Spring Security
	        auth = SecurityContextHolder.getContext().
	        		getAuthentication();
			
			user = (User)auth.getPrincipal();
			
			representation = service.getTaskReprByUsernameAndTaskId(
					user.getUsername(), taskId);
			
			//TODO add conversion

			//header for page
			model.put("TS_page_header", "edit task");
			
		}
		
		//adds object to read/fill
		model.put("tSPageMessage", tSPageMessage);
		
		//adds type of tasks radio buttons
		model.put("typeOftaskArary", getRadioButtons());
		
		
        return "task_edit_create_page";
    }
	
	/*
	 * Converts TSSPageMessage to TaskRepresentation
	 */
	private TaskRepresentation convertToTaskRepresentation(
			TSPageMessage message){
		
		TaskRepresentation task = new TaskRepresentationImpl();
		TypeOfTask type = null;
		LocalDate startDate = null;
		LocalDate endDate = null;
		
		//fills all fielsd;
		
		task.setId(message.getId());
		task.setTitle(message.getTaskName());
		task.setDescription(message.getDescription());
		
		if(message.getTypeOfTask().equals("routine")){
			type = TypeOfTask.Routine;
		}else if(message.getTypeOfTask().equals("flexible term")){
			type = TypeOfTask.FlexibleTerm;
		}else{
			type = TypeOfTask.LimitedTerm;
		}
		
		task.setType(type);
		task.setActive(message.getIsActive());
		task.setNecessaryTime(message.getNecessaryHours());
		
		startDate = new LocalDate(message.getStartDay());
		endDate = new LocalDate(message.getEndDay());
		
		task.setStartDate(startDate);
		task.setInterval(new Interval(startDate.toDate().getTime(),
				endDate.toDate().getTime()));
		
		task.setActiveDayAt(1, message.getMonday());
		task.setActiveDayAt(2, message.getTuesday());
		task.setActiveDayAt(3, message.getWednesday());
		task.setActiveDayAt(4, message.getThursday());
		task.setActiveDayAt(5, message.getFriday());
		task.setActiveDayAt(6, message.getSaturday());
		task.setActiveDayAt(7, message.getSunday());
		
		task.setActiveHourAt(0, message.getHour_0());
		task.setActiveHourAt(1, message.getHour_1());
		task.setActiveHourAt(2, message.getHour_2());
		task.setActiveHourAt(3, message.getHour_3());
		task.setActiveHourAt(4, message.getHour_4());
		task.setActiveHourAt(5, message.getHour_5());
		task.setActiveHourAt(6, message.getHour_6());
		task.setActiveHourAt(7, message.getHour_7());
		task.setActiveHourAt(8, message.getHour_8());
		task.setActiveHourAt(9, message.getHour_9());
		task.setActiveHourAt(10, message.getHour_10());
		task.setActiveHourAt(11, message.getHour_11());
		task.setActiveHourAt(12, message.getHour_12());
		task.setActiveHourAt(13, message.getHour_13());
		task.setActiveHourAt(14, message.getHour_14());
		task.setActiveHourAt(15, message.getHour_15());
		task.setActiveHourAt(16, message.getHour_16());
		task.setActiveHourAt(17, message.getHour_17());
		task.setActiveHourAt(18, message.getHour_18());
		task.setActiveHourAt(19, message.getHour_19());
		task.setActiveHourAt(20, message.getHour_20());
		task.setActiveHourAt(20, message.getHour_21());
		task.setActiveHourAt(20, message.getHour_22());
		task.setActiveHourAt(20, message.getHour_23());
		
		return task;
	}
	
	/*
	 * Makes radiobuttons for view
	 */
	private ArrayList<String> getRadioButtons(){
		ArrayList<String> typeOftaskArary = new 
				ArrayList<String>();
		
		typeOftaskArary.add("routine");
		typeOftaskArary.add("flexible term");
		typeOftaskArary.add("limited term");
		
		return typeOftaskArary;
	}
	
    /*//adds "message" object to model
    @ModelAttribute("tSPageMessage")
    public TSPageMessage getLoginForm() {
	        return new TSPageMessage();
    }*/
}
