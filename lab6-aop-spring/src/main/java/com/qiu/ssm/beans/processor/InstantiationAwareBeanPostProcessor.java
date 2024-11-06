package com.qiu.ssm.beans.processor;

/**
 * @author _qqiu
 */
public interface InstantiationAwareBeanPostProcessor {
    default Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws Exception {
        return null;
    }
    default Object postProcessAfterInstantiation(Object bean, String beanName) throws Exception {
        return bean;
    }
}
