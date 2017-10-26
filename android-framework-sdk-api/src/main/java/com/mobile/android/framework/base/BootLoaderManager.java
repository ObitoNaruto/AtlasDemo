package com.mobile.android.framework.base;

/**
 * Created by xinming.xxm on 2016/10/19.
 */

public interface BootLoaderManager {

    /**
     * 注入上下文
     * @param atlasWrapperApplicationContext
     */
    BootLoaderManager attachContext(AtlasWrapperApplicationContext atlasWrapperApplicationContext);

    /**
     * 获取上下文环境
     * @return
     */
    AtlasWrapperApplicationContext getContext();

    /**
     * 启动加载
     */
    void load();
}
