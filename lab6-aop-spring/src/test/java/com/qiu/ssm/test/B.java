package com.qiu.ssm.test;

import com.qiu.ssm.annotation.di.Autowired;
import com.qiu.ssm.annotation.stereotype.Component;

@Haha
@Component
public class B {
    @Autowired
    private C c;

    @Log
    public void test() {
        System.out.println(c);
        c.test();
        System.out.println("B");
    }
}