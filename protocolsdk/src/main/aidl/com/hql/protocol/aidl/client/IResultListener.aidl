// IServiceResultListener.aidl
package com.hql.protocol.aidl.client;

import com.hql.protocol.aidl.base.DataModel;
// Declare any non-default types here with import statements
/**
* 接听接口，客户端接收服务端返回的消息
* */
interface IResultListener {
   void onRequstBack(in DataModel dataModel);
}
