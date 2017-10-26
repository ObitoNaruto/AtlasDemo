package com.mobile.android.android.framework.sdk.biz;

import com.mobile.android.framework.base.MicroService;
import com.mobile.android.framework.base.ServicesLoaderManager;
import com.mobile.android.log.LogManager;

/**
 * Created by xinming.xxm on 2016/10/20.
 */

public class ServicesLoaderManagerImpl implements ServicesLoaderManager {

    private final static String TAG = ServicesLoaderManagerImpl.class.getSimpleName();

    @Override
    public void preLoad() {

    }

    @Override
    public void load() {
        LogManager.getInstance().d(TAG, "load called!");
    }

    @Override
    public void afterLoad() {

    }

    @Override
    public void registerService(String serviceName, MicroService service) {

    }

    @Override
    public void registerLazyService(String serviceName, String className) {

    }
}
