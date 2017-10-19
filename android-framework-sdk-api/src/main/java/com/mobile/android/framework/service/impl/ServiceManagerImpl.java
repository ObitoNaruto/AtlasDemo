package com.mobile.android.framework.service.impl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import com.mobile.android.framework.base.AtlasWrapperApplicationContext;
import com.mobile.android.framework.base.MicroService;
import com.mobile.android.framework.service.ServiceManager;
import com.mobile.android.framework.service.common.CommonService;
import com.mobile.android.log.LogManager;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xinming.xxm on 2016/10/13.
 */

public class ServiceManagerImpl extends ServiceManager {

    private AtlasWrapperApplicationContext mAtlasWrapperApplicationContext;

    /**
     * 存储已经被初始化的服务
     */
    private Map<String, Object> mServices;

    /**
     * 存储懒加载的服务
     */
    private Map<String, String> mLazyServices;

    public ServiceManagerImpl() {
        init();
    }

    @Override
    public void attachContext(AtlasWrapperApplicationContext atlasWrapperApplicationContext) {
        mAtlasWrapperApplicationContext = atlasWrapperApplicationContext;
    }

    @Override
    public <T> boolean registerService(String serviceName, T service) {
        if (service instanceof MicroService) {
            //已经加载的服务内存缓存
            return mServices.put(serviceName, service) != null;
        } else if (service instanceof String) {
            //懒加载类型服务内存缓存
            return mLazyServices.put(serviceName, (String) service) != null;
        } else {
            //用于扩展其他类型服务
            return mServices.put(serviceName, service) != null;
        }
    }

    @Override
    public <T> T unregisterService(String serviceName) {
        //在内存中先反注册掉懒加载进来的服务数据
        mLazyServices.remove(serviceName);
        //然后在内存中反注册掉已经初始化的服务
        return (T) mServices.remove(serviceName);
    }

    @Override
    public <T> T getExtSystemService(String serviceName) {
        //内存缓存中已经包含待查找的服务，取出直接返回
        if (mServices.containsKey(serviceName)) {
            return (T) mServices.get(serviceName);
        } else if (mLazyServices.containsKey(serviceName)) {//懒加载的服务
            //懒加载的服务进行初始化，然后内存缓存起来
            String serviceClassName = mLazyServices.get(serviceName);
            if (TextUtils.isEmpty(serviceClassName)) {
                return null;
            }
            synchronized (serviceClassName) {
                //双重检查
                if (mServices.containsKey(serviceName)) {
                    return (T) mServices.get(serviceName);
                }
                //约定好，业务方定义的扩展服务都是CommonService类型服务
                CommonService service = null;
                try {
                    //通过当前上下文的classLoader获取Class对象
                    Class<?> clazz = mAtlasWrapperApplicationContext
                            .getApplicationContext()
                            .getClassLoader()
                            .loadClass(serviceClassName);
                    //通过反射进行服务的初始化
                    service = (CommonService) clazz.newInstance();
                } catch (ClassNotFoundException e) {
                    LogManager.getInstance().e(ServiceManagerImpl.class.getName(), "getExtSystemService failed!", e);
                } catch (InstantiationException e) {
                    LogManager.getInstance().e(ServiceManagerImpl.class.getName(), "getExtSystemService failed!", e);
                } catch (IllegalAccessException e) {
                    LogManager.getInstance().e(ServiceManagerImpl.class.getName(), "getExtSystemService failed!", e);
                }
                if (service != null) {
                    //为服务注入项目上下文环境
                    service.attachContext(mAtlasWrapperApplicationContext);
                    //将初始化后的服务内存缓存起来
                    mServices.put(serviceName, service);
                }
                return (T) service;
            }
        }
        return null;
    }

    @Override
    public void destroyService(MicroService service) {
        if (service == null) {
            LogManager.getInstance().d(service.toString() + "has not been existed!");
            return;
        }
        Set<String> keys = mServices.keySet();
        MicroService microService;
        Object obj;
        for (String key : keys) {
            obj = mServices.get(key);
            if (obj instanceof MicroService) {
                microService = (MicroService) obj;
                if (service == microService) {
                    mServices.remove(key);
                    break;
                }
            }
        }
    }

    @Override
    public void onSaveState(SharedPreferences.Editor editor) {
        //保存状态参数回调,通知所有服务保存自己参数
        for (Object object : mServices.values()) {
            if (object instanceof MicroService) {
                ((MicroService) object).saveState(editor);
            }
        }
    }

    @Override
    public void onRestoreState(SharedPreferences.Editor editor) {
        //恢复状态参数回调，通知所有服务恢复自己参数
        for (Object object : mServices.values()) {
            if (object instanceof MicroService) {
                ((MicroService) object).restoreState(editor);
            }
        }
    }

    @Override
    public void exit() {
        Object[] values = mServices.values().toArray();
        MicroService service;
        for (Object object : values) {
            if (object instanceof MicroService) {
                service = (MicroService) object;
                if (service.isActivated()) {
                    //服务处在激活状态，调用服务的自毁接口进行释放
                    service.destroy(null);
                }
            }
        }
        //内存缓存释放
        mServices.clear();
        mLazyServices.clear();
    }

    /**
     * 初始化
     */
    private void init() {
        mServices = new ConcurrentHashMap<>();
        mLazyServices = new ConcurrentHashMap<>();
    }
}
