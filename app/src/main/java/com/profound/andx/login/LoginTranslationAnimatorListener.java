package com.profound.andx.login;

import android.animation.Animator;
import android.app.Activity;
import android.widget.EditText;

/**
 * Created by yingjp on 2017/8/31.
 */

public class LoginTranslationAnimatorListener implements Animator.AnimatorListener {
    private Activity mActivity;
    private EditText mFocusView;

    public LoginTranslationAnimatorListener(Activity activity, EditText focusView) {
        mActivity = activity;
        mFocusView = focusView;
    }

    @Override
    public void onAnimationStart(Animator animation) {
        //do nothing
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        LoginInputViewUtils.showKeyBoard(mActivity, mFocusView);
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        //do nothing
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        //do nothing
    }
}
