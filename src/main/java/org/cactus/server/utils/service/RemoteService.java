package org.cactus.server.utils.service;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * This annotation allows your class service broadcast via HTTP by HESSIAN
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RemoteService {

    ServiceType type() default ServiceType.HESSIAN;

    Class<?> serviceInterface();

}
