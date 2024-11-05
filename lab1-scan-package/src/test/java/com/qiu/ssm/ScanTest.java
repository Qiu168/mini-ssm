package com.qiu.ssm;

import com.qiu.ssm.test.A;
import com.qiu.ssm.test.B;
import com.qiu.ssm.util.Assert;
import com.qiu.ssm.util.ClassUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class ScanTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //获取class，但是不进行初始化
        Set<Class<?>> classSet = ClassUtil.getClassSet("com.qiu.ssm.test");
        Assert.isTrue(classSet.size()==2);
        Assert.isTrue(classSet.contains(A.class));
        Assert.isTrue(classSet.contains(B.class));
        System.out.println(classSet);
        //实例数据，此时才会进行初始化
        for (Class<?> aClass : classSet) {
            if(aClass==A.class){
                Constructor<?> nonArgsConstructor = aClass.getConstructor();
                Object o = nonArgsConstructor.newInstance();
            }
        }
    }
}
