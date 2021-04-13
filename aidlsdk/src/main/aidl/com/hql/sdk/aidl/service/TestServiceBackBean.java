package com.hql.sdk.aidl.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.hql.sdk.aidl.base.BaseModel;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/4/1
 * <br /> Description :
 */
public class TestServiceBackBean extends BaseModel implements Parcelable {
    public static final Creator<TestServiceBackBean> CREATOR = new CREATOR();
    private String data = "这是服务返回的数据";
    private String customMsg;

    static class CREATOR implements Creator<TestServiceBackBean> {

        @Override
        public TestServiceBackBean createFromParcel(Parcel source) {
            return new TestServiceBackBean(source);
        }

        @Override
        public TestServiceBackBean[] newArray(int size) {
            return new TestServiceBackBean[size];
        }

        CREATOR() {

        }
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(data);
        dest.writeString(customMsg);
    }

    protected TestServiceBackBean(Parcel in) {
        super(in);
        data = in.readString();
        customMsg = in.readString();
    }

    public TestServiceBackBean(String msg) {
        customMsg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCustomMsg() {
        return customMsg;
    }

    public void setCustomMsg(String customMsg) {
        this.customMsg = customMsg;
    }
}
