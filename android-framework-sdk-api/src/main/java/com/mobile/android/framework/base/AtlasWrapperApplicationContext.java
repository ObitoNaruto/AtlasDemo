package com.mobile.android.framework.base;

import com.mobile.android.framework.AtlasWrapperApplication;

/**
 * Created by xinming.xxm on 2016/10/12.
 */
public interface AtlasWrapperApplicationContext {

    /**
     * 附着上下文
     * @param application
     */
    void attachContext(AtlasWrapperApplication application);

    /**
     * 注册服务
     * @param interfaceName 服务接口名
     * @param service 服务对象
     * @param <T>
     * @return
     */
    <T> boolean registerService(String interfaceName, T service);

    /**
     * 反注册服务
     * @param interfaceName 服务接口名
     * @param <T> 服务
     * @return
     */
    <T> T unregisterService(String interfaceName);

    /**
     * 后去android上下文
     * @return
     */
    AtlasWrapperApplication getApplicationContext();

    /**
     * 获取扩展服务
     * @param serviceName
     * @param <T>
     * @return
     */
    <T> T getExtSystemService(String serviceName);

    /**
     * 获取扩展服务
     * @param serviceClass
     * @param <T>
     * @return
     */
    <T> T getExtSystemService(Class<T> serviceClass);


    void clearState();
}
