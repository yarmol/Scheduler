package main.java.com.yvalera.scheduler.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInititialization extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	//Application context configuration
    @Override
    protected Class<?>[] getRootConfigClasses(){
        return new Class<?>[]{
        		//Empty class, without it application doesn't start
                RootConfig.class
        };
    }

    //Servlet context configuration
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
