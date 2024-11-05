package com.qiu.ssm.util;



/**
 * @author _qqiu
 */
public abstract class StringUtil {
    public static boolean isEmpty(String str){
        if (str != null){
            str = str.trim();
        }
        return null==str||"".equals(str);
    }

    /**
     * 判断字符串是否为非空
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    };
}