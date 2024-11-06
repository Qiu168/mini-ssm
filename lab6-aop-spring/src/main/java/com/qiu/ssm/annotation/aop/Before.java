package com.qiu.ssm.annotation.aop;

import java.lang.annotation.*;

/**
 * @author _qiu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Before {
    Class<? extends Annotation>[] annotation() default {};
    Class<? extends Annotation>[] within() default {};
}
