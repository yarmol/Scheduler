package main.java.com.yvalera.scheduler.dao;

import main.java.com.yvalera.scheduler.model.persistentObjects.User;
import org.hibernate.Session;

/**
 * This is the interface to the ORM DB. Transmitting
 * a Session object need to provide lazy initialization
 * of User objects.
 */
public interface Dao{
	
	public User getUserById(long id, Session session);
	
	//public void saveUser(User user, Session session);
	
	//public void deleteUser(User user);
	
	/**
	 * returns Session object to make possible User 
	 * object's lazy initialization 
	 */
	public Session getSession();
}
