package com.hql.sdk.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;


public final class LoggerUtil {
    private static String sTag = android.os.Process.myPid() + "";

    public static final int LEVEL_VERBOSE = 0;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARN = 3;
    public static final int LEVEL_ERROR = 4;
    public static final int LEVEL_NONE = 10;

    public static final int JSON_INDENT = 2;
    public static final int MIN_STACK_OFFSET = 3;

    public static int logLevel = LEVEL_VERBOSE;

    private LoggerUtil() {
    }

    private static LoggerUtil instance = new LoggerUtil();

    public static LoggerUtil getInstance() {
        return instance;
    }

    public static void init(int level, String tag) {
        logLevel = level;
        sTag = tag;
    }

    public static void v(String msg) {
        v(msg, msg);
    }

    public static void d(String msg) {
        d(msg, msg);
    }

    public static void i(String msg) {
        i(msg, msg);
    }

    public static void w(String msg) {
        w(msg, msg);
    }

    public static void e(String msg) {
        e(msg, msg);
    }

    public static void v(String tag, String msg) {
        if (LEVEL_VERBOSE >= logLevel && !TextUtils.isEmpty(msg)) {
            String s = getMethodNames();
            Log.v(String.format("%-24s", tag).replaceAll(" ", "-"), String.format(s, msg));
            //LogToFile.v(tag, String.format(s, msg));
        }
    }

    public static void d(String tag, String msg) {
        if (LEVEL_DEBUG >= logLevel && !TextUtils.isEmpty(msg)) {
            String s = getMethodNames();
            Log.d(String.format("%-24s", tag).replaceAll(" ", "-"), String.format(s, msg));
            //LogToFile.d(tag, String.format(s, msg));
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL_INFO >= logLevel && !TextUtils.isEmpty(msg)) {
            String s = getMethodNames();
            Log.i(String.format("%-24s", tag).replaceAll(" ", "-"), String.format(s, msg));
            //LogToFile.i(tag, String.format(s, msg));
        }
    }

    public static void w(String tag, String msg) {
        if (LEVEL_WARN >= logLevel && !TextUtils.isEmpty(msg)) {
            String s = getMethodNames();
            Log.w(String.format("%-24s", tag).replaceAll(" ", "-"), String.format(s, msg));
            //LogToFile.w(tag, String.format(s, msg));
        }
    }

    public static void e(String tag, String msg) {
        if (LEVEL_ERROR >= logLevel && !TextUtils.isEmpty(msg)) {
            String s = getMethodNames();
            Log.e(String.format("%-24s", tag).replaceAll(" ", "-"), String.format(s, msg));
            //LogToFile.e(tag, String.format(s, msg));
        }
    }

    /**
     * ??????json??????????????????,???log??????????????????????????? "system.out" ???????????????
     *
     * @param tag ???????????????????????????,???????????????
     */
    public static void json(String tag, String json) {
        if (TextUtils.isEmpty(json)) {
            d("Empty/Null json content", tag);
            return;
        }

        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                message = message.replaceAll("\n", "\n??? ");
                String s = getMethodNames();
                System.out.print(String.format(s, message));
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                message = message.replaceAll("\n", "\n??? ");
                String s = getMethodNames();
                System.out.print(String.format(s, message));

            }
        } catch (Exception e) {
            e.printStackTrace();
            e("Invalid Json", tag);
        }

    }


    /**
     * ??????????????????????????????,??????????????????,?????????????????????
     */
    private static String getMethodNames() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int stackOffset = getStackOffset(stackTrace);
        stackOffset++;
        //?????????
        StringBuilder threadNameSb = new StringBuilder(64);
        threadNameSb.append("[").append(Thread.currentThread().getName())
                .append("]");
        String threadName = String.format("%-13s", threadNameSb);
        //?????????
        String methodName = String.format("%-20s", stackTrace[stackOffset].getMethodName());
        //???????????????,??????
        StringBuilder fileLineNameSb = new StringBuilder(64);
        fileLineNameSb.append("(").append(stackTrace[stackOffset].getFileName())
                .append(":").append(stackTrace[stackOffset].getLineNumber())
                .append(") ").append("%s");
        String fileLineName = String.format("%-24s", fileLineNameSb);
        return threadName + methodName + fileLineName;
    }

    private static int getStackOffset(StackTraceElement... trace) {
        int i = MIN_STACK_OFFSET;
        while (i < trace.length) {
            String name = trace[i].getClassName();
            if (!LoggerUtil.class.getName().equalsIgnoreCase(name)) {
                return --i;
            }
            i++;
        }
        return -1;
    }

    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}