package com.pdasilem.resourceservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BeanScopeLogger implements BeanPostProcessor {

    private final ConfigurableListableBeanFactory beanFactory;

    public BeanScopeLogger(ApplicationContext context) {
        this.beanFactory = ((ConfigurableApplicationContext) context).getBeanFactory();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (beanFactory.containsBeanDefinition(beanName)) {
            String scope = beanFactory.getBeanDefinition(beanName).getScope();
            if (scope.isEmpty()) {
                scope = ConfigurableListableBeanFactory.SCOPE_SINGLETON; // Singleton by default
            }
            log.debug("Bean: " + beanName + " | Scope: " + scope);
        }
        return bean;
    }
}
