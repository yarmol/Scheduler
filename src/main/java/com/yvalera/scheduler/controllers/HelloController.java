package main.java.com.yvalera.scheduler.controllers;

import main.java.com.yvalera.scheduler.service.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/welcome")
public class HelloController {

	@Autowired
	private ScheduleService scheduleService;
	
    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {

        model.addAttribute("toPrint", scheduleService.getDaysSchedule());
        return "hello";
    } 
}
