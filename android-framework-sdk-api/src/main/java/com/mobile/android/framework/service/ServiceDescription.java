package com.mobile.android.framework.service;

import com.mobile.android.framework.base.MicroDescription;

/**
 * Created by xinming.xxm on 2016/10/18.
 */

public class ServiceDescription extends MicroDescription {

    /**
     * 接口服务名称
     */
    private String mInterfaceClassName;

    /**
     * 当前服务是否懒加载
     */
    private boolean mIsLazy = true;


    public boolean isLazy() {
        return mIsLazy;
    }

    public ServiceDescription setLazy(boolean isLazy) {
        mIsLazy = isLazy;
        return this;
    }

    public String getInterfaceClass() {
        return mInterfaceClassName;
    }

    public ServiceDescription setInterfaceClass(String interfaceClass) {
        mInterfaceClassName = interfaceClass;
        return this;
    }
}
