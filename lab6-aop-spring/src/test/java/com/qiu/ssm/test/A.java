package com.qiu.ssm.test;

import com.qiu.ssm.annotation.stereotype.Component;

@Component
@Haha
public class A {
    @Log
    public void test(){
        System.out.println("A");
    }
}
