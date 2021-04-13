package com.hql.protocol.aidl.base;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.hql.protocol.control.SDKManger;
import com.hql.protocol.utils.LoggerUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author ly-huangql
 * <br /> Create time : 2021/4/1
 * <br /> Description : 客户端和服务端的基础交互数据
 */
public class DataModel implements Parcelable {
    public static final String MESSAGE_TYPE_DISPATCH = "dispatch";
    public static final String MESSAGE_TYPE_REQUEST = "request";
    public static final String MESSAGE_TYPE_REQUEST_SYNC = "requestsync";
    public static final String MESSAGE_TYPE_RESPONSE = "response";
    //private JSONObject data;
    private String message;
    private String messageType;
    private boolean needResponse;
    private int protocolId;
    private String requestAuthor;
    private String requestClientPackageName;
    private String requestCode;
    private String responseCode;
    private int statusCode;
    private String versionName;
    private JSONObject data;
    private String dataStr = "";

    public DataModel() {

    }

    protected DataModel(Parcel in) {
        message = in.readString();
        messageType = in.readString();
        needResponse = in.readByte() != 0;
        protocolId = in.readInt();
        requestAuthor = in.readString();
        requestClientPackageName = in.readString();
        requestCode = in.readString();
        responseCode = in.readString();
        statusCode = in.readInt();
        versionName = in.readString();
        dataStr = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(messageType);
        dest.writeByte((byte) (needResponse ? 1 : 0));
        dest.writeInt(protocolId);
        dest.writeString(requestAuthor);
        dest.writeString(requestClientPackageName);
        dest.writeString(requestCode);
        dest.writeString(responseCode);
        dest.writeInt(statusCode);
        dest.writeString(versionName);
        dest.writeString(dataStr);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataModel> CREATOR = new CREATOR();

    static class CREATOR implements Creator<DataModel> {

        @Override
        public DataModel createFromParcel(Parcel source) {
            return new DataModel(source);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }

        CREATOR() {

        }
    }

    public DataModel buildDisptachModel() {
        this.needResponse = false;
        this.requestAuthor = SDKManger.getInstance().getRequester();
        this.messageType = MESSAGE_TYPE_DISPATCH;
        this.statusCode = 200;
        return this;
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("requestCode", this.requestCode);
            jSONObject.put("responseCode", this.responseCode);
            jSONObject.put("needResponse", this.needResponse);
            jSONObject.put("protocolId", this.protocolId);
            jSONObject.put("versionName", this.versionName);
            jSONObject.put("requestAuthor", this.requestAuthor);
            jSONObject.put("message", this.message);
            jSONObject.put("messageType", this.messageType);
            jSONObject.put("statusCode", this.statusCode);
            jSONObject.put("data", getData());
        } catch (Throwable e) {
            LoggerUtil.e("DataModel", e.getMessage());
        }
        return jSONObject.toString();
    }

    public static DataModel parseJsonToTagModel(String jsonStr) {
        if (jsonStr != null && jsonStr.length() >= 1) {
            JSONObject jsonObject = null;
            DataModel baseDataModel = null;

            try {
                baseDataModel = new DataModel();
                jsonObject = new JSONObject(jsonStr);
                baseDataModel.requestCode = jsonObject.optString("requestCode");
                baseDataModel.responseCode = jsonObject.optString("responseCode");
                baseDataModel.needResponse = jsonObject.optBoolean("needResponse");
                baseDataModel.protocolId = jsonObject.optInt("protocolId");
                baseDataModel.versionName = jsonObject.optString("versionName");
                baseDataModel.requestAuthor = jsonObject.optString("requestAuthor");
                baseDataModel.message = jsonObject.optString("message");
                baseDataModel.messageType = jsonObject.optString("messageType");
                baseDataModel.statusCode = jsonObject.optInt("statusCode");
                baseDataModel.data = jsonObject.optJSONObject("data");
                baseDataModel.dataStr = jsonObject.optJSONObject("data").toString();
            } catch (JSONException var4) {
                var4.printStackTrace();
                Log.d("DataModel", "JSONException ERROR = " + var4.toString());
            }

            return baseDataModel;
        } else {
            return null;
        }
    }

    public String getRequestCode() {
        return this.requestCode;
    }

    public void setRequestCode(String str) {
        this.requestCode = str;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(String str) {
        this.responseCode = str;
    }

    public boolean isNeedResponse() {
        return this.needResponse;
    }

    public void setNeedResponse(boolean z) {
        this.needResponse = z;
    }

    public int getProtocolId() {
        return this.protocolId;
    }

    public void setProtocolId(int i) {
        this.protocolId = i;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionName(String str) {
        this.versionName = str;
    }

    public String getRequestAuthor() {
        return this.requestAuthor;
    }

    public void setRequestAuthor(String str) {
        this.requestAuthor = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public void setMessageType(String str) {
        this.messageType = str;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int i) {
        this.statusCode = i;
    }

    public JSONObject getData() {

        try {
            JSONObject jsonObject = new JSONObject(dataStr);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void setData(JSONObject data) {
        this.data = data;
        this.dataStr = data.toString();
    }

    public String getRequestClientPackageName() {
        return this.requestClientPackageName;
    }

    public void setRequestClientPackageName(String str) {
        this.requestClientPackageName = str;
    }

}
