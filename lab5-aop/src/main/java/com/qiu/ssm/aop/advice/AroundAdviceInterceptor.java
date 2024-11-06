package com.qiu.ssm.aop.advice;

import com.qiu.ssm.aop.MethodInvocation;
import com.qiu.ssm.aop.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @author _qqiu
 */
public class AroundAdviceInterceptor extends AbstractAspectAdvice implements MethodInterceptor{
    public AroundAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }
    private Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return super.invokeAdviceMethod(proceedingJoinPoint, null, null);
    }
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        return around(mi);
    }
}
