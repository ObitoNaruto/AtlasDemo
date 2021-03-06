package com.mobile.android.log;

/**
 * Created by xinming.xxm on 2016/10/13.
 */

public interface BaseLog {

    int v(String msg);

    int v(String tag, String msg);

    int v(String tag, String msg, Throwable tr);


    int d(String msg);

    int d(String tag, String msg);

    int d(String tag, String msg, Throwable tr);


    int i(String msg);

    int i(String tag, String msg);

    int i(String tag, String msg, Throwable tr);


    int w(String msg);

    int w(String tag, String msg);

    int w(String tag, String msg, Throwable tr);


    int e(String msg);

    int e(String tag, String msg);

    int e(String tag, String msg, Throwable tr);
}
