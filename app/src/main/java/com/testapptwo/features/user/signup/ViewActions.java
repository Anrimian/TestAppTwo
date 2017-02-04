package com.testapptwo.features.user.signup;

/**
 * Created on 30.01.2017.
 */

public interface ViewActions {

    void setLogin(String login);
    void setPassword(String password);
    void setRepeatPassword(String repeatPassword);

    void submit();
}
