package com.qiu.ssm.beans.processor;

import com.qiu.ssm.annotation.di.Autowired;
import com.qiu.ssm.beans.BeanDefinition;
import com.qiu.ssm.beans.factory.DefaultBeanFactory;
import com.qiu.ssm.util.StringUtil;

import java.lang.reflect.Field;

/**
 * @author _qiu
 */
public class AutowiredFieldProcessor implements FieldProcessor {
    @Override
    public String processField(Object o, Field field) {
        if (!field.isAnnotationPresent(Autowired.class)) {
            return null;
        }
        Autowired autowired = field.getAnnotation(Autowired.class);
        String autowiredBeanName = autowired.value().trim();
        Class<?> autowiredBeanType=field.getType();
        if (autowiredBeanName.isEmpty()) {
            autowiredBeanName = StringUtil.getBeanName(autowiredBeanType.getName());
        }
        return autowiredBeanName;
    }
}
