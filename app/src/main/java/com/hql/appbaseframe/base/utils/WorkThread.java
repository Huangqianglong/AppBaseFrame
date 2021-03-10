package com.hql.appbaseframe.base.utils;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/3/10
 * <br /> Description : 单线程工具类
 */
public class WorkThread {
    private static HandlerThread mWorkerThread;
    private static Handler mWorkerHandler;
    private static class WorkThreadHolder{
        private static WorkThread mInstance = new WorkThread();
    }
    public static WorkThread getInstance(){
        return WorkThreadHolder.mInstance;
    }
    private WorkThread(){
        initWorkThread();
    }
    private void initWorkThread() {
        mWorkerThread = new HandlerThread("" + System.currentTimeMillis());
        mWorkerThread.start();
        mWorkerHandler = new Handler(mWorkerThread.getLooper());
    }
    public void runOnWorkThread(Runnable runnable, int delayMillis) {
        if (mWorkerThread.getThreadId() == android.os.Process.myTid() && delayMillis == 0) {
            mWorkerHandler.post(runnable);
        } else {
            // If we are not on the worker thread, then post to the worker handler
            mWorkerHandler.postDelayed(runnable, delayMillis);
        }
    }
}
