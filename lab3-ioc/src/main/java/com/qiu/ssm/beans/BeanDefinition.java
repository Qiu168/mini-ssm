package com.qiu.ssm.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean的配置信息
 * @author _qqiu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeanDefinition {
    public static final String SINGLETON="SINGLETON";
    /**
     * 实现类的全类名
     */
    private String beanClassName;
    private Class<?> beanClass;
    /**
     * 是否懒加载
     * 目前不写懒加载实现
     */
    private boolean lazyInit = false;
    /**
     * 对象名（默认首字母小写，@Qualify）
     */
    private String factoryBeanName;
    /**
     * 默认SINGLETON
     */
    private String scope = SINGLETON;
//    private <T> Function<Class<T>,T> constructorFunction;
}
