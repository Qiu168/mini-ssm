package com.qiu.ssm.beans;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Bean配置信息被读取并实例化成一个实例后，这个实例封装在BeanWrapper中
 * 被包装的对象可以变引用。引用此包装类。
 * @author _qiu
 */
@Data
@AllArgsConstructor
public class BeanWrapper {
    /**
     * Bean的实例化对象
     */
    private Object wrappedObject;
    private Object proxyObject;
}
