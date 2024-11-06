package com.qiu.ssm.aop.advice;

import com.qiu.ssm.aop.JoinPoint;
import com.qiu.ssm.aop.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author _qqiu
 */
public class AfterAdviceInterceptor extends AbstractAspectAdvice implements MethodInterceptor{
    public AfterAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }
    private void after(JoinPoint joinPoint) throws Throwable {
        super.invokeAdviceMethod(joinPoint, null, null);
    }
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object proceed;
        try {
            proceed = mi.proceed();
        } finally {
            after(mi);
        }
        return proceed;
    }
}
