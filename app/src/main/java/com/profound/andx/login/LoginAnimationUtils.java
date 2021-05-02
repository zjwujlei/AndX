package com.profound.andx.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.profound.andx.R;


/**
 * Created by yingjp on 2017/7/20.
 */

public class LoginAnimationUtils {
    public static final String TAG = "LoginAnimationUtils";
    public static final int ANIMATION_TIME_LOGIN_HOME = 460;
    public static final int ANIMATION_TIME_DELAY_LOGIN_HOME = 140;
    public static final int ANIMATION_TIME_TOGGLE_LEFT_AND_RIGHT = 440;
    public static final int ANIMATION_TIME_LINE = 400;
    public static final int ANIMATION_TIME_LOAD_SUCCESS = 400;

    private LoginAnimationUtils() {
        //nothing
    }

    /**
     * 从下到显示位置，从透明到显示
     *
     * @return AnimationSet
     */
    public static AnimationSet inAnimUpTranslateAndAlpha(final View view, int duration) {
        final AnimationSet animSet = getCommonAnimationSet(duration);
        animSet.setAnimationListener(new LoginInAnimationListener(view));
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        animSet.addAnimation(translate);
        animSet.addAnimation(alpha);
        runAnimation(view, animSet);
        return animSet;
    }

    /**
     * 从显示位置往上，从显示到透明
     *
     * @return AnimationSet
     */
    public static AnimationSet outAnimUpTranslateAndAlpha(final View view, int duration) {
        final AnimationSet animSet = getCommonAnimationSet(duration);
        animSet.setAnimationListener(new LoginOutAnimationListener(view));
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        animSet.addAnimation(translate);
        animSet.addAnimation(alpha);
        runAnimation(view, animSet);
        return animSet;
    }

    /**
     * 从上到显示位置，从透明到显示
     *
     * @return AnimationSet
     */
    public static AnimationSet inAnimDownTranslateAndAlpha(final View view, int duration) {
        final AnimationSet animSet = getCommonAnimationSet(duration);
        animSet.setAnimationListener(new LoginInAnimationListener(view));
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        animSet.addAnimation(translate);
        animSet.addAnimation(alpha);
        runAnimation(view, animSet);
        return animSet;
    }

    /**
     * 从显示位置往下，从显示到透明
     *
     * @return AnimationSet
     */
    public static AnimationSet outAnimDownTranslateAndAlpha(final View view, int duration) {
        final AnimationSet animSet = getCommonAnimationSet(duration);
        animSet.setAnimationListener(new LoginOutAnimationListener(view));
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        animSet.addAnimation(translate);
        animSet.addAnimation(alpha);
        runAnimation(view, animSet);
        return animSet;
    }

    /**
     * 从透明到显示
     *
     * @return AnimationSet
     */
    public static AnimationSet inAnimAlpha(final View view, int duration) {
        final AnimationSet animSet = getCommonAnimationSet(duration);
        animSet.setAnimationListener(new LoginInAnimationListener(view));
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        animSet.addAnimation(alpha);
        runAnimation(view, animSet);
        return animSet;
    }

    /**
     * 从显示到透明
     *
     * @return AnimationSet
     */
    public static AnimationSet outAnimAlpha(final View view, int duration) {
        final AnimationSet animSet = getCommonAnimationSet(duration);
        animSet.setAnimationListener(new LoginOutAnimationListener(view));
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        animSet.addAnimation(alpha);
        runAnimation(view, animSet);
        return animSet;
    }

    /**
     * 获得公共的AnimationSet
     *
     * @param duration
     * @return
     */
    public static AnimationSet getCommonAnimationSet(int duration) {
        final AnimationSet animSet = new AnimationSet(true);
        animSet.setFillAfter(true);
        animSet.setDuration(duration);
        return animSet;
    }

    /**
     * 运行AnimationSet
     *
     * @param view
     * @param animSet
     */
    public static void runAnimation(final View view, final AnimationSet animSet) {
        try {
            view.startAnimation(animSet);
        } catch (Exception e) {
//            LogUtil.logException(TAG, e.getMessage(), e);
        }
    }

