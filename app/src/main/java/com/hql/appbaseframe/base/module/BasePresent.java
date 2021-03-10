package com.hql.appbaseframe.base.module;

import android.content.Context;

/**
 * @author ly-huangql
 * <br /> Create time : 2020/2/14
 * <br /> Description :
 */
public abstract class BasePresent<V extends IFragmentBaseView, M extends IFragmentBaseModel> implements IFragmentBasePresent {
    protected Context mContext;
    protected V mView;
    protected M mModel;
    /**
     * 上次进入页面的登陆状态
     */
    protected boolean mLastState = false;
    protected final static int STATE_LOGIN = 1;
    protected final static int STATE_LOGOUT = 2;
    /**
     * 当前是否隐藏
     */
    protected boolean mHidden = false;

    public BasePresent(Context mContext, V view) {
        this.mContext = mContext;
        this.mView = getView(view);
        this.mModel = getModel();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        mHidden = hidden;
        if (null != mModel) {
            //LoggerUtil.d("hql","当前hidden状态 "+hidden);
            if (hidden) {
                mModel.unregisterListener();
            } else {
                mModel.resumeRegisterListener();
            }
        }


    }

    @Override
    public void resetData() {
        mModel.resetData();
    }

    protected abstract V getView(IFragmentBaseView view);

    protected abstract M getModel();

    /**
     * 登陆状态变更
     */
    protected void loginStateChange(boolean isLogin) {

    }

    public void onDestroy() {

    }
}
