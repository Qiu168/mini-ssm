package com.qiu.ssm;

import com.qiu.ssm.annotation.AliasFor;
import com.qiu.ssm.annotation.stereotype.Component;
import com.qiu.ssm.annotation.stereotype.Service;
import com.qiu.ssm.util.AnnotationUtil;
import com.qiu.ssm.util.Assert;
import org.junit.Test;

public class AliasTest {
    @Service("AAA")
//    @TestAnno("666")
    static class Test{

    }
    public static void main(String[] args) {
        Component component = AnnotationUtil.getMergedAnnotation(Test.class, Component.class);
        Assert.isTrue(component!=null);
        System.out.println(component.value());
        Assert.isTrue(component.value().equals("AAA"));
    }
}
