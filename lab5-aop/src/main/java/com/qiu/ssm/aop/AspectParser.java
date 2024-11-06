package com.qiu.ssm.aop;

import com.qiu.ssm.annotation.aop.*;
import com.qiu.ssm.aop.advice.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Aspect解析器
 *
 * @author _qqiu
 */
public abstract class AspectParser {
    static final Map<Class<? extends Annotation>, List<MethodInterceptor>> withinMap = new HashMap<>();
    static final Map<Class<? extends Annotation>, List<MethodInterceptor>> annotationMap = new HashMap<>();
    private static final Map<Class<?>, BiFunction<Method,Object,MethodInterceptor>> supplierMap=new HashMap<>();
    static {
        supplierMap.put(After.class,AfterAdviceInterceptor::new);
        supplierMap.put(AfterReturning.class,AfterReturningAdviceInterceptor::new);
//        supplierMap.put(AfterThrowing.class, AfterThrowingAdviceInterceptor::new);
        supplierMap.put(Around.class,AroundAdviceInterceptor::new);
        supplierMap.put(Before.class,BeforeAdviceInterceptor::new);
    }
    public static void parse(Object aspect) throws Exception {
        Method[] methods = aspect.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if(m.getAnnotations().length==0)continue;
            parsing(After.class,m,aspect);
            parsing(AfterReturning.class,m,aspect);
            parsing(AfterThrowing.class,m,aspect);
            parsing(Around.class,m,aspect);
            parsing(Before.class,m,aspect);
        }
    }

    /**
     * 解析Aspect类
     * @param annoClz After/AfterReturning/AfterThrowing/Around/Before
     * @param m method
     * @param aspect Aspect
     * @param <T> annoClz
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    static <T extends Annotation> void parsing0(Class<T> annoClz, Method m, Object aspect) throws Exception {
        T t=m.getAnnotation(annoClz);
        if(t!=null){
            for (Class<? extends Annotation> afterAnno : (Class<? extends Annotation>[])t.annotationType().getDeclaredMethod("annotation").invoke(t)) {
                List<MethodInterceptor> interceptors = annotationMap.getOrDefault(afterAnno, new ArrayList<>());
                if(annoClz==AfterThrowing.class){
                    interceptors.add(new AfterThrowingAdviceInterceptor(m,aspect, Arrays.stream(((AfterThrowing) t).throwable()).collect(Collectors.toList())));
                    annotationMap.put(afterAnno,interceptors);
                    continue;
                }
                interceptors.add(supplierMap.get(annoClz).apply(m,aspect));
                annotationMap.put(afterAnno,interceptors);
            }
            for (Class<? extends Annotation> afterWithin : (Class<? extends Annotation>[])t.annotationType().getDeclaredMethod("within").invoke(t)) {
                List<MethodInterceptor> interceptors = withinMap.getOrDefault(afterWithin, new ArrayList<>());
                if(annoClz==AfterThrowing.class){
                    interceptors.add(new AfterThrowingAdviceInterceptor(m,aspect, Arrays.stream(((AfterThrowing) t).throwable()).collect(Collectors.toList())));
                    withinMap.put(afterWithin,interceptors);
                    continue;
                }
                interceptors.add(supplierMap.get(annoClz).apply(m,aspect));
                withinMap.put(afterWithin,interceptors);
            }
        }
    }
    /**
     * 解析Aspect类
     * @param annoClz After/AfterReturning/AfterThrowing/Around/Before
     * @param m method
     * @param aspect Aspect
     * @param <T> annoClz
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    static <T extends Annotation> void parsing(Class<T> annoClz, Method m, Object aspect) throws Exception {
        T t = m.getAnnotation(annoClz);
        if (t != null) {
            // 定义一个方法来添加拦截器，避免重复代码
            BiConsumer<Map<Class<? extends Annotation>, List<MethodInterceptor>>, Class<? extends Annotation>> addInterceptor =
                    (map, annotation) -> {
                        List<MethodInterceptor> interceptors = map.getOrDefault(annotation, new ArrayList<>());
                        MethodInterceptor interceptor = (annoClz == AfterThrowing.class)
                                ? new AfterThrowingAdviceInterceptor(m, aspect, Arrays.stream(((AfterThrowing) t).throwable()).collect(Collectors.toList()))
                                : supplierMap.get(annoClz).apply(m, aspect);
                        interceptors.add(interceptor);
                        map.put(annotation, interceptors);
                    };

            // 处理 annotationMap
            for (Class<? extends Annotation> afterAnno : (Class<? extends Annotation>[]) t.annotationType().getDeclaredMethod("annotation").invoke(t)) {
                addInterceptor.accept(annotationMap, afterAnno);
            }

            // 处理 withinMap
            for (Class<? extends Annotation> afterWithin : (Class<? extends Annotation>[]) t.annotationType().getDeclaredMethod("within").invoke(t)) {
                addInterceptor.accept(withinMap, afterWithin);
            }
        }
    }

}
