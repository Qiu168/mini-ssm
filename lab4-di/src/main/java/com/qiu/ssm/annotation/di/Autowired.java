package com.qiu.ssm.annotation.di;

import java.lang.annotation.*;

/**
 * @author _qqiu
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
