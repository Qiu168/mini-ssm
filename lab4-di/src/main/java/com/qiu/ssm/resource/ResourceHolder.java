package com.qiu.ssm.resource;

/**
 * @author _qiu
 */
public abstract class ResourceHolder {
    static ResourceLoader resource;

    public static ResourceLoader getResourceLoader() {
        return resource;
    }

    public static void setResourceLoader(ResourceLoader resourceLoader) {
        resource = resourceLoader;
    }
}
