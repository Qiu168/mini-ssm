package com.qiu.ssm.aop;

/**
 * {@code @Around} 使用
 * @author _qqiu
 */
public interface ProceedingJoinPoint extends JoinPoint{
    public Object proceed() throws Throwable;
}
