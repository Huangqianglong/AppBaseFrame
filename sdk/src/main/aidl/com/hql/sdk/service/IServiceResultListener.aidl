// IServiceResultListener.aidl
package com.hql.sdk.service;
import com.hql.sdk.service.TestServiceBackBean;
// Declare any non-default types here with import statements

interface IServiceResultListener {
   void onServiceCallBack(in TestServiceBackBean serviceBackBean);
}
