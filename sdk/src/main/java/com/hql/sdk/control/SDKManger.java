package com.hql.sdk.control;

import android.content.Context;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/4/1
 * <br /> Description : 负责和服务的连接，和对服务的操作
 */
public class SDKManger {
    private ClientAPI mClientAPI;
    private ServiceControl mServiceControl;

    private SDKManger() {
    }


    private static class SDKMangerHolder {
        private static SDKManger mInstance = new SDKManger();
    }

    public static SDKManger getInstance() {
        return SDKMangerHolder.mInstance;
    }

    public void init(Context context) {
        if (null == mServiceControl) {
            mServiceControl = new ServiceControl(context);
        }
        if (null == mClientAPI) {
            mClientAPI = new ClientAPI(mServiceControl);
        }

    }

    public void relase(Context context) {
        if (null == mServiceControl) {
            mServiceControl.release(context);
        }
    }

    public ClientAPI getAPI() {
        return mClientAPI;
    }
}
