package com.qiu.ssm.beans.loader;

import com.qiu.ssm.annotation.stereotype.Component;
import com.qiu.ssm.beans.BeanDefinition;
import com.qiu.ssm.util.AnnotationUtil;
import com.qiu.ssm.util.StringUtil;

import java.lang.annotation.Annotation;

/**
 * ComponentBeanLoader
 *
 * @author _qqiu
 */
public class ComponentBeanLoader implements BeanLoader{
    @Override
    public boolean isLoad(Class<?> clz) {
        for (Annotation annotation : clz.getAnnotations()) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            // 判断是否是Component注解
            if (annotationType.isAnnotationPresent(Component.class) || annotation instanceof Component) {
                return true;
            }
        }
        return false;
    }
    @Override
    public BeanDefinition loadBean(Class<?> clz) {
        BeanDefinition beanDefinition = BeanLoader.super.loadBean(clz);
        Component component = AnnotationUtil.getMergedAnnotation(clz, Component.class);
        if(component==null|| StringUtil.isEmpty(component.value())){
            return beanDefinition;
        }
        beanDefinition.setFactoryBeanName(component.value());
        return beanDefinition;
    }
}
