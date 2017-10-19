package com.mobile.android.framework.service;

import android.content.SharedPreferences;

import com.mobile.android.framework.base.AtlasWrapperApplicationContext;
import com.mobile.android.framework.base.MicroContent;
import com.mobile.android.framework.base.MicroService;

/**
 * Created by xinming.xxm on 2016/10/13.
 */

public abstract class ServiceManager implements MicroContent {

    /**
     * 附着上下文
     *
     * @param atlasWrapperApplicationContext
     */
    public abstract void attachContext(AtlasWrapperApplicationContext atlasWrapperApplicationContext);

    /**
     * 注册扩展服务
     *
     * @param serviceName 服务接口名称
     * @param service     服务对象
     * @param <T>
     * @return
     */
    public abstract <T> boolean registerService(String serviceName, T service);

    /**
     * 反注册扩展服务，服务必然已经在初始化
     *
     * @param serviceName 服务接口名称
     * @param <T>
     * @return
     */
    public abstract <T> T unregisterService(String serviceName);

    /**
     * 获取扩展服务
     *
     * @param serviceName 服务接口名称
     * @param <T>
     * @return
     */
    public abstract <T> T getExtSystemService(String serviceName);

    /**
     * 销毁服务
     * @param service 销毁的服务对象
     */
    public abstract void destroyService(MicroService service);

    /**
     * 退出
     */
    public abstract void exit();

    @Override
    public void saveState(SharedPreferences.Editor editor) {
        onSaveState(editor);
    }

    @Override
    public void restoreState(SharedPreferences.Editor editor) {
        onRestoreState(editor);
    }
}

