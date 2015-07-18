package main.java.com.yvalera.scheduler.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//for Security database access
	@Autowired
	DataSource dataSource;
	
	/*@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
			throws Exception {
		auth.inMemoryAuthentication().
				withUser("user").password("user").roles("USER");
	}*/

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
			throws Exception{
		
		auth.jdbcAuthentication().dataSource(dataSource).
				usersByUsernameQuery("select username, password,"
						+ " true from security where username=?").
				authoritiesByUsernameQuery("select username, "
						+ "'ROLE_USER' from security where username=?").
				passwordEncoder(new StandardPasswordEncoder("#sch$"));
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/app/**").access("hasRole('USER')").and()
			.formLogin().defaultSuccessUrl("/app/days", false).and()
			//link on logout
			.logout().logoutUrl("/j_spring_security_logout")
			.logoutSuccessUrl("/login").and()
			.formLogin().loginPage("/login").and()
			;
	}
}
