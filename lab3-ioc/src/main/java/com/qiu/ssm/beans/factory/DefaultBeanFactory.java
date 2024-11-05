package com.qiu.ssm.beans.factory;

import com.qiu.ssm.annotation.stereotype.Component;
import com.qiu.ssm.beans.BeanDefinition;
import com.qiu.ssm.util.AnnotationUtil;
import com.qiu.ssm.util.Assert;
import com.qiu.ssm.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author _qiu
 */
@Slf4j
public class DefaultBeanFactory implements BeanFactory{
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private final Map<String, Object> factoryBeanInstanceCache = new ConcurrentHashMap<>();
    protected void doRegisterBeanDefinition(List<BeanDefinition> beanDefinitions) throws Exception {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                throw new Exception("The \"" + beanDefinition.getFactoryBeanName() + "\" is exists!!");
            }
            beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
    }
    @Override
    public Object getBean(String beanName) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        //如果是单例，那么在上一次调用getBean获取该bean时已经初始化过了，拿到不为空的实例直接返回即可
        Object instance = factoryBeanInstanceCache.get(beanName);
        if (instance != null) {
            return instance;
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        Assert.notNull(beanDefinition,"找不到bean："+beanName);
        //实例化Bean，放入Ioc容器
        Object o = instantiateBean(beanDefinition);
        factoryBeanInstanceCache.put(beanName,o);
        return o;
    }
    protected void doCreateBean(){
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()) {
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private Object instantiateBean(BeanDefinition beanDefinition) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clz = beanDefinition.getBeanClass();
        Constructor<?>[] constructors = clz.getConstructors();
        Assert.isTrue(constructors.length==1,"to many constructors in "+beanDefinition.getBeanClass().getName());
        Constructor<?> constructor = constructors[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] args=new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            args[i]=getBean(parameterTypes[i]);
        }
        return constructor.newInstance(args);
    }

    /**
     * 使用cast进行类型转换时，会在编译期进行类型检查，可以在一定程度上避免在运行时出现类型转换异常。
     * @param requiredType 被获取bean的class
     * @return bean对象
     * @param <T> bean的类型
     */
    @Override
    public <T> T getBean(Class<T> requiredType) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Component annotation = AnnotationUtil.getMergedAnnotation(requiredType,Component.class);
        String beanName= StringUtil.toLowerFirstCase(requiredType.getSimpleName());
        if(annotation!=null&&!"".equals(annotation.value())){
            beanName=annotation.value();
        }
        return requiredType.cast(getBean(beanName));
    }

    @Override
    public List<Object> getAllBeans() {
        return Arrays.asList(factoryBeanInstanceCache.keySet().toArray());
    }
}
