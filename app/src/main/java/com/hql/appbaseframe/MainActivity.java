package com.hql.appbaseframe;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hql.appbaseframe.base.module.BaseActivity;
import com.hql.appbaseframe.base.utils.LoggerUtil;
import com.hql.appbaseframe.base.utils.TextUtils;
import com.hql.appbaseframe.fragemnent.TestFragment;

public class MainActivity extends BaseActivity {
    private TestFragment mTestFragment;
    private FragmentManager mFragmentManager;
    private RelativeLayout mFragmentContainer;
    private Fragment mCurrentFragment;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        initFragmentConfig(this);

    }

    void loadFragment(View view) {
        if (TextUtils.isEmpty(mTestFragment)) {
            createFragment();
        } else {
            if (mTestFragment.isVisible()) {
                hideFragment(mTestFragment);
            } else {
                showFragment(mTestFragment, mTestFragment.getClass().getSimpleName());
            }
        }

    }

    private void createFragment() {
        mTestFragment = new TestFragment();
        showFragment(mTestFragment, TestFragment.class.getSimpleName());
    }

    private void hideFragment(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setCustomAnimations(
                R.anim.slide_enter,
                R.anim.slide_exit
        );
        if (null != fragment && fragment.isAdded()) {
            transaction.hide(fragment);
        }
        transaction.commit();
    }

    private void showFragment(Fragment fragment, String tag) {
        FragmentTransaction transition = mFragmentManager.beginTransaction();
        transition.setCustomAnimations(
                R.anim.slide_enter,
                R.anim.slide_exit
        );
        if (null != fragment && fragment.isAdded()) {
            transition.hide(fragment);
        }

        if (fragment.isAdded()) {
            transition.show(fragment);
        } else {
            transition.add(R.id.rl_fragment_container, fragment, tag);
        }
        transition.commit();
        LoggerUtil.d(TAG, " 执行动画 transaction：" + System.currentTimeMillis());
        mCurrentFragment = fragment;
    }

    private void initFragmentConfig(FragmentActivity activity) {
        if (null == mFragmentContainer) {
            mFragmentContainer = findViewById(R.id.rl_fragment_container);
        }
        if (null == mFragmentManager) {
            mFragmentManager = activity.getSupportFragmentManager();
        }
    }

    @Override
    public void initView() {
        LoggerUtil.d(TAG,"设置布局");
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initData() {
        LoggerUtil.d(TAG,"初始化数据");
        initFragmentConfig(this);
    }
}
