package com.qiu.ssm.aop.advice;


import com.qiu.ssm.aop.JoinPoint;
import com.qiu.ssm.aop.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author _qqiu
 */
public class AfterReturningAdviceInterceptor extends AbstractAspectAdvice implements MethodInterceptor {

    public AfterReturningAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        //先调用下一个拦截器
        Object retVal = mi.proceed();
        //再调用后置通知
        this.afterReturning(mi,retVal);
        return retVal;
    }

    private void afterReturning(JoinPoint joinPoint,Object retVal) throws Throwable {
        super.invokeAdviceMethod(joinPoint, retVal, null);
    }
}
