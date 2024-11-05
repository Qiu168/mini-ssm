package com.qiu.ssm.test;

import com.qiu.ssm.annotation.di.Autowired;
import com.qiu.ssm.annotation.stereotype.Controller;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Controller
@Getter
@Setter
public class B {
    @Autowired
    private A a;
    int[] arr;
}
