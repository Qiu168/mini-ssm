package com.qiu.ssm.aop.advice;

import com.qiu.ssm.annotation.aop.Around;
import com.qiu.ssm.aop.JoinPoint;
import com.qiu.ssm.aop.ProceedingJoinPoint;
import com.qiu.ssm.util.Assert;

import java.lang.reflect.Method;


/**
 * @author _qqiu
 */
public abstract class AbstractAspectAdvice implements Advice {

    /**通知方法*/
    private final Method aspectMethod;

    /**切面类*/
    private final Object aspectTarget;

    public AbstractAspectAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

	/**
     * 调用通知方法
     */
    public Object invokeAdviceMethod(JoinPoint joinPoint, Object returnValue, Throwable tx) throws Throwable {
        Class<?>[] paramTypes = this.aspectMethod.getParameterTypes();
        if (paramTypes.length == 0) {
            return this.aspectMethod.invoke(aspectTarget);
        } else {
            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                if(paramTypes[i]== ProceedingJoinPoint.class){
                    Assert.notNull(aspectMethod.getAnnotation(Around.class),"method args can not be pjp unless method is Around");
                    args[i] = joinPoint;
                }else if (paramTypes[i] == JoinPoint.class) {
                    args[i] = joinPoint;
                } else if (paramTypes[i] == Throwable.class) {
                    args[i] = tx;
                } else if (paramTypes[i] == Object.class) {
                    args[i] = returnValue;
                }
            }
            return this.aspectMethod.invoke(aspectTarget, args);
        }
    }
}
