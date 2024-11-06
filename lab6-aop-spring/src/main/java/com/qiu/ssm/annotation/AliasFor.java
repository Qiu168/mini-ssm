package com.qiu.ssm.annotation;

import java.lang.annotation.*;

/**
 * @author _qqiu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AliasFor {
    Class<? extends Annotation> annotation() default Annotation.class;
    String value() default "";
}
