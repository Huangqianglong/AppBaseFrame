package com.hql.appbaseframe.fragemnent;

import android.content.Context;

import com.hql.appbaseframe.base.module.BasePresent;
import com.hql.appbaseframe.base.module.IFragmentBaseView;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/3/10
 * <br /> Description :
 */
public class TestPresenter extends BasePresent<ITestFragment,ITestModel> implements ITestPresenter {
    public TestPresenter(Context mContext, ITestFragment view) {
        super(mContext, view);
    }

    @Override
    protected ITestFragment getView(IFragmentBaseView view) {
        return (ITestFragment) view;
    }

    @Override
    protected ITestModel getModel() {
        return new TestModel();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void init() {

    }
}
