package com.qiu.ssm.beans.factory;

import com.qiu.ssm.beans.NoSuchBeanException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author _qqiu
 */
public interface BeanFactory {

    /**
     * 根据类名获取bean的实例
     * @param name 被获取bean的类名
     * @return bean 对象
     * @throws Exception 异常
     */
    Object getBean(String name) throws Exception;

    /**
     * 根据类的class获取bean的实例
     * @param requiredType 被获取bean的class
     * @return bean
     * @param <T> 被获取的类
     * @throws Exception 异常
     */
    <T> T getBean(Class<T> requiredType) throws Exception;

    /**
     * @return Ioc容器中的所有Bean
     */
    List<Object> getAllBeans();
}