package com.testapptwo.features.user.signin;

import android.support.v4.app.FragmentActivity;

import com.testapptwo.R;
import com.testapptwo.features.main.MainPageFragment;
import com.testapptwo.features.user.state.StateObserver;

/**
 * Created on 30.01.2017.
 */

public class SignInController implements ViewActions, StateObserver {

    private SignInView signInView;
    private SignInModel signInModel;

    private FragmentActivity activity;

    public SignInController(FragmentActivity activity, SignInView signInView, SignInModel signInModel) {
        this.activity = activity;
        this.signInView = signInView;
        this.signInModel = signInModel;
    }

    public void bind() {
        signInView.bindView(this, signInModel.getLogin(), signInModel.getPassword());
        signInModel.registerObserver(this);
    }

    public void unbind() {
        signInModel.unregisterObserver(this);
    }

    @Override
    public void setLogin(String login) {
        signInModel.setLogin(login);
    }

    @Override
    public void setPassword(String password) {
        signInModel.setPassword(password);
    }

    @Override
    public void submit() {
        signInModel.signIn();
    }

    @Override
    public void onWait() {
        signInView.showWait();
    }

    @Override
    public void onProgress() {
        signInView.showProgress();
    }

    @Override
    public void onError() {
        signInView.showError(signInModel.getErrorMessageId());
    }

    @Override
    public void onComplete() {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_activity_container, new MainPageFragment())
                .commit();
    }
}
