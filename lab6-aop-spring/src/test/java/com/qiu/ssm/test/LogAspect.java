package com.qiu.ssm.test;

import com.qiu.ssm.annotation.aop.AfterThrowing;
import com.qiu.ssm.annotation.aop.Around;
import com.qiu.ssm.annotation.aop.Aspect;
import com.qiu.ssm.annotation.aop.Before;
import com.qiu.ssm.annotation.stereotype.Component;
import com.qiu.ssm.aop.ProceedingJoinPoint;
import com.qiu.ssm.beans.NoSuchBeanException;

@Aspect
@Component
public class LogAspect {
    @Before(annotation = Log.class)
    public void before() {
        System.out.println("Before");
    }

    @Around(within = Haha.class)
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around before");
        pjp.proceed();
        System.out.println("around after");
    }

    @AfterThrowing(annotation = Log.class, throwable = NoSuchBeanException.class)
    public void afterThrowing(Throwable throwable) {
        System.out.println(throwable.getClass());
    }
}