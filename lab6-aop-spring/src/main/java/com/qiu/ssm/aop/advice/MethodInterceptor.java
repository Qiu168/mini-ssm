package com.qiu.ssm.aop.advice;

import com.qiu.ssm.aop.MethodInvocation;

/**
 * @author _qqiu
 */
public interface MethodInterceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
