package Test.model.ScheduleProcessor.SimpleRealization;

import static org.junit.Assert.assertEquals;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Model;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;
import main.java.com.yvalera.scheduler.model.ScheduleProcessors.simpleRealization.ScheduleProcessorSimpleImpl;
import main.java.com.yvalera.scheduler.model.persistentObjects.User;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Ignore;
import org.junit.Test;

/*
 * Tests for ScheduleProcessorSimpleImpl
 */
public class TestsForSimpleRealization {
	
	private Model model = new ScheduleProcessorSimpleImpl();;
	private Schedule schedule;
	
	/*
	 * tests few passes with small period. All tasks are
	 * within requested period
	 */
	@Test
	public void test_1(){
		
		Interval requestedInterval = new 
				Interval(new LocalDate(2000, 1, 1).toDate()
						.getTime(), 
						new LocalDate(2000, 1, 7).toDate()
						.getTime());
		
		Interval fillRoutineDaysInterval = new 
				Interval(new LocalDate(2000, 1, 1).toDate()
						.getTime(), 
						new LocalDate(2000, 1, 7).toDate()
						.getTime());
		
		Interval speciaTaskInterval = new 
				Interval(new LocalDate(2000, 1, 2).toDate()
						.getTime(), 
						new LocalDate(2000, 1, 5).toDate()
						.getTime());
		
		LocalDate specialDayDate = new LocalDate(2000, 1, 3);
		
		LocalDate flexibleTermPointStart = 
				new LocalDate(2000, 1, 3);
		

		
		User user = UserFactory.getUser(
				fillRoutineDaysInterval, 
				speciaTaskInterval, specialDayDate, 
				flexibleTermPointStart);
		
		for(int i=0; i<5; i++){
		
			schedule = model.calculateSchedule(user,
					requestedInterval);
		
			//TextView view = new TextView();
			//view.printView(schedule, requestedInterval);
		
			assertEquals(schedule.getAbsentDayErrors().size(), 0);
			assertEquals(schedule.getTasksErrors().size(), 0);
			assertEquals(schedule.getTotalFreeTime(), 7);
			
			//for(String s: schedule.getTasksNames()){
			//	System.out.println(s);
			//}
			
			
			//System.out.println("task names size: " + schedule.
			//		getTasksNames().size());
		}
	}
	
	/*
	 * tests few passes. Requested interval shorter than
	 * filled days and Limited term task starts before
	 * requested interval starts
	 */
	@Test
	public void test_2(){
		
		Interval requestedInterval = new 
				Interval(new LocalDate(2000, 1, 10).toDate()
						.getTime(), 
						new LocalDate(2000, 3, 22).toDate()
						.getTime());
		
		Interval fillRoutineDaysInterval = new 
				Interval(new LocalDate(2000, 1, 1).toDate()
						.getTime(), 
						new LocalDate(2000, 10, 7).toDate()
						.getTime());
		
		Interval limitedTaskInterval = new 
				Interval(new LocalDate(2000, 1, 2).toDate()
						.getTime(), 
						new LocalDate(2000, 1, 5).toDate()
						.getTime());
		
		LocalDate specialDayDate = new LocalDate(2000, 1, 3);
		
		LocalDate flexibleTermPointStart = 
				new LocalDate(2000, 1, 3);
		
		User user = UserFactory.getUser(
				fillRoutineDaysInterval, 
				limitedTaskInterval, specialDayDate, 
				flexibleTermPointStart);
		
		//for(int i=0; i<5; i++){
			schedule = model.calculateSchedule(user,
				requestedInterval);
			
			//TextView view = new TextView();
			//view.printView(schedule, requestedInterval);
		
			assertEquals(schedule.getAbsentDayErrors().size(), 0);
			assertEquals(schedule.getTasksErrors().size(), 0);
			//assertEquals(schedule.getTotalFreeTime(), 7);
		//}
	}
}
