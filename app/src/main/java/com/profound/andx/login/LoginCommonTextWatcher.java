package com.profound.andx.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * 增加
 * Created by yingjp on 2017/7/25.
 */

public class LoginCommonTextWatcher implements TextWatcher {
    private View mCleanView;
    private EditText mInputView;

    public LoginCommonTextWatcher(EditText inputView, View cleanView) {
        mInputView = inputView;
        mCleanView = cleanView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //do nothing
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0 && mInputView.hasFocus()) {
            mCleanView.setVisibility(View.VISIBLE);
            mInputView.setTag(s.toString());
        } else {
            mCleanView.setVisibility(View.GONE);
        }
    }
}
