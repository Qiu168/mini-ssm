package com.qiu.ssm;

import com.qiu.ssm.annotation.SpringBootApplication;
import com.qiu.ssm.context.ApplicationContext;
import com.qiu.ssm.test.B;
import com.qiu.ssm.test.C;

@SpringBootApplication
public class AopCircularDependenceTest {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = ApplicationContext.run(AopCircularDependenceTest.class);
        B b = context.getBean(B.class);
        C c = context.getBean(C.class);
        b.test();
    }
}
