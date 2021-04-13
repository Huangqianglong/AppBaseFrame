package com.hql.protocol.aidl.service;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;


import com.hql.protocol.aidl.base.DataModel;

import java.util.ArrayList;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/4/12
 * <br /> Description :
 */
public class MsgQueueHandler {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ArrayList<DataModel> mDataList = new ArrayList<>();
    private boolean isPosting = false;
    private onQueueUpListener mQueueUpListener;
    private HandlerThread mWorkerThread;
    private Handler mWorkerHandler;
    private QueueRunnable mQueueRunnable = new QueueRunnable();

    public MsgQueueHandler(onQueueUpListener listener) {
        mQueueUpListener = listener;
        mWorkerThread = new HandlerThread("" + System.currentTimeMillis());
        mWorkerThread.start();
        mWorkerHandler = new Handler(mWorkerThread.getLooper());
    }


    private DataModel getLastMsg() {
        return mDataList.size() > 0 ? mDataList.get(0) : null;
    }

    private void removeLastMsg() {
        if (mDataList.size() > 0) {
            mDataList.remove(mDataList.size() - 1);
        }
    }

    private class QueueRunnable implements Runnable {
        DataModel dataModel;

        public void setDataModel(DataModel dataModel) {
            this.dataModel = dataModel;
        }

        @Override
        public void run() {
            //加入队列
            mDataList.add(dataModel);
            //如果空闲，则进行处理
            if (!isPosting) {
                mHandler.post(updateItemRunnable);
            }
        }
    }

    /**
     * 消息排队等待处理
     *
     * @param dataModel
     */
    public void queueUp(DataModel dataModel) {
        synchronized (mQueueRunnable) {
            mQueueRunnable.setDataModel(dataModel);
            mWorkerHandler.post(mQueueRunnable);
        }
    }


    Runnable updateItemRunnable = new Runnable() {
        @Override
        public void run() {
            isPosting = true;
            while (!mDataList.isEmpty()) {
                dispatchTask(getLastMsg());
                removeLastMsg();
            }
            isPosting = false;
        }
    };

    private void dispatchTask(DataModel dataModel) {
        mQueueUpListener.dispatchTask(dataModel);
    }

    public void onDestroy() {
        mHandler.removeCallbacks(updateItemRunnable);
        mDataList.clear();
    }

    public interface onQueueUpListener {
        void dispatchTask(DataModel dataModel);
    }
}
