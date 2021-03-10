package com.hql.appbaseframe.base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.hql.appbaseframe.R;

import me.drakeet.support.toast.ToastCompat;

/**
 * @author ly-huangql
 * <br /> Create time : 2020/2/28
 * <br /> Description :
 */
public class ToastUtil {
    private static ToastCompat mToast = null;
    private static Context mContext;
    private static TextView textView;
    private static final String TAG = ToastUtil.class.getSimpleName();
    private final static int yOffset = 120;

    public static void init(Context context) {
        mContext = context;//context.getApplicationContext();
        textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.layout_toast, null, false);
    }

    public static void resetContext(Context context) {
        LoggerUtil.d(TAG, "弹出Toast,重置");
        mContext = context;
        mToast = ToastCompat.makeText(mContext, "", Toast.LENGTH_SHORT);
    }


    //    public static boolean checkNetAndToast(){
//        boolean hasNet = MapApplication.isNetAvailabel();
//        if(!hasNet){
//            showToast(R.string.personal_no_network_notify);
//        }
//        return hasNet;
//    }
    @SuppressLint("ShowToast")
    public static void showToast(final String text) {
//        if (mToast == null) {
//            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
//        } else {
//            mToast.setText(text);
//            mToast.setDuration(Toast.LENGTH_SHORT);
//        }
//        mToast.show();
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoggerUtil.d(TAG, "1弹出toast String: " + text);
                if (!((Activity) mContext).isFinishing() && !TextUtils.isEmpty(text)) {
//                    cancelLastToast();
                    if (mToast == null) {
                        mToast = ToastCompat.makeText(mContext, text, Toast.LENGTH_SHORT);
                    }
                    textView.setText(text);
                    mToast.setView(textView);
                    mToast.setGravity(Gravity.BOTTOM, 0, yOffset);
                    LoggerUtil.d(TAG, "2弹出toast String: " + text);
                    mToast.show();
                }
            }
        });


    }

    public static void showToast(final int msgId) {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!((Activity) mContext).isFinishing()) {
//                    cancelLastToast();
                    if (mToast == null) {
                        mToast = ToastCompat.makeText(mContext, mContext.getString(msgId), Toast.LENGTH_SHORT);
                    }
                    textView.setText(msgId);
                    mToast.setView(textView);
                    LoggerUtil.d(TAG, "弹出toast msgId: " + textView.getText().toString());
                    mToast.show();
                }

            }
        });

    }


    public static void cancelLastToast() {
        // bug#23499241:  移除fd,避免: InputChannel-JNI: Error 24 dup channel fd ***
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}
