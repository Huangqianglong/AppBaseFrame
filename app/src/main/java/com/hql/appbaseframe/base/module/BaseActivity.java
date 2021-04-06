package com.hql.appbaseframe.base.module;

import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.hql.appbaseframe.base.utils.LoggerUtil;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/3/31
 * <br /> Description :
 */
public abstract class BaseActivity extends FragmentActivity {
    private boolean isInit = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        initView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!isInit){
            isInit = true;
            Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                @Override
                public boolean queueIdle() {
                    initData();
                    return false;
                }
            });
        }

    }
    public abstract void initView();
    public abstract void initData();
    public abstract int getLayoutID();
}
