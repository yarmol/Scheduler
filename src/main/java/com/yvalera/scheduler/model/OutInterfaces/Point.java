package main.java.com.yvalera.scheduler.model.OutInterfaces;

/*
 * This is the outer interface to read Tasks
 * Point means one indivisible cell on
 * schedule
 */
public interface Point {

	public String getTitle();
	
	public String getDescription();
	
	public String getStartDay();
	
	public String getEndDay();
}
