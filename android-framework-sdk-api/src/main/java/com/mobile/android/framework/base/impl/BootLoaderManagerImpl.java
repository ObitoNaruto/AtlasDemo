package com.mobile.android.framework.base.impl;

import android.app.Application;

import com.mobile.android.framework.base.AtlasWrapperApplicationContext;
import com.mobile.android.framework.base.BootLoaderManager;
import com.mobile.android.framework.service.common.ExternalServiceManager;
import com.mobile.android.framework.service.common.impl.ExternalServiceManagerImpl;

/**
 * Created by xinming.xxm on 2016/10/19.
 */

public class BootLoaderManagerImpl implements BootLoaderManager {

    private AtlasWrapperApplicationContext mAtlasWrapperApplicationContext;

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

    }
}
