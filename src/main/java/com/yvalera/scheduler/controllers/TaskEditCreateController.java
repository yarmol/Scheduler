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

/*
 * This controller processes request on edit/create tasks page
 */
@Controller
@RequestMapping("/app/task/edit")
public class TaskEditCreateController {
	
	@Autowired
	private Service service;
	
	/*
	 * Processes information in incoming new/edited task.
	 * Passes changes in TaskRepresantation object to Service
	 * layer
	 */
	@RequestMapping(method = RequestMethod.POST)
    public String getTaskParameters(@ModelAttribute("tSPageMessage")
    		@Valid  TSPageMessage tSPageMessage,
    		Errors errors, ModelMap model) {
    	
		Authentication auth = null;
		User user = null;//SpringSecurity user
		
		if(errors.hasErrors()){//shows this page again
			
			//header of page
			model.put("TS_page_header", "edit task");
			
			//adds type of tasks radio buttons
			model.put("typeOftaskArary", getRadioButtons());
			
			//adds last message object
			model.put("tSPageMessage", tSPageMessage);
			return "task_edit_create_page";
		}else{//redirects to another page
			
			//retrieve username from Spring Security
			auth = SecurityContextHolder.getContext().
	        		getAuthentication();
			
			user = (User)auth.getPrincipal();
			
			service.updateUserTasks(user.getUsername(), 
					convertToTaskRepresentation(tSPageMessage));
			
			return "redirect:/app/task/review";
		}       
    }
	
	/*
	 * Processes a tasks requests
	 */
	@RequestMapping(method = RequestMethod.GET)
    public String showTask(@RequestParam(value="task_id",
    	defaultValue="0") long taskId, ModelMap model) {
    	
		TaskRepresentation representation = null;
		Authentication auth = null;
		User user = null;
		TSPageMessage tSPageMessage = null;
		
		if(taskId == 0){
			tSPageMessage = new TSPageMessage();
			model.put("TS_page_header", "create new task");
		}else{
			
			//retrieve userId from Spring Security
	        auth = SecurityContextHolder.getContext().
	        		getAuthentication();
			
			user = (User)auth.getPrincipal();
			
			representation = service.getTaskReprByUsernameAndTaskId(
					user.getUsername(), taskId);
			
			tSPageMessage = convertToTSPageMessage(representation);

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
	 * Converts TaskRepresentation to TSPageMessage
	 */
	private TSPageMessage convertToTSPageMessage(
			TaskRepresentation toConvert){
		
		TSPageMessage message = new TSPageMessage();
		LocalDate startDate = null;
		LocalDate endDate = null;
		
		//fills all fielsd;
		
		message.setId(toConvert.getId());
		message.setTaskName(toConvert.getTitle());
		message.setDescription(toConvert.getDescription());
		
		if(toConvert.getType().equals(TypeOfTask.Routine)){
			message.setTypeOfTask("routine");
		}else if(toConvert.getType().equals(TypeOfTask.FlexibleTerm)){
			message.setTypeOfTask("flexible term");
		}else{
			message.setTypeOfTask("limited term");
		}
		
		message.setIsActive(toConvert.getIsActive());
		message.setNecessaryHours(toConvert.getNecessaryTime());
		
		//if it is necessary start date field
		if(toConvert.getType().equals(TypeOfTask.FlexibleTerm)){
			startDate = new LocalDate(toConvert.getStartDate());
		}else{//if necessary interval
			startDate = new LocalDate(toConvert.getInterval().
					getStartMillis());
		}
		
		endDate = new LocalDate(toConvert.getInterval().
				getEndMillis());
		
		message.setStartDay(startDate.toString());
		message.setEndDay(endDate.toString());
		
		message.setMonday(toConvert.getActiveDayAt(1));
		message.setTuesday(toConvert.getActiveDayAt(2));
		message.setWednesday(toConvert.getActiveDayAt(3));
		message.setThursday(toConvert.getActiveDayAt(4));
		message.setFriday(toConvert.getActiveDayAt(5));
		message.setSaturday(toConvert.getActiveDayAt(6));
		message.setSunday(toConvert.getActiveDayAt(7));
		
		message.setHour_0(toConvert.getActiveHourAt(0));
		message.setHour_1(toConvert.getActiveHourAt(1));
		message.setHour_2(toConvert.getActiveHourAt(2));
		message.setHour_3(toConvert.getActiveHourAt(3));
		message.setHour_4(toConvert.getActiveHourAt(4));
		message.setHour_5(toConvert.getActiveHourAt(5));
		message.setHour_6(toConvert.getActiveHourAt(6));
		message.setHour_7(toConvert.getActiveHourAt(7));
		message.setHour_8(toConvert.getActiveHourAt(8));
		message.setHour_9(toConvert.getActiveHourAt(9));
		message.setHour_11(toConvert.getActiveHourAt(11));
		message.setHour_12(toConvert.getActiveHourAt(12));
		message.setHour_13(toConvert.getActiveHourAt(13));
		message.setHour_14(toConvert.getActiveHourAt(14));
		message.setHour_15(toConvert.getActiveHourAt(15));
		message.setHour_16(toConvert.getActiveHourAt(16));
		message.setHour_17(toConvert.getActiveHourAt(17));
		message.setHour_18(toConvert.getActiveHourAt(18));
		message.setHour_19(toConvert.getActiveHourAt(19));
		message.setHour_20(toConvert.getActiveHourAt(20));
		message.setHour_21(toConvert.getActiveHourAt(21));
		message.setHour_22(toConvert.getActiveHourAt(22));
		message.setHour_23(toConvert.getActiveHourAt(23));
		
		return message;
	}
	
	/*
	 * Converts TSPageMessage to TaskRepresentation
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
}
