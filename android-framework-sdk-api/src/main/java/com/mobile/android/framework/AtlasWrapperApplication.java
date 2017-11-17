package com.mobile.android.framework;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.taobao.atlas.bundleInfo.AtlasBundleInfoManager;
import android.taobao.atlas.framework.Atlas;
import android.taobao.atlas.runtime.ActivityTaskMgr;
import android.taobao.atlas.runtime.ClassNotFoundInterceptorCallback;
import android.text.TextUtils;
import android.widget.Toast;

import com.mobile.android.framework.base.AtlasWrapperApplicationContext;
import com.mobile.android.framework.base.impl.AtlasWrapperApplicationContextImpl;
import com.mobile.android.log.LogManager;

import org.osgi.framework.BundleException;

import java.io.File;

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
        LogManager.getInstance().d("AtlasWrapperApplication", "onCreate called!");
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
        Atlas.getInstance().setClassNotFoundInterceptorCallback(new ClassNotFoundInterceptorCallback() {
            @Override
            public Intent returnIntent(Intent intent) {
                LogManager.getInstance().d("DemoApplication", "returnIntent called!");
                final String className = intent.getComponent().getClassName();
                final String bundleName = AtlasBundleInfoManager.instance().getBundleForComponet(className);
                LogManager.getInstance().d("DemoApplication", "returnIntent called! className:" + className + ", bundleName:" + bundleName);

                if (!TextUtils.isEmpty(bundleName) && !AtlasBundleInfoManager.instance().isInternalBundle(bundleName)) {

                    //远程bundle
                    Activity activity = ActivityTaskMgr.getInstance().peekTopActivity();
                    File remoteBundleFile = new File(activity.getExternalCacheDir(),"lib" + bundleName.replace(".","_") + ".so");

                    String path = "";
                    if (remoteBundleFile.exists()){
                        path = remoteBundleFile.getAbsolutePath();
                    }else {
                        Toast.makeText(activity, " 远程bundle不存在，请确定 : " + remoteBundleFile.getAbsolutePath() , Toast.LENGTH_LONG).show();
                        return intent;
                    }


                    PackageInfo info = activity.getPackageManager().getPackageArchiveInfo(path, 0);
                    try {
                        Atlas.getInstance().installBundle(info.packageName, new File(path));
                    } catch (BundleException e) {
                        Toast.makeText(activity, " 远程bundle 安装失败，" + e.getMessage() , Toast.LENGTH_LONG).show();

                        e.printStackTrace();
                    }

                    activity.startActivities(new Intent[]{intent});

                }

                return intent;
            }
        });
        //初始化AtlasWrapperApplicationContext
        try {
//            mAtlasWrapperApplicationContext = (AtlasWrapperApplicationContext) Class.forName("com.mobile.android.framework.base.impl.AtlasWrapperApplicationContextImpl").newInstance();
            mAtlasWrapperApplicationContext = new AtlasWrapperApplicationContextImpl();
            mAtlasWrapperApplicationContext.attachContext(this);
        } catch (Exception e) {
            LogManager.getInstance().e("AtlasWrapperApplication", "init throw a exception.", e);
            e.printStackTrace();
        }
    }
}
