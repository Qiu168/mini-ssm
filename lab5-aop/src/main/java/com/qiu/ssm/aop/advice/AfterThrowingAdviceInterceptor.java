package com.qiu.ssm.aop.advice;


import com.qiu.ssm.aop.MethodInvocation;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 异常通知
 *
 * @author _qqiu
 */
@Setter
public class AfterThrowingAdviceInterceptor extends AbstractAspectAdvice implements MethodInterceptor {

    private List<Class<? extends Throwable>> exceptions;

    public AfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget, List<Class<? extends Throwable>> exceptions) {
        super(aspectMethod, aspectTarget);
        this.exceptions=exceptions;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            //直接调用下一个拦截器，如果不出现异常就不调用异常通知
            return mi.proceed();
        } catch (Throwable e) {
            //异常捕捉中调用通知方法
            for (Class<? extends Throwable> exception : exceptions) {
                if(e.getClass()==exception){
                    invokeAdviceMethod(mi, null, e.getCause());
                    break;
                }
            }
            throw e;
        }
    }

}
