package com.rockstar.buspassvitran;

import android.content.Context;
import android.content.SharedPreferences;

public class CommonMethodsJava {

    public final static String PRE_NAME = "appname_prefs";
    public final static String USER_ID = "USER_ID";

    public static void setPreference(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PRE_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPrefrence(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PRE_NAME, 0);
        return prefs.getString(key, "DNF");
    }
}
