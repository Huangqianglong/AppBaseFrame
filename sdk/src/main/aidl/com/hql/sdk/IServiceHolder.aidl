// IServiceHolder.aidl
package com.hql.sdk;

import com.hql.sdk.client.TestClientBean;
import com.hql.sdk.service.IServiceResultListener;
// Declare any non-default types here with import statements

interface IServiceHolder {
    void sendClientMsg(in TestClientBean clientBean );
    void setOnServiceListener(in IServiceResultListener listener);
}
