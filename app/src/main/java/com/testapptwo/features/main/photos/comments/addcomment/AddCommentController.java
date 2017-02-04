package com.testapptwo.features.main.photos.comments.addcomment;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created on 30.01.2017.
 */

public class AddCommentController implements ViewActions, ModelObserver {

    private AddDialogView view;
    private AddCommentModel model;

    private Dialog dialog;
    private FragmentActivity activity;

    public AddCommentController(FragmentActivity activity,
                                Dialog dialog,
                                AddDialogView view,
                                AddCommentModel model) {
        this.activity = activity;
        this.dialog = dialog;
        this.view = view;
        this.model = model;
    }

    public void bind() {
        view.bind(this, model.getText());
        model.registerObserver(this);
    }

    public void unbind() {
        model.unregisterObserver(this);
    }

    @Override
    public void send() {
        model.send();
    }

    @Override
    public void cancel() {
        dialog.dismiss();
    }

    @Override
    public void onComplete() {
        dialog.dismiss();
    }

    @Override
    public void onError() {
        Toast.makeText(activity, model.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setText(String text) {
        model.setText(text);
    }


}
