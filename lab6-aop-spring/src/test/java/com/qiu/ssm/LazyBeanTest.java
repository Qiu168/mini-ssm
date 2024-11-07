package com.qiu.ssm;

import com.qiu.ssm.annotation.SpringBootApplication;
import com.qiu.ssm.aop.AdvisedSupport;
import com.qiu.ssm.aop.CglibAopProxy;
import com.qiu.ssm.aop.ProceedingJoinPoint;
import com.qiu.ssm.aop.advice.AroundAdviceInterceptor;
import com.qiu.ssm.aop.advice.MethodInterceptor;
import com.qiu.ssm.beans.lazy.LazyBeanAspect;
import com.qiu.ssm.context.ApplicationContext;
import com.qiu.ssm.test.A;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@SpringBootApplication(scanPackage = "com.qiu.ssm.test")
public class LazyBeanTest {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = ApplicationContext.run(AopTest.class);
        A lazyBean = (A) createLazyBean(A.class);
        lazyBean.test();
    }
    private static Object createLazyBean(Class<?> declaringClass) throws NoSuchMethodException {
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