    /**
     * 视图从右侧滑入动画
     *
     * @param view
     * @param duration
     */
    public static AnimatorSet inAnimTranslateFromRight(final View view, float distance, int duration) {
        ObjectAnimator oalinear1 = ObjectAnimator.ofFloat(view, "translationX", distance, view.getTranslationX());
        AnimatorSet aSet = new AnimatorSet();
        aSet.play(oalinear1);
        aSet.setDuration(duration);
        aSet.start();
        return aSet;
    }

    /**
     * 从左视图切换到右视图的动画（从右往左移）
     *
     * @param leftView
     * @param rightView
     * @param duration
     */
    public static AnimatorSet viewChangeAnimLeftToRight(final View leftView, final View rightView, float distance, int duration) {
        ObjectAnimator leftAnim = ObjectAnimator.ofFloat(leftView, "translationX", 0, -distance);
        ObjectAnimator rightAnim = ObjectAnimator.ofFloat(rightView, "translationX", distance, 0);
        AnimatorSet aSet = new AnimatorSet();
        aSet.play(leftAnim).with(rightAnim);
        aSet.setDuration(duration);
        aSet.start();
        return aSet;
    }

    /**
     * 从右视图切换到左视图的动画（从左往右移）
     *
     * @param leftView
     * @param rightView
     * @param duration
     */
    public static AnimatorSet viewChangeAnimRightToLeft(final View leftView, final View rightView, float distance, int duration) {
        ObjectAnimator rightAnim = ObjectAnimator.ofFloat(rightView, "translationX", 0, distance);
        ObjectAnimator leftAnim = ObjectAnimator.ofFloat(leftView, "translationX", -distance, 0);
        AnimatorSet aSet = new AnimatorSet();
        aSet.play(leftAnim).with(rightAnim);
        aSet.setDuration(duration);
        aSet.start();
        return aSet;
    }

    /**
     * 输入框底部的线打开动画
     *
     * @param lineView
     * @param duration
     */
    public static void animLineOpen(final View lineView, int duration) {
        lineView.setScaleX(1);
        AnimationSet animSet = getCommonAnimationSet(duration);
        ScaleAnimation scaleAni = new ScaleAnimation(0, 1, 1, 1);
        animSet.addAnimation(scaleAni);
        runAnimation(lineView, animSet);
    }

    /**
     * 输入框底部的线关闭动画
     *
     * @param lineView
     * @param duration
     */
    public static void animLineClose(final View lineView, int duration) {
        lineView.setScaleX(1);
        AnimationSet animSet = getCommonAnimationSet(duration);
        ScaleAnimation scaleAni = new ScaleAnimation(1, 0, 1, 1);
        animSet.addAnimation(scaleAni);
        runAnimation(lineView, animSet);
    }

    /**
     * 启动加载中转圈动画
     *
     * @param view 动画显示的view
     */
    public static void startLodingAnimation(final ImageView view) {
        try {
            view.setVisibility(View.VISIBLE);
            view.setImageResource(R.drawable.gh_cm_loading_animation);
            AnimationDrawable animationDrawable = (AnimationDrawable) view.getDrawable();
            animationDrawable.start();
        } catch (Exception e) {
//            LogUtil.logException(TAG, e.getMessage(), e);
        }
    }

    /**
     * 停止加载中转圈动画
     *
     * @param view 动画显示的view
     */
    public static void stopLodingAnimation(final ImageView view) {
        try {
            view.setVisibility(View.GONE);
            AnimationDrawable animationDrawable = (AnimationDrawable) view.getDrawable();
            animationDrawable.stop();
        } catch (Exception e) {
//            LogUtil.logException(TAG, e.getMessage(), e);
        }
    }

    /**
     * 启动成功后的打勾动画
     *
     * @param view 动画显示的view
     */
    public static void startSuccessAnimation(final ImageView view) {
        try {
            view.setVisibility(View.VISIBLE);
            view.setImageResource(R.drawable.gh_cm_load_finish_animation);
            AnimationDrawable animationDrawable = (AnimationDrawable) view.getDrawable();
            animationDrawable.start();
        } catch (Exception e) {
//            LogUtil.logException(TAG, e.getMessage(), e);
        }
    }

}
