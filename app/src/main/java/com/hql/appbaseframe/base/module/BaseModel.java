package com.hql.appbaseframe.base.module;


import com.hql.appbaseframe.base.utils.WorkThread;

/**
 * @author ly-huangql
 * <br /> Create time : 2020/2/14
 * <br /> Description :
 */
public abstract class BaseModel implements IFragmentBaseModel {
    public BaseModel() {

    }

    public void resumeRegisterListener() {

    }

    public void unregisterListener() {

    }

    protected void runOnWorkThread(Runnable runnable, int delayMillis) {
        WorkThread.getInstance().runOnWorkThread(runnable, delayMillis);
    }

    @Override
    public void resetData() {

    }
}
