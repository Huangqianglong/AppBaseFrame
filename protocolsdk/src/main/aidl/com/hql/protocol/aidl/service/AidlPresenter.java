package com.hql.protocol.aidl.service;

import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.hql.protocol.aidl.base.DataModel;
import com.hql.protocol.aidl.client.IJsonProtocolBinder;
import com.hql.protocol.aidl.client.IResultListener;
import com.hql.protocol.utils.LoggerUtil;


/**
 * @author ly-huangql
 * <br /> Create time : 2021/4/9
 * <br /> Description : 向服务端发送数据的接口
 */
public class AidlPresenter {
    private static final String TAG = AidlPresenter.class.getSimpleName() + "hql";
    private IServiceHolder mIServiceHolder;
    private RemoteCallbackList<IResultListener> mClientListener = new RemoteCallbackList<>();
    private MsgQueueHandler mMsgQueueHandler;
    public AidlPresenter(){
        mMsgQueueHandler= new MsgQueueHandler(onQueueUpListener);
    }
    //客户端主要通过这里拿到binder通信
    public IJsonProtocolBinder.Stub jsonProtocolBinder = new IJsonProtocolBinder.Stub() {
        @Override
        public void request(String jason, String author) throws RemoteException {
            mIServiceHolder.sendToService(DataModel.parseJsonToTagModel(jason));
        }

        @Override
        public void registerReceive(String author, IResultListener listener) throws RemoteException {
            //LoggerUtil.d(TAG, "客户端注入监听器");
            mClientListener.register(listener);
        }

        @Override
        public void unregisterReceive(String author, IResultListener listener) throws RemoteException {
            mClientListener.unregister(listener);
        }
    };

    private void dispatchAutoMessage(DataModel dataModel) {
        if (null != dataModel) {
            synchronized (mClientListener) {
                try {
                    int size = mClientListener.beginBroadcast();
                    LoggerUtil.d(TAG, ">>客户端size " + size);
                    for (int i = 0; i < size; i++) {
                        IResultListener listener = mClientListener.getBroadcastItem(i);
                        LoggerUtil.d(TAG, ">>发送消息 " + dataModel);
                        listener.onRequstBack(dataModel);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                } finally {
                    mClientListener.finishBroadcast();
                }
            }
        }
    }

    /**
     * beginBroadcast的方式有可能会阻塞，先放到队列
     * @param dataModel
     */
    public void sendToClient(DataModel dataModel){
        mMsgQueueHandler.queueUp(dataModel);
    }

    public void setJsonProtocolCallback(IServiceHolder holder) {
        mIServiceHolder = holder;
    }
    MsgQueueHandler.onQueueUpListener onQueueUpListener = new MsgQueueHandler.onQueueUpListener() {
        @Override
        public void dispatchTask(DataModel dataModel) {
            dispatchAutoMessage(dataModel);
        }
    };

    public void onDestroy() {
        mClientListener.kill();
        mMsgQueueHandler.onDestroy();

    }
}
