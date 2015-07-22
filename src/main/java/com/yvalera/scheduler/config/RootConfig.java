package main.java.com.yvalera.scheduler.config;

import java.util.Properties;

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
}
