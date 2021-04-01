package com.hql.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.hql.sdk.IServiceHolder;
import com.hql.sdk.client.TestClientBean;
import com.hql.sdk.service.IServiceResultListener;
import com.hql.sdk.service.TestServiceBackBean;
import com.hql.sdk.utils.LoggerUtil;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/4/1
 * <br /> Description :
 */
public class MyService extends Service {
    private IServiceResultListener mListener;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return new SDKBinder();
    }

    private class SDKBinder extends IServiceHolder.Stub {

        @Override
        public void sendClientMsg(TestClientBean clientBean) throws RemoteException {
            LoggerUtil.d("hql", "收到客户端发送消息 getCustomMsg:" + clientBean.getCustomMsg()
                    + ">default>" + clientBean.getMsg()
            );
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        mListener.onServiceCallBack(new TestServiceBackBean("服务返回数据！！！！"));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }, 5000);
        }

        @Override
        public void setOnServiceListener(IServiceResultListener listener) throws RemoteException {
            Log.d("hql", "收到SDK 设置的服务回调 ");
            mListener = listener;
        }
    }

}
