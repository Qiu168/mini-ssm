package com.qiu.ssm.beans.lazy;

import com.qiu.ssm.annotation.aop.Around;
import com.qiu.ssm.aop.AdvisedSupport;
import com.qiu.ssm.aop.CglibAopProxy;
import com.qiu.ssm.aop.ProceedingJoinPoint;
import com.qiu.ssm.aop.advice.AroundAdviceInterceptor;
import com.qiu.ssm.aop.advice.MethodInterceptor;
import com.qiu.ssm.util.ContextUtil;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author _qqiu
 */
public class LazyBeanAspect {
    private LazyBeanAspect() {
    }
    private static final LazyBeanAspect INSTANCE=new LazyBeanAspect();
    public static LazyBeanAspect getInstance(){
        return INSTANCE;
    }
    @Around
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        Object target = pjp.getTarget();
        if(target==null){
            pjp.setTarget(ContextUtil.getBean(pjp.getTargetClass()));
        }
        pjp.proceed();
    }
    public static Object createLazyBean(Class<?> declaringClass) throws NoSuchMethodException {
        LazyBeanAspect lazyBeanAspect=LazyBeanAspect.getInstance();
        Method[] declaredMethods = declaringClass.getDeclaredMethods();
        Map<Method, List<MethodInterceptor>> map = new HashMap<>();
        for (Method declaredMethod : declaredMethods) {
            AroundAdviceInterceptor interceptor = new AroundAdviceInterceptor(lazyBeanAspect.getClass().getMethod("around", ProceedingJoinPoint.class), lazyBeanAspect);
            map.put(declaredMethod, Collections.singletonList(interceptor));
        }
        AdvisedSupport advisedSupport = new AdvisedSupport(declaringClass, null, map);
        CglibAopProxy cglibAopProxy = new CglibAopProxy(advisedSupport);
        return cglibAopProxy.getProxy();
    }
}
