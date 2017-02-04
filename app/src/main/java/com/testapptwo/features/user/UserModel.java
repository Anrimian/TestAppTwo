package com.testapptwo.features.user;

import android.content.SharedPreferences;

import com.testapptwo.api.data.UserInfo;
import com.testapptwo.features.Preferences;

/**
 * Created on 30.01.2017.
 */

public class UserModel {

    private volatile static UserModel instance;

    public static UserModel getInstance() {
        if (instance == null) {
            synchronized (UserModel.class) {
                if (instance == null) {
                    instance = new UserModel();
                }
            }
        }
        return instance;
    }

    private UserInfo userInfo;

    private UserModel() {
        loadUserInfo();
    }

    public static boolean isLogin() {
        return getInstance().getUserInfo() != null;
    }

    public static String getToken() {
        UserInfo userInfo = getInstance().getUserInfo();
        if (userInfo != null) {
            return userInfo.getToken();
        }
        return null;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        saveUserInfo();
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    private static final String USER_NAME = "user_name";
    private static final String USER_TOKEN = "user_token";
    private static final String USER_ID = "user_id";

    private void saveUserInfo() {
        SharedPreferences preferences = Preferences.get();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_NAME, userInfo.getName());
        editor.putInt(USER_ID, userInfo.getUserId());
        editor.putString(USER_TOKEN, userInfo.getToken());
        editor.apply();
    }

    private void loadUserInfo() {
        SharedPreferences preferences = Preferences.get();
        String name = preferences.getString(USER_NAME, null);
        String token = preferences.getString(USER_TOKEN, null);
        int id = preferences.getInt(USER_ID, -1);
        if (name != null) {
            userInfo = new UserInfo(name, id, token);
        }
    }
}
