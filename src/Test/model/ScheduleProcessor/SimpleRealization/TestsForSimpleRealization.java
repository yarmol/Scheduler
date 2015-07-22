package Test.model.ScheduleProcessor.SimpleRealization;

import static org.junit.Assert.assertEquals;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Model;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;
import main.java.com.yvalera.scheduler.model.ScheduleProcessors.simpleRealization.ScheduleProcessorSimpleImpl;
import main.java.com.yvalera.scheduler.model.persistentObjects.User;
import main.java.com.yvalera.scheduler.model.persistentObjects.Task.Task;
import main.java.com.yvalera.scheduler.model.persistentObjects.Task.TypeOfTask;
import main.java.com.yvalera.scheduler.service.auxiliary_objects.UserFactory;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
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
	 * 
	 * @author Yakubovich Valeriy
	 */
	@Test
	public void test_1(){
		
		Interval requestedInterval = new 
				Interval(new LocalDate(2015, 6, 18).toDate()
						.getTime(), 
						new LocalDate(2015, 6, 23).toDate()
						.getTime());
		
		Interval LimitedTaskInterval = new 
				Interval(new LocalDate(2015, 6, 17).toDate()
						.getTime(), 
						new LocalDate(2015, 6, 21).toDate()
						.getTime());
		
		LocalDate flexibleTermstartDate = new LocalDate(2015, 06, 20);
		
		User user = UserFactory.createStandartNewUser("");
		
		//adds task to created user
		Task flexibleTerms = new Task();
		Task limitedTerms = new Task();
		
		flexibleTerms.setTitle("flexible");
		flexibleTerms.setStartDate(flexibleTermstartDate);
		flexibleTerms.setType(TypeOfTask.FlexibleTerm);
		flexibleTerms.setNecessaryTime(18);
		
		limitedTerms.setTitle("limited");
		limitedTerms.setInterval(LimitedTaskInterval);
		limitedTerms.setType(TypeOfTask.LimitedTerm);
		limitedTerms.setNecessaryTime(10);
		
		flexibleTerms.setActive(true);
		limitedTerms.setActive(true);
		
		flexibleTerms.setId(1000);
		limitedTerms.setId(2000);
		
		user.getTasks().add(flexibleTerms);
		user.getTasks().add(limitedTerms);
				
		for(int i = 1; i < 8; i++){
			if(i != 4){//makes one day unactive
				flexibleTerms.setActiveDayAt(i, true);
			}
			limitedTerms.setActiveDayAt(i, true);
		}
		
		for(int j = 0; j < 24; j++){
			if(j > 10 && j < 20){
				limitedTerms.setActiveHourAt(j, true);
			}
			if(j > 14){
				flexibleTerms.setActiveHourAt(j, true);
			}
		}
		
		//for(int i=0; i<5; i++){
		
			schedule = model.calculateSchedule(user,
					requestedInterval);
		
			TextView view = new TextView();
			view.printView(schedule, requestedInterval);
		
			assertEquals(schedule.getTasksErrors().size(), 0);
			assertEquals(schedule.getTotalFreeTime(), 61);
		//}
	}
}
