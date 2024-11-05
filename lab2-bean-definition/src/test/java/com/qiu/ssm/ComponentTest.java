package com.qiu.ssm;

import com.qiu.ssm.beans.BeanDefinition;
import com.qiu.ssm.beans.BeanDefinitionReader;
import com.qiu.ssm.test.C;
import com.qiu.ssm.test.D;
import com.qiu.ssm.util.Assert;

import java.util.List;

public class ComponentTest {
    public static void main(String[] args) {
        BeanDefinitionReader reader=new BeanDefinitionReader("com.qiu.ssm.test");
        List<BeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        Assert.isTrue(beanDefinitions.size() == 2);
        Assert.isTrue(beanDefinitions.get(0).getBeanClass()== C.class);
        Assert.isTrue(beanDefinitions.get(1).getBeanClass()== D.class);
        System.out.println(beanDefinitions);
    }
}
