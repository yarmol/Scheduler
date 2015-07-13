package main.java.com.yvalera.scheduler.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//Controller of the login page
@Controller
//@RequestMapping(value = "/user**", method = RequestMethod.POST)
@RequestMapping("/user/temp")
public class UsersController {
	
    @RequestMapping(method = RequestMethod.GET)
    //@RequestMapping(value = "/user/temp")
    public String tempRedirect(ModelMap model) {
    	
        return "temp";
    }
}
