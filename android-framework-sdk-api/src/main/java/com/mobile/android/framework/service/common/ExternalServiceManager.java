package com.mobile.android.framework.service.common;

import com.mobile.android.framework.service.ext.ExternalService;

/**
 * Created by xinming.xxm on 2016/10/17.
 */

public abstract class ExternalServiceManager extends CommonService {

    /**
     * 获取扩展服务
     * @param serviceName 服务接口类名
     * @return
     */
    public abstract ExternalService getExtSystemService(String serviceName);
}
