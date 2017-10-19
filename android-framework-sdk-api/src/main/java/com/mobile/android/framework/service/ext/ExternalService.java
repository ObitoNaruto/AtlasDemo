package com.mobile.android.framework.service.ext;

import android.os.Bundle;

import com.mobile.android.framework.base.MicroService;

/**
 * Created by xinming.xxm on 2016/10/13.
 */
public abstract class ExternalService extends MicroService {

    private boolean mIsActivated = false;

    @Override
    public boolean isActivated() {
        return mIsActivated;
    }

    @Override
    public void create(Bundle extras) {
        onCreate(extras);
        mIsActivated = true;
    }

    @Override
    public void destroy(Bundle extras) {
//        getNarutoApplicationContext().onDestroyContent(this);
        onDestroy(extras);
        mIsActivated = false;
    }
}
