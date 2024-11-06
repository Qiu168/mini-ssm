package com.qiu.ssm.util;

import java.lang.reflect.InvocationTargetException;

/**
 * @author _qqiu
 */
public abstract class ExceptionUtil {
    public static Throwable getCause(Throwable e) {
        while (e.getClass() == InvocationTargetException.class) {
            e = e.getCause();
        }
        return e;
    }
}
