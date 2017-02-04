package com.testapptwo.features.user.signup;

import android.content.Context;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.testapptwo.R;
import com.testapptwo.utils.android.views.SimpleTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 30.01.2017.
 */

public class SignUpView {

    @BindView(R.id.et_login)
    EditText etLogin;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.repeat_password)
    EditText etRepeatPassword;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.tv_error)
    TextView tvError;

    private ViewActions viewActions;

    public SignUpView(View view) {
        ButterKnife.bind(this, view);
        etLogin.addTextChangedListener(new LoginTextWatcher());
        etLogin.setOnEditorActionListener(new LoginEditorListener());
        etPassword.addTextChangedListener(new PasswordTextWatcher());
        etPassword.setOnEditorActionListener(new PasswordEditorListener());
        etRepeatPassword.addTextChangedListener(new RepeatPasswordTextWatcher());
        etRepeatPassword.setOnEditorActionListener(new RepeatPasswordEditorListener());
        btnSubmit.setOnClickListener(v -> submit());
    }

    public void bindView(ViewActions viewActions, String login, String password, String repeatPassword) {
        this.viewActions = viewActions;
        etLogin.setText(login);
        etPassword.setText(password);
        etRepeatPassword.setText(repeatPassword);
    }

    public void showWait() {
        progressBar.setVisibility(View.INVISIBLE);
        tvError.setVisibility(View.INVISIBLE);
        setEnabled(true);
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.INVISIBLE);
        setEnabled(false);
    }

    public void showError(int errorMessageId) {
        progressBar.setVisibility(View.INVISIBLE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(errorMessageId);
        setEnabled(true);
    }

    private void setEnabled(boolean enabled) {
        etLogin.setEnabled(enabled);
        etPassword.setEnabled(enabled);
        etRepeatPassword.setEnabled(enabled);
        btnSubmit.setEnabled(enabled);
    }

    private void submit() {
        final String userName = etLogin.getText().toString();
        if (userName.isEmpty()) {
            etLogin.setError(getContext().getString(R.string.empty_login_error));
            return;
        }
        final String password = etPassword.getText().toString();
        if (password.isEmpty()) {
            etPassword.setError(getContext().getString(R.string.empty_password_error));
            return;
        }
        final String repeatPassword = etRepeatPassword.getText().toString();
        if (!repeatPassword.equals(password)) {
            etRepeatPassword.setError(getContext().getString(R.string.passwords_not_equals));
            return;
        }
        viewActions.submit();
    }

    private Context getContext() {
        return etLogin.getContext();
    }

    private class LoginTextWatcher extends SimpleTextWatcher {
        @Override
        public void onTextChanged(Editable s) {
            viewActions.setLogin(s.toString());
        }
    }

    private class PasswordTextWatcher extends SimpleTextWatcher {
        @Override
        public void onTextChanged(Editable s) {
            viewActions.setPassword(s.toString());
        }
    }

    private class RepeatPasswordTextWatcher extends SimpleTextWatcher {
        @Override
        public void onTextChanged(Editable s) {
            viewActions.setRepeatPassword(s.toString());
        }
    }

    private class LoginEditorListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            etPassword.requestFocus();
            return true;
        }
    }

    private class PasswordEditorListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            etRepeatPassword.requestFocus();
            return true;
        }
    }

    private class RepeatPasswordEditorListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            submit();
            return true;
        }
    }
}
