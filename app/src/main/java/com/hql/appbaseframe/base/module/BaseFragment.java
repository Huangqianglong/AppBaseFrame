package com.hql.appbaseframe.base.module;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author ly-huangql
 * <br /> Create time : 2020/2/14
 * <br /> Description :
 */
public abstract class BaseFragment<Y extends IFragmentBasePresent> extends Fragment implements IFragmentBaseView {
    protected View mLayoutView;
    protected Context mContext;
    public Y mPresent;
    protected boolean isFirstLoad = true;
    //protected boolean isVisible = false;
    private final static String TAG = "BaseFragment";
    private final static int ACTION_INIT = 10001;
    private Handler mBaseHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ACTION_INIT:
                    lazyLoadData();
                    onCreatePresent();
                    mPresent.init();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LoggerUtil.d(TAG,"onCreate>>>>>");
        mContext = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //LoggerUtil.d(TAG,"onCreateView>>>>>");
        mLayoutView = inflater.inflate(getLayoutID(), container, false);
        return mLayoutView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //LoggerUtil.d(TAG,"onActivityCreated>>>>>");
        initBundleData(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //LoggerUtil.d(TAG,"onViewCreated>>>>>");
        initView(view);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresent.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresent.onDetach();
        //LoggerUtil.d(TAG,"onDetach>>>>>");
        //OnResetChildFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        //LoggerUtil.d(TAG,"onResume>>>>>");
        if (getUserVisibleHint()) {
            onVisible();
        }
        if (null != mPresent) {
            mPresent.onResume();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mPresent.onHiddenChanged(hidden);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mPresent) {
            mPresent.onPause();
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //LoggerUtil.d(TAG,"setUserVisibleHint>>>>>");
        //this.isVisible = isVisibleToUser;
        if (getUserVisibleHint()) {
            onVisible();
        }
    }


    /**
     * 可见, 执行延迟加载
     */
    protected void onVisible() {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                if (isFirstLoad && getView() != null) {
                    //如果这里执行处理，实际上view未真正加载到窗口，故这里延迟加载，让view先显示出来，优化体验
                    mBaseHandler.sendEmptyMessageDelayed(ACTION_INIT,0);
                    isFirstLoad = false;
                }
                return false;
            }
        });

    }

    public void resetData() {
        mPresent.resetData();
    }

    protected abstract int getLayoutID();

    /**
     * 处理传递的Bundle
     *
     * @param savedInstanceState
     */
    protected abstract void initBundleData(@Nullable Bundle savedInstanceState);


    protected abstract void initView(View view);

    protected abstract void onCreatePresent();

    /**
     * 延迟加载，可以作为第一次显示的时候加载数据
     */
    protected abstract void lazyLoadData();
}
