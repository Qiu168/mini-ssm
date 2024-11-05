package com.qiu.ssm.annotation.aop;

import java.lang.annotation.*;

/**
 * @author _qiu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Around {
    Class<? extends Annotation>[] annotation() default {};
    Class<?>[] within() default {};
}
