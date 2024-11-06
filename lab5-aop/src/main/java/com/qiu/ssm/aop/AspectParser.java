package com.qiu.ssm.aop;

import com.qiu.ssm.annotation.aop.*;
import com.qiu.ssm.aop.advice.AfterReturningAdviceInterceptor;
import com.qiu.ssm.aop.advice.BeforeAdviceInterceptor;
import com.qiu.ssm.aop.advice.MethodInterceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Aspect解析器
 *
 * @author _qqiu
 */
abstract class AspectParser {
    static final Map<Class<? extends Annotation>, List<MethodInterceptor>> withinMap = new HashMap<>();
    static final Map<Class<? extends Annotation>, List<MethodInterceptor>> annotationMap = new HashMap<>();
    static void parse(Object aspect) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Method[] methods = aspect.getClass().getMethods();
        for (Method m : methods) {
            parsing(After.class,m,aspect);
            parsing(AfterReturning.class,m,aspect);
            parsing(AfterThrowing.class,m,aspect);
            parsing(Around.class,m,aspect);
            parsing(Before.class,m,aspect);
        }
    }
    @SuppressWarnings("unchecked")
    static <T extends Annotation> void parsing(Class<T> annoClz, Method m, Object aspect) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        T t=m.getAnnotation(annoClz);
        if(t!=null){
            for (Class<? extends Annotation> afterAnno : (Class<? extends Annotation>[])t.annotationType().getDeclaredMethod("annotation").invoke(t)) {
                List<MethodInterceptor> interceptors = annotationMap.getOrDefault(afterAnno, new ArrayList<>());
                interceptors.add(new AfterReturningAdviceInterceptor(m,aspect));
                annotationMap.put(afterAnno,interceptors);
            }
            for (Class<? extends Annotation> afterWithin : (Class<? extends Annotation>[])t.annotationType().getDeclaredMethod("within").invoke(t)) {
                List<MethodInterceptor> interceptors = withinMap.getOrDefault(afterWithin, new ArrayList<>());
                interceptors.add(new AfterReturningAdviceInterceptor(m,aspect));
                annotationMap.put(afterWithin,interceptors);
            }
        }
    }
}
