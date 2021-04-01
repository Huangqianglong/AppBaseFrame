package com.hql.sdk.client;

import android.os.Parcel;
import android.os.Parcelable;

import com.hql.sdk.base.BaseModel;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/4/1
 * <br /> Description :
 */
public class TestClientBean extends BaseModel implements Parcelable {
    public static final Creator<TestClientBean> CREATOR = new CREATOR();
    private String msg = "这是客户端尝试向服务端发送的数据";
    private String customMsg;

    static class CREATOR implements Creator<TestClientBean> {

        @Override
        public TestClientBean createFromParcel(Parcel source) {
            return new TestClientBean(source);
        }

        @Override
        public TestClientBean[] newArray(int size) {
            return new TestClientBean[size];
        }

        CREATOR() {

        }
    }

    protected TestClientBean(Parcel in) {
        super(in);
        msg = in.readString();
        customMsg = in.readString();
    }

    public TestClientBean(String msg) {
        customMsg = msg;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.msg);
        dest.writeString(this.customMsg);
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCustomMsg() {
        return customMsg;
    }

    public void setCustomMsg(String customMsg) {
        this.customMsg = customMsg;
    }
}
