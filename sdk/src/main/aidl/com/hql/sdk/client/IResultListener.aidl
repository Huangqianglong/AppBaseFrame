// IServiceResultListener.aidl
package com.hql.sdk.client;
import com.hql.sdk.service.TestServiceBackBean;
// Declare any non-default types here with import statements

interface IResultListener {
   void onSendClientMsg(in TestServiceBackBean serviceBackBean);
}
