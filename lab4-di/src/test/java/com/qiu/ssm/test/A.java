package com.qiu.ssm.test;

import com.qiu.ssm.annotation.di.Autowired;
import com.qiu.ssm.annotation.di.Value;
import com.qiu.ssm.annotation.stereotype.Controller;
import lombok.Getter;
import lombok.Setter;

@Controller
@Getter
@Setter
public class A {
    static {
        //没有初始化索引没有输出
        System.out.println("initialize");
    }
    int a;
    @Value("${server.port}")
    Integer port;
    @Autowired
    private B b;
}
