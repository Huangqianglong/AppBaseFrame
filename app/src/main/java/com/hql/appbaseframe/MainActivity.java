package com.hql.appbaseframe;

import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.hql.appbaseframe.base.module.BaseActivity;
import com.hql.appbaseframe.base.utils.LoggerUtil;
import com.hql.appbaseframe.base.utils.TextUtils;
import com.hql.appbaseframe.fragemnent.TestFragment;
import com.hql.sdk.base.JsonData;
import com.hql.sdk.client.IResultListener;
import com.hql.sdk.client.TestClientBean;
import com.hql.sdk.control.SDKManger;
import com.hql.sdk.service.TestServiceBackBean;

public class MainActivity extends BaseActivity {
    private TestFragment mTestFragment;
    private FragmentManager mFragmentManager;
    private RelativeLayout mFragmentContainer;
    private Fragment mCurrentFragment;
    private static final String TAG = MainActivity.class.getSimpleName();
    private Gson mGson;

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
        LoggerUtil.d(TAG, "设置布局");
        SDKManger.getInstance().init(this);
    }

    @Override
    public void initData() {
        mGson = new Gson();
        LoggerUtil.d(TAG, "初始化数据");
        initFragmentConfig(this);
        SDKManger.getInstance().getAPI().setOnResultListener(resultListener);

    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SDKManger.getInstance().destroy();
    }

    IResultListener.Stub resultListener = new IResultListener.Stub() {
        @Override
        public void onSendClientMsg(TestServiceBackBean serviceBackBean)  {
            JsonData jsonData = mGson.fromJson(serviceBackBean.getJsonData(), JsonData.class);
            LoggerUtil.d(TAG, ">>>>>>>客户端收到服务端返回数据：" + serviceBackBean.getCustomMsg()
                    + ">default>" + serviceBackBean.getData()
                    + ">default>" + jsonData.getParamz().getFeeds().get(0).getData().getSummary()
            );
        }
    };
    public void sendMsg2Service(View view){
        TestClientBean testClientBean = new TestClientBean("这是hql发的测试数据");
        testClientBean.setJsonData("{\n" +
                "  \"paramz\": {\n" +
                "    \"feeds\": [\n" +
                "      {\n" +
                "        \"id\": 299076,\n" +
                "        \"oid\": 288340,\n" +
                "        \"category\": \"article\",\n" +
                "        \"data\": {\n" +
                "          \"subject\": \"荔枝新闻3.0：不止是阅读\",\n" +
                "          \"summary\": \"江苏广电旗下资讯类手机应用“荔枝新闻”于近期推出全新升级换代的3.0版。\",\n" +
                "          \"cover\": \"/Attachs/Article/288340/3e8e2c397c70469f8845fad73aa38165_padmini.JPG\",\n" +
                "          \"pic\": \"\",\n" +
                "          \"format\": \"txt\",\n" +
                "          \"changed\": \"2015-09-22 16:01:41\"\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"PageIndex\": 1,\n" +
                "    \"PageSize\": 20,\n" +
                "    \"TotalCount\": 53521,\n" +
                "    \"TotalPage\": 2677\n" +
                "  }\n" +
                "}");
        LoggerUtil.d(TAG, ">>>>>>>初始化发送   " + testClientBean.getCustomMsg());
        SDKManger.getInstance().getAPI().sentTestData(testClientBean);


    }
}
