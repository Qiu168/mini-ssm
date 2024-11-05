package com.qiu.ssm;

import com.qiu.ssm.beans.BeanDefinition;
import com.qiu.ssm.beans.BeanDefinitionReader;
import com.qiu.ssm.beans.loader.BeanLoader;
import com.qiu.ssm.util.StringUtil;

import java.util.List;

public class BeanDefinitionTest {
    static class AllClassBeanLoader implements BeanLoader{
        @Override
        public boolean isLoad(Class<?> clz) {
            return true;
        }
    }

    public static void main(String[] args) {
        BeanDefinitionReader reader=new BeanDefinitionReader("com.qiu.ssm.test");
        reader.addBeanLoader(new AllClassBeanLoader());
        List<BeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        System.out.println(beanDefinitions);
    }
}
