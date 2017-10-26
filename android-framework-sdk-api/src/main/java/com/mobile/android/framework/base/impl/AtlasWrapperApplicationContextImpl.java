package com.mobile.android.framework.base.impl;

import com.mobile.android.framework.AtlasWrapperApplication;
import com.mobile.android.framework.base.AtlasWrapperApplicationContext;
import com.mobile.android.framework.service.ServiceManager;
import com.mobile.android.framework.service.common.ExternalServiceManager;
import com.mobile.android.framework.service.impl.ServiceManagerImpl;

/**
 * Created by xinming.xxm on 2016/10/12.
 */

public class AtlasWrapperApplicationContextImpl implements AtlasWrapperApplicationContext {

    private AtlasWrapperApplication mAtlasWrapperApplication;

    private ServiceManager mServiceManager;

    @Override
    public void attachContext(AtlasWrapperApplication application) {
        mAtlasWrapperApplication = application;
        init();
    }

    @Override
    public AtlasWrapperApplication getApplicationContext() {
        return mAtlasWrapperApplication;
    }

    @Override
    public <T> boolean registerService(String interfaceName, T service) {
        return mServiceManager.registerService(interfaceName, service);
    }

    @Override
    public <T> T unregisterService(String interfaceName) {
        return mServiceManager.unregisterService(interfaceName);
    }

    @Override
    public <T> T getExtSystemService(Class<T> serviceClass) {
        //根据类对象获取其类名
        String serviceName = getExtSystemServiceName(serviceClass);
        return serviceName != null ? (T) getExtSystemService(serviceName) : null;
    }

    @Override
    public <T> T getExtSystemService(String serviceName) {
        if (mServiceManager == null) {
            return null;
        }
        T service = mServiceManager.getExtSystemService(serviceName);
        if (service != null) {
            return service;
        }
        ExternalServiceManager externalServiceManager = mServiceManager.getExtSystemService(ExternalServiceManager.class.getName());
        if (externalServiceManager != null) {
            return (T) externalServiceManager.getExtSystemService(serviceName);
        }
        return null;
    }

    @Override
    public void clearState() {
        /// TODO: 17-10-12  
    }

    private void init() {
        //服务管理框架serviceManager初始化
        mServiceManager = new ServiceManagerImpl();
        mServiceManager.attachContext(this);

        //启动加载
        new BootLoaderManagerImpl().attachContext(AtlasWrapperApplicationContextImpl.this).load();
    }

    private String getExtSystemServiceName(Class<?> serviceClass) {
        if (serviceClass == null) {
            return null;
        }
        return serviceClass.getName();
    }
}
