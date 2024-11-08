package com.qiu.ssm.aop;


import net.sf.cglib.proxy.*;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;


/**
 * CGLIB AOP 代理
 *
 * @author _qqiu
 */
public class CglibAopProxy implements AopProxy {
    private final AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport config) {
        this.advised=config;
    }

    @Override
    public Object getProxy() {
        return getProxy(advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(@Nullable ClassLoader classLoader) {
        Enhancer enhancer=new Enhancer();
        if (classLoader != null) {
            enhancer.setClassLoader(classLoader);
        }
        Class<?> targetClass = advised.getTargetClass();
        enhancer.setSuperclass(targetClass);
        Callback[] callbacks = getCallbacks();
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(new ProxyCallbackFilter(advised));
        return targetClass.cast(enhancer.create());
    }

    private Callback[] getCallbacks() {
        Callback aopInterceptor = new DynamicAdvisedInterceptor(this.advised);
        return new Callback[]{aopInterceptor,NoOp.INSTANCE};
    }

    /**
     * 决定一个方法要不要被代理
     */
    private static class ProxyCallbackFilter implements CallbackFilter {

        private final AdvisedSupport advised;

        ProxyCallbackFilter(AdvisedSupport advised) {
            this.advised = advised;
        }

        @Override
        public int accept(Method method) {
            //todo
            return 0;
        }
    }

    /**
     * 增强
     */
    private static class DynamicAdvisedInterceptor implements MethodInterceptor, Serializable {

        private final AdvisedSupport advised;

        public DynamicAdvisedInterceptor(AdvisedSupport advised) {
            this.advised = advised;
        }

        @Override
        @Nullable
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Class<?> targetClass = advised.getTargetClass();
            List<com.qiu.ssm.aop.advice.MethodInterceptor> chain
                    = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
            MethodInvocation invocation = new MethodInvocation(
                    proxy,
                    this.advised.getTarget(),
                    method,
                    args,
                    this.advised.getTargetClass(),
                    chain
            );
            //开始拦截器链的调用
            return invocation.proceed();
        }
    }
}
