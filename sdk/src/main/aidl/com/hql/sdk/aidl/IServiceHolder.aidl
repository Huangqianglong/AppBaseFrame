// IServiceHolder.aidl
package com.hql.sdk.aidl;

import com.hql.sdk.aidl.client.TestClientBean;
import com.hql.sdk.aidl.service.IServiceResultListener;
// Declare any non-default types here with import statements

interface IServiceHolder {
    void sendClientMsg(in TestClientBean clientBean );
    void setOnServiceListener(in IServiceResultListener listener);
}
