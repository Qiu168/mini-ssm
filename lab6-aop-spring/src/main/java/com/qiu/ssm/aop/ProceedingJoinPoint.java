package com.qiu.ssm.aop;

/**
 * {@code @Around} 使用
 * @author _qqiu
 */
public interface ProceedingJoinPoint extends JoinPoint{
    Object proceed() throws Throwable;
    void setTarget(Object target);
}
