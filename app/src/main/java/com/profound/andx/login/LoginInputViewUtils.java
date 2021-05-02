package com.profound.andx.login;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.profound.andx.R;


/**
 * 登录模块输入框工具类
 * Created by yingjp on 2017/7/25.
 */

public class LoginInputViewUtils {
    public static final String TAG = "LoginInputViewUtils";

    private LoginInputViewUtils() {
        //nothing
    }

    /**
     * 输入框的监听事件处理
     *
     * @param inputView
     * @param cleanView
     * @param lineView
     */
    public static void inputViewBindListener(final EditText inputView, final View cleanView, final View lineView) {
        if(inputView == null || cleanView == null || lineView == null){
            return;
        }
        inputView.setOnFocusChangeListener(
                new LoginCommonOnFocusChangeListener(inputView, lineView, cleanView));
        inputView.addTextChangedListener(new LoginCommonTextWatcher(inputView, cleanView));
        cleanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputView.setText("");
            }
        });
    }

    public static void showOrHintPasswordView(final EditText inputView, final ImageView clickView) {
        if(inputView == null || clickView == null){
            return;
        }
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isShow = false;
                Object tag = v.getTag();
                if (tag != null && tag instanceof Boolean){
                    isShow = (boolean) v.getTag();
                }
                if (isShow) {
                    //隐藏密码
                    inputView.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    inputView.setSelection(inputView.getText().length());
                    v.setTag(false);
                    clickView.setImageResource(R.drawable.gh_cm_input_password_hint);
                } else {
                    //显示密码
                    inputView.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    inputView.setSelection(inputView.getText().length());
                    v.setTag(true);
                    clickView.setImageResource(R.drawable.gh_cm_input_password_show);
                }
            }
        });
    }

    /**
     * 设置键盘右下角按键处理
     *
     * @param editText
     * @param foucsView
     */
    public static void dispatchOnEditorAction(Activity activity, EditText editText, View foucsView) {
        if(activity == null || editText == null || foucsView == null){
            return;
        }
        editText.setOnEditorActionListener(new LoginOnEditorActionListener(activity, foucsView));
    }

    /**
     * 动画结束后让输入框获得焦点，并弹出键盘
     *
     * @param editText
     */
    public static void showKeyBoardAfterAnim(Activity activity, EditText editText, View lineView,
                                             AnimatorSet animatorSet) {
        if(activity == null || editText == null  || animatorSet == null){
            return;
        }
        if(lineView != null){
            lineView.setScaleX(0);
        }
        animatorSet.addListener(new LoginTranslationAnimatorListener(activity, editText));
    }

    /**
     * 让输入框获得焦点，并弹出键盘
     *
     * @param editText
     */
    public static void showKeyBoard(Activity activity, EditText editText) {
        if(activity == null || editText == null){
            return;
        }
        editText.requestFocus();
        //如果键盘是关闭的，则获取焦点不能自动弹出键盘，需要手动调用
        KeyBoardUtils.openKeybord(editText, activity);
    }

    /**
     * 让输入框获得焦点，并弹出键盘
     *
     * @param focusView
     */
    public static void hideKeyBoard(Activity activity, View focusView) {
        if(activity == null || focusView == null){
            return;
        }
        //有些键盘不会自动关闭，需要手动调用
        KeyBoardUtils.hideInputForce(activity);
        focusView.requestFocus();
    }
}
