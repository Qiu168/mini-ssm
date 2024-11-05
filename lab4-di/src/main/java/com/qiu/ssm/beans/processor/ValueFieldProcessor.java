package com.qiu.ssm.beans.processor;

import com.qiu.ssm.annotation.di.Value;
import com.qiu.ssm.resource.ElInterpreter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author _qiu
 */
public class ValueFieldProcessor implements FieldProcessor{
    private static  final Map<Class<?>, Function<String,?>> MAP =new HashMap<>();
    static {
        MAP.put(int.class, Integer::valueOf);
        MAP.put(Integer.class, Integer::valueOf);
        MAP.put(String.class, o->o);
    }
    @Override
    public String processField(Object o, Field field) {
        if(!field.isAnnotationPresent(Value.class))return null;
        Function<String, ?> function = MAP.get(field.getType());
        if (function == null) {
            return null;
        }
        Value value = field.getAnnotation(Value.class);
        String el = value.value();
        String parsing = ElInterpreter.parsing(el);
        try {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            field.set(o,function.apply(parsing));
            field.setAccessible(accessible);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
