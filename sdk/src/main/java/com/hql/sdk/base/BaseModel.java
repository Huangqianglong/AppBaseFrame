package com.hql.sdk.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/4/1
 * <br /> Description : 客户端和服务端的基础交互数据
 */
public class BaseModel implements Parcelable {
    /**
     * 协议数据类型
     */
    private int protocolType;
    /**
     * 数据来源
     */
    private String packageName;

    /**
     * 回调ID
     */
    private int callBackID;
    /**
     * json格式数据
     */
    private String jsonData;
    public BaseModel(){

    }
    protected BaseModel(Parcel in) {
        protocolType = in.readInt();
        packageName = in.readString();
        callBackID = in.readInt();
        jsonData = in.readString();
    }

    public static final Creator<BaseModel> CREATOR = new Creator<BaseModel>() {
        @Override
        public BaseModel createFromParcel(Parcel in) {
            return new BaseModel(in);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(protocolType);
        dest.writeString(packageName);
        dest.writeInt(callBackID);
        dest.writeString(jsonData);
    }


    public int getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(int protocolType) {
        this.protocolType = protocolType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getCallBackID() {
        return callBackID;
    }

    public void setCallBackID(int callBackID) {
        this.callBackID = callBackID;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
