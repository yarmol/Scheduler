package main.java.com.yvalera.scheduler.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

/*
 * This is a Hibernate configuration class
 * 
 * @author Yakubovich Valeriy
 */
@Configuration
@ComponentScan("main.java.com.yvalera.scheduler.model.persistentObjects")//turn on auto component determination
class HibernateConfig{
	
	//connection with db
	@Bean
	public DataSource dataSource() {
	    DriverManagerDataSource ds = new DriverManagerDataSource();

	    ds.setDriverClassName("com.mysql.jdbc.Driver");
	    ds.setUrl("jdbc:mysql://localhost:3306/spring");
	    ds.setUsername("root");
	    ds.setPassword("mValera");

	    return ds;
	}
	
	@Bean//it work with Hibernate persistence objects
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
	   
		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
	   
		sfb.setDataSource(dataSource);
		sfb.setPackagesToScan(new String[] {"Hibernate.annotation.storaging_types.undirectional_one_to_many"});//persistence objects
	   
		Properties props = new Properties();

		props.setProperty("dialect", "org.hibernate.dialect.MYSQLDialect");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		
		sfb.setHibernateProperties(props);

		return sfb;
	}
}