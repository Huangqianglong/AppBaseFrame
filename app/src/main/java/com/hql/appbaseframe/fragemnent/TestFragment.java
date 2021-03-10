package com.hql.appbaseframe.fragemnent;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.hql.appbaseframe.R;
import com.hql.appbaseframe.base.module.BaseFragment;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/3/10
 * <br /> Description :
 */
public class TestFragment extends BaseFragment<ITestPresenter> implements ITestFragment{
    @Override
    protected int getLayoutID() {
        return R.layout.content_main;
    }

    @Override
    protected void initBundleData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void onCreatePresent() {
        mPresent = new TestPresenter(mContext,this);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
