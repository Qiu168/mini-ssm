package com.qiu.ssm.util;

import com.qiu.ssm.annotation.AliasFor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author _qqiu
 */
public abstract class AnnotationUtil {
    @SuppressWarnings("unchecked")
    public static <A extends Annotation> A getMergedAnnotation(Class<?> element, Class<A> annotationType) {
        Annotation annotation = findAnnotation(element, annotationType);
        if (annotation == null || annotation.annotationType() == annotationType) {
            return (A) annotation;
        }
        return mergeAttributes(annotation, annotationType);
    }

    private static <A extends Annotation> Annotation findAnnotation(Class<?> element, Class<A> annotationType) {
        // 直接在类上查找注解
        A annotation = element.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }

        // 查找元注解
        for (Annotation declaredAnnotation : element.getAnnotations()) {
            Annotation metaAnnotation = findAnnotation(declaredAnnotation.annotationType(), annotationType);
            if (metaAnnotation != null) {
                return declaredAnnotation;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private static <A extends Annotation> A mergeAttributes(Annotation annotation, Class<A> annotationType) {
        Map<String, Object> attributes = new HashMap<>();
        //因为Annotation实例是代理实现的所以要先拿到接口
        for (Method method : annotation.getClass().getInterfaces()[0].getDeclaredMethods()) {
            try {
                // 获取属性值
                AliasFor aliasFor = method.getAnnotation(AliasFor.class);
                if (aliasFor != null && aliasFor.annotation() == annotationType) {
                    Object value = method.invoke(annotation);
                    String aliasName = "".equals(aliasFor.value()) ? method.getName() : aliasFor.value();
                    attributes.put(aliasName, value);
                    attributes.put(method.getName(), value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(attributes.size()==0){
            return null;
        }
        //JDK代理
        return (A) Proxy.newProxyInstance(
                annotationType.getClassLoader(),
                new Class<?>[]{annotationType},
                (proxy, method, args) -> attributes.get(method.getName())
        );
    }
}
