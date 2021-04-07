package com.lzaromskis.camerasecurity.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private static SharedPreferences _prefs;

    public static final String HOSTNAME = "hostname";
    public static final String SECRET = "secret";
    public static final String IS_SECRET_VALID = "is_secret_valid";

    private SharedPrefs() {

    }

    public static void init(Context context) {
        if (_prefs == null)
            _prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static String readString(String key) {
        return _prefs.getString(key, "");
    }

    public static void writeString(String key, String value) {
        SharedPreferences.Editor editor = _prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static boolean readBoolean(String key) {
        return _prefs.getBoolean(key, false);
    }

    public static void writeBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = _prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static int readInteger(String key) {
        return _prefs.getInt(key, 0);
    }

    public static void writeInteger(String key, int value) {
        SharedPreferences.Editor editor = _prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }
}
