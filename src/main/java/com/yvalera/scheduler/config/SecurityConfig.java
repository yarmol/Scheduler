package main.java.com.yvalera.scheduler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
		//auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
		//auth.inMemoryAuthentication().withUser("superadmin").password("superadmin").roles("SUPERADMIN");
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
