package main.java.com.yvalera.scheduler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//Empty class, without it application doesn't start
@Configuration
@Import({SecurityConfig.class})
public class RootConfig {
	
}
