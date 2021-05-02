package com.profound.andx.login;


import android.view.View;
import android.view.animation.Animation;

/**
 * 登录模块进入动画
 * Created by yingjp on 2017/7/21.
 */

public class LoginInAnimationListener implements Animation.AnimationListener {

    private View mView;

    public LoginInAnimationListener(View view) {
        mView = view;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (mView != null) {
            mView.setAlpha(1);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (mView != null) {
            mView.setAlpha(1);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        //do nothing
    }
}
