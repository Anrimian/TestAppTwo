package com.testapptwo.features.user.signin;

import com.testapptwo.api.ApiWrapper;
import com.testapptwo.api.request.LoginRequestInfo;
import com.testapptwo.api.response.ResponseInfo;
import com.testapptwo.features.user.UserModel;
import com.testapptwo.features.user.state.State;
import com.testapptwo.features.user.state.StateObservable;
import com.testapptwo.features.user.state.StateObserver;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.testapptwo.api.ErrorMessages.selectErrorMessageId;

/**
 * Created on 30.01.2017.
 */

public class SignInModel {

    private State state = State.WAIT;
    private StateObservable stateObservable = new StateObservable();
    private int errorMessageId;

    private String login;
    private String password;

    public void signIn() {
        showState(State.PROGRESS);
        ApiWrapper.getApi()
                .signIn(new LoginRequestInfo(login, password))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseInfo::getData)
                .subscribe(userInfo -> {
                    UserModel.getInstance().setUserInfo(userInfo);
                    showState(State.COMPLETE);
                }, throwable -> {
                    errorMessageId = selectErrorMessageId(throwable);
                    showState(State.ERROR);
                });
    }

    public void registerObserver(StateObserver observer) {
        state.showState(observer);
        stateObservable.registerObserver(observer);
    }

    public void unregisterObserver(StateObserver observer) {
        stateObservable.unregisterObserver(observer);
    }

    private void showState(State state) {
        this.state = state;
        stateObservable.showState(state);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getErrorMessageId() {
        return errorMessageId;
    }
}
