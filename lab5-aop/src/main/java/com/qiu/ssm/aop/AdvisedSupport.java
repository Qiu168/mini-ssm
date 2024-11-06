package com.qiu.ssm.aop;

import com.qiu.ssm.aop.advice.MethodInterceptor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 代理类支持
 * @author _qqiu
 */
class AdvisedSupport {

    /**
     * 被代理的类class
     */
    @Getter
    private Class<?> targetClass;

    /**被代理的对象实例*/
    @Getter
    @Setter
    private Object target;

    /**被代理的方法对应的拦截器集合*/
    private Map<Method, List<MethodInterceptor>> methodCache;

    /**
     * 获取拦截器
     */
    public List<MethodInterceptor> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
        List<MethodInterceptor> cached = methodCache.get(method);
        if (cached == null) {
            Method m = null;
            try {
                m = targetClass.getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            cached = methodCache.get(m);
            this.methodCache.put(m, cached);
        }
        return cached;
    }
}
