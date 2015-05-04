package main.java.com.yvalera.scheduler.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInititialization extends AbstractAnnotationConfigDispatcherServletInitializer {
    // ���� ����� ������ ��������� ������������ ������� �������������� Beans
    // ��� ������������� ����� � ��� �������������� ��������� @Bean
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }

    // ��� ��������� ������������, � ������� �������������� ViewResolver
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
