package com.qiu.ssm.beans.processor;

import java.lang.reflect.Field;

/**
 * @author _qiu
 */
public interface FieldProcessor {
    /**
     * @param o 实例对象
     * @param field 成员变量
     * @return if返回null不处理，else返回值当作BeanName，通过BeanName获取到对象后注入field中
     */
    String processField(Object o, Field field);
}