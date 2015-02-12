package org.cactus.server.config;

import org.cactus.server.utils.service.RemoteServiceAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("org.cactus.server")
public class RemountingServicesConfig {

    @Bean
    public RemoteServiceAnnotationBeanPostProcessor remoteServiceAnnotationBeanPostProcessor() {
        return new RemoteServiceAnnotationBeanPostProcessor();
    }

}
