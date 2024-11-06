package com.qiu.ssm.annotation.di;

import java.lang.annotation.*;

/**
 * @author _qiu
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Qualifier {
    String value();
}
