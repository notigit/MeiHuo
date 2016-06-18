package com.xiaohua.meihuo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;

/**
 * Created by Administrator on 2016/3/11 0011.
 */
public class UserUtil {

    /**
     * 读取配置
     *
     * @param context
     * @param xmlName
     *            保存文件名
     * @param key
     *            字段
     * @return "none"
     */
    public static boolean ReadSharedPerference(Context context, String xmlName,
                                              String key) {
        SharedPreferences preferences = context.getSharedPreferences(xmlName,
                PreferenceActivity.MODE_PRIVATE);
        // 得到文件中的name标签值，第二个参数表示如果没有这个标签的话，返回的默认值
        boolean value = preferences.getBoolean(key, false);
        // 提示用户读取成功
        return value;
    }

    /**
     * 保存配置
     *
     * @param context
     * @param xmlName
     *            保存文件名
     * @param key
     *            字段
     * @param value
     *            值
     */
    public static void SharedPerferencesCreat(Context context, String xmlName,
                                              String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(xmlName,
                PreferenceActivity.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    /**
     * 清空数据
     *
     * @param context
     * @param xmlName
     */
    public static void clearPerfssences(Context context, String xmlName) {

        SharedPreferences preferences = context.getSharedPreferences(xmlName,
                PreferenceActivity.MODE_WORLD_READABLE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.commit();

    }

}
