// IServiceHolder.aidl
package com.hql.protocol.aidl.service;

import com.hql.protocol.aidl.base.DataModel;
// Declare any non-default types here with import statements

interface IServiceHolder {
    /**
    * 客户端向服务端发送消息
    * */
    void sendToService(in DataModel dataModel );
}
