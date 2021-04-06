package com.hql.sdk.control;


import com.hql.sdk.aidl.client.IResultListener;
import com.hql.sdk.aidl.client.TestClientBean;
import com.hql.sdk.base.STATE_CODE;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/4/1
 * <br /> Description : 提供给客户端调用的接口
 */

public class ClientAPI {
    private ServiceControl mServiceControl;

    public ClientAPI(ServiceControl control) {
        mServiceControl = control;
    }

    public int setOnResultListener(IResultListener listener) {
        if (null != mServiceControl) {
            return mServiceControl.setOnResultListener(listener);
        }
        return STATE_CODE.SERVICE_DISCONNECT;
    }

    public int sentTestData(TestClientBean bean) {
        if (null != mServiceControl) {
            return mServiceControl.sentTestData(bean);
        }
        return STATE_CODE.SERVICE_DISCONNECT;
    }

    public void onDestroy() {

    }
}
