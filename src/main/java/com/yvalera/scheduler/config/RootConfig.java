package main.java.com.yvalera.scheduler.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

/*
 * This is a root application config class
 * 
 * @author Yakubovich Valeriy
 */
//Empty class, without it({SecurityConfig.class}) application doesn't start
@Configuration
@Import({SecurityConfig.class})
public class RootConfig {
	
	//TODO make it with connection pool
	//TODO remove settings to file
	//connection with db
	@Bean
	public DataSource dataSource() {
	    DriverManagerDataSource ds = new DriverManagerDataSource();

	    ds.setDriverClassName("com.mysql.jdbc.Driver");
	    ds.setUrl("jdbc:mysql://localhost:3306/scheduler");
	    ds.setUsername("root");
	    ds.setPassword("mValera");

	    return ds;
	}
	
	@Bean//it work with Hibernate persistence objects
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
		Properties props = new Properties();
	   
		sfb.setDataSource(dataSource);
	   
		//persistence objects
		sfb.setPackagesToScan(new String[] 
			   {"main.java.com.yvalera.scheduler.model.persistentObjects"});

		props.setProperty("dialect", "org.hibernate.dialect.MYSQLDialect");
	   
		/*
		 * !!! Tells Hibernate to AUTOMATIC create or alter 
		 * application tables in the database.
		 */
		props.setProperty("hibernate.hbm2ddl.auto", "update");
	   
		sfb.setHibernateProperties(props);

		return sfb;
	}
	
	/*
	 * Will be invoked after application started. If table security 
	 * doesn't exists, it will create it
	 */
	@PostConstruct
	public void createSecurityTable(){
		
		//System.out.println("db verification");
		
	    DataSource source = dataSource();
	    Connection conn = null;
	    Statement statement = null;
	     
	    try {
	    	conn = source.getConnection();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	     
	    /*
	     * if table "security" exists there will not be exceptions
	     */
	    String query = "SELECT 1 FROM security LIMIT 1;";
	     
	    try {
			statement = conn.createStatement();
	    } catch (SQLException e) {
			e.printStackTrace();
	    }
	     
	    try {
			statement.executeQuery(query);
			//System.out.println("db exists");
	    } catch (SQLException e){//if table doesn't exists
	    	
	    	//System.out.println("creates new db");
	    	
	    	try {
				statement = conn.createStatement();
	    	} catch (SQLException e1) {
				e1.printStackTrace();
	    	}
	    	 
	    	//80 because it encrypted password length
	    	query = "create table security("+
	    			 "id Serial, " +
	    			 "username varchar(20) NOT NULL UNIQUE, " +
	    			 "password varchar(80) NOT NULL, " +
	    			 "ROLE_USER ENUM('USER', 'ADMIN'), " +
	    			 "PRIMARY KEY(id)) " +
					 "character set utf8;";
	    	 
	    	try {
				statement.executeUpdate(query);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	    }
	}
}
