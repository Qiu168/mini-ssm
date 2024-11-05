package com.qiu.ssm.beans.loader;

import com.qiu.ssm.beans.BeanDefinition;
import com.qiu.ssm.util.StringUtil;

/**
 * 选择Bean进行管理
 *
 * @author _qqiu
 */
public interface BeanLoader {
    boolean isLoad(Class<?> clz);
    default BeanDefinition loadBean(Class<?> clz){
        return BeanDefinition.builder()
                .beanClassName(clz.getName())
                .beanClass(clz)
                .factoryBeanName(StringUtil.toLowerFirstCase(clz.getSimpleName()))
                .build();
    }
}
