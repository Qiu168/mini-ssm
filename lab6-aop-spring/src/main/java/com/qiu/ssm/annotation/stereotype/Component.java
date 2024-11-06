package com.qiu.ssm.annotation.stereotype;

import com.qiu.ssm.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author _qqiu
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
