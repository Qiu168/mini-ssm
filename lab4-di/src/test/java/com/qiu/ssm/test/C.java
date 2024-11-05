package com.qiu.ssm.test;

import com.qiu.ssm.annotation.di.Autowired;
import com.qiu.ssm.annotation.stereotype.Component;

@Component
public class C {
    @Autowired
    public D d;

//    public C(D d) {
//        this.d = d;
//    }
}
