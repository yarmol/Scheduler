package main.java.com.yvalera.scheduler.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
 * Handles all excrptions in applications
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	
	/*
	 * Exception.class - which exception method
	 * will be intercept
	 */
	@ExceptionHandler(value = Exception.class)
	public String processException(){
		
		return "exception_page";
	}
}
