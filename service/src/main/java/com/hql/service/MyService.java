package com.hql.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.google.gson.Gson;
import com.hql.sdk.IServiceHolder;
import com.hql.sdk.base.JsonData;
import com.hql.sdk.client.TestClientBean;
import com.hql.sdk.control.SDKManger;
import com.hql.sdk.service.IServiceResultListener;
import com.hql.sdk.service.TestServiceBackBean;
import com.hql.sdk.utils.LoggerUtil;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/4/1
 * <br /> Description :
 */
public class MyService extends Service {
    private Gson mGson;
    private IServiceResultListener mListener;

    private static final String SERVICE_CHANNEL = "sdk_service";
    private static final String SERVICE_NOTIFY_NAME = "sdk_service_name";
    private final static String TAG = MyService.class.getSimpleName() + "hql";
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mGson = new Gson();
        LoggerUtil.d("ceshi", "<<Service<<<<<Object :" + SDKManger.getInstance().getTest() + ">>PID>" + android.os.Process.myPid());
        setForegroundService();
        setForegroundService();
    }

    private void setForegroundService() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(SERVICE_CHANNEL, SERVICE_NOTIFY_NAME,
                    NotificationManager.IMPORTANCE_LOW);
            mChannel.setSound(null, null);
            notificationManager.createNotificationChannel(mChannel);
            Notification notification = new Notification.Builder(this,
                    SERVICE_CHANNEL)
                    .setSound(null)
                    .build();
            startForeground(1, notification);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new SDKBinder();
    }

    private class SDKBinder extends IServiceHolder.Stub {

        @Override
        public void sendClientMsg(final TestClientBean clientBean) throws RemoteException {
            JsonData data = mGson.fromJson(clientBean.getJsonData(), JsonData.class);
            LoggerUtil.d("hql", "收到客户端发送消息 getCustomMsg:" + clientBean.getCustomMsg()
                    + ">default>" + clientBean.getMsg()
                    + ">json>" + data.getParamz().getFeeds().get(0).getData().getSummary()
            );
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        TestServiceBackBean bean = new TestServiceBackBean("服务返回数据！！！！");
                        JsonData newData = mGson.fromJson(clientBean.getJsonData(), JsonData.class);

                        newData.getParamz().getFeeds().get(0).getData().setSummary("这是改过的Summary");
                        bean.setJsonData(mGson.toJson(newData));
                        mListener.onServiceCallBack(bean);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoggerUtil.d(TAG, TAG + "onDestroy ");
    }
}
