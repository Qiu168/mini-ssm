package com.qiu.ssm.annotation.aop;

import java.lang.annotation.*;

/**
 * @author _qiu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AfterThrowing {
    Class<? extends Annotation>[] annotation() default {};
    Class<? extends Annotation>[] within() default {};
    Class<? extends Throwable>[] throwable() default Throwable.class;
}
