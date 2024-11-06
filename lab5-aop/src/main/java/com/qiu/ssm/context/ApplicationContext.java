package com.qiu.ssm.context;

import com.qiu.ssm.annotation.SpringBootApplication;
import com.qiu.ssm.beans.BeanDefinition;
import com.qiu.ssm.beans.BeanDefinitionReader;
import com.qiu.ssm.beans.factory.DefaultBeanFactory;
import com.qiu.ssm.resource.DefaultResourceLoader;
import com.qiu.ssm.resource.ResourceHolder;
import com.qiu.ssm.resource.ResourceLoader;
import com.qiu.ssm.util.Assert;
import com.qiu.ssm.util.ContextUtil;
import com.qiu.ssm.util.StringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author _qiu
 */
@Slf4j
public class ApplicationContext extends DefaultBeanFactory {
    @SneakyThrows
    public static ApplicationContext run(Class<?> mainClass){
        ApplicationContext context = new ApplicationContext();
        SpringBootApplication springBootApplication = mainClass.getAnnotation(SpringBootApplication.class);
        Assert.notNull(springBootApplication,"can not find @SpringBootApplication");
        String scanPackage=springBootApplication.scanPackage();
        if(StringUtil.isEmpty(scanPackage)){
            scanPackage=mainClass.getPackage().getName();
        }
        context.refresh(scanPackage);
        ContextUtil.setApplicationContext(context);
        return context;
    }
    protected void refresh(String scanPackage) throws Exception {
        //定位配置文件
        ResourceLoader resourceLoader=new DefaultResourceLoader();
        ResourceHolder.setResourceLoader(resourceLoader);
        BeanDefinitionReader reader=new BeanDefinitionReader(scanPackage);
        List<BeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        doRegisterBeanDefinition(beanDefinitions);
        doCreateBean();
        log.info("refresh finish");
    }
}
