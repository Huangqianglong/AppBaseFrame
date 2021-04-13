package com.hql.protocol.base;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/4/1
 * <br /> Description :
 */
public interface STATE_CODE {
    /**
     * 远程服务异常
     */
    int SERVICE_EXCEPTION = -1;
    /**
     * 服务断开
     */
    int SERVICE_DISCONNECT = 0;
    /**
     * 发送成功
     */
    int SERVICE_OK = 0;

}
