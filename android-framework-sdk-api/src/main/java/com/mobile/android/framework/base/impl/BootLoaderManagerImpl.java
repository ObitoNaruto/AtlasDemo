package com.mobile.android.framework.base.impl;

import android.app.Application;

import com.mobile.android.framework.base.AtlasWrapperApplicationContext;
import com.mobile.android.framework.base.BootLoaderManager;
import com.mobile.android.framework.base.ServicesLoaderManager;
import com.mobile.android.framework.service.common.ExternalServiceManager;
import com.mobile.android.framework.service.common.impl.ExternalServiceManagerImpl;
import com.mobile.android.log.LogManager;

/**
 * Created by xinming.xxm on 2016/10/19.
 */

public class BootLoaderManagerImpl implements BootLoaderManager {

    private AtlasWrapperApplicationContext mAtlasWrapperApplicationContext;
    private ServicesLoaderManager mServicesLoaderManager;

    @Override
    public BootLoaderManagerImpl attachContext(AtlasWrapperApplicationContext atlasWrapperApplicationContext) {
        mAtlasWrapperApplicationContext = atlasWrapperApplicationContext;
        return this;
    }

    @Override
    public AtlasWrapperApplicationContext getContext() {
        return mAtlasWrapperApplicationContext;
    }

    @Override
    public void load() {
        Application application = mAtlasWrapperApplicationContext.getApplicationContext();
        if (application == null) {
            return;
        }

        //1.初始化扩展服务管理服务，用于加载扩展所有的扩展服务，常驻内存中
        ExternalServiceManager externalServiceManager = new ExternalServiceManagerImpl();
        externalServiceManager.attachContext(mAtlasWrapperApplicationContext);
        mAtlasWrapperApplicationContext.registerService(ExternalServiceManager.class.getName(), externalServiceManager);

        //2.
        String servicesLoaderManagerStr = "com.mobile.android.android.framework.sdk.biz.ServicesLoaderManagerImpl";
        try{
            Class<?> clazz = application.getClassLoader().loadClass(servicesLoaderManagerStr);
            mServicesLoaderManager = (ServicesLoaderManager)clazz.newInstance();
            mServicesLoaderManager.load();
        }catch (Exception e){
            LogManager.getInstance().e(this.getClass().getSimpleName(), "ServicesLoaderManager init failed!", e);
        }

    }
}
