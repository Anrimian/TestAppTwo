package com.testapptwo.utils.android.views;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created on 30.01.2017.
 */

public abstract class SimpleTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        onTextChanged(s);
    }

    public abstract void onTextChanged(Editable s);
}
