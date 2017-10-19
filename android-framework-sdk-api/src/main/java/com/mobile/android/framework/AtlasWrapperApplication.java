package com.mobile.android.framework;

import android.app.Application;

import com.mobile.android.framework.base.AtlasWrapperApplicationContext;
import com.mobile.android.log.LogManager;

/**
 * Created by xinming.xxm on 2016/10/12.
 */

public class AtlasWrapperApplication extends Application {

    /**
     * android系统上下文
     */
    private static AtlasWrapperApplication mInstance;

    /**
     * app上下文
     */
    private AtlasWrapperApplicationContext mAtlasWrapperApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
    }

    @Override
    public void onTerminate() {
        mAtlasWrapperApplicationContext.clearState();
        super.onTerminate();
    }

    public AtlasWrapperApplicationContext getAtlasWrapperApplicationContext() {
        return mAtlasWrapperApplicationContext;
    }

    public static AtlasWrapperApplication getInstance() {
        return mInstance;
    }

    private void init() {
        //初始化AtlasWrapperApplicationContext
        try {
            mAtlasWrapperApplicationContext = (AtlasWrapperApplicationContext) Class.forName("com.mobile.android.framework.base.impl.AtlasWrapperApplicationContextImpl").newInstance();
            mAtlasWrapperApplicationContext.attachContext(this);
        } catch (Exception e) {
            LogManager.getInstance().e("AtlasWrapperApplication", "init throw a exception.", e);
            e.printStackTrace();
        }
    }
}
