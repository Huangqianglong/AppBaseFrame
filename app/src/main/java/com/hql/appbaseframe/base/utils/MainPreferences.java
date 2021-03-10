package com.hql.appbaseframe.base.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hql.appbaseframe.base.utils.encryption.AESEncryptor;

/**
 * @version V1.0
 * @Title: Preferences.java
 * @Description: SharedPreferences配置管理
 */
public class MainPreferences {
    private final static String TAG = "MainPreferences";

    private MainPreferences() {
    }

    private static SharedPreferences prefs;

    private static SharedPreferences prefsCookie;
    /**
     * @Fields SHAREDNAME : 文件名
     */
    public static final String SHAREDNAME = "bl_prefs";
    public static final String SHAREPREFERENCE_COOKIE_NAME = "bl_cookie_prefs";
    public static final int SHAREPREFERENCE_TAG_DEFAULT = 0;
    public static final int SHAREPREFERENCE_TAG_COOKIE = 1;


    public static final String KEY_GAS_LIKE = "KeyGasLike";
    public static final String KEY_ENABLE_EAGLE = "EnableEagle";

    public static final String KEY_GUIDE_ENDURANCE_POINT = "key_guide_endurance_point";
    public static final String KEY_GUIDE_ENDURANCE_POSITION_SERVICE = "key_guide_endurance_position_service";
    public static final String KEY_GUIDE_ENDURANCE_POSITION = "key_guide_endurance_position";
    public static final String KEY_INIT_COPY_RES = "key_init_copy_res";

    /**
     * @param app
     * @return void
     * @Title: init
     * @description 要在app里初始化
     */
    public static void init(Application app) {
        prefs = app.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
    }

    /**
     * @param app
     * @param pref_name 新的文件名
     * @description 重置添加新的pre文件
     * @author lizihan
     * @version V1.0
     */
    public static void init(Application app, String pref_name) {
        prefsCookie = app.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
    }

    /**
     * 得到存储 的sharepreference值
     *
     * @param name
     * @param defaultValue 默认值
     * @return
     */
    public static String getValue(String name, String defaultValue) {

        return getTagValue(name, defaultValue, SHAREPREFERENCE_TAG_DEFAULT);
    }

    /**
     * 根据标签判断是否是cookjar文件
     *
     * @param name
     * @param defaultValue
     * @param tag
     * @return
     */
    private static String getTagValue(String name, String defaultValue, int tag) {
        try {
            String result;
            if (tag == SHAREPREFERENCE_TAG_COOKIE) {
                //cookieJar sharepreference文件
                result = prefsCookie.getString(name, defaultValue);
            } else {
                result = prefs.getString(name, defaultValue);
            }

            /**
             *如果获取的值本身就是默认值，则说明是没有对应的key的值或者是原文，直接返回默认值，则不需要解密，否则解出来也是错误的数据
             */
            if (defaultValue != null && defaultValue.equals(result)) {
                return defaultValue;
            }
            return AESEncryptor.getInstance().decrypt(result);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 暂定以标签标识sharepreference文件
     *
     * @param name
     * @param defaultValue
     * @param tag          1：cookjar文件
     * @return
     */
    public static String getValueTag(String name, String defaultValue, int tag) {

        return getTagValue(name, defaultValue, tag);
    }


    public static String getValue(String name) {
        return getValue(name, "");
    }

    /**
     * 根据标签区分value值
     *
     * @param name
     * @param tag
     * @return
     */
    public static String getValue(String name, int tag) {
        return getTagValue(name, "", tag);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * 区分标签获得boolean值
     *
     * @param key
     * @param tag
     * @return
     */
    public static boolean getBoolean(String key, int tag) {
        if (tag == SHAREPREFERENCE_TAG_COOKIE) {
            return prefsCookie.getBoolean(key, false);

        } else {
            return getBoolean(key, false);
        }
    }

    public static int getInt(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getIntTag(String key, int tag) {
        if (tag == SHAREPREFERENCE_TAG_COOKIE) {
            return prefsCookie.getInt(key, 0);
        } else {
            return getInt(key, 0);
        }
    }

    public static long getLong(String key, long defaultValue) {
        return prefs.getLong(key, defaultValue);
    }

    /**
     * @param name
     * @param value
     * @return void
     * @Title: put
     * @description 保存到配置文件
     */
    public static void put(String name, Object value) {
        put(name, value, 0);
    }

    /**
     * 根据标签标识放在不同的文件中
     *
     * @param name
     * @param value
     * @param tag
     */
    public static void put(String name, Object value, int tag) {
        if (value != null) {
            SharedPreferences.Editor editor;
            if (tag == SHAREPREFERENCE_TAG_COOKIE) {
                editor = prefsCookie.edit();
            } else {
                editor = prefs.edit();
            }

            if (value.getClass() == String.class) {
                try {
                    String result = (String) value;
                    String aesValue = AESEncryptor.getInstance().encrypt(result);
                    editor.putString(name, aesValue);
                } catch (Exception ex) {
                    Log.e(TAG, ex.toString());
                }
            }

            if (value.getClass() == Float.class) {
                editor.putFloat(name, ((Float) value).floatValue());
            }

            if (value.getClass() == Integer.class) {
                editor.putInt(name, ((Integer) value).intValue());
            }

            if (value.getClass() == Long.class) {
                editor.putLong(name, ((Long) value).longValue());
            }

            if (value.getClass() == Boolean.class) {
                editor.putBoolean(name, (Boolean) value);
            }
            editor.commit();
        }
    }

    /**
     * 根据标签区分放在不同的文件中
     *
     * @param name
     * @param value
     * @param tag
     */
    public static void putValueByTag(String name, Object value, int tag) {
        put(name, value, tag);
    }


    public static void delete(String name) {
        deleteByTag(name, SHAREPREFERENCE_TAG_DEFAULT);
    }

    public static void delete(String name, int tag) {
        deleteByTag(name, tag);
    }

    /**
     * 根据标签删除tag值
     *
     * @param name
     * @param tag
     */
    public static void deleteByTag(String name, int tag) {
        SharedPreferences.Editor editor;
        if (tag == SHAREPREFERENCE_TAG_COOKIE) {
            editor = prefsCookie.edit();
        } else {
            editor = prefs.edit();
        }
        editor.remove(name);
        editor.commit();
    }

    public static void clear() {
        clear(SHAREPREFERENCE_TAG_DEFAULT);
    }

    public static void clear(int tag) {
        if (tag == SHAREPREFERENCE_TAG_COOKIE) {
            prefsCookie.edit().clear().commit();
        } else {
            prefs.edit().clear().commit();
        }
    }
}
