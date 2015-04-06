package org.cactus.server.utils.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.rmi.RemoteException;

public class RemoteServiceAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements PriorityOrdered {

    private int order = Ordered.LOWEST_PRECEDENCE - 1;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RemoteService service = AnnotationUtils.findAnnotation(bean.getClass(), RemoteService.class);

        Object resultBean = bean;

        if (null != service) {
            if (ServiceType.HESSIAN == service.type()) {
                HessianServiceExporter hessianServiceExporter = new HessianServiceExporter();
                hessianServiceExporter.setServiceInterface(service.serviceInterface());
                hessianServiceExporter.setService(bean);
                hessianServiceExporter.afterPropertiesSet();

                resultBean = hessianServiceExporter;
            } else if (ServiceType.RMI == service.type()) {
                RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
                rmiServiceExporter.setServiceInterface(service.serviceInterface());
                rmiServiceExporter.setService(bean);
                rmiServiceExporter.setServiceName(beanName);
                try {
                    rmiServiceExporter.afterPropertiesSet();
                } catch (RemoteException remoteException) {
                    throw new FatalBeanException("Exception initializing RmiServiceExporter", remoteException);
                }
                resultBean = rmiServiceExporter;
            }
        }

        return resultBean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
