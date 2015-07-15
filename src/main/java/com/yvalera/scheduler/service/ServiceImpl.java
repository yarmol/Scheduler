package main.java.com.yvalera.scheduler.service;

import main.java.com.yvalera.scheduler.dao.Dao;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Model;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;
import main.java.com.yvalera.scheduler.model.persistentObjects.User;

import org.hibernate.Session;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * Service layer implementation. No to forget
 * close Session after all
 */
@Component
public class ServiceImpl implements Service{

	@Autowired
	private Dao dao;
	
	@Autowired
	private Model model;

	@Override
	public Schedule getSchedule(Interval interval, long userId) {
		
		Session session = dao.getSession();
		
		User user = dao.getUserById(userId, session);
		
		Schedule schedule = model.calculateSchedule(user, interval);
				
		//closes retrieved sesssion after all work with persisted
		//User done
		session.close();
		
		return schedule;
	}
	
}
