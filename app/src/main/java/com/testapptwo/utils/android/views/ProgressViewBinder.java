package com.testapptwo.utils.android.views;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.testapptwo.R;

/**
 * Created on 21.02.2016.
 */
public class ProgressViewBinder {

    private ProgressBar progressBar;
    private TextView tvMessage;
    private View btnTryAgain;

    private String link;

    public ProgressViewBinder(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        tvMessage = (TextView) view.findViewById(R.id.tv_message);
        btnTryAgain = view.findViewById(R.id.btn_action);
    }

    public ProgressViewBinder(ProgressBar progressBar, TextView tvMessage, View btnTryAgain) {
        this.progressBar = progressBar;
        this.tvMessage = tvMessage;
        this.btnTryAgain = btnTryAgain;
    }

    public void setTryAgainButtonOnClickListener(View.OnClickListener listener) {
        btnTryAgain.setOnClickListener(listener);
    }

    public void hideAll() {
        progressBar.setVisibility(View.INVISIBLE);
        tvMessage.setVisibility(View.INVISIBLE);
        btnTryAgain.setVisibility(View.INVISIBLE);
    }

    public void showMessage(int messageId, boolean showTryAgainButton) {
        String message = progressBar.getContext().getString(messageId);
        showMessage(message, showTryAgainButton);
    }

    public void showMessage(String message, boolean showTryAgainButton) {
        progressBar.setVisibility(View.INVISIBLE);
        tvMessage.setVisibility(View.VISIBLE);
        if (message != null) {
            tvMessage.setText(message);
        }
        if (showTryAgainButton) {
            btnTryAgain.setVisibility(View.VISIBLE);
        } else {
            btnTryAgain.setVisibility(View.INVISIBLE);
        }
    }

    public void showProgress() {
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(View.INVISIBLE);
        btnTryAgain.setVisibility(View.INVISIBLE);
    }
}
