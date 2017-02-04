package com.testapptwo.features.main.photos.comments.addcomment;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.testapptwo.R;
import com.testapptwo.utils.android.views.SimpleTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 03.02.2017.
 */

public class AddDialogView {

    @BindView(R.id.edit_text)
    EditText editText;

    @BindView(R.id.send)
    TextView btnSend;

    @BindView(R.id.cancel)
    TextView btnCancel;

    private ViewActions viewActions;

    public AddDialogView(View view) {
        ButterKnife.bind(this, view);
        editText.addTextChangedListener(new CommentTextWatcher());
        btnSend.setOnClickListener(v -> send());
        btnCancel.setOnClickListener(v -> viewActions.cancel());
    }

    public void bind(ViewActions viewActions, String text) {
        this.viewActions = viewActions;
        editText.setText(text);
    }

    private void send() {
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(getContext(), R.string.empty_comment_text, Toast.LENGTH_SHORT).show();
            return;
        }
        viewActions.send();
    }

    private Context getContext() {
        return editText.getContext();
    }

    private class CommentTextWatcher extends SimpleTextWatcher {

        @Override
        public void onTextChanged(Editable s) {
            viewActions.setText(s.toString());
        }
    }
}
