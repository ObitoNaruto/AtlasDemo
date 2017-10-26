package com.mobile.android.log;

/**
 * Created by xinming.xxm on 2016/10/13.
 */

public class LogManager {

    private static BaseLog mBaseLog = new DefaultLogImpl();

    public static void setBaseLog(BaseLog newBaseLog) {
        mBaseLog = newBaseLog;
    }

    public static BaseLog getInstance() {
        return mBaseLog;
    }
}
