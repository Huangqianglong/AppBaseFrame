// IServiceResultListener.aidl
package com.hql.sdk.aidl.service;
import com.hql.sdk.aidl.service.TestServiceBackBean;
// Declare any non-default types here with import statements

interface IServiceResultListener {
   void onServiceCallBack(in TestServiceBackBean serviceBackBean);
}
