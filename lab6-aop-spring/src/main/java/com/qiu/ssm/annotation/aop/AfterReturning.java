package com.qiu.ssm.annotation.aop;

import java.lang.annotation.Annotation;

public @interface AfterReturning {
    Class<? extends Annotation>[] annotation() default {};
    Class<? extends Annotation>[] within() default {};
}
