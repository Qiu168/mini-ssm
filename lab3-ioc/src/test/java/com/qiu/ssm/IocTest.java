package com.qiu.ssm;

import com.qiu.ssm.annotation.SpringBootApplication;
import com.qiu.ssm.context.ApplicationContext;
import com.qiu.ssm.test.C;
import com.qiu.ssm.test.D;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@SpringBootApplication(scanPackage = "com.qiu.ssm.test")
public class IocTest {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        ApplicationContext run = ApplicationContext.run(IocTest.class);
        List<Object> allBeans = run.getAllBeans();
        System.out.println(allBeans);
        D d = run.getBean(D.class);
        C c = run.getBean(C.class);
        System.out.println(d.c==c);
    }
    @Test
    public void circularDependencies(){
        //将C中的构造函数解开，StackOverflowError
        //constructor 注入的循环依赖  需要@Lazy解决，这里我就不实现了
        ApplicationContext run = ApplicationContext.run(IocTest.class);
    }
}
