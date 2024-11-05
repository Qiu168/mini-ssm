package com.qiu.ssm.test;

import com.qiu.ssm.annotation.stereotype.Controller;

@Controller
public class A {
    static {
        //没有初始化索引没有输出
        System.out.println("initialize");
    }
    int a;
    String b;
}
