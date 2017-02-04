package com.testapptwo.api.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 27.01.2017.
 */

public class UserInfo {

    @SerializedName("userId")
    @Expose
    private int userId;

    @SerializedName("login")
    @Expose
    private String name;

    @SerializedName("token")
    @Expose
    private String token;

    public UserInfo(String name, int userId, String token) {
        this.name = name;
        this.userId = userId;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "login='" + name + '\'' +
                ", userId=" + userId +
                ", token='" + token + '\'' +
                '}';
    }
}
