package com.mobile.android.framework.base;

import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by xinming.xxm on 2016/10/13.
 */

public abstract class MicroService implements MicroContent{

    private AtlasWrapperApplicationContext mAtlasWrapperApplicationContext;

    public void attachContext(AtlasWrapperApplicationContext atlasWrapperApplicationContext) {
        mAtlasWrapperApplicationContext = atlasWrapperApplicationContext;
    }

    public AtlasWrapperApplicationContext getAtlasWrapperApplicationContext() {
        return mAtlasWrapperApplicationContext;
    }

    /**
     * 初始化服务
     * @param extras
     */
    public abstract void create(Bundle extras);

    /**
     * 销毁服务
     * @param extras
     */
    public abstract void destroy(Bundle extras);


    /**
     * 是否已经被激活（onCreate()被调用，onDestroy()还没有被调用）
     * @return
     */
    public abstract boolean isActivated();

    /**
     * 服务初始化回调
     * @param extras
     */
    protected abstract void onCreate(Bundle extras);

    /**
     * 销毁服务回调
     * @param extras
     */
    protected abstract void onDestroy(Bundle extras);


    @Override
    public void saveState(SharedPreferences.Editor editor) {
        onSaveState(editor);
    }

    @Override
    public void restoreState(SharedPreferences.Editor editor) {
        onRestoreState(editor);
    }
}
