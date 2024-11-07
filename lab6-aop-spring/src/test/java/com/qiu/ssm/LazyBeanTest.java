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
        A lazyBean = (A) LazyBeanAspect.createLazyBean(A.class);
        lazyBean.test();
    }
}
