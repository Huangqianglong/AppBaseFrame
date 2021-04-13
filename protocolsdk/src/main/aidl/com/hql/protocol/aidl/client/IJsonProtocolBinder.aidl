// IJsonProtocolBinder.aidl
package com.hql.protocol.aidl.client;

// Declare any non-default types here with import statements

import com.hql.protocol.aidl.client.IResultListener;

interface IJsonProtocolBinder {
  void request(String jason, String author);

      void registerReceive(String author, IResultListener listener);

      void unregisterReceive(String var1, IResultListener listener);
}
