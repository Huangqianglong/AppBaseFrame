package com.hql.appbaseframe.base.module;

/**
 * @author ly-huangql
 * <br /> Create time : 2020/2/14
 * <br /> Description :
 */
public interface IFragmentBasePresent {
    void onResume();
    void onPause();
    void onHiddenChanged(boolean hidden);
    void onDetach();
    void onDestroy();
    void init();

    /**
     * 清空数据
     */
    void resetData();
}
