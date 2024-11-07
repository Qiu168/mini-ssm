package com.qiu.ssm;

import com.qiu.ssm.annotation.SpringBootApplication;
import com.qiu.ssm.context.ApplicationContext;
import com.qiu.ssm.test.A;

@SpringBootApplication(scanPackage = "com.qiu.ssm.test")
public class AopTest {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = ApplicationContext.run(AopTest.class);
        A a = context.getBean(A.class);
        a.test();
    }
}
