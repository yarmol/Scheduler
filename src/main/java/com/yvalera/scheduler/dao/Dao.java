package main.java.com.yvalera.scheduler.dao;

import main.java.com.yvalera.scheduler.model.persistentObjects.User;

import org.hibernate.Session;

/**
 * This is the interface to the ORM DB. Transmitting
 * a Session object need to provide lazy initialization
 * of User objects.
 * 
 * @author Yakubovich Valeriy
 */
public interface Dao{
	
	/**
	 * @return User object by user name parameter
	 */
	public User getUserByUserName(String userName, Session session);
	
	//public void deleteUser(User user);
	
	/**
	 * returns Session object to make possible User 
	 * object's lazy initialization. Warning, Session must be
	 * closed in Service layer!
	 */
	public Session getSession();
	
	/**
     * Saves new user to database. Returns true if new user was
     * saved and false if new user wasn't saved 
     */
	public boolean saveNewUser(User user, 
			String password, Session session);
}
