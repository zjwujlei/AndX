package com.profound.andx.login;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

/**
 * 键盘右下角按键监听
 * Created by yingjp on 2017/8/2.
 */

public class LoginOnEditorActionListener implements TextView.OnEditorActionListener {
    private View mFoucsView;
    private Activity mActivity;

    public LoginOnEditorActionListener(Activity activity, View foucsView) {
        mFoucsView = foucsView;
        mActivity = activity;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        /*判断是否是“down”键*/
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            mFoucsView.requestFocus();
            KeyBoardUtils.hideInputForce(mActivity);
        }
        return false;
    }
}
