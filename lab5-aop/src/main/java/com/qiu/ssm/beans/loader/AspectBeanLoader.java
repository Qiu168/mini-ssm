package com.qiu.ssm.beans.loader;

import com.qiu.ssm.annotation.aop.Aspect;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * AspectBeanLoader
 *
 * @author _qqiu
 */
public class AspectBeanLoader implements BeanLoader{
    @Getter
    private final List<Class<?>> aspectClz=new ArrayList<>();
    @Override
    public boolean isLoad(Class<?> clz) {
        if(clz.isAnnotationPresent(Aspect.class)){
           aspectClz.add(clz);
        }
        return false;
    }
}
