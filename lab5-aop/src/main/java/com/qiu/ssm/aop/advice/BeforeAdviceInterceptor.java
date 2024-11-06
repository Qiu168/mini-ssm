package com.qiu.ssm.aop.advice;


import com.qiu.ssm.aop.JoinPoint;
import com.qiu.ssm.aop.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 前置通知
 */
public class BeforeAdviceInterceptor extends AbstractAspectAdvice implements MethodInterceptor {


    public BeforeAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void before(JoinPoint joinPoint) throws Throwable {
        super.invokeAdviceMethod(joinPoint, null, null);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        //在调用下一个拦截器前先执行前置通知
        before(mi);
        return mi.proceed();
    }
}
