package main.java.com.yvalera.scheduler.service;

import main.java.com.yvalera.scheduler.dao.Dao;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Model;
import main.java.com.yvalera.scheduler.model.OutInterfaces.Schedule;
import main.java.com.yvalera.scheduler.model.persistentObjects.User;
import main.java.com.yvalera.scheduler.service.auxiliary_objects.UserFactory;

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
	public Schedule getSchedule(Interval interval, String userName){
		
		Session session = dao.getSession();
		
		User user = dao.getUserByUserName(userName, session);
		
		Schedule schedule = model.calculateSchedule(user, interval);
				
		//closes retrieved sesssion after all work with persisted
		//User object done
		session.close();
		
		return schedule;
	}
	
	/**
     * Saves new user to database. Returns true if new user was
     * saved and false if new user wasn't saved 
     * 
     * TODO think to request opportunity to save new user
     * and after that create and fills new user. 
     */
	public boolean addNewUser(String username, 
			String password){
		
		Session session = dao.getSession();
		boolean saved = false;
		
		//creates new user
    	User user = UserFactory.createStandartNewUser(username);
		
		//tries to save user
    	saved = dao.saveNewUser(user, password, session);
    	
    	//closes retrieved sesssion after all work with persisted
    	//User object done
    	session.close();
    	
		return saved;
	}
}
