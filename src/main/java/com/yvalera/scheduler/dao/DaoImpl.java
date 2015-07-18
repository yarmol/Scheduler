package main.java.com.yvalera.scheduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import main.java.com.yvalera.scheduler.model.persistentObjects.User;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class DaoImpl implements Dao{

	//for saveNewUserToDB method
	@Autowired
	DataSource dataSource;
	
	@Override
	public User getUserById(long id, Session session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Saves new user to database
	 * @return true if new user was saved and false in not
	 */
	@Override
	public boolean saveNewUserToDB(String username, String password){
    	
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
			
			statement.setString(1, username);
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
    	
    	return true;
    }

}
