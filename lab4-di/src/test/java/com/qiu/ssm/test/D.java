package com.qiu.ssm.test;

import com.qiu.ssm.annotation.stereotype.Service;

@Service
public class D {
    public C c;

    public D(C c) {
        this.c = c;
    }
}
