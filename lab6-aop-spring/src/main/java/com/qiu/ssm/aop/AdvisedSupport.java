package com.qiu.ssm.aop;

import com.qiu.ssm.aop.advice.MethodInterceptor;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 代理类支持
 * 一个代理类对应一个AdviceSupport
 * @author _qqiu
 */
public class AdvisedSupport {

    /**
     * 被代理的类class
     */
    @Getter
    private Class<?> targetClass;

    /**被代理的对象实例*/
    @Getter
    @Setter
    private Object target;

    /**被代理的方法对应的拦截器集合*/
    private final Map<Method, List<MethodInterceptor>> methodCache;

    public AdvisedSupport(Class<?> targetClass, Object target) {
        this.targetClass = targetClass;
        this.target = target;
        this.methodCache = new HashMap<>();
        List<MethodInterceptor> clzAdvice=new ArrayList<>();
        for (Annotation annotation : targetClass.getAnnotations()) {
            //获取注解的Class对象=获取实例对象的唯一接口
            clzAdvice.addAll(AspectParser.withinMap.getOrDefault(annotation.getClass().getInterfaces()[0],new ArrayList<>()));
        }
        for (Method method : targetClass.getDeclaredMethods()) {
            List<MethodInterceptor> methodInterceptors=new ArrayList<>();
            for (Annotation annotation : method.getAnnotations()) {
                methodInterceptors.addAll(AspectParser.annotationMap.getOrDefault(annotation.getClass().getInterfaces()[0],new ArrayList<>()));
            }
            methodInterceptors.addAll(clzAdvice);
            if(!methodInterceptors.isEmpty()){
                methodCache.put(method,methodInterceptors);
            }
        }
    }
    public boolean shouldBeWrapped(){
        return !methodCache.isEmpty();
    }
    public AdvisedSupport(Class<?> targetClass, Object target, Map<Method, List<MethodInterceptor>> methodCache) {
        this.targetClass = targetClass;
        this.target = target;
        this.methodCache = methodCache;
    }

    /**
     * 获取拦截器
     */
    public List<MethodInterceptor> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
        List<MethodInterceptor> cached = methodCache.get(method);
        if (cached == null) {
            Method m = null;
            try {
                m = targetClass.getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            cached = methodCache.get(m);
            if(cached!=null){
                this.methodCache.put(method, cached);
            }
        }
        if(cached==null){
            cached= Collections.emptyList();
        }
        return cached;
    }
}
