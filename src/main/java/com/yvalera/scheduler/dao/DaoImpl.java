package main.java.com.yvalera.scheduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import main.java.com.yvalera.scheduler.model.persistentObjects.User;
import main.java.com.yvalera.scheduler.model.persistentObjects.Task.Task;
import main.java.com.yvalera.scheduler.service.auxiliary_objects.UserFactory;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class DaoImpl implements Dao{

	//for saveNewUserToDB method
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SessionFactory sessionFactory; 
	
	/**
	 * @return User object by username
	 */
	@Override
	public User getUserByUserName(String username, Session session) {
		
		//Transaction tx = session.beginTransaction();
		
		Criteria crit = session.createCriteria(User.class);
		Criterion nameOfUser = Restrictions.eq("name", username);
		crit.setMaxResults(1);
		User user = (User) crit.uniqueResult();
		
		//tx.commit();
		
		return user;
	}
	
	/**
	 * @return opened session
	 */
	@Override
	public Session getSession() {
		return sessionFactory.openSession();
	}

	/**
	 * Saves new user to database
	 * @return true if new user was saved and false in not
	 */
	@Override
	public boolean saveNewUser(User user, String password,
			Session session){
    	
    	Connection conn = null;
    	PreparedStatement statement = null;
    	
    	/*
    	 * encodes password to keeping it encoded in DB. 
    	 * "#sch$" it's just random char sequence, must match 
    	 * with another one in SecurityConfig configuer method.
    	 */
    	PasswordEncoder encoder = new StandardPasswordEncoder("#sch$");
    	
    	try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    	
    	try {
			statement = conn.prepareStatement(
					"insert into security (username, password, " +
					"ROLE_USER) values (?, ?, 'USER');");
			
			statement.setString(1, user.getName());
			statement.setString(2, encoder.encode(password));
		
    	} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
    	
    	try {
			statement.execute();
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
    	
    	
		//Transaction tx = session.beginTransaction();
		
		session.persist(user);
		
		//tx.commit();    	
    	
    	return true;
    }

}
