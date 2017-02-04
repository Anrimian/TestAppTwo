package com.testapptwo.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 27.01.2017.
 */

public class LoginRequestInfo {

    @SerializedName("login")
    @Expose
    private final String login;

    @SerializedName("password")
    @Expose
    private final String password;

    public LoginRequestInfo(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
