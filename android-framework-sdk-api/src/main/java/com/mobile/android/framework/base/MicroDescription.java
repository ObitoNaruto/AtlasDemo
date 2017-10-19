package com.mobile.android.framework.base;

/**
 * Created by xinming.xxm on 2016/10/18.
 */

public abstract class MicroDescription {

    /**
     * app id
     */
    private String mAppId;

    /**
     * 服务名称
     */
    private String mName;

    /**
     * 服务实现类名称
     */
    private String mServiceClassName;

    public MicroDescription setAppId(String appId) {
        mAppId = appId;
        return this;
    }

    public String getAppId() {
        return mAppId;
    }

    public MicroDescription setName(String serviceName) {
        mName = serviceName;
        return this;
    }

    public String getName (){
        return mName;
    }

    public MicroDescription setServiceClassName(String serviceClassName) {
        mServiceClassName = serviceClassName;
        return this;
    }

    public String getServiceClassName() {
        return mServiceClassName;
    }
}
