package com.profound.andx.login;

import android.view.View;
import android.view.animation.Animation;

/**
 * 登录模块退出动画
 * Created by yingjp on 2017/7/21.
 */

public class LoginOutAnimationListener implements Animation.AnimationListener {

    private View mView;

    public LoginOutAnimationListener(View view) {
        mView = view;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        //do nothing
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (mView != null) {
            mView.setAlpha(0);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        //do nothing
    }
}
