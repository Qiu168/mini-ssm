package com.qiu.ssm.resource;

/**
 * @author _qiu
 */
public interface ResourceName {
    String PORT="server.port";
    String ACTIVE="spring.profiles.active";
    static String[] getResourceNames(){
        return new String[]{PORT,ACTIVE};
    }

}
