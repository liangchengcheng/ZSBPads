package com.lcc.utils;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

public abstract class TextWatcher implements android.text.TextWatcher {

    public String getEditString() {
        EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            return editText.getText().toString();
        }
        return null;
    }

    private TextInputLayout textInputLayout;

    public TextWatcher(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
