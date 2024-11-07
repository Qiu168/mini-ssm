package com.qiu.ssm.beans.factory;

import com.qiu.ssm.annotation.di.Autowired;
import com.qiu.ssm.annotation.stereotype.Component;
import com.qiu.ssm.aop.*;
import com.qiu.ssm.beans.BeanDefinition;
import com.qiu.ssm.beans.BeanWrapper;
import com.qiu.ssm.beans.lazy.LazyBeanAspect;
import com.qiu.ssm.beans.processor.FieldProcessor;
import com.qiu.ssm.beans.processor.InstantiationAwareBeanPostProcessor;
import com.qiu.ssm.util.AnnotationUtil;
import com.qiu.ssm.util.Assert;
import com.qiu.ssm.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author _qiu
 */
@Slf4j
public class DefaultBeanFactory implements BeanFactory{
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private final Map<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();
    private final Map<String, Object> earlyBeanMap = new HashMap<>();
    private volatile List<FieldProcessor> fieldProcessors=null;
    private volatile List<InstantiationAwareBeanPostProcessor> instantiationAwareBeanPostProcessors=null;
    protected void doRegisterBeanDefinition(List<BeanDefinition> beanDefinitions) throws Exception {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                throw new Exception("The \"" + beanDefinition.getFactoryBeanName() + "\" is exists!!");
            }
            beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
    }
    @Override
    public Object getBean(String beanName) throws Exception {
        //如果是单例，那么在上一次调用getBean获取该bean时已经初始化过了，拿到不为空的实例直接返回即可
        BeanWrapper instance = factoryBeanInstanceCache.get(beanName);
        if (instance != null) {
            return instance.getProxyObject();
        }
        if(earlyBeanMap.containsKey(beanName)){
            return earlyBeanMap.get(beanName);
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        Assert.notNull(beanDefinition,"找不到bean："+beanName);
        List<InstantiationAwareBeanPostProcessor> postProcessorList = getBeanPostProcessors();
        for (InstantiationAwareBeanPostProcessor beanPostProcessor : postProcessorList) {
            beanPostProcessor.postProcessBeforeInstantiation(beanDefinition.getBeanClass(),beanName);
        }
        //实例化Bean，放入Ioc容器
        Object o = instantiateBean(beanDefinition);
        for (InstantiationAwareBeanPostProcessor beanPostProcessor : postProcessorList) {
            o=beanPostProcessor.postProcessAfterInstantiation(o,beanName);
        }
        BeanWrapper beanWrapper= wrapIfNecessary(o);
        earlyBeanMap.put(beanDefinition.getFactoryBeanName(),beanWrapper.getProxyObject());
        //4.注入
        populateBean(beanWrapper);
        factoryBeanInstanceCache.put(beanName, beanWrapper);
        return beanWrapper.getProxyObject();
    }

    private BeanWrapper wrapIfNecessary(Object o) {
        AdvisedSupport advisedSupport = new AdvisedSupport(o.getClass(), o);
        if(advisedSupport.shouldBeWrapped()){
            CglibAopProxy cglibAopProxy=new CglibAopProxy(advisedSupport);
            return new BeanWrapper(o,cglibAopProxy.getProxy());
        }
        return new BeanWrapper(o,o);
    }

    protected void initAspect(List<Class<?>> aspectClz) throws Exception {
        //创建Aspect类
        for (Class<?> clz : aspectClz) {
            Object instance = clz.newInstance();
            populateLazyBean(instance);
            factoryBeanInstanceCache.put(StringUtil.getBeanName(clz.getSimpleName()), new BeanWrapper(instance,instance));
            AspectParser.parse(instance);
        }
    }

    private void populateLazyBean(Object o) throws NoSuchMethodException, IllegalAccessException {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Class<?> declaringClass = field.getDeclaringClass();
                Object proxy=LazyBeanAspect.createLazyBean(declaringClass);
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                field.set(o, proxy);
                field.setAccessible(accessible);
            }
        }
    }

    private void populateBean(BeanWrapper beanWrapper) throws Exception {
        Object o=beanWrapper.getWrappedObject();
        Field[] fields = o.getClass().getDeclaredFields();
        List<FieldProcessor> processors = getFieldProcessors();
        for (Field field : fields) {
            for (FieldProcessor fieldProcessor : processors) {
                String beanName = fieldProcessor.processField(o, field);
                if(beanName==null)continue;
                Object bean = getBean(beanName);
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                field.set(o,bean);
                field.setAccessible(accessible);
            }
        }
    }

    private List<FieldProcessor> getFieldProcessors() {
        //double check
        if(fieldProcessors==null){
            synchronized (this){
                if(fieldProcessors==null){
                    fieldProcessors=new ArrayList<>();
                    for (FieldProcessor fieldProcessor : ServiceLoader.load(FieldProcessor.class)) {
                        fieldProcessors.add(fieldProcessor);
                    }
                }
            }
        }
        return fieldProcessors;
    }
    private List<InstantiationAwareBeanPostProcessor> getBeanPostProcessors() {
        //double check
        if(instantiationAwareBeanPostProcessors==null){
            synchronized (this){
                if(instantiationAwareBeanPostProcessors==null){
                    instantiationAwareBeanPostProcessors=new ArrayList<>();
                    for (InstantiationAwareBeanPostProcessor beanPostProcessor : ServiceLoader.load(InstantiationAwareBeanPostProcessor.class)) {
                        instantiationAwareBeanPostProcessors.add(beanPostProcessor);
                    }
                }
            }
        }
        return instantiationAwareBeanPostProcessors;
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
    private Object instantiateBean(BeanDefinition beanDefinition) throws Exception {
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
    public <T> T getBean(Class<T> requiredType) throws Exception {
        Component annotation = AnnotationUtil.getMergedAnnotation(requiredType,Component.class);
        String beanName= StringUtil.toLowerFirstCase(requiredType.getSimpleName());
        if(annotation!=null&&!"".equals(annotation.value())){
            beanName=annotation.value();
        }
        return requiredType.cast(getBean(beanName));
    }

    @Override
    public List<Object> getAllBeans() {
        return factoryBeanInstanceCache.values().stream().map(BeanWrapper::getWrappedObject).collect(Collectors.toList());
    }
}
