package main.java.com.yvalera.scheduler.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//Controller of the login page
@Controller
@RequestMapping({"/", "/login"})
public class LoginPageController {
	
    @RequestMapping(method = RequestMethod.GET)
    public String login() {
    	
        return "login_page";
    }
}
