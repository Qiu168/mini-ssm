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
    public static String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return String.valueOf(chars);
    }
    /**
     * 从全类名中得到类名
     * @param name 全类名
     * @return 首字母小写的类名
     */
    public static String getBeanName(String name) {
        return toLowerFirstCase(name.substring(name.lastIndexOf('.') + 1));
    }
}
