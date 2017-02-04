package com.testapptwo.features.user.signup;

import android.support.v4.app.FragmentActivity;

import com.testapptwo.R;
import com.testapptwo.features.main.MainPageFragment;
import com.testapptwo.features.user.state.StateObserver;

/**
 * Created on 30.01.2017.
 */

public class SignUpController implements ViewActions, StateObserver {

    private SignUpView view;
    private SignUpModel model;

    private FragmentActivity activity;

    public SignUpController(FragmentActivity activity, SignUpView view, SignUpModel model) {
        this.activity = activity;
        this.view = view;
        this.model = model;
    }

    public void bind() {
        view.bindView(this, model.getLogin(), model.getPassword(), model.getRepeatPassword());
        model.registerObserver(this);
    }

    public void unbind() {
        model.unregisterObserver(this);
    }

    @Override
    public void setLogin(String login) {
        model.setLogin(login);
    }

    @Override
    public void setPassword(String password) {
        model.setPassword(password);
    }

    @Override
    public void setRepeatPassword(String repeatPassword) {
        model.setRepeatPassword(repeatPassword);
    }

    @Override
    public void submit() {
        model.signUp();
    }

    @Override
    public void onWait() {
        view.showWait();
    }

    @Override
    public void onProgress() {
        view.showProgress();
    }

    @Override
    public void onError() {
        view.showError(model.getErrorMessageId());
    }

    @Override
    public void onComplete() {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_activity_container, new MainPageFragment())
                .commit();
    }
}
