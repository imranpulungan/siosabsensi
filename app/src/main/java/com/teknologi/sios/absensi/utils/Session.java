package com.teknologi.sios.absensi.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.teknologi.sios.absensi.activity.LoginActivity;


public class Session {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    private final int PRIVATE_MODE = 0;

    private final String PREF_NAME = "ingo.session";
    private final String KEY_AUTH_TOKEN = "ingo.auth";
    private final String KEY_NOTIFICATION_ID = "notification.id";

    private Session(Context c) {
        this.context = c;
        pref = c.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static Session with(Context c) {
        return new Session(c);
    }

    public void createLoginSession(String token) {
        editor.putString(KEY_AUTH_TOKEN, token);
        editor.commit();
    }

    public boolean isSignIn() {
        return TextUtils.isEmpty(pref.getString(KEY_AUTH_TOKEN, "")) ? false : true;
    }

    public String getToken() {
        return pref.getString(KEY_AUTH_TOKEN, "");
    }

    public void clearLoginSession() {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public int getNotificationid() {
        return pref.getInt(KEY_NOTIFICATION_ID, 0);
    }

    public void setNotificationid() {
        /**
         * set notification id
         * if  notification id >= 0, reset notification id
         * else increment notification id
         */
        int _notificationid = pref.getInt(KEY_NOTIFICATION_ID, 0);
        if (_notificationid >= 20) {
            _notificationid = 0;
        } else {
            _notificationid += 1;
        }
        editor.putInt(KEY_NOTIFICATION_ID, _notificationid);
        editor.commit();
    }
}
