package com.qiu.ssm;

import com.qiu.ssm.annotation.aop.AfterThrowing;
import com.qiu.ssm.annotation.aop.Around;
import com.qiu.ssm.annotation.aop.Aspect;
import com.qiu.ssm.annotation.aop.Before;
import com.qiu.ssm.aop.*;
import com.qiu.ssm.beans.NoSuchBeanException;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Slf4j
public class CglibProxyTest {
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Log {}
    @Target({ElementType.METHOD,ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Haha {}

    @Aspect
    public static class LogAspect {
        @Before(annotation =Log.class)
        public void before() {
            System.out.println("Before");
        }
        @Around(within = Haha.class)
        public void around(ProceedingJoinPoint pjp) throws Throwable {
            System.out.println("around before");
            pjp.proceed();
            System.out.println("around after");
        }
        @AfterThrowing(annotation = Log.class,throwable = NoSuchBeanException.class)
        public void afterThrowing(Throwable throwable) {
            System.out.println(throwable.getClass());
        }
    }


    @Haha
    public static class Test {
        @Log
        public void test() {
            System.out.println("test");
        }
        public void test1() {
            System.out.println("test1");
        }
        @Log
        public void error(){
            System.out.println("running");
            throw new NoSuchBeanException();
        }
    }

    public static void main(String[] args) throws Exception {
        AspectParser.parse(new LogAspect());
        AdvisedSupport advisedSupport = new AdvisedSupport(Test.class, new Test());
//        AopProxy aopProxy=new JdkDynamicAopProxy(advisedSupport);
        AopProxy aopProxy=new CglibAopProxy(advisedSupport);
        Test test = (Test)aopProxy.getProxy();
        test.test();
        System.out.println("-----");
        test.test1();
        System.out.println("-----");
        try {
            test.error();
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}
