package com.hql.protocol.control;

import android.content.Context;
import android.os.IBinder;
import android.text.TextUtils;

import com.hql.protocol.aidl.base.DataModel;
import com.hql.protocol.aidl.service.AidlPresenter;
import com.hql.protocol.aidl.service.IServiceHolder;
import com.hql.protocol.utils.LoggerUtil;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/4/1
 * <br /> Description : 负责和服务的连接，和对服务的操作
 */

public class SDKManger {
    private final static String TAG = SDKManger.class.getSimpleName() + "hql";
    private AidlPresenter mAidlPresenter;
    private String mServiceVision;
    private Context mContext;
    //private ServiceControl mServiceControl;
    /**
     * 发送方标志，用来区分请求的发送方
     */
    private String mRequester;

    private SDKManger() {
        mServiceVision = "2021.04.10";
    }

    public String getRequester() {
        if (TextUtils.isEmpty(this.mRequester) && this.mContext != null) {
            this.mRequester = this.mContext.getApplicationContext().getPackageName();
        }
        return this.mRequester;
    }

    public void setRequester(String requester) {
        mRequester = requester;
    }

    private static class SDKMangerHolder {
        private static SDKManger mInstance = new SDKManger();
    }

    public static SDKManger getInstance() {
        return SDKMangerHolder.mInstance;
    }

    //    public void init(Context context) {
//        if (null == mServiceControl) {
//            mServiceControl = new ServiceControl(context);
//        }
//        if (null == mClientAPI) {
//            mClientAPI = new ClientAPI(mServiceControl);
//        }
//    }
    public void init(Context context, String author, IServiceHolder holder) {
        mContext = context;
        if (this.mAidlPresenter == null) {
            this.mAidlPresenter = new AidlPresenter();
        }
        this.mAidlPresenter.setJsonProtocolCallback(holder);
    }

    public void onDestroy() {
        if (this.mAidlPresenter == null) {
            mAidlPresenter.onDestroy();
        }
        mContext = null;
    }
//
//    public ClientAPI getAPI() {
//        return mClientAPI;
//    }

    public IBinder initProtocolBinder() {
        if (this.mAidlPresenter == null) {
            this.mAidlPresenter = new AidlPresenter();
        }
        return this.mAidlPresenter.jsonProtocolBinder;

    }

    public void setServerVersion(String serverVersion) {
        mServiceVision = serverVersion;
    }

    public void sendToClient(DataModel dataModel) {
        LoggerUtil.d(TAG, "sendToClient  >>" + dataModel);
        if (dataModel != null) {
            LoggerUtil.d(TAG, "mAidlPresenter  >>" + mAidlPresenter);
            if (this.mAidlPresenter != null) {
                this.mAidlPresenter.sendToClient(dataModel);
            }

        }
    }
}
