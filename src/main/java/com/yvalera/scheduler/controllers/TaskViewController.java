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
@RequestMapping("/app/task/review")
public class TaskViewController {
	
	/*
	 * Processes a tasks requests
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showTask() {

        return "tasks_view_page";
    }
}

	