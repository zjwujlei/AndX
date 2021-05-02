package com.profound.andx.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

/**
 * 焦点变化时，底部线的动画实现
 * Created by yingjp on 2017/7/25.
 */

public class LoginCommonOnFocusChangeListener implements View.OnFocusChangeListener {
    private EditText mInputView;
    private View mLineView;
    private View mCleanView;

    public LoginCommonOnFocusChangeListener(EditText inputView, View lineView, View cleanView) {
        mInputView = inputView;
        mLineView = lineView;
        mCleanView = cleanView;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (!TextUtils.isEmpty(mInputView.getText().toString())) {
                mCleanView.setVisibility(View.VISIBLE);
            }
            LoginAnimationUtils.animLineOpen(mLineView, LoginAnimationUtils.ANIMATION_TIME_LINE);
        } else {
            mCleanView.setVisibility(View.GONE);
            LoginAnimationUtils.animLineClose(mLineView, LoginAnimationUtils.ANIMATION_TIME_LINE);
        }
    }
}
