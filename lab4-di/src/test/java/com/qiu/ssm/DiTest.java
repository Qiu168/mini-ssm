package com.qiu.ssm;

import com.qiu.ssm.annotation.SpringBootApplication;
import com.qiu.ssm.context.ApplicationContext;
import com.qiu.ssm.test.*;
import org.junit.Assert;

import java.lang.reflect.InvocationTargetException;

@SpringBootApplication(scanPackage = "com.qiu.ssm.test")
public class DiTest {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        ApplicationContext context = ApplicationContext.run(DiTest.class);
        A a = context.getBean(A.class);
        B b = context.getBean(B.class);
        C c = context.getBean(C.class);
        D d = context.getBean(D.class);
        System.out.println(b.getA());
        System.out.println(a);
        System.out.println(a.getB());
        System.out.println(b);
        System.out.println(c);
        System.out.println(d.c);
        System.out.println(c.d);
        System.out.println(d);
        Assert.assertEquals(a.getB(),b);
        Assert.assertEquals(a.getPort(),Integer.valueOf(79779));
    }
}
