package com.qiu.ssm;

import com.qiu.ssm.annotation.SpringBootApplication;
import com.qiu.ssm.context.ApplicationContext;
import com.qiu.ssm.test.B;
import com.qiu.ssm.test.C;

@SpringBootApplication(scanPackage = "com.qiu.ssm.test")
public class AopCircularDependenceTest {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = ApplicationContext.run(AopCircularDependenceTest.class);
        B b = context.getBean(B.class);
        b.test();
    }
    //输出如下
//    Before                             B before
//    around before                      B around before
//    com.qiu.ssm.test.C@77ec78b9        System.out.println(c);
//    Before                             C before
//    around before                      C around before
//    C                                  System.out.println("C");
//    around after                       C around after
//    B                                  System.out.println("B");
//    around after                       B around after
}
