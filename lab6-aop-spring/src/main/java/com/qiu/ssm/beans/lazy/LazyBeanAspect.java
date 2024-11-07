package com.qiu.ssm.beans.lazy;

import com.qiu.ssm.annotation.aop.Around;
import com.qiu.ssm.aop.ProceedingJoinPoint;
import com.qiu.ssm.util.ContextUtil;

/**
 * @author _qqiu
 */
public class LazyBeanAspect {
    private LazyBeanAspect() {
    }
    private static final LazyBeanAspect INSTANCE=new LazyBeanAspect();
    public static LazyBeanAspect getInstance(){
        return INSTANCE;
    }
    @Around
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        Object target = pjp.getTarget();
        if(target==null){
            pjp.setTarget(ContextUtil.getBean(pjp.getTargetClass()));
        }
        pjp.proceed();
    }
}
