package com.quenDel.multiUtils.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Natraj3 on 7/1/2017.
 */

public class AppPreference {

    private static final String APP_PREFENCE = "quenDel_app_preferences";

    public static void setStringPreference(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void setIntegerPreference(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getIntegerPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    public static void setBooleanPreference(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBooleanPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void clearAllPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

}


