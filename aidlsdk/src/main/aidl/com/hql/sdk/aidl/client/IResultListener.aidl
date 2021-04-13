// IServiceResultListener.aidl
package com.hql.sdk.aidl.client;

import com.hql.sdk.aidl.service.TestServiceBackBean;
// Declare any non-default types here with import statements

interface IResultListener {
   void onSendClientMsg(in TestServiceBackBean serviceBackBean);
}
