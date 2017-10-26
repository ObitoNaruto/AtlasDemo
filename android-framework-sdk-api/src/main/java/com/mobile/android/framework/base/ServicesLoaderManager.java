package com.mobile.android.framework.base;

/**
 * Created by xinming.xxm on 2016/10/20.
 */

public interface ServicesLoaderManager {

    /**
     * 加载前操作
     */
    void preLoad();

    /**
     * 加载服务
     */
    void load();

    /**
     * 加载后操作
     */
    void afterLoad();

    /**
     * 注册服务
     * @param serviceName
     * @param service
     */
    void registerService(String serviceName, MicroService service);

    /**
     * 注册懒加载服务
     * @param serviceName
     * @param className
     */
    void registerLazyService(String serviceName, String className);


}
