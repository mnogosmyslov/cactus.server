package org.cactus.server.config;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {
                DatabaseConfig.class,
                DataSourceConfig.class,
                RemountingServicesConfig.class,
                WebConfig.class,
                WebSocketConfig.class,
                WebSecurityConfig.class,
                MongoConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {
                WebConfig.class,
                WebSecurityConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] {
                new DelegatingFilterProxy("springSecurityFilterChain")
        };
    }
}
