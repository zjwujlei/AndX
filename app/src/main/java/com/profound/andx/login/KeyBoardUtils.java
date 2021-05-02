package com.profound.andx.login;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Description: 软键盘相关的工具类
 * Author     : xq
 * Date       : 2017/6/2
 */

public class KeyBoardUtils {

    private static final String TAG = KeyBoardUtils.class.getSimpleName();

    /**
     * 打开软键盘
     * 魅族可能会有问题
     *
     * @param mEditText
     * @param mContext
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        try {
            InputMethodManager imm = (InputMethodManager) mContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY);
        }catch (Exception e){
//            LogUtil.logException(TAG, e.getMessage(), e);
        }
        
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     * @param mContext
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        try {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        }catch (Exception e){
//            LogUtil.logException(TAG, e.getMessage(), e);
        }

    }


    /**
     * des:隐藏软键盘,这种方式参数为activity
     *
     * @param activity
     */
    public static void hideInputForce(Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null) {
            return;
        }
        try {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getWindowToken(), 0);
        } catch (Exception e) {
//            LogUtil.logException(TAG, e.getMessage(), e);
        }
    }

    /**
     * 打开键盘
     **/
    public static void showInput(Context context, View view) {
        try {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                view.requestFocus();
                imm.showSoftInput(view, 0);
            }
        }catch (Exception e){
//            LogUtil.logException(TAG, e.getMessage(), e);
        }

    }

    /**
     * 强制打开键盘
     * @param context
     * @param view
     */
    public static void ForceShowInput(Context context, View view){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }catch (Exception e){
//            LogUtil.logException(TAG, e.getMessage(), e);
        }

    }
}
