package com.hql.sdk.control;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;

import com.hql.sdk.IServiceHolder;
import com.hql.sdk.base.STATE_CODE;
import com.hql.sdk.client.IResultListener;
import com.hql.sdk.client.TestClientBean;
import com.hql.sdk.service.IServiceResultListener;
import com.hql.sdk.service.TestServiceBackBean;
import com.hql.sdk.utils.LoggerUtil;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/4/1
 * <br /> Description :通过aidl,负责打通服务和SDK的控制器
 */
public class ServiceControl {
    private final static String SERVICE_PACKAGE_NAME = "com.hql.service";
    private static final String SERVICE_ACTION_NAME = "com.hql.test_service";
    private IServiceHolder mService;
    private boolean isBindService;
    private final static String TAG = ServiceControl.class.getSimpleName();
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Context mContext;
    /**
     * sdk--> client的回调
     */
    private IResultListener mClientListenr;

    public ServiceControl(Context context) {
        mContext = context;
        tryToBindService();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IServiceHolder.Stub.asInterface(service);
            try {
                mService.setOnServiceListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBindService = false;
            mService = null;
            boolean res = tryToBindService();
        }
    };

    public void release(Context context) {
        context.unbindService(mConnection);
    }

    public int sentTestData(TestClientBean bean) {
        if (null != mService) {
            try {
                mService.sendClientMsg(bean);
                return STATE_CODE.SERVICE_OK;
            } catch (RemoteException e) {
                e.printStackTrace();
                return STATE_CODE.SERVICE_EXCEPTION;
            }
        }
        return STATE_CODE.SERVICE_DISCONNECT;
    }

    IServiceResultListener.Stub mListener = new IServiceResultListener.Stub() {
        @Override
        public void onServiceCallBack(TestServiceBackBean serviceBackBean) throws RemoteException {
            if (null != mClientListenr) {
                mClientListenr.onSendClientMsg(serviceBackBean);
            }
        }
    };

    private boolean tryToBindService() {
        mHandler.removeCallbacks(bindRunnable);
        try {
            Intent intent = new Intent();
            intent.setPackage(SERVICE_PACKAGE_NAME);
            intent.setAction(SERVICE_ACTION_NAME);
            isBindService = mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        } catch (Exception ex) {
            isBindService = false;
            LoggerUtil.e(TAG, "服务绑定异常>>>>>>>" + ex.toString());
        }

        if (!isBindService) {
            mHandler.removeCallbacks(bindRunnable);
            mHandler.postDelayed(bindRunnable, 2000);
        }
        LoggerUtil.d(TAG, "服务绑定结果 isBindService：" + isBindService);
        return isBindService;
    }

    private Runnable bindRunnable = new Runnable() {
        @Override
        public void run() {
            tryToBindService();
        }
    };

    public int setOnResultListener(IResultListener listener) {
        mClientListenr = listener;
        return STATE_CODE.SERVICE_OK;
    }
}
