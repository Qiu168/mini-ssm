package com.qiu.ssm.annotation;


import com.qiu.ssm.annotation.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author _qiu
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Configuration {

}
