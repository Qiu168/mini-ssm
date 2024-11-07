package com.qiu.ssm.test;

import com.qiu.ssm.annotation.SpringBootApplication;
import com.qiu.ssm.context.ApplicationContext;

@SpringBootApplication
public class AopCircularDependenceTest {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = ApplicationContext.run(AopCircularDependenceTest.class);
        B b = context.getBean(B.class);
        C c = context.getBean(C.class);
        b.test();
    }
}
