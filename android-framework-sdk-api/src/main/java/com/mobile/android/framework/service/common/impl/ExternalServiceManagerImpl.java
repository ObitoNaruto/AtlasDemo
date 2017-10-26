package com.mobile.android.framework.service.common.impl;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.mobile.android.framework.service.ServiceDescription;
import com.mobile.android.framework.service.common.ExternalServiceManager;
import com.mobile.android.framework.service.ext.ExternalService;
import com.mobile.android.log.LogManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by xinming.xxm on 2016/10/17.
 */

public class ExternalServiceManagerImpl extends ExternalServiceManager {

    private Map<String, ExternalService> mInitializedExtServices;

    private Map<String, ServiceDescription> mInjectionExtServices;

    @Override
    public void onSaveState(SharedPreferences.Editor editor) {

    }

    @Override
    public void onRestoreState(SharedPreferences.Editor editor) {

    }

    @Override
    protected void onCreate(Bundle extras) {
        init();
    }

    @Override
    protected void onDestroy(Bundle extras) {

    }

    @Override
    public ExternalService getExtSystemService(String serviceName) {
        ExternalService externalService = mInitializedExtServices.get(serviceName);
        //服务已经初始化，直接返回
        if (externalService != null) {
            return externalService;
        }
        ServiceDescription serviceDescription = mInjectionExtServices.get(serviceName);
        if(serviceDescription == null) {
            return null;
        }
        synchronized (serviceDescription) {
            try{
                //获取类对象
                Class<?> clazz = Class.forName(serviceDescription.getServiceClassName());
                //对象初始化
                externalService = (ExternalService) clazz.newInstance();
                //向刚初始化的服务对象注入上下文
                externalService.attachContext(getAtlasWrapperApplicationContext());
                //服务初始化，各个服务可以在onCreate中进行初始化操作
                externalService.create(null);
                //对服务进行内存缓存
                mInitializedExtServices.put(serviceDescription.getInterfaceClass(), externalService);
            }catch (ClassNotFoundException | InstantiationException | IllegalAccessException e){
                LogManager.getInstance().e(this.getClass().getName(), "getExtSystemService failed!", e);
            }
        }
        return externalService;
    }

    private void init() {
        mInitializedExtServices = new ConcurrentHashMap<>();
        mInjectionExtServices = new ConcurrentHashMap<>();
    }
}


