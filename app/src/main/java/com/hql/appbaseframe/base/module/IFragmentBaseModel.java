package com.hql.appbaseframe.base.module;

/**
 * @author ly-huangql
 * <br /> Create time : 2020/2/14
 * <br /> Description :
 */
public interface IFragmentBaseModel {
    void onDetach();
    void onDestroy();
    void resumeRegisterListener();
    void unregisterListener();
    void resetData();
}
