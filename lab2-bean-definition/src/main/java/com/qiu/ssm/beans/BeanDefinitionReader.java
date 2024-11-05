package com.qiu.ssm.beans;


import com.qiu.ssm.beans.loader.BeanLoader;
import com.qiu.ssm.util.ClassUtil;
import lombok.Getter;

import java.util.*;

/**
 * 读取配置文件，扫描相关的类解析成BeanDefinition
 *
 * @author _qqiu
 */
public class BeanDefinitionReader {
    @Getter
    private final Properties config = new Properties();
    private final List<BeanLoader> beanLoaders=new ArrayList<>();

    @Getter
    private final Set<Class<?>> registerBeanClasses;

    public BeanDefinitionReader(String scanPackage) {
        //扫描，扫描资源文件(class)，并保存到集合中
        registerBeanClasses = ClassUtil.getClassSet(scanPackage);
        ServiceLoader<BeanLoader> loaders=ServiceLoader.load(BeanLoader.class);
        for (BeanLoader loader : loaders) {
            beanLoaders.add(loader);
        }
    }

    /**
     * 把配置文件中扫描到的所有的配置信息转换为BeanDefinition对象
     */
    public List<BeanDefinition> loadBeanDefinitions() {
        List<BeanDefinition> result = new ArrayList<>();
        try {
            for (Class<?> beanClass : registerBeanClasses) {
                //如果是一个接口，是不能实例化的，不需要封装
                for (BeanLoader beanLoader : beanLoaders) {
                    if (beanLoader.isLoad(beanClass)) {
                        BeanDefinition beanDefinition = beanLoader.loadBean(beanClass);
                        result.add(beanDefinition);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    /**
     * 添加 Bean Loader
     */
    public void addBeanLoader(BeanLoader beanLoader){
        beanLoaders.add(beanLoader);
    }

}
