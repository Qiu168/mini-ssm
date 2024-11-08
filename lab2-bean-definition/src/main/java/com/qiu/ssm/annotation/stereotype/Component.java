package com.qiu.ssm.annotation.stereotype;

import java.lang.annotation.*;

/**
 * @author _qqiu
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
