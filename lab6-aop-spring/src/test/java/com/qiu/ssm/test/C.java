package com.qiu.ssm.test;

import com.qiu.ssm.annotation.di.Autowired;
import com.qiu.ssm.annotation.stereotype.Component;

@Haha
@Component
public class C {
    @Autowired
    private B b;

    @Log
    public void test() {
        System.out.println("C");
    }
}