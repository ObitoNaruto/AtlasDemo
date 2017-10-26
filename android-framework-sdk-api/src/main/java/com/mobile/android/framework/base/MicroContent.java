package com.mobile.android.framework.base;

import android.content.SharedPreferences;

/**
 * Created by xinming.xxm on 2016/10/13.
 */

public interface MicroContent {

    /**
     * 保存状态
     * @param editor
     */
    void saveState(SharedPreferences.Editor editor);

    /**
     * 恢复状态
     * @param editor
     */
    void restoreState(SharedPreferences.Editor editor);

    /**
     * 保存状态回调
     * @param editor
     */
    void onSaveState(SharedPreferences.Editor editor);

    /**
     * 恢复状态回调
     * @param editor
     */
    void onRestoreState(SharedPreferences.Editor editor);
}
